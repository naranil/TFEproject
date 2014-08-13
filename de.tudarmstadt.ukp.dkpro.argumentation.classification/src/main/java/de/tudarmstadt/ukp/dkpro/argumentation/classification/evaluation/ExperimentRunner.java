package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ExperimentRunner
{
    static Logger logger = Logger.getLogger(ExperimentRunner.class.getName());

    public static void main(String[] args) throws IOException
    {
        // different settings
        List<AbstractEvaluationScenario> evaluationScenarios = new ArrayList<>();

        // baseline
        evaluationScenarios.add(new WithDependencies());


        long startTime = System.currentTimeMillis();
        for (AbstractEvaluationScenario evaluationScenario : evaluationScenarios) {
            logger.info("Evaluation scenario: " + evaluationScenario);

            // first: full cross validation
            evaluationScenario.runFullCrossValidation();

            // in-domain for all domains
            for (DocumentDomain documentDomain : DocumentDomain.values()) {
                evaluationScenario.runInDomainCrossValidation(documentDomain);
            }

            // cross-domain for all domains
            for (DocumentDomain documentDomain : DocumentDomain.values()) {
                evaluationScenario.runCrossDomain(documentDomain);
            }

//            evaluationScenario.runInDomainCrossValidation(DocumentDomain.PRAYER_IN_SCHOOLS);
//            evaluationScenario.runCrossDomain(DocumentDomain.MAINSTREAMING);
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println(totalTime);



            Runtime rt = Runtime.getRuntime();
            rt.exec("python src/main/python/end.py");
            rt.exec("python src/main/python/getMarkdown.py");
        }

    }

    public static void main1test(String[] args)
    {
//        new BaselineNGram().runInDomainCrossValidation(DocumentDomain.PUBLIC_PRIVATE_SCHOOLS);
        new BaselineNGram().runCrossDomain(DocumentDomain.PUBLIC_PRIVATE_SCHOOLS);
    }

}
