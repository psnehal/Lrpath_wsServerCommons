package org.ncibi.ws.encoder;

import java.io.OutputStream;
import java.io.Writer;

public interface XmlEncoder<T>
{
    public String toXml();
    public void toXml(Writer writer);
    public void toXml(OutputStream stream);
    public void setResult(T result);
}
