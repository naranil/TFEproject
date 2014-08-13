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

// Compute the ratio and the total number of punctuation symbols ("," ";" ":" "." "!" "?")

/**
 *
 * @author Anil Narassiguin
 * @deprecated I created this feature at the beginning Replaced by
 * {@link #PunctuationFeature}
 *
 */

@Deprecated
public class SimplePunctuationExtractor
        extends FeatureExtractorResource_ImplBase
        implements DocumentFeatureExtractor
{
    static final String PUNCTUATION = "punctuation";

    @Override
    public List<Feature> extract(JCas jcas)
            throws TextClassificationException
    {

        double nb_punc = 0;
        double total_pos = 0;
        for (Token token : JCasUtil.select(jcas, Token.class)) {
            if (token.getPos().getPosValue().startsWith(".")
                    || token.getPos().getPosValue().startsWith(",")
                    || token.getPos().getPosValue().startsWith(":")) {
                nb_punc += 1;
            }
            total_pos += 1;

        }

        List<Feature> featList = new ArrayList<Feature>();
        featList.addAll(Arrays.asList(new Feature(PUNCTUATION + "_total", nb_punc)));
        featList.addAll(Arrays.asList(new Feature(PUNCTUATION + "_ratio", nb_punc / total_pos)));
        return featList;
    }
}
