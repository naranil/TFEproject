package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.syntactic;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.ROOT;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.util.TreeUtils;
import de.tudarmstadt.ukp.dkpro.tc.api.features.DocumentFeatureExtractor;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;
import de.tudarmstadt.ukp.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;
import edu.stanford.nlp.trees.Tree;

public class DependencyTreeDepth
extends FeatureExtractorResource_ImplBase
implements DocumentFeatureExtractor

/**
 * @author Anil Narassiguin
 * @return The mean of Standford's trees depth
 * @return The depth of the biggest tree
 *
 *  The code was inspired from Christian Stab's project
 *  q.v. Identifying Argumentative Discourse Structures in Persuasive Essays
 *
 */
{

    public static final String FN_DEPENDENCY_TREE_DEPTH = "DependencyTreeDepth";

    @Override
    public List<Feature> extract(JCas jcas){
        List<Feature> featList = new ArrayList<Feature>();
        double meanTreeDepth = 0;
        double maxTreeDepth = 0;
        double nbSentences = select(jcas, Sentence.class).size();

        for (Sentence sentence : select(jcas, Sentence.class)){

            Collection<ROOT> root = JCasUtil.selectCovered(ROOT.class,sentence);

            if (!root.isEmpty()) {
                Tree tree = TreeUtils.createStanfordTree(root.iterator().next());
                double treeDepth = tree.depth();
                meanTreeDepth += treeDepth;

                if (treeDepth > maxTreeDepth){
                    maxTreeDepth = treeDepth;
                }
            }

        }

        meanTreeDepth /= nbSentences;

        featList.add(new Feature(FN_DEPENDENCY_TREE_DEPTH + "_mean", meanTreeDepth));
        featList.add(new Feature(FN_DEPENDENCY_TREE_DEPTH + "_max", maxTreeDepth));

        return featList;
    }

}
