package de.tudarmstadt.ukp.dkpro.argumentation.classification.features.utils;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.features.syntactic.ProductionRules;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.ROOT;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.util.TreeUtils;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.meta.FreqDistBasedMetaCollector;
import edu.stanford.nlp.trees.Tree;

/**
 *
 * @author Christian Stab (for the first version with sentences as units)
 * @author Anil Narassiguin (texts as units)
 * Generates all the syntactic rules from the Stanford Tree.
 *
 */
public class ProductionRulesCollector
    extends FreqDistBasedMetaCollector
{

    public static final String PRODRULES_FD_KEY = "prodRules.ser";
    @ConfigurationParameter(name = ProductionRules.PARAM_PRODRULES_FD_FILE, mandatory = true)
    private File prodRulesFdFile;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

    }

    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {

        FrequencyDistribution<String> rules = new FrequencyDistribution<String>();
        for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
            Collection<ROOT> root = JCasUtil.selectCovered(ROOT.class, sentence);
            if (!root.isEmpty()) {
                Tree tree = TreeUtils.createStanfordTree(root.iterator().next());
                getProductionRules(tree, rules);
            }
        }

        for (String ngram : rules.getKeys()) {
            fd.addSample(ngram, rules.getCount(ngram));
        }
    }

    public static FrequencyDistribution<String> getProductionRules(Tree tree,
            FrequencyDistribution<String> rules)
    {
        if (tree.getChildrenAsList().size() > 1) {
            String rule = tree.value() + "->";
            for (Tree t : tree.getChildrenAsList()) {
                rule = rule + t.value() + ",";
            }
            rules.addSample(rule, 1);
        }

        for (Tree t : tree.getChildrenAsList()) {
            getProductionRules(t, rules);
        }

        return rules;
    }

    @Override
    public Map<String, String> getParameterKeyPairs()
    {
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put(ProductionRules.PARAM_PRODRULES_FD_FILE, PRODRULES_FD_KEY);
        return mapping;
    }

    @Override
    protected File getFreqDistFile()
    {
        return prodRulesFdFile;
    }
}