package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation;

import java.util.Arrays;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.pos.POSRatioFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.lab.task.Dimension;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.LuceneNGramDFE;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.base.FrequencyDistributionNGramFeatureExtractorBase;



public class ExtendedBaseline
    extends AbstractEvaluationScenario
{

    @SuppressWarnings("unchecked")
    @Override Dimension<List<Object>> getPipelineParameters()
    {

        return Dimension.create(
                DIM_PIPELINE_PARAMS,
                Arrays.asList(new Object[] {
                        FrequencyDistributionNGramFeatureExtractorBase.PARAM_NGRAM_MIN_N, 1,
                        FrequencyDistributionNGramFeatureExtractorBase.PARAM_NGRAM_MAX_N, 3,
                        FrequencyDistributionNGramFeatureExtractorBase.PARAM_NGRAM_USE_TOP_K,
                        10000
//                         LuceneNgramFeatureExtractorBase.PARAM_NGRAM_MIN_N, 2,
//                         LuceneNgramFeatureExtractorBase.PARAM_NGRAM_MAX_N, 4,
                        // LucenePOSNGramFeatureExtractorBase.PARAM_POS_NGRAM_MIN_N, 2,
                        // LucenePOSNGramFeatureExtractorBase.PARAM_POS_NGRAM_MAX_N, 4,
//                        LuceneSkipNgramFeatureExtractorBase.PARAM_SKIP_NGRAM_MIN_N, 2,
//                        LuceneSkipNgramFeatureExtractorBase.PARAM_SKIP_NGRAM_MAX_N, 2,
//                        LuceneSkipNgramFeatureExtractorBase.PARAM_SKIP_SIZE, 1

                // Also FrequencyDistributionPosNGramFeatureExtractorBase ?
                }));
    }

    @SuppressWarnings("unchecked")
    @Override
    Dimension<List<String>> getFeatureSets()
    {
        return Dimension.create(
                DIM_FEATURE_SET,
                Arrays.asList(new String[] {
                        LuceneNGramDFE.class.getName(),
                        POSRatioFeatureExtractor.class.getName(),
//                        LuceneSkipNGramDFE.class.getName(),
//                        SimpleVerbsExtractor.class.getName(),
//                        SimplePunctuationExtractor.class.getName(),
//                        SimpleAdjectiveExtractor.class.getName(),
//                        ComparativeExtractor.class.getName(), SuperlativeExtractor.class.getName(),
//                        ModalVerbExtractor.class.getName(),

                }));

    }


}
