package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.lexical;

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
 * @return Statistics about tokens (nb of Tokens, size in letters of the biggest token,
 * size of the smallest token, average size of tokens
 *
 */

public class SimpleTokenExtractor
    extends FeatureExtractorResource_ImplBase
    implements DocumentFeatureExtractor
{
    static final String TOKEN = "token";

    @Override
    public List<Feature> extract(JCas jcas)
        throws TextClassificationException
    {

        double nbToken = 0;
        double minToken = -1; // Sizes in letter
        double maxToken = 0;
        double meanToken = 0;
        for (Token token : JCasUtil.select(jcas, Token.class)) {
            nbToken++;
            if (minToken < 0) {
                minToken = token.getCoveredText().length(); // gets the size value of the first
                                                            // token
            }
            if (minToken > token.getCoveredText().length()) {
                minToken = token.getCoveredText().length();
            }
            if (maxToken < token.getCoveredText().length()) {
                maxToken = token.getCoveredText().length();
            }

            meanToken += token.getCoveredText().length();
        }
        try {
            meanToken /= nbToken;
        }
        catch (Exception e) {
            meanToken = 0;
        }

        List<Feature> featList = new ArrayList<Feature>();
        featList.addAll(Arrays.asList(new Feature("nb_" + TOKEN, nbToken)));
        featList.addAll(Arrays.asList(new Feature("max_" + TOKEN + "_size", maxToken)));
        featList.addAll(Arrays.asList(new Feature("min_" + TOKEN + "_size", minToken)));
        featList.addAll(Arrays.asList(new Feature("mean_" + TOKEN + "_size", meanToken)));
        return featList;
    }
}