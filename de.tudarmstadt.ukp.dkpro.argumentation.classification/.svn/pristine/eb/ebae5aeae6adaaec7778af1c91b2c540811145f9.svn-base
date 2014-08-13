package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.sentiment;

import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.stanfordsentiment.SentimentAnnotation;
import de.tudarmstadt.ukp.dkpro.tc.api.exception.TextClassificationException;
import de.tudarmstadt.ukp.dkpro.tc.api.features.DocumentFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;
import de.tudarmstadt.ukp.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;

/**
 *
 * This feature extractor takes for each sentence its main class in term of sentiment (very
 * negative, negative, neutral, positive, very positive) and counts all the consecutive rules (ex
 * "++ -> -", "- -> 0", ect...) and give their distribution
 * This feature was built by Ivan and Anil
 *
 * Use {@link #getMaximumIndex(double[] t)} to find the main sentiment of the sentence
 *
 * @author Anil Narassiguin
 * @author Ivan Habernal
 * @param jcas
 *            of a document
 * @return distribution of the 25 sentiment rules
 *
 */

// Check this feature extractor with unit test... Worked only when I remove "default:" in switch, find out why...
public class SentimentFluctuation
    extends FeatureExtractorResource_ImplBase
    implements DocumentFeatureExtractor
{

    Map<String, Integer> sentimentFluctuation = new HashMap<String, Integer>();

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


            sentimentFluctuation.put(rule, sentimentFluctuation.get(rule) + 1);


        }

        for (String rule : sentimentFluctuation.keySet()) {
            features.add(new Feature("Sentiment Fluctuation " + rule, sentimentFluctuation.get(rule)));
        }

        return features;
    }

    public static Map<String, Integer> sentimentRules()
    {
        String[] sentiments = { "VeryNegative", "Negative", "Neutral", "Positive", "VeryPositive" };
        Map<String, Integer> sentimentFluctuation = new HashMap<String, Integer>();

        for (String sentiment1 : sentiments) {
            for (String sentiment2 : sentiments) {
                sentimentFluctuation.put(sentiment1 + " -> " + sentiment2, 0);
            }
        }

        return sentimentFluctuation;

    }

    public static int getMaximumIndex(double[] t)
    {

        double max = t[0];
        int maxIndex = 0;

        for (int i = 0; i < t.length; i++) {
            if (t[i] > max) {
                max = t[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public static String correspondingSentiment(int maxIndex)
    {
        String symbol = "";
        switch (maxIndex) {
        case 0:
            symbol = "VeryNegative";
            break;
        case 1:
            symbol = "Negative";
            break;
        case 2:
            symbol = "Neutral";
            break;
        case 3:
            symbol = "Positive";
            break;
        case 4:
            symbol = "VeryPositive";

        }

        return symbol;
    }

}
