package de.tudarmstadt.ukp.dkpro.argumentation.classification.ngram.lemma;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.text.AnnotationFS;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.NGram;

public class NGramLemmaIterable<T extends AnnotationFS>
implements Iterable<NGram>
{
    List<NGram> nGramLemmaList;

    private NGramLemmaIterable(Iterable<Lemma> tokens, int n)
    {
        this.nGramLemmaList = createNGramLemmaList(tokens, n);
    }

    public static <T extends AnnotationFS> NGramLemmaIterable<Lemma> create(Iterable<Lemma> tokens, int n)
    {
        return new NGramLemmaIterable<Lemma>(tokens, n);
    }

    @Override
    public Iterator<NGram> iterator()
    {
        return nGramLemmaList.iterator();
    }

    private List<NGram> createNGramLemmaList(Iterable<Lemma> lemmas, int n)
    {
        List<NGram> nGramsLemma = new ArrayList<NGram>();

        // fill token list
        List<Lemma> lemmaList = new ArrayList<Lemma>();
        for (Lemma lemma : lemmas) {
            lemmaList.add(lemma);
        }

        // remove last element, if it contains a punctuation mark
        if (lemmaList.size() > 0) {
            String lastElementText = lemmaList.get(lemmaList.size() - 1).getValue().toUpperCase();
            if (lastElementText.length() == 1
                    && (lastElementText.equals(".")
                            || lastElementText.equals("!") || lastElementText.equals("?"))) {
                lemmaList.remove(lemmaList.size() - 1);
            }
        }

        for (int k = 1; k <= n; k++) {
            // if the number of tokens is less than k => break
            if (lemmaList.size() < k) {
                break;
            }
            nGramsLemma.addAll(getNGramsLemma(lemmaList, k));
        }

        return nGramsLemma;
    }

    private List<NGram> getNGramsLemma(List<Lemma> lemmaList, int k)
    {
        List<NGram> nGramsLemma = new ArrayList<NGram>();

        int size = lemmaList.size();
        for (int i = 0; i < (size + 1 - k); i++) {
            try {
                NGram ngramLemma = new NGram(lemmaList.get(i).getCAS().getJCas(), lemmaList.get(i)
                        .getBegin(), lemmaList.get(i + k - 1).getEnd());
                ngramLemma.setText(getLemmaText(lemmaList, i, i + k - 1));
                nGramsLemma.add(ngramLemma);
            }
            catch (CASException e) {
                throw new IllegalStateException(e);
            }
        }

        return nGramsLemma;
    }

    private String getLemmaText(List<Lemma> lemmaList, int start, int end)
    {
        List<String> tokenTexts = new ArrayList<String>();
        for (int i = start; i <= end; i++) {
            tokenTexts.add(lemmaList.get(i).getValue());
        }
        return StringUtils.join(tokenTexts, "_");
    }
}
