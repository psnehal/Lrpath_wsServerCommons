package org.ncibi.ws.model.xstream.converter;

import java.util.Map;

import org.ncibi.ws.Response;
import org.ncibi.ws.ResponseStatus;
import org.ncibi.ws.model.NcibiCopyright;
import org.ncibi.ws.model.NcibiSupport;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ResponseConverter<T> implements Converter
{
    private Response<T> response;
    private HierarchicalStreamWriter writer;
    private MarshallingContext context;

    public ResponseConverter(String application)
    {
        /*
         * Ignore application for now. We might need to add it back later.
         */
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean canConvert(Class aClass)
    {
        return aClass.equals(Response.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void marshal(Object value, HierarchicalStreamWriter streamWriter,
                MarshallingContext marshallingContext)
    {
        this.response = (Response<T>) value;
        this.writer = streamWriter;
        this.context = marshallingContext;

        generateRequestHeader();
        generateResponseResults();
        endRequestHeader();
    }

    private void generateRequestHeader()
    {
        writer.startNode("Request");
        writer.addAttribute("type", "fetch");
        generateRequestParameters();
        writer.endNode(); // Request
    }

    private void generateRequestParameters()
    {
        writer.startNode("ParameterSet");
        ResponseStatus rs = this.response.getResponseStatus();
        for (Map.Entry<String, String> entry : rs.getArgs().entrySet())
        {
            if (entry == null) 
            { 
                //System.out.println("entry == null");
                continue;
            }
            else if (entry.getValue() == null)
            {
                //System.out.println("entry.getValue() == null");
                continue;
            }
            else if (entry.getValue().toString() != "")
        	{
	            writer.startNode(entry.getKey().toString());
	            writer.setValue(entry.getValue().toString());
	            writer.endNode();
        	}
        }
        writer.endNode();
    }

    private void generateResponseResults()
    {
        T results = response.getResponseValue();
        writer.startNode("Response");
        if (!response.isSuccess())
        {
            generateErrorResponseForResults();
        }
        else if (results != null)
        {
            generateStandardResponseHeader();
            generateResults(results);
        }
        else
        {
            writer.setValue("There were no records that matched your query.");
        }
    }

    private void generateErrorResponseForResults()
    {
        ResponseStatus rs = response.getResponseStatus();

        writer.startNode("Error");
        writer.startNode("Code");
        writer.setValue(rs.getMessage());
        writer.endNode();
        writer.startNode("Message");
        writer.setValue(rs.getMessage());
        writer.endNode();
        writer.endNode();
    }

    private void generateStandardResponseHeader()
    {
        generateCopyrightTags();
        generateSupportTags();
    }

    private void generateResults(T results)
    {
        writer.startNode("ResultSet");
        boolean hasTagName = false;
        if (hasTagName) // ncibiResponse.getResultTag())
        {
            writer.startNode("Result");
            context.convertAnother(results);
            writer.endNode();
        }
        else
        {
            context.convertAnother(results);
        }
        writer.endNode();// Results
    }

    private void generateCopyrightTags()
    {
        writer.startNode("Copyright");
        for (NcibiCopyright copyrightEntry : NcibiCopyright.values())
        {
            writer.startNode(copyrightEntry.tag());
            writer.setValue(copyrightEntry.value());
            writer.endNode();
        }
        writer.endNode();
    }

    private void generateSupportTags()
    {
        writer.startNode("Support");
        for (NcibiSupport supportEntry : NcibiSupport.values())
        {
            writer.startNode(supportEntry.tag());
            writer.setValue(supportEntry.value());
            writer.endNode();
        }
        writer.endNode();
    }

    private void endRequestHeader()
    {
        writer.endNode(); // Response
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
    {
        throw new UnsupportedOperationException("unmarshal call is not supported.");
    }

}
