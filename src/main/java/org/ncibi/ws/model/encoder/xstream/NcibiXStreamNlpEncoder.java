package org.ncibi.ws.model.encoder.xstream;

import org.ncibi.ws.encoder.xstream.AbstractNcibiXStreamEncoder;

import com.thoughtworks.xstream.XStream;

public class NcibiXStreamNlpEncoder<T> extends AbstractNcibiXStreamEncoder<T>
{
    public NcibiXStreamNlpEncoder(XStream xstream)
    {
        super(xstream);
    }
    
    @Override
    protected void setupResultsEncoder()
    {   
    }

}
