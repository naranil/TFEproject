package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation.report;

import java.util.Properties;

import de.tudarmstadt.ukp.dkpro.lab.reporting.BatchReportBase;
import de.tudarmstadt.ukp.dkpro.lab.storage.StorageService;
import de.tudarmstadt.ukp.dkpro.lab.storage.impl.PropertiesAdapter;
import de.tudarmstadt.ukp.dkpro.lab.task.TaskContextMetadata;
import de.tudarmstadt.ukp.dkpro.tc.core.Constants;
import de.tudarmstadt.ukp.dkpro.tc.weka.task.TestTask;

/**
 *
 * Reports the N-first features determined with Information Gain
 * @author Anil Narassiguin
 *
 */
public class BatchFeatureSelection
    extends BatchReportBase
    implements Constants
{

    @Override
    public void execute()
        throws Exception
    {
        StorageService store = getContext().getStorageService();

        Properties props = new Properties();

        for (TaskContextMetadata subcontext : getSubtasks()) {
            if (subcontext.getType().startsWith(TestTask.class.getName())) {
                props.putAll(store.retrieveBinary(subcontext.getId(), FEATURE_SELECTION_DATA_FILENAME, new PropertiesAdapter()).getMap());
            }
        }

        getContext().storeBinary(FEATURE_SELECTION_DATA_FILENAME, new PropertiesAdapter(props));

    }

}
