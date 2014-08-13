package de.tudarmstadt.ukp.dkpro.argumentation.classification;

import de.tudarmstadt.ukp.dkpro.core.berkeleyparser.BerkeleyParser;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpSemanticRoleLabeler;
import de.tudarmstadt.ukp.dkpro.core.languagetool.LanguageToolSegmenter;
import de.tudarmstadt.ukp.dkpro.core.maltparser.MaltParser;
import de.tudarmstadt.ukp.dkpro.core.matetools.MateLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpNameFinder;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * StanfordCoreferenceResolver was failing (assertion error -> NPE) with stanford-gpl:1.5.0;
 * now fixed in stanford-gpl.1.5.1-SNAPSHOT
 */
public class CoreferenceResolutionTest
{
    protected AnalysisEngineDescription getPipeline()
            throws ResourceInitializationException
    {

        return createEngineDescription(
                // Token, Sentence
                createEngineDescription(LanguageToolSegmenter.class),
                // Constituent, POS
                createEngineDescription(BerkeleyParser.class),
                // Dependency
                createEngineDescription(MaltParser.class),
                // Stem
                createEngineDescription(SnowballStemmer.class),
                // Lemma
                createEngineDescription(MateLemmatizer.class),
                // NamedEntity
                createEngineDescription(OpenNlpNameFinder.class,
                        OpenNlpNameFinder.PARAM_VARIANT, "person"),
                createEngineDescription(OpenNlpNameFinder.class,
                        OpenNlpNameFinder.PARAM_VARIANT, "organization"),
                // CoreferenceChain, CoreferenceLink
                createEngineDescription(StanfordCoreferenceResolver.class),
                // SemanticPredicate, SemanticArgument
                createEngineDescription(ClearNlpSemanticRoleLabeler.class)
        );

    }

    private JCas jCas;
    private AnalysisEngineDescription preprocessing;

    @Before
    public void setUp()
            throws Exception
    {
        final String text =
                "Thanks for the statical analysis (henceforward to be known as the JennM's god daughter report). \n"
                        + "Good luck with educating your daughter at home, Annabel. My only experience was meeting two children in France who were educated by their French/British parents. I was very impressed but obviously, you'd need to look at a lot more examples. \n"
                        + "I think some level of supervision is essential, but it might help if those responsible had a certain degree of sympathy for parents like you. ";

        jCas = JCasFactory
                .createJCas(TypeSystemDescriptionFactory.createTypeSystemDescription());
        jCas.setDocumentText(text);
        jCas.setDocumentLanguage("en");

        preprocessing = getPipeline();
    }

    @Test
    public void testPipeline()
            throws Exception
    {
        SimplePipeline.runPipeline(jCas, preprocessing,
                AnalysisEngineFactory.createEngineDescription(CasDumpWriter.class)
        );
    }

    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(CoreferenceResolutionTest.class);
        for (Failure failure : result.getFailures()) {
            failure.getException().printStackTrace(System.err);
        }
        System.out.println(result.wasSuccessful());
    }
}
