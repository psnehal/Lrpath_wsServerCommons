package org.ncibi.ws.resource;

import org.ncibi.ws.Response;

public interface ResourceHandler<V,A>
{
    public Response<V> createResponse(A args);
}
