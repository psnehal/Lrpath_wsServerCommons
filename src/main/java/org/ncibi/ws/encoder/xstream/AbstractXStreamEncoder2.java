package org.ncibi.ws.encoder.xstream;

import com.thoughtworks.xstream.XStream;

public abstract class AbstractXStreamEncoder2
{
    protected abstract void setupConverters();
    
    protected final XStream xstream;
    
    public AbstractXStreamEncoder2(XStream xstream)
    {
        this.xstream = xstream;
        setupConverters();
    }
    
    public <T> String toXml(T obj)
    {
        return this.xstream.toXML(obj);
    }
}
