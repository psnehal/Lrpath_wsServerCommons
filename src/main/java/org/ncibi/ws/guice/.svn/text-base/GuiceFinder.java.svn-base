package org.ncibi.ws.guice;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import com.google.inject.Injector;

/**
 * Implements a Finder for Guice integration.
 * 
 * @author gtarcea
 *
 */
public class GuiceFinder extends Finder
{
    private final Injector injector;

    public GuiceFinder(final Injector injector)
    {
        super();
        this.injector = injector;
    }

    public GuiceFinder(final Injector injector, final Context context,
            final Class<? extends ServerResource> targetClass)
    {
        super(context, targetClass);
        this.injector = injector;
    }

    @Override
    public ServerResource create(final Class<? extends ServerResource> targetClass,
            final Request request, final Response response)
    {
        ServerResource resource = this.injector.getInstance(targetClass);
        return resource;
    }
}
