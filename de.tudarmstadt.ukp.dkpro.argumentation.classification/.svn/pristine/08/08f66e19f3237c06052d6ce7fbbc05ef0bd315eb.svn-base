package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.pos;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.tc.api.exception.TextClassificationException;
import de.tudarmstadt.ukp.dkpro.tc.api.features.DocumentFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;
import de.tudarmstadt.ukp.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Compute the ratio and the total number of all kind of adjectives (adjectives, comparative, superlative)

public class SimpleAdjectiveExtractor
        extends FeatureExtractorResource_ImplBase
        implements DocumentFeatureExtractor
{
    static final String ADJECTIVE = "adjective";

    @Override
    public List<Feature> extract(JCas jcas)
            throws TextClassificationException
    {

        double nb_adjective = 0;
        double total_pos = 0;
        for (Token token : JCasUtil.select(jcas, Token.class)) {
            if (token.getPos().getPosValue().startsWith("JJ")) {
                nb_adjective += 1;
            }
            total_pos += 1;

        }

        List<Feature> featList = new ArrayList<Feature>();
        featList.addAll(Arrays.asList(new Feature(ADJECTIVE + "_total", nb_adjective)));
        featList.addAll(Arrays.asList(new Feature(ADJECTIVE + "_ratio", nb_adjective / total_pos)));
        return featList;
    }
}
