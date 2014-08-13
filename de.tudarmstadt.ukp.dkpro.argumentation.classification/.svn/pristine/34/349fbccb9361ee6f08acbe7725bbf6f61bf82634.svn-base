package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * (c) 2013 Ivan Habernal
 */
public class ConfusionMatrix
{

    int total = 0;

    int correct = 0;

    private final Map<String, Map<String, Integer>> map;

    private int numberOfDecimalPlaces = 3;

    private final TreeSet<String> allGoldLabels = new TreeSet<String>();

    private final TreeSet<String> allExpectedLabels = new TreeSet<String>();

    private final List<String> labelSeries = new ArrayList<String>();

    public ConfusionMatrix()
    {
        this.map = new TreeMap<>();

    }

    public void setNumberOfDecimalPlaces(int numberOfDecimalPlaces)
        throws IllegalArgumentException
    {
        if (numberOfDecimalPlaces < 1 || numberOfDecimalPlaces > 100) {
            throw new IllegalArgumentException("Argument must be in rage 1-100");
        }

        this.numberOfDecimalPlaces = numberOfDecimalPlaces;
    }

    private String getFormat()
    {
        return "%." + numberOfDecimalPlaces + "f";
    }

    public void increaseValue(String expectedValue, String actualValue)
    {
        increaseValue(expectedValue, actualValue, 1);
    }

    /**
     * Returns the series of actual labels
     *
     * @return list of labels
     */
    public List<String> getLabelSeries()
    {
        return labelSeries;
    }

    /**
     * Increases value of expectedValue x actualValue n times
     *
     * @param expectedValue
     *            exp
     * @param actualValue
     *            ac
     * @param times
     *            n-times
     */
    public void increaseValue(String expectedValue, String actualValue, int times)
    {
        allGoldLabels.add(expectedValue);
        allExpectedLabels.add(actualValue);

        for (int i = 0; i < times; i++) {
            labelSeries.add(actualValue);
        }

        if (!map.containsKey(expectedValue)) {
            map.put(expectedValue, new TreeMap<String, Integer>());
        }

        if (!map.get(expectedValue).containsKey(actualValue)) {
            map.get(expectedValue).put(actualValue, 0);
        }

        int currentValue = this.map.get(expectedValue).get(actualValue);
        this.map.get(expectedValue).put(actualValue, currentValue + times);

        total += times;

        if (expectedValue.equals(actualValue)) {
            correct += times;
        }
    }

    public double getAccuracy()
    {
        return ((double) correct / (double) total);
    }

    public int getTotalSum()
    {
        return total;
    }

    public int getRowSum(String label)
    {
        int result = 0;

        for (Integer i : map.get(label).values()) {
            result += i;
        }

        return result;
    }

    public int getColSum(String label)
    {
        int result = 0;

        for (Map<String, Integer> row : this.map.values()) {
            if (row.containsKey(label)) {
                result += row.get(label);
            }
        }

        return result;
    }

    public Map<String, Double> getPrecisionForLabels()
    {
        Map<String, Double> precisions = new LinkedHashMap<String, Double>();
        for (String label : allGoldLabels) {
            double precision = getPrecisionForLabel(label);

            precisions.put(label, precision);
        }
        return precisions;
    }

    public double getPrecisionForLabel(String label)
    {
        double precision = 0;
        int tp = 0;
        int fpAndTp = 0;

        if (map.containsKey(label) && map.get(label).containsKey(label)) {
            tp = this.map.get(label).get(label);
            fpAndTp = getColSum(label);
        }

        if (fpAndTp > 0) {
            precision = (double) tp / (double) (fpAndTp);
        }

        return precision;
    }

    /**
     * Micro-averaged F-measure gives equal weight to each document and is therefore considered as
     * an average over all the document/category pairs. It tends to be dominated by the
     * classifierâ€™s performance on common categories. (It's actually the accuracy).
     * <p/>
     * (from Ozgur et al., 2005. Text Categorization with Class-Based and Corpus-Based Keyword
     * Selection.)
     *
     * @return double
     */
    public double getMicroFMeasure()
    {
        int allTruePositives = 0;
        int allTruePositivesAndFalsePositives = 0;
        int allTruePositivesAndFalseNegatives = 0;

        for (String label : map.keySet()) {
            if (map.containsKey(label) && map.get(label).containsKey(label)) {
                allTruePositives += this.map.get(label).get(label);
            }
            allTruePositivesAndFalsePositives += getColSum(label);
            allTruePositivesAndFalseNegatives += getRowSum(label);
        }

        double precision = (double) allTruePositives / (double) allTruePositivesAndFalsePositives;
        double recall = (double) allTruePositives / (double) allTruePositivesAndFalseNegatives;

        return (2.0 * precision * recall) / (precision + recall);
    }

    /**
     * Macro-averaged F-measure gives equal weight to each category, regardless of its frequency. It
     * is influenced more by the classifierâ€™s performance on rare categories.
     *
     * @return double
     */
    public double getMacroFMeasure()
    {
        Map<String, Double> fMeasureForLabels = getFMeasureForLabels();

        double totalFMeasure = 0;

        for (Double d : fMeasureForLabels.values()) {
            totalFMeasure += d;
        }

        return totalFMeasure / fMeasureForLabels.size();
    }

    /**
     * TODO javadoc
     *
     * @param beta
     * @return
     */
    public double getMacroFMeasure(double beta)
    {
        Map<String, Double> fMeasureForLabels = getFMeasureForLabels(beta);

        double totalFMeasure = 0;

        for (Double d : fMeasureForLabels.values()) {
            totalFMeasure += d;
        }

        return totalFMeasure / fMeasureForLabels.size();
    }

    public Map<String, Double> getFMeasureForLabels()
    {
        Map<String, Double> fMeasure = new LinkedHashMap<String, Double>();

        Map<String, Double> precisionForLabels = getPrecisionForLabels();
        Map<String, Double> recallForLabels = getRecallForLabels();

        for (String label : allGoldLabels) {
            double p = precisionForLabels.get(label);
            double r = recallForLabels.get(label);

            double fm = 0;

            if ((p + r) > 0) {
                fm = (2 * p * r) / (p + r);
            }

            fMeasure.put(label, fm);
        }

        return fMeasure;
    }

    /**
     * See http://en.wikipedia.org/wiki/F1_score
     *
     * @param beta
     *            beta paremeter; higher than 1 prefers recall, lower than 1 prefers precision
     * @return
     */
    public Map<String, Double> getFMeasureForLabels(double beta)
    {
        Map<String, Double> fMeasure = new LinkedHashMap<String, Double>();

        Map<String, Double> precisionForLabels = getPrecisionForLabels();
        Map<String, Double> recallForLabels = getRecallForLabels();

        for (String label : allGoldLabels) {
            double p = precisionForLabels.get(label);
            double r = recallForLabels.get(label);

            double fm = 0;

            if ((p + r) > 0) {
                fm = (1.0 + (beta * beta)) * ((p * r) / ((beta * beta * p) + r));
            }

            fMeasure.put(label, fm);
        }

        return fMeasure;
    }

    public Map<String, Double> getRecallForLabels()
    {
        Map<String, Double> recalls = new LinkedHashMap<String, Double>();
        for (String label : allGoldLabels) {
            double recall = getRecallForLabel(label);

            recalls.put(label, recall);
        }
        return recalls;
    }

    public double getRecallForLabel(String label)
    {
        int fnAndTp = 0;
        double recall = 0;
        int tp = 0;

        if (map.containsKey(label) && map.get(label).containsKey(label)) {
            tp = this.map.get(label).get(label);
            fnAndTp = getRowSum(label);
        }

        if (fnAndTp > 0) {
            recall = (double) tp / (double) (fnAndTp);
        }

        return recall;
    }

    /**
     * Returns the half of the confidence interval on accuracy on alpha = 95
     *
     * @return conf. int
     */
    public double getConfidence95Accuracy()
    {
        return 1.96 * Math.sqrt(getAccuracy() * (1.0 - getAccuracy()) / total);
    }

    /**
     * Returns the half of the confidence interval on accuracy on alpha = 90
     *
     * @return conf. int
     */
    public double getConfidence90Accuracy()
    {
        return 1.645 * Math.sqrt(getAccuracy() * (1.0 - getAccuracy()) / total);
    }

    public double getConfidence90AccuracyLow()
    {
        return getAccuracy() - getConfidence90Accuracy();
    }

    public double getConfidence90AccuracyHigh()
    {
        return getAccuracy() + getConfidence90Accuracy();
    }

    /**
     * Returns the lower bound of the accuracy with alpha = 95
     *
     * @return accuracy minus half of the confidence interval
     */
    public double getConfidence95AccuracyLow()
    {
        return getAccuracy() - getConfidence95Accuracy();
    }

    /**
     * Returns the upper bound of the accuracy with alpha = 95
     *
     * @return accuracy plus half of the confidence interval
     */
    public double getConfidence95AccuracyHigh()
    {
        return getAccuracy() + getConfidence95Accuracy();
    }

    /**
     * Returns the half of confidence interval on alpha = 95 (see
     * http://alias-i.com/lingpipe/docs/api
     * /com/aliasi/classify/ConfusionMatrix.html#confidence95%28%29)
     *
     * @return conf
     */
    public double getConfidence95MacroFM()
    {
        return 1.96 * Math.sqrt(getMacroFMeasure() * (1.0 - getMacroFMeasure()) / total);
    }

    public double getConfidence90MacroFM()
    {
        return 1.66 * Math.sqrt(getMacroFMeasure() * (1.0 - getMacroFMeasure()) / total);
    }

    /**
     * Returns the lower bound of the macro F-measure with alpha = 95
     *
     * @return macro F-measure minus half of the confidence interval
     */
    public double getConfidence95MacroFMLow()
    {
        return getMacroFMeasure() - getConfidence95MacroFM();
    }

    /**
     * Returns the upper bound of the macro F-measure with alpha = 95
     *
     * @return macro F-measure plus half of the confidence interval
     */
    public double getConfidence95MacroFMHigh()
    {
        return getMacroFMeasure() + getConfidence95MacroFM();
    }

    public double getCohensKappa()
    {
        // compute p (which is actaually accuracy)
        double p = getAccuracy();

        // System.out.println(p);

        // compute pe
        double pe = 0;
        for (String label : this.allGoldLabels) {
            double row = getRowSum(label);
            double col = getColSum(label);

            // System.out.println("Label " + label + ", sumCol: " + col + ", sumRow: " + row);

            pe += (row * col) / getTotalSum();
        }

        pe = pe / getTotalSum();

        return (p - pe) / (1 - pe);
    }

    public double getCohensKappaWeighted(ConfusionMatrix weights)
    {
        throw new RuntimeException("Not yet implemented");

        // find max value
        // int wmax = 0;
        // for (String i : weights.allGoldLabels) {
        // for (String j : weights.allGoldLabels) {
        // if (weights.map.containsKey(i) && weights.map.get(i).containsKey(j)) {
        // int possibleMax = weights.map.get(i).get(j);
        // if (possibleMax > wmax) {
        // wmax = possibleMax;
        // }
        // }
        // }
        // }
        //
        // return 0;
    }

    private List<List<String>> prepareToString()
    {
        // adding zeros
        for (String row : allGoldLabels) {
            if (!map.containsKey(row)) {
                map.put(row, new TreeMap<String, Integer>());
            }

            for (String col : allExpectedLabels) {
                if (!map.get(row).containsKey(col)) {
                    map.get(row).put(col, 0);
                }
            }
        }

        List<List<String>> result = new ArrayList<>();

        List<String> allExpectedLabelsSorted = new ArrayList<>();
        TreeSet<String> extraExpectedLabels = new TreeSet<>(allExpectedLabels);
        extraExpectedLabels.removeAll(allGoldLabels);

        allExpectedLabelsSorted.addAll(allGoldLabels);
        allExpectedLabelsSorted.addAll(extraExpectedLabels);

        // header
        List<String> header = new ArrayList<>();
        header.add("");
        header.addAll(allExpectedLabelsSorted);
        result.add(header);

        for (String rowLabel : allGoldLabels) {
            List<String> row = new ArrayList<>();
            row.add(rowLabel);

            for (String expectedLabel : allExpectedLabelsSorted) {
                int value = 0;

                if (this.map.containsKey(rowLabel)
                        && this.map.get(rowLabel).containsKey(expectedLabel)) {
                    value = this.map.get(rowLabel).get(expectedLabel);
                }
                row.add(Integer.toString(value));
            }

            result.add(row);
        }

        return result;
    }

    @Override
    public String toString()
    {
        String f = "%10s";

        List<List<String>> table = prepareToString();
        StringBuilder sb = new StringBuilder();

        for (List<String> row : table) {
            for (String value : row) {
                sb.append(String.format(f, value));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String toStringLatex()
    {
        List<List<String>> table = prepareToString();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < table.size(); i++) {
            List<String> row = table.get(i);

            for (int j = 0; j < row.size(); j++) {
                String value = row.get(j);

                if ((i == 0 || j == 0) && !value.isEmpty()) {
                    sb.append("\\textbf{" + value + "} ");
                }
                else {
                    sb.append(value);
                    sb.append(" ");
                }

                if (j < row.size() - 1) {
                    sb.append("& ");
                }
            }

            sb.append("\\\\\n");
        }

        return sb.toString();
    }

    private String f(double val)
    {
        return String.format(Locale.ENGLISH, getFormat(), val);
    }

    public String printNiceResults()
    {
        return "Macro F-measure: " + f(getMacroFMeasure()) + " (CI at .95: "
                + f(getConfidence95MacroFM()) + "; [" + f(getConfidence95MacroFMLow()) + "-"
                + f(getConfidence95MacroFMHigh()) + "]), Accuracy: " + f(getMicroFMeasure())
                + " (CI at .95: " + f(getConfidence95Accuracy()) + " ["
                + f(getConfidence95AccuracyLow()) + "-" + f(getConfidence95AccuracyHigh()) + "])";
    }

    public String printLabelPrecRecFm()
    {
        Map<String, Double> precisionForLabels = getPrecisionForLabels();
        Map<String, Double> recallForLabels = getRecallForLabels();
        Map<String, Double> fMForLabels = getFMeasureForLabels();

        StringBuilder sb = new StringBuilder("P/R/Fm: ");

        for (Map.Entry<String, Double> entry : precisionForLabels.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(String.format(Locale.ENGLISH, getFormat(), entry.getValue()));
            sb.append("/");
            sb.append(String.format(Locale.ENGLISH, getFormat(),
                    recallForLabels.get(entry.getKey())));
            sb.append("/");
            sb.append(String.format(Locale.ENGLISH, getFormat(), fMForLabels.get(entry.getKey())));
            sb.append(" ");
        }

        return sb.toString();
    }

    /**
     * Returns the series of actual labels
     *
     * @return A Csv table with Precision, Recall, Accuracy, 95% CI for Accuracy, Macro F1, 95% CI for F1
     */

    public String resultsCsv()
    {
        Map<String, Double> precisionForLabels = getPrecisionForLabels();
        Map<String, Double> recallForLabels = getRecallForLabels();
        String csv = "";
        for (Map.Entry<String, Double> entry : precisionForLabels.entrySet()) {
            csv += "Precision " + (entry.getKey()) + ", "
                    + String.format(Locale.ENGLISH, getFormat(), 100 * entry.getValue()) + "\n";
            csv += "Recall "
                    + (entry.getKey())
                    + ", "
                    + String.format(Locale.ENGLISH, getFormat(),
                            100 * recallForLabels.get(entry.getKey())) + "\n";
        }
        csv += "Accuracy, " + f(100 * getMicroFMeasure()) + "\n";
        csv += "95_CI_Acc, " + f(100 * getConfidence95MacroFMLow()) + "-"
                + f(100 * getConfidence95MacroFMHigh()) + "\n";
        csv += "Macro F1, " + f(100 * getMacroFMeasure()) + "\n";
        csv += "95_CI_F1, " + f(100 * getConfidence95MacroFMLow()) + "-"
                + f(100 * getConfidence95MacroFMHigh()) + "\n";
        return csv;
    }

    public double getAvgPrecision()
    {
        double res = 0;
        Collection<Double> values = getPrecisionForLabels().values();
        for (double d : values) {
            res += d;
        }

        return res / values.size();
    }

    public double getAvgRecall()
    {
        double res = 0;
        Collection<Double> values = getRecallForLabels().values();
        for (double d : values) {
            res += d;
        }

        return res / values.size();
    }

    public static ConfusionMatrix parseFromText(String text)
        throws IllegalArgumentException
    {
        try {

            String[] lines = text.split("\n");
            String[] l = lines[0].split("\\s+");

            List<String> labels = new ArrayList<String>();
            for (int i = 0; i < l.length; i++) {
                if (!l[i].isEmpty()) {
                    labels.add(l[i]);
                }
            }

            ConfusionMatrix result = new ConfusionMatrix();

            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];

                String[] split = line.split("\\s+");

                List<String> row = new ArrayList<String>();
                for (int j = 0; j < split.length; j++) {
                    if (!split[j].isEmpty()) {
                        row.add(split[j]);
                    }
                }

                String predictedLabel = row.get(0);

                for (int r = 1; r < row.size(); r++) {
                    String s = row.get(r);
                    Integer val = Integer.valueOf(s);

                    String acutalLabel = labels.get(r - 1);

                    result.increaseValue(predictedLabel, acutalLabel, val);
                }
            }

            return result;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Wrong input format", e);
        }
    }

    public static void main(String[] args)
    {
        {
            ConfusionMatrix cm = new ConfusionMatrix();
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "2");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "3");
            cm.increaseValue("1", "2");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "3");
            cm.increaseValue("1", "3");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("3", "3");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("3", "3");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("3", "3");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("3", "3");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "1");
            cm.increaseValue("1", "3");
            cm.increaseValue("1", "2");
            cm.increaseValue("2", "3");

            System.out.println(cm);
            System.out.println(cm.getCohensKappa());
            System.out.println(cm.printLabelPrecRecFm());
            System.out.println(cm.printNiceResults());
        }

        {
            // after corrections
            ConfusionMatrix cm = new ConfusionMatrix();
            cm.increaseValue("1", "1", 30);
            cm.increaseValue("3", "3", 12);

            cm.increaseValue("1", "2", 5);
            // cm.increaseValue("1", "1", 5);

            cm.increaseValue("1", "3", 3);
            cm.increaseValue("2", "3", 1);
            // cm.increaseValue("3", "3", 1);

            System.out.println(cm);
            System.out.println(cm.getCohensKappa());
            System.out.println(cm.printLabelPrecRecFm());
            System.out.println(cm.printNiceResults());
        }

        {
            // new
            ConfusionMatrix cm = new ConfusionMatrix();
            cm.increaseValue("1", "1", 4);
            cm.increaseValue("3", "3", 4);
            cm.increaseValue("1", "3");
            cm.increaseValue("2", "3");

            System.out.println(cm);
            System.out.println(cm.getCohensKappa());
            System.out.println(cm.printLabelPrecRecFm());
            System.out.println(cm.printNiceResults());
        }

        {
            // new after correction
            ConfusionMatrix cm = new ConfusionMatrix();
            cm.increaseValue("1", "1", 4);
            cm.increaseValue("3", "3", 5);
            cm.increaseValue("1", "3");

        }

        {
            ConfusionMatrix cm = new ConfusionMatrix();
            cm.increaseValue("1", "1", 750);
            cm.increaseValue("1", "2", 250);
            cm.increaseValue("2", "1", 500);
            cm.increaseValue("2", "2", 500);

            System.out.println(cm);
            System.out.println(cm.printLabelPrecRecFm());
            System.out.println(cm.printNiceResults());

        }
        {
            ConfusionMatrix cm = new ConfusionMatrix();
            cm.increaseValue("T", "T", 67);
            cm.increaseValue("T", "H", 33);
            cm.increaseValue("H", "H", 0);

            System.out.println(cm);
            System.out.println(cm.printLabelPrecRecFm());
            System.out.println(cm.printNiceResults());
            System.out.println(cm.getAccuracy() + " " + cm.getConfidence95AccuracyLow() + " "
                    + cm.getConfidence95AccuracyHigh());
            System.out.println(cm.getAccuracy() + " " + cm.getConfidence90AccuracyLow() + " "
                    + cm.getConfidence90AccuracyHigh());
        }
        {
            ConfusionMatrix cm = new ConfusionMatrix();
            cm.increaseValue("T", "T", 55);
            cm.increaseValue("T", "H", 45);
            cm.increaseValue("H", "H", 0);

            System.out.println(cm);
            System.out.println(cm.printLabelPrecRecFm());
            System.out.println(cm.printNiceResults());
            System.out.println(cm.getAccuracy() + " " + cm.getConfidence95AccuracyLow() + " "
                    + cm.getConfidence95AccuracyHigh());
            System.out.println(cm.getAccuracy() + " " + cm.getConfidence90AccuracyLow() + " "
                    + cm.getConfidence90AccuracyHigh());

        }

    }

}
/*

 */
