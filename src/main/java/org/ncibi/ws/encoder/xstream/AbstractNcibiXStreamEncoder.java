package org.ncibi.ws.encoder.xstream;

import org.ncibi.ws.Response;
import org.ncibi.ws.model.xstream.converter.ResponseConverter;

import com.thoughtworks.xstream.XStream;

public abstract class AbstractNcibiXStreamEncoder<T> extends AbstractXStreamEncoder<Response<T>>
{
    protected abstract void setupResultsEncoder();
    
    public AbstractNcibiXStreamEncoder(XStream xstream)
    {       
        super(xstream);
    }
    
    @Override
    protected void setupConverters()
    {
        this.xstream.registerConverter(new ResponseConverter<T>("NCIBI"));
        this.xstream.alias("NCIBI", Response.class);
        setupResultsEncoder();
    }
    
    @Override
    public void setResult(Response<T> result)
    {
        super.setResult(result);
    }
}
