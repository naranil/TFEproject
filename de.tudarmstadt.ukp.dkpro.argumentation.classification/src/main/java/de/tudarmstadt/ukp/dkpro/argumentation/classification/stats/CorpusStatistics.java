package de.tudarmstadt.ukp.dkpro.argumentation.classification.stats;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.reader.CorpusReader;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.languagetool.LanguageToolSegmenter;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import java.util.Collection;
import java.util.Locale;

/**
 * Basic gold data corpus statistics (tokens, sentences)
 *
 * @author Ivan Habernal
 */
public class CorpusStatistics
        extends JCasAnnotator_ImplBase
{
    // tokens
    DescriptiveStatistics tokenStatistics = new DescriptiveStatistics();

    // sentences
    DescriptiveStatistics sentenceStatistics = new DescriptiveStatistics();

    @Override public void process(JCas aJCas)
            throws AnalysisEngineProcessException
    {
        Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
        tokenStatistics.addValue(tokens.size());

        Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
        sentenceStatistics.addValue(sentences.size());
    }

    @Override public void collectionProcessComplete()
            throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();

        System.out.println("Total documents: " + tokenStatistics.getN());
        System.out.println("Tokens: " + formatStatistics(tokenStatistics));
        System.out.println("Sentences: " + formatStatistics(sentenceStatistics));
    }

    /**
     * Returns important statistics (count, sum, mean, standard deviation) of the statistics as string.
     *
     * @param statistics stats
     * @return string
     */
    protected static String formatStatistics(DescriptiveStatistics statistics)
    {
        return String.format(Locale.ENGLISH, "N: %d, Sum: %d, Mean: %.2f, StdDev: %.2f",
                statistics.getN(), (long) statistics.getSum(), statistics.getMean(),
                statistics.getStandardDeviation());
    }

    public static void main(String[] args)
    {
        try {
            // P1 class
            SimplePipeline.runPipeline(
                    CollectionReaderFactory.createReaderDescription(CorpusReader.class,
                            CorpusReader.PARAM_SOURCE_LOCATION,
                            "src/main/resources/gold_data", CorpusReader.PARAM_LANGUAGE, "en",
                            CorpusReader.PARAM_PATTERNS, "*P1*.txt"),
                    AnalysisEngineFactory.createEngineDescription(LanguageToolSegmenter.class),
                    AnalysisEngineFactory.createEngineDescription(CorpusStatistics.class)
            );

            // P2 class
            SimplePipeline.runPipeline(
                    CollectionReaderFactory.createReaderDescription(CorpusReader.class,
                            CorpusReader.PARAM_SOURCE_LOCATION,
                            "src/main/resources/gold_data", CorpusReader.PARAM_LANGUAGE, "en",
                            CorpusReader.PARAM_PATTERNS, "*P2*.txt"),
                    AnalysisEngineFactory.createEngineDescription(LanguageToolSegmenter.class),
                    AnalysisEngineFactory.createEngineDescription(CorpusStatistics.class)
            );

            // all
            SimplePipeline.runPipeline(
                    CollectionReaderFactory.createReaderDescription(CorpusReader.class,
                            CorpusReader.PARAM_SOURCE_LOCATION,
                            "src/main/resources/gold_data", CorpusReader.PARAM_LANGUAGE, "en",
                            CorpusReader.PARAM_PATTERNS, "*.txt"),
                    AnalysisEngineFactory.createEngineDescription(LanguageToolSegmenter.class),
                    AnalysisEngineFactory.createEngineDescription(CorpusStatistics.class)
            );

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /*
Total documents: 990
Tokens: N: 990, Sum: 130085, Mean: 131.40, StdDev: 138.69
Sentences: N: 990, Sum: 6371, Mean: 6.44, StdDev: 6.53
     */
}
