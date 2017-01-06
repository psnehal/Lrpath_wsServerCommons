package org.ncibi.ws.resource;

import org.ncibi.ws.Response;
import org.ncibi.ws.encoder.Encoder;

public abstract class AbstractSimpleResponseServerResource2<V, A> extends AbstractResponseServerResource<V, A>
{
    protected abstract V performResourceAction(A args);
    protected abstract A getArgumentsToResource();
    
    private Encoder<Response<V>> encoder;
    
    public AbstractSimpleResponseServerResource2(Encoder<Response<V>> encoder)
    {
        this.encoder = encoder;
    }

    @Override
    public V makeEmptyResponseValue()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResourceHandler<V, A> createResourceHandler()
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
        switch (getResponseFormat())
        {
            case XML:
                return encoder.toXml(response);
            case RPC_XML:
                return encoder.toRpcXml(response);
            case JSON:
                return encoder.toJson(response);
            default:
                return "";
        }
    }

}
