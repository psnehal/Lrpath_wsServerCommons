package org.ncibi.ws.encoder.xstream;

import org.ncibi.ws.Response;
import org.ncibi.ws.model.xstream.converter.ResponseConverter;

import com.thoughtworks.xstream.XStream;

public abstract class AbstractNcibiXStreamEncoder2 extends AbstractXStreamEncoder2
{
    protected abstract void setupResultsEncoder();
    
    public AbstractNcibiXStreamEncoder2(XStream xstream)
    {       
        super(xstream);
    }
   
    @Override
    protected void setupConverters()
    {
        @SuppressWarnings("unchecked")
        ResponseConverter convert = new ResponseConverter("NCIBI");
        this.xstream.registerConverter(convert);
        this.xstream.alias("NCIBI", Response.class);
        setupResultsEncoder();
    }
}
