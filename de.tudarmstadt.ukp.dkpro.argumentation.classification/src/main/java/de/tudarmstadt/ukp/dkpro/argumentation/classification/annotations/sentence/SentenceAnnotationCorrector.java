package de.tudarmstadt.ukp.dkpro.argumentation.classification.annotations.sentence;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

/**
 * If there is no {@linkplain de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence} annotated
 * in the document, it will create one sentence annotation spanning the whole document.
 *
 * @author Ivan Habernal
 */
public class SentenceAnnotationCorrector
        extends JCasAnnotator_ImplBase
{
    @Override public void process(JCas aJCas)
            throws AnalysisEngineProcessException
    {
        Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);

        if (sentences.isEmpty()) {
            Sentence sentence = new Sentence(aJCas);

            sentence.setBegin(0);
            sentence.setEnd(aJCas.getDocumentText().length());

            sentence.addToIndexes(aJCas);
        }
    }
}