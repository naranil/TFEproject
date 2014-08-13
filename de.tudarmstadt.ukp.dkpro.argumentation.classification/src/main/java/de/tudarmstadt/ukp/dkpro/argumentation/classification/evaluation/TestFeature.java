package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation;

import java.util.Arrays;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.ngram.lemma.LuceneNGramLemmaBase;
import de.tudarmstadt.ukp.dkpro.argumentation.classification.ngram.lemma.LuceneNGramLemmaDFE;
import de.tudarmstadt.ukp.dkpro.lab.task.Dimension;

/**
 *
 */
public class TestFeature
        extends AbstractEvaluationScenario
{



    @SuppressWarnings("unchecked")
    @Override Dimension<List<String>> getFeatureSets()
    {
        return Dimension.create(
                DIM_FEATURE_SET,
                Arrays.asList(new String[] { LuceneNGramLemmaDFE.class.getName(), }));
    }

    @SuppressWarnings("unchecked")
    @Override
    Dimension<List<Object>> getPipelineParameters()
    {

        return Dimension.create(
                DIM_PIPELINE_PARAMS,
                Arrays.asList(new Object[] {
                        LuceneNGramLemmaBase.PARAM_NGRAM_MIN_N, 1,
                        LuceneNGramLemmaBase.PARAM_NGRAM_MAX_N, 3,
                        LuceneNGramLemmaBase.PARAM_NGRAM_USE_TOP_K, 20000,
                }));
    }

}