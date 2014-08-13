package de.tudarmstadt.ukp.dkpro.argumentation.classification.evaluation;

/**
 * Document domains (homeschooling, redshirting, etc.)
 */
public enum DocumentDomain
{
    HOMESCHOOLING,
    REDSHIRTING,
    PRAYER_IN_SCHOOLS,
    SINGLE_SEX_EDUCATION,
    MAINSTREAMING,
    PUBLIC_PRIVATE_SCHOOLS;

    @Override
    public String toString()
    {
        return super.toString().toLowerCase().replaceAll("_", "-");
    }

    /**
     * Returns DocTopic given the string name
     *
     * @param documentDomain string name
     * @return doc topic
     * @throws java.lang.IllegalArgumentException if enum constant does not exist or param is null
     */
    public static DocumentDomain fromString(String documentDomain)
            throws IllegalArgumentException
    {
        if (documentDomain == null) {
            throw new IllegalArgumentException("Parameter documentDomain is null");
        }

        return DocumentDomain.valueOf(documentDomain.toUpperCase().replaceAll("-", "_"));
    }
}
