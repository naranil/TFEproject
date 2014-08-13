package de.tudarmstadt.ukp.dkpro.argumentation.classification;

import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.tc.core.io.SingleLabelReaderBase;

public class CorpusReaderTest
    extends SingleLabelReaderBase{

    @Override
    public String getTextClassificationOutcome(JCas jcas)
            throws CollectionException
    {

            String uriString = DocumentMetaData.get(jcas).getDocumentTitle();

            if (uriString.contains("P1")){
                return "P1";
            }
            else if (uriString.contains("P2")) {
                return "P2";
            }

            else {
                return "unknown";
            }

    }

}