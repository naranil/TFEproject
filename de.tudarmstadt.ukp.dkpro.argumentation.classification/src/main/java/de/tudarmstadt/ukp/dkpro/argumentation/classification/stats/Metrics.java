package de.tudarmstadt.ukp.dkpro.argumentation.classification.stats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

// note: a bit far-fetched because I read my results in a csv generated by the code
// but I'll try to change it soon
public class Metrics
{
    public static void main(String[] args)
    {
         runAnalysis();

    }

    public static void runAnalysis()
    {
        String csvFile = getDirectory();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        System.out.println(csvFile);
        double TP = 0;
        double TN = 0;
        double FP = 0;
        double FN = 0;
        Vector<String> vc = new Vector<String>();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] matrix = line.split(cvsSplitBy);
                // results[i] =
                for (int i = 0; i < 3; i++) {
                    vc.add(matrix[i]);
                }
            }

            TP = Double.parseDouble(vc.get(4).replace("\"", ""));
            TN = Double.parseDouble(vc.get(8).replace("\"", ""));
            FP = Double.parseDouble(vc.get(5).replace("\"", ""));
            FN = Double.parseDouble(vc.get(7).replace("\"", ""));

            System.out.println("TP: "+ TP + " TN: " + TN + " FP: "+ FP + " FN: "+ FN);
            System.out.println("Precison: " + precision(TP, FP));
            System.out.println("Recall: " + recall(TP, FN));
            System.out.println("Accuracy: " + accuracy(TP, TN, FP, FN));
            System.out.println("F-measure: " + fMeasure(TP, TN, FP, FN));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String getDirectory()
    {
        String path = "/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository";

        String files = "";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            if (listOfFile.getName().startsWith("Batch")) {
                files = listOfFile.getName();
            }

        }
        return "/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository/"
                + files + "/confusionMatrix.csv";
    }

    public static double precision(double TP, double FP){
        double pred = 0;
        try{
            pred = TP / (TP + FP);

        }
        catch (ArithmeticException e){
            e.printStackTrace();
        }
        return pred;
    }

    public static double recall(double TP, double FN){
        double rec = 0;
        try{
            rec = TP / (TP  + FN);
        }
        catch (ArithmeticException e){
            e.printStackTrace();
        }
        return rec;
    }

    public static double accuracy(double TP, double TN, double FP, double FN){
        double acc = 0;
        try{
            acc = (TP + TN) / (TP + TN + FP + FN);
        }
        catch (ArithmeticException e){
            e.printStackTrace();
        }
        return acc;
    }

    public static double fMeasure(double TP, double TN, double FP, double FN){
        double fmes = 0;
        try{
            fmes = (2 * precision(TP, FP) * recall(TP, FN)) / (precision(TP, FP) + recall(TP, FN));
        }
        catch (ArithmeticException e){
            e.printStackTrace();
        }
        return fmes;
    }

}
