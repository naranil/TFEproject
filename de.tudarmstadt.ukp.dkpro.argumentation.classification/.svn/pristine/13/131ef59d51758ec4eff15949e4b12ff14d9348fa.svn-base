package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.sentiment;

import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.stanfordsentiment.SentimentAnnotation;
import de.tudarmstadt.ukp.dkpro.tc.api.exception.TextClassificationException;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;

public class SentimentFluctuationBinary
    extends SentimentFluctuation
{
    @Override
    public List<Feature> extract(JCas jcas)
        throws TextClassificationException
    {
        Map<String, Integer> sentimentFluctuation = sentimentRules();
        List<Feature> features = new ArrayList<Feature>();

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Sentence> sentenceList = new ArrayList(select(jcas, Sentence.class));
        for (int i = 0; i < sentenceList.size() - 1; i++) {
            Sentence sentence1 = sentenceList.get(i);
            Sentence sentence2 = sentenceList.get(i + 1);

            SentimentAnnotation sentiment1 = selectCovered(SentimentAnnotation.class, sentence1)
                    .get(0);
            SentimentAnnotation sentiment2 = selectCovered(SentimentAnnotation.class, sentence2)
                    .get(0);

            double[] sentimentArray1 = { sentiment1.getVeryNegative(), sentiment1.getNegative(),
                    sentiment1.getNeutral(), sentiment1.getPositive(), sentiment1.getVeryPositive() };

            double[] sentimentArray2 = { sentiment2.getVeryNegative(), sentiment2.getNegative(),
                    sentiment2.getNeutral(), sentiment2.getPositive(), sentiment2.getVeryPositive() };


            String rule = correspondingSentiment(getMaximumIndex(sentimentArray1)) + " -> "
                    + correspondingSentiment(getMaximumIndex(sentimentArray2));


            sentimentFluctuation.put(rule, 1);


        }

        for (String rule : sentimentFluctuation.keySet()) {
            features.add(new Feature("Sentiment Fluctuation " + rule, sentimentFluctuation.get(rule)));
        }

        return features;
    }

}
