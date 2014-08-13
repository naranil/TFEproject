package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.syntactic;

import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.ROOT;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.S;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.SBAR;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.SBARQ;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.SINV;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.SQ;
import de.tudarmstadt.ukp.dkpro.tc.api.exception.TextClassificationException;
import de.tudarmstadt.ukp.dkpro.tc.api.features.DocumentFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;
import de.tudarmstadt.ukp.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;

/**
 *
 * @author Anil Narassiguin Feature: Mean of clause per sentence and max number of clause in a
 *         sentence
 * @return The mean number of clauses per sentences
 * @return Max number of clause in a sentence
 * From "Identifying Argumentative Discourse Strutures in Persuasive Essays"
 *
 */

/*
 * We consider the five categories of clauses described in Penn Treebank
 * http://web.mit.edu/6.863/www/PennTreebankTags.html#Clause S - simple declarative clause, i.e. one
 * that is not introduced by a (possible empty) subordinating conjunction or a wh-word and that does
 * not exhibit subject-verb inversion. SBAR - Clause introduced by a (possibly empty) subordinating
 * conjunction. SBARQ - Direct question introduced by a wh-word or a wh-phrase. Indirect questions
 * and relative clauses should be bracketed as SBAR, not SBARQ. SINV - Inverted declarative
 * sentence, i.e. one in which the subject follows the tensed verb or modal. SQ - Inverted yes/no
 * question, or main clause of a wh-question, following the wh-phrase in SBARQ.
 */
public class SubClausesRatio
    extends FeatureExtractorResource_ImplBase
    implements DocumentFeatureExtractor
{

    static final String SUBCLAUSES = "sub_clauses_sentences";

    @Override
    public List<Feature> extract(JCas jcas)
        throws TextClassificationException
    {
        double nbSentences = select(jcas, Sentence.class).size();
        double nbClauses = 0;
        double maxClauses = 0;

        for (Sentence sentence : select(jcas, Sentence.class)) {
            for (ROOT root : selectCovered(ROOT.class, sentence)) {

                double nbClauseSentence = selectCovered(S.class, root).size()
                        + selectCovered(SBAR.class, root).size()
                        + selectCovered(SBARQ.class, root).size()
                        + selectCovered(SINV.class, root).size()
                        + selectCovered(SQ.class, root).size();

                nbClauses += nbClauseSentence;

                if (nbClauseSentence > maxClauses) {
                    maxClauses = nbClauseSentence;
                }
            }

        }

        try {
            nbClauses /= nbSentences;
        }
        catch (ArithmeticException e) {
            nbClauses = 0;
        }

        List<Feature> featList = new ArrayList<Feature>();
        featList.add(new Feature(SUBCLAUSES + "_mean", nbClauses));
        featList.add(new Feature(SUBCLAUSES + "_max", maxClauses));
        return featList;

    }
}
