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

/**
*
* @author Anil Narassiguin
* @return Ratio and Total number of superlative words (adjectives and adverbs)
*
*/

public class SuperlativeExtractor
        extends FeatureExtractorResource_ImplBase
        implements DocumentFeatureExtractor
{
    static final String SUPERLATIVE = "superlative";

    @Override
    public List<Feature> extract(JCas jcas)
            throws TextClassificationException
    {

        double nb_superlative = 0;
        double total_pos = 0;
        for (Token token : JCasUtil.select(jcas, Token.class)) {
            if (token.getPos().getPosValue().startsWith("JJR") ||
                    token.getPos().getPosValue().startsWith("RBS")) {
                nb_superlative += 1;
            }
            total_pos += 1;

        }

        List<Feature> featList = new ArrayList<Feature>();
        featList.addAll(Arrays.asList(new Feature(SUPERLATIVE + "_total", nb_superlative)));
        featList.addAll(
                Arrays.asList(new Feature(SUPERLATIVE + "_ratio", nb_superlative / total_pos)));
        return featList;
    }
}
