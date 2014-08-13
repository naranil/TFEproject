package de.tudarmstadt.ukp.dkpro.argumentation.classification;

import de.tudarmstadt.ukp.dkpro.argumentation.classification.reader.CorpusReader;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Document;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.tc.api.features.Feature;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

public class TestPunct
{
    @Test
    public void eeReaderTest()
            throws Exception
    {
        //This test needs a certain amount of memory to run.
        //A build server might not have that much memory available
        //    	Assume.assumeTrue(Runtime.getRuntime().maxMemory() > 1000000000);
        //    	System.out.println(System.getProperty("java.class.path"));
        //    	File file = new File(System.getProperty("java.class.path")+"/default/countries");
        //
        //    	System.out.println(file.exists());

        if (!System.getProperties().containsKey("DKPRO_HOME")) {
            if (System.getenv("DKPRO_HOME") != null) {
                System.setProperty("DKPRO_HOME", System.getenv("DKPRO_HOME"));
            }
        }

        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                CorpusReader.class,
                CorpusReader.PARAM_SOURCE_LOCATION, "src/main/resources/gold_data",
                CorpusReader.PARAM_LANGUAGE, "en",
                CorpusReader.PARAM_PATTERNS, new String[] {
                        CorpusReader.INCLUDE_PREFIX + "*.xml"
                }
        );

        AnalysisEngineDescription engines = createEngineDescription(
                createEngineDescription(BreakIteratorSegmenter.class),
                createEngineDescription(OpenNlpPosTagger.class,
                        OpenNlpPosTagger.PARAM_LANGUAGE, "en"));

        List<Feature> featList = new ArrayList<Feature>();
//        featList.add
        int casCount = 0;
        for (JCas jcas : new JCasIterable(reader)) {
            // FIXME missing imports
            /*
            AnnotationIndex<Annotation> topicIndex = jcas.getAnnotationIndex(Topic.type);
            AnnotationIndex<Annotation> rTestIndex = jcas.getAnnotationIndex(ReadingTest.type);
            AnnotationIndex<Annotation> answerIndex = jcas.getAnnotationIndex(Answer.type);
            AnnotationIndex<Annotation> questionIndex = jcas.getAnnotationIndex(Question.type);
            AnnotationIndex<Annotation> sentenceIndex = jcas.getAnnotationIndex(Sentence.type);
            AnnotationIndex<Annotation> scoreIndex = jcas.getAnnotationIndex(Score.type);

            assertEquals(1, topicIndex.size());
            assertEquals(1, rTestIndex.size());
            assertEquals(4, answerIndex.size());
            assertEquals(1, questionIndex.size());

            int sentenceCount = 0;
            int docEnd = jcas.getAnnotationIndex(Document.type).iterator().next().getEnd();
            FSIterator<Annotation> sentenceIterator = sentenceIndex.iterator();
            while (sentenceIterator.hasNext() && sentenceIterator.next().getEnd() <= docEnd) {
                sentenceCount++;
            }
            assertEquals(sentenceCount, scoreIndex.size());
            System.out.println(
                    docEnd + ", " + sentenceCount + ", " + scoreIndex.size() + ", " + topicIndex
                            .size());
            casCount++;
            */
        }
        assertEquals(5, casCount); //for the example
        //    	assertEquals(46, casCount); //for the training file

    }
}