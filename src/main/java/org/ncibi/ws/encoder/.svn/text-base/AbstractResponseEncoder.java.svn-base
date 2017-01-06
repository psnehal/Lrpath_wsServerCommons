package org.ncibi.ws.encoder;

import java.beans.DefaultPersistenceDelegate;
import java.beans.XMLEncoder;

import org.ncibi.ws.AbstractBeanXMLResponseEncoder2;
import org.ncibi.ws.Response;
import org.ncibi.ws.encoder.xstream.AbstractNcibiXStreamEncoder2;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public abstract class AbstractResponseEncoder<I,T> implements Encoder<Response<T>>
{
    protected abstract String[] persistenceFields();

    protected abstract void setupAliases(XStream xstream);

    protected abstract void marshaller(I obj, HierarchicalStreamWriter writer, MarshallingContext context);
    
    private final Class<I> itemClass;

    private final XStream jsonStream = new XStream(new JsonHierarchicalStreamDriver());
    
    private final AbstractBeanXMLResponseEncoder2<I> rpcEncoder = new AbstractBeanXMLResponseEncoder2<I>()
    {
        @Override
        protected void setupPersistenceDelegatesForResponseValue(XMLEncoder encoder)
        {
            String[] fields = persistenceFields();
            if (fields != null)
            {
                encoder.setPersistenceDelegate(itemClass, new DefaultPersistenceDelegate(fields));
            }
        }
    };
    
    private final AbstractNcibiXStreamEncoder2 xmlStreamEncoder = new AbstractNcibiXStreamEncoder2(new XStream())
    {
        @Override
        protected void setupResultsEncoder()
        {
            setupAliases(xstream);
            xstream.registerConverter(new AbstractXStreamConverter<I>()
            {
                @SuppressWarnings("unchecked")
                @Override
                protected Class converterClass()
                {
                    return itemClass;
                }

                @Override
                protected void marshallObj(I obj, HierarchicalStreamWriter writer, MarshallingContext context)
                {
                    marshaller(obj, writer, context);
                }
            });
        }

    };
    
    public AbstractResponseEncoder(Class<I> cls)
    {
        this.itemClass = cls;
    }

    @Override
    public String toXml(Response<T> obj)
    {
        return xmlStreamEncoder.toXml(obj);
    }

    @Override
    public String toRpcXml(Response<T> response)
    {
        rpcEncoder.setObjectToEncode(response);
        return rpcEncoder.toXmlString();
    }

    @Override
    public String toJson(Response<T> obj)
    {
        return jsonStream.toXML(obj);
    }
}
