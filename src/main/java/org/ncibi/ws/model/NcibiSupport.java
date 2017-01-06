package org.ncibi.ws.model;

public enum NcibiSupport
{
    SUPPORT_STATEMENT(
                "Statement",
                "Supported by the National Institutes of Health as part of the NIH\'s National Center for Integrative Biomedical Informatics (NCIBI)"),
    GRANT_NUMBER("grantNumber", "U54 DA021519"),
    SUPPORT_DETAILS("supportDetails", "http://www.ncibi.org");
    
    private final String tag;
    private final String value;

    private NcibiSupport(String tag, String value)
    {
        this.tag = tag;
        this.value = value;
    }

    public String tag()
    {
        return this.tag;
    }

    public String value()
    {
        return this.value;
    }
}
