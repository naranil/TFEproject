package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DocumentDomainTest
{

    @Test
    public void testToString()
            throws Exception
    {
        assertEquals("prayer-in-schools", DocumentDomain.PRAYER_IN_SCHOOLS.toString());
        assertEquals(DocumentDomain.fromString("prayer-in-schools"), DocumentDomain.PRAYER_IN_SCHOOLS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromString()
    {
        DocumentDomain.fromString(null);
        DocumentDomain.fromString("all");
    }

}