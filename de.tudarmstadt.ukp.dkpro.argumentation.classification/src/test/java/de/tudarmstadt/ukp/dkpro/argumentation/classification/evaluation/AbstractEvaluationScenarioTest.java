package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.reader.CorpusReader;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.writer.AnalysisResultConsoleWriter;
import de.tudarmstadt.ukp.dkpro.lab.task.Dimension;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class AbstractEvaluationScenarioTest
{
    private File file;

    public AbstractEvaluationScenarioTest(File file)
    {
        this.file = file;
    }

//    private JCas jCas;
    private static AnalysisEngineDescription preprocessing;

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        File[] files = new File("src/main/resources/gold_data/").listFiles();
        if (files == null || files.length == 0) {
            throw new AssertionError("No gold data files found");
        }

        Collection<Object[]> data = new ArrayList<Object[]>();
        for (File f : files) {
            data.add(new Object[] { f });
        }

        return data;
    }

    /*
    @Before
    public void setUp()
            throws Exception
    {

        jCas = JCasFactory
                .createJCas(TypeSystemDescriptionFactory.createTypeSystemDescription());
        String text = IOUtils.toString(this.getClass().getClassLoader()
                .getResourceAsStream("gold_data/1289_P1_artcomment_prayer-in-schools.txt"));
        jCas.setDocumentText(text);
        jCas.setDocumentLanguage("en");

        //        preprocessing = new MockAbstractEvaluationScenario().getPreprocessingStanford();
        //        preprocessing = new MockAbstractEvaluationScenario().getPreprocessingClearNLP();
    }
    */

    @BeforeClass
    public static void setUpBeforeClass()
            throws Exception
    {
        preprocessing = new MockAbstractEvaluationScenario().getPreprocessing();
//        preprocessing = new MockAbstractEvaluationScenario().getPreprocessingStanford();
    }

    /*
    @Ignore
    @Test
    public void testGetPreprocessing()
            throws Exception
    {
        AnalysisEngineDescription preprocessing = this.preprocessing;
        SimplePipeline.runPipeline(jCas, preprocessing,
                //                AnalysisEngineFactory.createEngineDescription(CasDumpWriter.class),
                AnalysisEngineFactory.createEngineDescription(AnalysisResultConsoleWriter.class)
        );
    }
    */

//    @Ignore
    @Test
    public void testAllFiles()
            throws Exception
    {
        CollectionReaderDescription readerDescription = CollectionReaderFactory
                .createReaderDescription(CorpusReader.class, CorpusReader.PARAM_SOURCE_LOCATION,
                        AbstractEvaluationScenario.GOLD_DATA_PATH,
                        CorpusReader.PARAM_LANGUAGE, AbstractEvaluationScenario.LANGUAGE_CODE,
                        CorpusReader.PARAM_PATTERNS,
                        CorpusReader.INCLUDE_PREFIX + file.getName());

        SimplePipeline.runPipeline(
                readerDescription,
                preprocessing,
                AnalysisEngineFactory.createEngineDescription(AnalysisResultConsoleWriter.class)
        );
    }

    public static class MockAbstractEvaluationScenario
            extends AbstractEvaluationScenario
    {

        @Override Dimension<List<String>> getFeatureSets()
        {
            return null;
        }

        @Override Dimension<List<Object>> getPipelineParameters()
        {
            return null;
        }
    }

    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(AbstractEvaluationScenarioTest.class);
        for (Failure failure : result.getFailures()) {
            failure.getException().printStackTrace(System.err);
        }
        System.out.println(result.wasSuccessful());
    }
}