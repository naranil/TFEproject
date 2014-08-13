package de.tudarmstadt.ukp.dkpro.argumentation.classification;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceChain;
import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
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
 * In this pipeline, we use components wrapping tools from different vendors for every step of a
 * comprehensive analysis. This demonstrates how DKPro Core components can be mixed and matched. It
 * also shows how to access most of the analysis results produced by DKPro Core components can be
 * accessed.
 *
 * Needs 2GB heap.
 */
public class Test
{

    public static void main(String[] args)
        throws Exception
    {
        // Assemble pipeline
        JCasIterable pipeline = new JCasIterable(
                // Read input
                createReaderDescription(TextReader.class, TextReader.PARAM_SOURCE_LOCATION,
                        "src/main/resources/test", TextReader.PARAM_LANGUAGE, "en",
                        TextReader.PARAM_PATTERNS, TextReader.INCLUDE_PREFIX + "*.txt"),
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
                // Sentiment
                createEngineDescription(StanfordSentimentAnnotator.class),
                // NamedEntity
                createEngineDescription(OpenNlpNameFinder.class, OpenNlpNameFinder.PARAM_VARIANT,
                        "person"), createEngineDescription(OpenNlpNameFinder.class,
                        OpenNlpNameFinder.PARAM_VARIANT, "organization"),
                // CoreferenceChain, CoreferenceLink
                createEngineDescription(StanfordCoreferenceResolver.class),
                // SemanticPredicate, SemanticArgument
                createEngineDescription(ClearNlpSemanticRoleLabeler.class),
                // Write output as XMI for inspection in UIMA CAS Editor
                createEngineDescription(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION,
                        "src/main/resources/test/xmi", XmiWriter.PARAM_TYPE_SYSTEM_FILE,
                        "TypeSystem.xml"));

        // Run and show results in console
        for (JCas jcas : pipeline) {
            for (Sentence sentence : select(jcas, Sentence.class)) {
                System.out.printf("%n== Sentence ==%n");
                System.out.printf("  %-16s %-10s %-10s %-10s %-10s %n", "TOKEN", "LEMMA", "STEM",
                        "CPOS", "POS");
                for (Token token : selectCovered(Token.class, sentence)) {
                    System.out.printf("  %-16s %-10s %-10s %-10s %-10s %n", token.getCoveredText(),
                            token.getLemma() != null ? token.getLemma().getValue() : "", token
                                    .getStem() != null ? token.getStem().getValue() : "", token
                                    .getPos().getClass().getSimpleName(), token.getPos()
                                    .getPosValue());
                }

                System.out.println("========  LEMMA  ==========");
                for (Lemma lemma : selectCovered(Lemma.class, sentence)) {
                    System.out.println(lemma.getValue());
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
                    System.out.printf("  %-16s %-10s %-10s %n", dep.getDependencyType(), dep
                            .getDependent().getCoveredText(), dep.getGovernor().getCoveredText());
                }

                System.out.printf("%n  -- Semantic structure --%n");
                for (SemanticPredicate pred : selectCovered(SemanticPredicate.class, sentence)) {
                    System.out.printf("  %-16s %-10s", pred.getCoveredText(), pred.getCategory());
                    for (SemanticArgument arg : select(pred.getArguments(), SemanticArgument.class)) {
                        System.out.printf("\t%s:%s", arg.getRole(), arg.getCoveredText());
                    }
                    System.out.printf("%n");
                }

                System.out.printf("%n  -- Sentiment Coeff --%n");

                SentimentAnnotation sentiment = selectCovered(SentimentAnnotation.class, sentence)
                        .get(0);
                System.out.println("Very Negative: " + sentiment.getVeryNegative());
                System.out.println("Negative: " + sentiment.getNegative());
                System.out.println("Neutral: " + sentiment.getNeutral());
                System.out.println("Positive: " + sentiment.getPositive());
                System.out.println("Very Positive: " + sentiment.getVeryPositive());

            }

            System.out.printf("%n== Coreference chains (for the whole document) ==%n");
            for (CoreferenceChain chain : select(jcas, CoreferenceChain.class)) {
                CoreferenceLink link = chain.getFirst();
                while (link.getNext() != null) {
                    System.out
                            .printf("-> %s (%s) ", link.getCoveredText(), link.getReferenceType());
                    if (link.getReferenceRelation() != null) {
                        System.out.printf("-[%s]", link.getReferenceRelation());
                    }
                    link = link.getNext();
                }
                System.out.printf("%n");
            }

        }
    }
}
