package org.ncibi.ws.resource;

import org.ncibi.ws.Response;

public abstract class AbstractSimpleResponseServerResource<V, A> extends AbstractResponseServerResource<V, A>
{
    protected abstract V performResourceAction(A args);
    protected abstract String response2Xml(Response<V> response);
    protected abstract A getArgumentsToResource();
    
    @Override
    public V makeEmptyResponseValue()
    {
        return null;
    }
    
    @Override
    public ResourceHandler<V,A> createResourceHandler()
    {
        return new AbstractResourceHandler<V,A>()
        {
            @Override
            public Response<V> createResponse(A args)
            {
               V results = performResourceAction(args);
               return createSuccessResponseWithValueAndArgs(results, args);
            }      
        };
    }
    
    @Override
    public String createResponseAsXml()
    {
        A args = getArgumentsToResource();
        Response<V> response = createResponseFromArguments(args);
        return response2Xml(response);
    }
}
