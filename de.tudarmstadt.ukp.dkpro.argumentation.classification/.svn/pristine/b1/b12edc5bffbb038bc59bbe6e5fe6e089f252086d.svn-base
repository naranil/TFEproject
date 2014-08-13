package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.pos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.tc.api.exception.TextClassificationException;
import de.tudarmstadt.ukp.dkpro.tc.api.features.DocumentFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;
import de.tudarmstadt.ukp.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;

public class ModalVerbExtractor
    extends FeatureExtractorResource_ImplBase
    implements DocumentFeatureExtractor

/**
*
* @author Anil Narassiguin
* @return Ratio and Total number of modal verbs
*
*/
{
    static final String MODAL = "modal";

    @Override
    public List<Feature> extract(JCas jcas)
        throws TextClassificationException
    {

        double nbModal = 0;
        double totalPos = 0;
        for (Token token : JCasUtil.select(jcas, Token.class)) {
            if (token.getPos().getPosValue().startsWith("MB")) {
                nbModal++;
            }
            totalPos++;
        }

        List<Feature> featList = new ArrayList<Feature>();
        featList.addAll(Arrays.asList(new Feature(MODAL + "_total", nbModal)));
        featList.addAll(Arrays.asList(new Feature(MODAL + "_ratio", nbModal / totalPos)));
        return featList;
    }
}
