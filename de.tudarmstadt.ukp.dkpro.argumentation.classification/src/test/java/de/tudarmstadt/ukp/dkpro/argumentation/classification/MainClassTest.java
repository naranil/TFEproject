package de.tudarmstadt.ukp.dkpro.argumentation.classification;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import weka.classifiers.functions.SMO;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.reader.CorpusReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.lab.Lab;
import de.tudarmstadt.ukp.dkpro.lab.task.Dimension;
import de.tudarmstadt.ukp.dkpro.lab.task.ParameterSpace;
import de.tudarmstadt.ukp.dkpro.lab.task.impl.BatchTask.ExecutionPolicy;
import de.tudarmstadt.ukp.dkpro.tc.core.Constants;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.LuceneNGramDFE;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.LucenePOSNGramDFE;
import de.tudarmstadt.ukp.dkpro.tc.weka.report.BatchOutcomeIDReport;
import de.tudarmstadt.ukp.dkpro.tc.weka.report.BatchRuntimeReport;
import de.tudarmstadt.ukp.dkpro.tc.weka.report.BatchTrainTestReport;
import de.tudarmstadt.ukp.dkpro.tc.weka.report.ClassificationReport;
import de.tudarmstadt.ukp.dkpro.tc.weka.task.BatchTaskTrainTest;
import de.tudarmstadt.ukp.dkpro.tc.weka.writer.WekaDataWriter;

@Deprecated
public class MainClassTest
implements Constants
{

public static final String corpusFilePathTrain = "src/test/resources";
public static final String LANGUAGE_CODE = "en";
/*
public void run() throws Exception
{

    ParameterSpace pSpace = getParameterSpace();
    MainClass experiment = new MainClass();

    // ### Train Test
     experiment.runTrainTest(pSpace);

}



public static ParameterSpace getParameterSpace()
{
    Map<String, Object> dimReaders = new HashMap<String, Object>();
    // Name of the training Reader
    dimReaders.put(DIM_READER_TRAIN, CorpusReader.class);
    // Location, language, patterns
    dimReaders.put(DIM_READER_TRAIN_PARAMS, Arrays.asList(new Object[] {
            CorpusReader.PARAM_SOURCE_LOCATION, corpusFilePathTrain,
            CorpusReader.PARAM_LANGUAGE, LANGUAGE_CODE, CorpusReader.PARAM_PATTERNS,
            CorpusReader.INCLUDE_PREFIX + "*.txt" }));

    // Name of the test set
    dimReaders.put(DIM_READER_TEST, CorpusReader.class);
    dimReaders.put(DIM_READER_TEST_PARAMS, Arrays.asList(new Object[] {
            CorpusReader.PARAM_SOURCE_LOCATION, corpusFilePathTrain,
            CorpusReader.PARAM_LANGUAGE, LANGUAGE_CODE, CorpusReader.PARAM_PATTERNS,
            CorpusReader.INCLUDE_PREFIX + "*.txt" }));

    // Algorithms used
    @SuppressWarnings("unchecked")
    Dimension<List<String>> dimClassificationArgs = Dimension.create(DIM_CLASSIFICATION_ARGS,
            Arrays.asList(new String[] { SMO.class.getName() }));


    // Feature extractor
    @SuppressWarnings("unchecked")
    Dimension<List<Object>> dimPipelineParameters = Dimension.create(DIM_PIPELINE_PARAMS,

            Arrays.asList(new Object[] { LuceneNGramDFE.PARAM_NGRAM_USE_TOP_K, "500",
                    LuceneNGramDFE.PARAM_NGRAM_MIN_N, 1,
                    LuceneNGramDFE.PARAM_NGRAM_MAX_N, 3 }),
            Arrays.asList(new Object[] { LucenePOSNGramDFE.PARAM_NGRAM_USE_TOP_K, "500",
                    LucenePOSNGramDFE.PARAM_NGRAM_MIN_N, 1,
                    LucenePOSNGramDFE.PARAM_NGRAM_MAX_N, 3 }));


    // Run the features extractors
    @SuppressWarnings({ "unchecked", "deprecation" })
    Dimension<List<String>> dimFeatureSets = Dimension.create(DIM_FEATURE_SET,
            Arrays.asList(new String[] {LucenePOSNGramDFE.class.getName() }));

    // Set the feature mode (document, unit, pair)
    @SuppressWarnings("unchecked")
    Dimension<String> dimFeatureMode = Dimension.create(DIM_FEATURE_MODE, FM_DOCUMENT);

    // Set the learning mode (single-multi label, regression)
    @SuppressWarnings("unchecked")
    Dimension<String> dimLearningMode = Dimension.create(DIM_LEARNING_MODE, LM_SINGLE_LABEL);

    // Set the data writter (Weka format)
    @SuppressWarnings("unchecked")
    Dimension<String> dimDataWritter = Dimension.create(DIM_DATA_WRITER,
            WekaDataWriter.class.getName());

    @SuppressWarnings("unchecked")
    ParameterSpace pSpace = new ParameterSpace(Dimension.createBundle("readers", dimReaders),
            dimDataWritter, dimLearningMode, dimFeatureMode, dimPipelineParameters,
            dimFeatureSets, dimClassificationArgs);

    return pSpace;

}



// ##### TRAIN-TEST #####
protected void runTrainTest(ParameterSpace pSpace)
    throws Exception
{

    BatchTaskTrainTest batch = new BatchTaskTrainTest("Argumentation_Exp_CD",
            getPreprocessing());
    batch.addInnerReport(ClassificationReport.class);
    batch.setParameterSpace(pSpace);
    batch.setExecutionPolicy(ExecutionPolicy.RUN_AGAIN);
    batch.addReport(BatchTrainTestReport.class);
    batch.addReport(BatchOutcomeIDReport.class);
    batch.addReport(BatchRuntimeReport.class);

    // Run
    Lab.getInstance().run(batch);
}

protected AnalysisEngineDescription getPreprocessing()
    throws ResourceInitializationException
{

    return createEngineDescription(
            createEngineDescription(BreakIteratorSegmenter.class),
            createEngineDescription(OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE,
                    LANGUAGE_CODE)


    );
}
*/
}

