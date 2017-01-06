package org.ncibi.ws.resource;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.ncibi.commons.bean.BeanUtils;
import org.ncibi.ws.Response;
import org.ncibi.ws.ResponseStatus;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public abstract class AbstractResponseServerResource<V, A> extends ServerResource
{
    public abstract V makeEmptyResponseValue();

    public abstract ResourceHandler<V, A> createResourceHandler();

    public abstract String createResponseAsXml(); // Need to change the name.

    private Form form = null;
    private boolean calledInPost = false;
    private Representation entity = null;
    private ResponseFormat responseFormat;

    @Get
    public Representation doGet()
    {
        calledInPost = false;
        entity = null;
        return createResponse();
    }

    @Post
    public Representation doPost(Representation representation)
    {
        calledInPost = true;
        entity = representation;
        this.form = new Form(representation);
        return createResponse();
    }
    
    private Representation createResponse()
    {
        setResponseFormat();
        Representation r = new StringRepresentation(createResponseAsXml());
        setMediaType(r);
        return r;
    }

    private void setResponseFormat()
    {
        responseFormat = ResponseFormat.toResponseFormatWithDefault(
                getArgumentWithDefault("format", ResponseFormat.XML.formatName()), ResponseFormat.XML);
    }
    
    private void setMediaType(Representation r)
    {
        switch (responseFormat)
        {
            case JSON: 
                r.setMediaType(MediaType.APPLICATION_JSON); 
                break;
            case XML: 
                r.setMediaType(MediaType.TEXT_XML);
                break;
            case RPC_XML:
                r.setMediaType(MediaType.TEXT_XML);
                break;
            default:
                throw new IllegalStateException("Unknown ResponseFormat - should never happen.");
        }
    }
    
    protected ResponseFormat getResponseFormat()
    {
        return responseFormat;
    }

    protected Representation getEntity()
    {
        return this.entity;
    }

    protected String getArgument(String arg)
    {
        final String value = getQueryParameter(arg);
        if (isNull(value))
        {
            return "";
        }

        return value;
    }

    protected String getArgumentWithDefault(String arg, String defaultValue)
    {
        String value = getArgument(arg);
        if ("".equals(value))
        {
            return defaultValue;
        }

        return value;
    }

    protected int getArgumentWithDefault(String arg, int defaultValue)
    {
        String value = getArgument(arg);
        return NumberUtils.toInt(value, defaultValue);
    }

    protected double getArgumentWithDefault(String arg, double defaultValue)
    {
        String value = getArgument(arg);
        return NumberUtils.toDouble(value, defaultValue);
    }

    protected String getAttribute(String attribute)
    {
        return (String) getRequestAttributes().get(attribute);
    }

    // @SuppressWarnings("deprecation")
    private String getQueryParameter(String what)
    {
        if (calledInPost)
        {
            // Form form = getRequest().getEntityAsForm(); // new
            // Form(getRequest().getEntity());
            return this.form.getValues(what);
        }
        else
        {
            return getQuery().getValues(what);
        }
    }

    private boolean isNull(String str)
    {
        return str == null;
    }

    protected Response<V> createResponseFromArguments(A args)
    {
        Response<V> response;
        try
        {
            final ResourceHandler<V, A> resourceHandler = createResourceHandler();
            response = resourceHandler.createResponse(args);
        }
        catch (final Throwable exception)
        {
            exception.printStackTrace();
            response = createErrorResponse(args, exception);
        }

        return response;
    }

    private Response<V> createErrorResponse(A args, Throwable exception)
    {
        return createErrorResponse(args, exception.getMessage());
    }

    protected Response<V> createErrorResponse(A args, String message)
    {
        Map<String, String> arguments = BeanUtils.beanToMapOfFields(args);
        arguments.remove("serialVersionUID"); // Null object contains this
                                              // field.
        ResponseStatus status = new ResponseStatus(arguments, false, message);
        V value = makeEmptyResponseValue();
        return new Response<V>(status, value);
    }
}
