package org.ncibi.ws.encoder;

public interface Encoder<T>
{
    public String toXml(T obj);
    public String toRpcXml(T obj);
    public String toJson(T obj);
}
