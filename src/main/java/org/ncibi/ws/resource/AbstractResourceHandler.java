package org.ncibi.ws.resource;

import java.util.Map;

import org.ncibi.commons.bean.BeanUtils;
import org.ncibi.ws.Response;
import org.ncibi.ws.ResponseStatus;

public abstract class AbstractResourceHandler<V,A> implements ResourceHandler<V,A>
{
    protected Response<V> createSuccessResponseWithValueAndArgs(V value, A args)
    {
        Map<String, String> arguments = BeanUtils.beanToMapOfFields(args);
        arguments.remove("serialVersionUID"); //Null object contains this field.
        ResponseStatus status = new ResponseStatus(arguments, true, "Success");
        Response<V> response = new Response<V>(status, value);
        return response;
    }
}
