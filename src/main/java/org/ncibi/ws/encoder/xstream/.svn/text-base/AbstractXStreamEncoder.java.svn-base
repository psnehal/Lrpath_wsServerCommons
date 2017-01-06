package org.ncibi.ws.encoder.xstream;

import java.io.OutputStream;
import java.io.Writer;

import org.ncibi.ws.encoder.XmlEncoder;

import com.thoughtworks.xstream.XStream;

public abstract class AbstractXStreamEncoder<T> implements XmlEncoder<T>
{
    protected abstract void setupConverters();
    
    protected final XStream xstream;
    private T result;
    
    public AbstractXStreamEncoder(XStream xstream)
    {
        this.xstream = xstream;
        setupConverters();
    }
    
    @Override
    public String toXml()
    {
        return this.xstream.toXML(this.result);
    }
    
    @Override
    public void toXml(Writer writer)
    {
        this.xstream.toXML(this.result, writer);
    }
    
    @Override
    public void toXml(OutputStream stream)
    {
        this.xstream.toXML(this.result, stream);
    }
    
    @Override
    public void setResult(T result)
    {
        this.result = result;
    }
}
