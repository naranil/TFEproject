package de.tudarmstadt.ukp.dkpro.argumentation.classification.preprocessing;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceChain;
import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.semantics.type.SemanticArgument;
import de.tudarmstadt.ukp.dkpro.core.api.semantics.type.SemanticPredicate;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.ROOT;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.berkeleyparser.BerkeleyParser;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpSemanticRoleLabeler;
import de.tudarmstadt.ukp.dkpro.core.io.penntree.PennTreeUtils;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.languagetool.LanguageToolSegmenter;
import de.tudarmstadt.ukp.dkpro.core.maltparser.MaltParser;
import de.tudarmstadt.ukp.dkpro.core.matetools.MateLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpNameFinder;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordsentiment.SentimentAnnotation;
import de.tudarmstadt.ukp.dkpro.core.stanfordsentiment.StanfordSentimentAnnotator;

/**
 *
 * @author Anil Narassiguin
 * This class is a main class that generates a xmi file from a text file with those
 * information: Tokens, sentences, constituent, pos, dependency, stem, lemma, named entity,
 * coreference chain, coreference link, semantic predicate, semantic argument, sentiment
 * coefficients.
 *
 */
public class XmiGenerator
{
    public static void main(String[] args) throws Exception
    {
        // Assemble pipeline
        JCasIterable pipeline = new JCasIterable(
                // Read input
                createReaderDescription(TextReader.class,
                        TextReader.PARAM_SOURCE_LOCATION, "src/main/resources/gold_data",
                        TextReader.PARAM_LANGUAGE, "en",
                        TextReader.PARAM_PATTERNS,TextReader.INCLUDE_PREFIX + "*.txt"),
                // Token, Sentence
                createEngineDescription(LanguageToolSegmenter.class),
                // Constituent
                createEngineDescription(BerkeleyParser.class, BerkeleyParser.PARAM_WRITE_POS, false),
                // Pos
                createEngineDescription(StanfordPosTagger.class),
                // Dependency
                createEngineDescription(MaltParser.class),
                // Stem
                createEngineDescription(SnowballStemmer.class),
                // Lemma
                createEngineDescription(MateLemmatizer.class),
                // NamedEntity
                createEngineDescription(OpenNlpNameFinder.class,
                        OpenNlpNameFinder.PARAM_VARIANT, "person"),
                createEngineDescription(OpenNlpNameFinder.class,
                        OpenNlpNameFinder.PARAM_VARIANT, "organization"),
                // CoreferenceChain, CoreferenceLink
                createEngineDescription(StanfordCoreferenceResolver.class),
                // SemanticPredicate, SemanticArgument
                createEngineDescription(ClearNlpSemanticRoleLabeler.class),
                // Sentiment
                createEngineDescription(StanfordSentimentAnnotator.class),
                // Write output as XMI for inspection in UIMA CAS Editor
                createEngineDescription(XmiWriter.class,
                        XmiWriter.PARAM_TARGET_LOCATION, "src/main/resources/gold_data_xmi",
                        XmiWriter.PARAM_TYPE_SYSTEM_FILE, "TypeSystem.xml"));

        for (JCas jcas : pipeline) {
            for (Sentence sentence : select(jcas, Sentence.class)) {
                System.out.printf("%n== Sentence ==%n");
                System.out.printf("  %-16s %-10s %-10s %-10s %-10s %n", "TOKEN", "LEMMA", "STEM",
                        "CPOS", "POS");
                for (Token token : selectCovered(Token.class, sentence)) {
                    System.out.printf("  %-16s %-10s %-10s %-10s %-10s %n",
                            token.getCoveredText(),
                            token.getLemma() != null ? token.getLemma().getValue() : "",
                            token.getStem() != null ? token.getStem().getValue() : "",
                            token.getPos().getClass().getSimpleName(),
                            token.getPos().getPosValue());
                }

                System.out.printf("%n  -- Named Entities --%n");
                System.out.printf("  %-16s %-10s%n", "ENTITY", "TOKENS");
                for (NamedEntity ne : selectCovered(NamedEntity.class, sentence)) {
                    System.out.printf("  %-16s %-10s%n", ne.getValue(), ne.getCoveredText());
                }

                System.out.printf("%n  -- Constituents --%n");
                for (ROOT root : selectCovered(ROOT.class, sentence)) {
                    System.out.printf("  %s%n",
                            PennTreeUtils.toPennTree(PennTreeUtils.convertPennTree(root)));
                }

                System.out.printf("%n  -- Dependency relations --%n");
                System.out.printf("  %-16s %-10s %-10s %n", "TOKEN", "DEPREL", "DEP", "GOV");
                for (Dependency dep : selectCovered(Dependency.class, sentence)) {
                    System.out.printf("  %-16s %-10s %-10s %n",
                            dep.getDependencyType(),
                            dep.getDependent().getCoveredText(),
                            dep.getGovernor().getCoveredText());
                }

                System.out.printf("%n  -- Semantic structure --%n");
                for (SemanticPredicate pred : selectCovered(SemanticPredicate.class, sentence)) {
                    System.out.printf("  %-16s %-10s", pred.getCoveredText(), pred.getCategory());
                    for (SemanticArgument arg : select(pred.getArguments(), SemanticArgument.class)) {
                        System.out.printf("\t%s:%s", arg.getRole(), arg.getCoveredText());
                    }
                    System.out.printf("%n");
                }
            }

            System.out.printf("%n== Coreference chains (for the whole document) ==%n");
            for (CoreferenceChain chain : select(jcas, CoreferenceChain.class)) {
                CoreferenceLink link = chain.getFirst();
                while (link.getNext() != null) {
                    System.out.printf("-> %s (%s) ", link.getCoveredText(), link.getReferenceType());
                    if (link.getReferenceRelation() != null) {
                        System.out.printf("-[%s]", link.getReferenceRelation());
                    }
                    link = link.getNext();
                }
                System.out.printf("%n");
            }

            System.out.printf("%n== Sentiment Coefficients ==%n");
            for (SentimentAnnotation sentiment : select(jcas, SentimentAnnotation.class)) {
                System.out.println(sentiment);
            }
        }
    }

}
