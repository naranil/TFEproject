package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation;

import java.util.Arrays;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.lexical.DocStatFeature;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.lexical.PunctuationFeature;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.lexical.SimpleSentenceExtractor;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.pos.ComparativeExtractor;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.pos.ModalVerbExtractor;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.pos.SuperlativeExtractor;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.syntactic.SubClausesRatio;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.indicators.FirstPersonInArgumentComponent;
import de.tudarmstadt.ukp.dkpro.lab.task.Dimension;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.LuceneNGramDFE;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.LucenePOSNGramDFE;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.base.LuceneNgramFeatureExtractorBase;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.base.LucenePOSNGramFeatureExtractorBase;
import de.tudarmstadt.ukp.dkpro.tc.features.syntax.POSRatioFeatureExtractor;
public class SynctaticBaseline
    extends AbstractEvaluationScenario
{

    @SuppressWarnings("unchecked")
    @Override
    Dimension<List<Object>> getPipelineParameters()
    {

        return Dimension.create(
                DIM_PIPELINE_PARAMS,
                Arrays.asList(new Object[] {
                         LuceneNgramFeatureExtractorBase.PARAM_NGRAM_MIN_N, 1,
                         LuceneNgramFeatureExtractorBase.PARAM_NGRAM_MAX_N, 3,
                         LuceneNgramFeatureExtractorBase.PARAM_NGRAM_USE_TOP_K, "10000",
                         LucenePOSNGramFeatureExtractorBase.PARAM_POS_NGRAM_MIN_N,2,
                         LucenePOSNGramFeatureExtractorBase.PARAM_POS_NGRAM_MAX_N,3,
                         LucenePOSNGramFeatureExtractorBase.PARAM_POS_NGRAM_USE_TOP_K,"500",

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
                        LucenePOSNGramDFE.class.getName(),
                        POSRatioFeatureExtractor.class.getName(),
                        ComparativeExtractor.class.getName(),
                        SuperlativeExtractor.class.getName(),
                        ModalVerbExtractor.class.getName(),
                        SimpleSentenceExtractor.class.getName(),
                        SubClausesRatio.class.getName(),
                        FirstPersonInArgumentComponent.class.getName(),
                        DocStatFeature.class.getName(),
                        PunctuationFeature.class.getName(),
//                        ProductionRulesTest.class.getName(),
                        }));

    }

}