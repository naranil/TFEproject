package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.sentiment;

import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.stanfordsentiment.SentimentAnnotation;
import de.tudarmstadt.ukp.dkpro.tc.api.exception.TextClassificationException;
import de.tudarmstadt.ukp.dkpro.tc.api.features.DocumentFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;
import de.tudarmstadt.ukp.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;

/**
 * This feature takes the results of the Stanford parser which given a sentence returns 5 ratios:
 * --, -, neutral, +, ++ We calculate the modes (min, max, mean, sd) over all the sentences of the
 * text which results in 20 features.
 *
 * @author Anil Narassiguin
 *
 *
 */

// Note: This feature class is still not well written since I take the sentiment coefficients
// directly from the Stanford parser (In the future we should have our own Sentiment annotator
// which fits better to DKPro)

public class SentimentCoefficientsFeature
    extends FeatureExtractorResource_ImplBase
    implements DocumentFeatureExtractor

{
    /**
     * Extracts the sentiment features
     *
     * Use {@link #addElement(double[] org, double added)} to concatenate values
     *
     * @param jcas
     *            of a document
     * @return The 20 sentiment features
     */
    @Override
    public List<Feature> extract(JCas jcas)
        throws TextClassificationException
    {

        DescriptiveStatistics veryNegative = new DescriptiveStatistics();
        DescriptiveStatistics negative = new DescriptiveStatistics();
        DescriptiveStatistics neutral = new DescriptiveStatistics();
        DescriptiveStatistics positive = new DescriptiveStatistics();
        DescriptiveStatistics veryPositive = new DescriptiveStatistics();

        for (Sentence sentence : select(jcas, Sentence.class)) {
            SentimentAnnotation sentiment = selectCovered(SentimentAnnotation.class, sentence).get(0);

            veryNegative.addValue(sentiment.getVeryNegative());
            negative.addValue(sentiment.getNegative());
            neutral.addValue(sentiment.getNeutral());
            positive.addValue(sentiment.getPositive());
            veryPositive.addValue(sentiment.getVeryPositive());

        }

        List<Feature> featList = new ArrayList<Feature>();

        // Very negative
        featList.add(new Feature("sentiment_very_negative_mean", veryNegative.getMean()));
        featList.add(new Feature("sentiment_very_negative_max", veryNegative.getMax()));
        featList.add(new Feature("sentiment_very_negative_min", veryNegative.getMin()));
        featList.add(new Feature("sentiment_very_negative_sd", veryNegative.getStandardDeviation()));

        // negative
        featList.add(new Feature("sentiment_negative_mean", negative.getMean()));
        featList.add(new Feature("sentiment_negative_max", negative.getMax()));
        featList.add(new Feature("sentiment_negative_min", negative.getMin()));
        featList.add(new Feature("sentiment_negative_sd", negative.getStandardDeviation()));

        // neutral
        featList.add(new Feature("sentiment_neutral_mean", neutral.getMean()));
        featList.add(new Feature("sentiment_neutral_max", neutral.getMax()));
        featList.add(new Feature("sentiment_neutral_min", neutral.getMin()));
        featList.add(new Feature("sentiment_neutral_sd", neutral.getStandardDeviation()));

        // positive
        featList.add(new Feature("sentiment_positive_mean", positive.getMean()));
        featList.add(new Feature("sentiment_positive_max", positive.getMax()));
        featList.add(new Feature("sentiment_positive_min", positive.getMin()));
        featList.add(new Feature("sentiment_positive_sd", positive.getStandardDeviation()));

        // Very positive
        featList.add(new Feature("sentiment_very_positive_mean", veryPositive.getMean()));
        featList.add(new Feature("sentiment_very_positive_max", veryPositive.getMax()));
        featList.add(new Feature("sentiment_very_positive_min", veryPositive.getMin()));
        featList.add(new Feature("sentiment_very_positive_sd", veryPositive.getStandardDeviation()));

        return featList;
    }

}
