package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation;

import java.util.Arrays;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.pos.POSRatioFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.lab.task.Dimension;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.LuceneNGramDFE;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.base.FrequencyDistributionNGramFeatureExtractorBase;

public class POSRatioExp
    extends AbstractEvaluationScenario
{

    @SuppressWarnings("unchecked")
    @Override
    Dimension<List<Object>> getPipelineParameters()
    {

        return Dimension.create(DIM_PIPELINE_PARAMS, Arrays.asList(new Object[] {
                FrequencyDistributionNGramFeatureExtractorBase.PARAM_NGRAM_MIN_N, 1,
                FrequencyDistributionNGramFeatureExtractorBase.PARAM_NGRAM_MAX_N, 3,
                FrequencyDistributionNGramFeatureExtractorBase.PARAM_NGRAM_USE_TOP_K, 10000 }));
    }

    @SuppressWarnings("unchecked")
    @Override
    Dimension<List<String>> getFeatureSets()
    {
        return Dimension.create(
                DIM_FEATURE_SET,
                Arrays.asList(new String[] { LuceneNGramDFE.class.getName(),
                        POSRatioFeatureExtractor.class.getName() }));
    }

}