package de.tudarmstadt.ukp.dkpro.argumentation.classification.writer;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceChain;
import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.semantics.type.SemanticArgument;
import de.tudarmstadt.ukp.dkpro.core.api.semantics.type.SemanticPredicate;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.ROOT;
import de.tudarmstadt.ukp.dkpro.core.io.penntree.PennTreeUtils;
import de.tudarmstadt.ukp.dkpro.core.type.Dependency;
import de.tudarmstadt.ukp.dkpro.core.type.NamedEntity;
import org.apache.commons.io.output.CloseShieldOutputStream;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.CasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import java.io.*;

import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

/**
 * Writes the analysed cas in a readable form to System.out or into a file. Taken from GSCL tutorial
 * code.
 */
public class AnalysisResultConsoleWriter
        extends CasConsumer_ImplBase
{

    /**
     * Output file. If multiple CASes as processed, their contents are concatenated into this file.
     * Mind that a test case using this consumer with multiple CASes requires a reader which produced
     * the CASes always in the same order. When this file is set to "-", the dump does to
     * {@link System#out} (default).
     */
    public static final String PARAM_OUTPUT_FILE = "outputFile";

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true, defaultValue = "-")
    private File outputFile;

    private PrintWriter out;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException
    {
        super.initialize(context);

        try {
            if (out == null) {
                if ("-".equals(outputFile.getName())) {
                    out = new PrintWriter(new CloseShieldOutputStream(System.out), true);
                }
                else {
                    if (outputFile.getParentFile() != null) {
                        outputFile.getParentFile().mkdirs();
                    }
                    out = new PrintWriter(
                            new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"),
                            true);
                }
            }
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }

    }

    @Override
    public void process(CAS aCAS)
            throws AnalysisEngineProcessException
    {
        JCas jCas;
        try {
            jCas = aCAS.getJCas();
        }
        catch (CASException e) {
            throw new AnalysisEngineProcessException(e);
        }

        for (Sentence sentence : select(jCas, Sentence.class)) {
            out.printf("%n== Sentence ==%n");
            out.printf("  %-16s %-10s %-10s %-10s %-10s %n", "TOKEN", "LEMMA", "STEM",
                    "CPOS", "POS");
            for (Token token : selectCovered(Token.class, sentence)) {
                out.printf("  %-16s %-10s %-10s %-10s %-10s %n",
                        token.getCoveredText(),
                        token.getLemma() != null ? token.getLemma().getValue() : "",
                        token.getStem() != null ? token.getStem().getValue() : "",
                        token.getPos().getClass().getSimpleName(),
                        token.getPos().getPosValue());
            }

            out.printf("%n  -- Named Entities --%n");
            out.printf("  %-16s %-10s%n", "ENTITY", "TOKENS");
            for (NamedEntity ne : selectCovered(NamedEntity.class, sentence)) {
                out.printf("  %-16s %-10s%n", ne.getValue(), ne.getCoveredText());
            }

            out.printf("%n  -- Constituents --%n");
            for (ROOT root : selectCovered(ROOT.class, sentence)) {
                out.printf("  %s%n",
                        PennTreeUtils.toPennTree(PennTreeUtils.convertPennTree(root)));
            }

            out.printf("%n  -- Dependency relations --%n");
            out.printf("  %-16s %-10s %-10s %-10s %n", "TOKEN", "DEPREL", "DEP", "GOV");
            for (Dependency dep : selectCovered(Dependency.class, sentence)) {
                out.printf("  %-16s %-10s %-10s %n",
                        dep.getDependencyType(),
                        dep.getDependent().getCoveredText(),
                        dep.getGovernor().getCoveredText());
            }

            out.printf("%n  -- Semantic structure --%n");
            for (SemanticPredicate pred : selectCovered(SemanticPredicate.class, sentence)) {
                out.printf("  %-16s %-10s", pred.getCoveredText(), pred.getCategory());
                for (SemanticArgument arg : select(pred.getArguments(), SemanticArgument.class)) {
                    out.printf("\t%s:%s", arg.getRole(), arg.getCoveredText());
                }
                out.printf("%n");
            }
        }

        out.printf("%n== Coreference chains (for the whole document) ==%n");
        for (CoreferenceChain chain : select(jCas, CoreferenceChain.class)) {
            CoreferenceLink link = chain.getFirst();
            while (link.getNext() != null) {
                out.printf("-> %s (%s) ", link.getCoveredText(), link.getReferenceType());
                if (link.getReferenceRelation() != null) {
                    out.printf("-[%s]", link.getReferenceRelation());
                }
                link = link.getNext();
            }
            out.printf("%n");
        }
    }

}
