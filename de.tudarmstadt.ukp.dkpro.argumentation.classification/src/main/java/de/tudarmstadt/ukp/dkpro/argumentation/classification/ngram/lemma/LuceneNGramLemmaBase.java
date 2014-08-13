package de.tudarmstadt.ukp.dkpro.argumentation.classification.ngram.lemma;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;

import com.google.common.collect.MinMaxPriorityQueue;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.NGram;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.tc.api.features.meta.MetaCollector;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.base.NGramFeatureExtractorBase;
import de.tudarmstadt.ukp.dkpro.tc.features.ngram.util.TermFreqTuple;

public class LuceneNGramLemmaBase
    extends NGramFeatureExtractorBase

{
    public static final int n = 3;
    public static final String PARAM_LUCENE_DIR = "luceneDir";
    @ConfigurationParameter(name = PARAM_LUCENE_DIR, mandatory = true)
    protected File luceneDir;

    private FrequencyDistribution<String> trainingFD;

    public static final String LUCENE_NGRAM_LEMMA_FIELD = "ngramLemma";

    @Override
    public List<Class<? extends MetaCollector>> getMetaCollectorClasses()
    {
        List<Class<? extends MetaCollector>> metaCollectorClasses = new ArrayList<Class<? extends MetaCollector>>();
        metaCollectorClasses.add(LuceneNGramLemmaMetaCollector.class);

        return metaCollectorClasses;
    }

    @Override
    protected String getFieldName()
    {
        return LUCENE_NGRAM_LEMMA_FIELD;
    }

    @Override
    protected String getFeaturePrefix()
    {
        return "ngramLemma";
    }

    @Override
    protected int getTopN()
    {
        return ngramUseTopK;
    }

    @Override
    protected FrequencyDistribution<String> getTopNgrams()
        throws ResourceInitializationException
    {
        FrequencyDistribution<String> topNGrams = new FrequencyDistribution<String>();

        MinMaxPriorityQueue<TermFreqTuple> topN = MinMaxPriorityQueue.maximumSize(getTopN())
                .create();

        JCasIterable pipeline = new JCasIterable(
                // Read input
                createReaderDescription(XmiReader.class, XmiReader.PARAM_SOURCE_LOCATION,
                        "src/main/resources/gold_data_xmi", XmiReader.PARAM_LANGUAGE, "en",
                        XmiReader.PARAM_PATTERNS, XmiReader.INCLUDE_PREFIX + "*.xmi"));

        for (JCas jcas : pipeline){
            Collection<Lemma> tokens = JCasUtil.select(jcas, Lemma.class);
            NGramLemmaIterable<Lemma> ngrams = NGramLemmaIterable.create(tokens, ngramMaxN);
            Iterator<NGram> ngramIterator = ngrams.iterator();
//
//              while (ngramIterator.hasNext()){
//              String term = ngramIterator.next().getText();
//              long freq = termsEnum.totalTermFreq();
//              topN.add(element)
//          }
//        }

        IndexReader reader;
        try {
            reader = DirectoryReader.open(FSDirectory.open(luceneDir));
            Fields fields = MultiFields.getFields(reader);
            if (fields != null) {
                Terms terms = fields.terms(getFieldName());
                if (terms != null) {
                    TermsEnum termsEnum = terms.iterator(null);
//                    BytesRef text = null;
                    while (ngramIterator.hasNext()) {
                      String term = ngramIterator.next().getText();
                      long freq = termsEnum.totalTermFreq();
                        if (passesScreening(term)) {
                            topN.add(new TermFreqTuple(term, freq));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
        }

        int size = topN.size();
        for (int i = 0; i < size; i++) {
            TermFreqTuple tuple = topN.poll();
            topNGrams.addSample(tuple.getTerm(), tuple.getFreq());
        }

        getLogger().log(Level.INFO, "+++ TAKING " + topNGrams.getB() + " NGRAMS");

        return topNGrams;

    }

//        IndexReader reader;
//        try {
//            reader = DirectoryReader.open(FSDirectory.open(luceneDir));
//            System.out.println(luceneDir);
//            Fields fields = MultiFields.getFields(reader);
//            if (fields != null) {
//                Terms terms = fields.terms(getFieldName());
//                if (terms != null) {
//                    TermsEnum termsEnum = terms.iterator(null);
//                    BytesRef text = null;
//                    while ((text = termsEnum.next()) != null) {
//                        String term = text.utf8ToString();
//                        long freq = termsEnum.totalTermFreq();
//                        if (passesScreening(term)) {
//                            topN.add(new TermFreqTuple(term, freq));
//                        }
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            throw new ResourceInitializationException(e);
//        }
//
//        int size = topN.size();
//        for (int i = 0; i < size; i++) {
//            TermFreqTuple tuple = topN.poll();
//            topNGrams.addSample(tuple.getTerm(), tuple.getFreq());
//        }
//
//        getLogger().log(Level.INFO, "+++ TAKING " + topNGrams.getB() + " NGRAMS");
//
//        return topNGrams;
//    }

//    public class ValueComparator
//        implements Comparator<String>
//    {
//
//        Map<String, Long> base;
//
//        public ValueComparator(Map<String, Long> base)
//        {
//            this.base = base;
//        }
//
//        @Override
//        public int compare(String a, String b)
//        {
//
//            if (base.get(a) < base.get(b)) {
//                return 1;
//            }
//            else {
//                return -1;
//            }
//        }
//
//    }

    protected boolean passesScreening(String term)
    {
        return true;
    }

}
