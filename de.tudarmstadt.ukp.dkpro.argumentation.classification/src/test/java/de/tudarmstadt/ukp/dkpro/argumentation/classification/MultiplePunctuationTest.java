package de.tudarmstadt.ukp.dkpro.argumentation.classification;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.writer.AnalysisResultConsoleWriter;
import de.tudarmstadt.ukp.dkpro.core.berkeleyparser.BerkeleyParser;
import de.tudarmstadt.ukp.dkpro.core.languagetool.LanguageToolSegmenter;
import de.tudarmstadt.ukp.dkpro.core.maltparser.MaltParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * JUnit test for the Berkeley Parser and the MaltParser
 *
 * @author Anil Narassiguin
 */

public class MultiplePunctuationTest
{

    private AnalysisEngineDescription pipeline;
    private CAS cas;

    @Before
    public void setUp()
            throws Exception
    {
        pipeline = createEngineDescription(
                // Token, Sentence
                createEngineDescription(LanguageToolSegmenter.class),
                // Constituent, POS
                createEngineDescription(BerkeleyParser.class, BerkeleyParser.PARAM_WRITE_POS,
                        false),
                createEngineDescription(StanfordPosTagger.class),
                // Dependency
                createEngineDescription(MaltParser.class),
                // Dump
                createEngineDescription(AnalysisResultConsoleWriter.class)
        );
        cas = CasCreationUtils
                .createCas(TypeSystemDescriptionFactory.createTypeSystemDescription(), null, null);
        cas.setDocumentLanguage("en");
    }

    @Test
    public void testSinglePunctuation()
            throws Exception
    {
        cas.setDocumentText("How are you ?");

        try {
            SimplePipeline.runPipeline(cas, pipeline);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

//    @Ignore
    @Test
    public void testMultiplePunctuation()
            throws Exception
    {
        cas.setDocumentText("How are you ????????????");

        try {
            SimplePipeline.runPipeline(cas, pipeline);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(MultiplePunctuationTest.class);
        for (Failure failure : result.getFailures()) {
            failure.getException().printStackTrace(System.err);
        }
        System.out.println(result.wasSuccessful());
    }

}
