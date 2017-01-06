package org.ncibi.ws.encoder;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public abstract class AbstractXStreamConverter<T> implements Converter
{
    protected abstract Class<T> converterClass();
    protected abstract void marshallObj(T obj, HierarchicalStreamWriter writer, MarshallingContext context);

    @Override
    @SuppressWarnings("unchecked")
    public boolean canConvert(Class arg0)
    {
        return arg0.equals(converterClass());
    }

    @Override
    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context)
    {
        @SuppressWarnings("unchecked")
        T object = (T) obj;
        marshallObj(object, writer, context);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
