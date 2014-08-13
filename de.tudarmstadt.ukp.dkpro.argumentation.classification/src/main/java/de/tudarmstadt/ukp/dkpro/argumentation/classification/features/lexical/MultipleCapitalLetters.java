package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.lexical;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.tc.api.exception.TextClassificationException;
import de.tudarmstadt.ukp.dkpro.tc.api.features.DocumentFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;
import de.tudarmstadt.ukp.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;

/**
 *
 * @author Anil Narassiguin
 * @return The number of tokens with more than two capital letters or one capital letter which is
 *         not the first one.
 *
 */

public class MultipleCapitalLetters
    extends FeatureExtractorResource_ImplBase
    implements DocumentFeatureExtractor
{

    @Override
    public List<Feature> extract(JCas jcas)
        throws TextClassificationException
    {
        int nbWordsCapitalLetters = 0;
        for (Token token : select(jcas, Token.class)) {
            int nbCapitalLetters = 0;

            for (int i = 0; i < token.getCoveredText().length(); i++) {
                char letter = token.getCoveredText().charAt(i);
                char letterUpperCase = Character.toUpperCase(letter);

                if (letter == letterUpperCase) {
                    nbCapitalLetters++;
                }
            }

            if (nbCapitalLetters > 1) {
                nbWordsCapitalLetters++;
            }

            if (nbCapitalLetters == 1
                    && token.getCoveredText().charAt(0) != Character.toUpperCase(token
                            .getCoveredText().charAt(0))) {
                nbWordsCapitalLetters++;
            }

        }
        List<Feature> featList = new ArrayList<Feature>();
        featList.add(new Feature("nb_tokens_with_multiple_capital_letters", nbWordsCapitalLetters));
        return featList;
    }
}
