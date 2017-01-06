package org.ncibi.ws.model;

public enum NcibiCopyright
{
    COPYRIGHT_STATEMENT(
                "copyrightStatement",
                "Copyright 2010 by the Regents of the University of Michigan"),
    COPYRIGHT_YEAR("copyrightYear", "2010"),
    COPYRIGHT_DETAILS("copyrightDetails", "http://mimi.ncibi.org/MimiWeb/AboutPage.html#licensing");

    private final String tag;
    private final String value;

    private NcibiCopyright(String tag, String value)
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
