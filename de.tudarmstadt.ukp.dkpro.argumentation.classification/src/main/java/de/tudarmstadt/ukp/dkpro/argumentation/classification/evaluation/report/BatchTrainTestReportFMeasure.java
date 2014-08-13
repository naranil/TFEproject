package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation.report;

import de.tudarmstadt.ukp.dkpro.lab.reporting.BatchReportBase;
import de.tudarmstadt.ukp.dkpro.lab.reporting.FlexTable;
import de.tudarmstadt.ukp.dkpro.lab.storage.StorageService;
import de.tudarmstadt.ukp.dkpro.lab.storage.impl.PropertiesAdapter;
import de.tudarmstadt.ukp.dkpro.lab.task.Task;
import de.tudarmstadt.ukp.dkpro.lab.task.TaskContextMetadata;
import de.tudarmstadt.ukp.dkpro.tc.core.Constants;
import de.tudarmstadt.ukp.dkpro.tc.core.util.ReportUtils;
import de.tudarmstadt.ukp.dkpro.tc.weka.task.BatchTaskCrossValidation;
import de.tudarmstadt.ukp.dkpro.tc.weka.task.TestTask;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.*;

/**
 *
 */
@Deprecated
public class BatchTrainTestReportFMeasure
        extends BatchReportBase
        implements Constants
{

    private static final List<String> discriminatorsToExclude = Arrays.asList(new String[] {
            "files_validation", "files_training" });

    @Override
    public void execute()
            throws Exception
    {
        StorageService store = getContext().getStorageService();

        FlexTable<String> table = FlexTable.forClass(String.class);

        Map<String, List<Double>> key2resultValues = new HashMap<String, List<Double>>();
        Map<List<String>, Double> confMatrixMap = new HashMap<List<String>, Double>();

        for (TaskContextMetadata subcontext : getSubtasks()) {
            if (subcontext.getType().startsWith(TestTask.class.getName())) {

                File confMatrix = store.getStorageFolder(subcontext.getId(), CONFUSIONMATRIX_KEY);

                if (confMatrix.isFile()) {
                    confMatrixMap = ReportUtils.updateAggregateMatrix(confMatrixMap, confMatrix);
                }

            }
        }

        // this report is reused in CV, and we only want to aggregate confusion matrices from folds
        // in CV
        if (getContext().getId().startsWith(BatchTaskCrossValidation.class.getSimpleName())) {
            FlexTable<String> confMatrix = ReportUtils.createOverallConfusionMatrix(confMatrixMap);
            getContext().storeBinary(CONFUSIONMATRIX_KEY, confMatrix.getCsvWriter());
        }

        // output the location of the batch evaluation folder
        // otherwise it might be hard for novice users to locate this
        File dummyFolder = store.getStorageFolder(getContext().getId(), "dummy");
        // TODO can we also do this without creating and deleting the dummy folder?
        getContext().getLoggingService().message(getContextLabel(),
                "Storing detailed results in:\n" + dummyFolder.getParent() + "\n");
        dummyFolder.delete();
    }

    private String getKey(Map<String, String> discriminatorsMap)
    {
        Set<String> sortedDiscriminators = new TreeSet<String>(discriminatorsMap.keySet());

        List<String> values = new ArrayList<String>();
        for (String discriminator : sortedDiscriminators) {
            values.add(discriminatorsMap.get(discriminator));
        }
        return StringUtils.join(values, "_");
    }
}