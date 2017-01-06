package org.ncibi.ws.resource.restlet;

import org.ncibi.ws.guice.GuiceFinder;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class AbstractRestletApplication extends Application
{
    public abstract void configureRoutes();

    private final Router router = new Router(getContext());

    @Override
    public synchronized Restlet createInboundRoot()
    {
        configureRoutes();
        return router;
    }

    protected void attachRoute(String route, Class<? extends ServerResource> resource)
    {
        router.attach(route, resource);
    }

    protected void attachDIConfiguredRoute(String route, Class<? extends ServerResource> resource,
                AbstractModule module)
    {
        Injector injector = Guice.createInjector(module);
        router.attach(route, new GuiceFinder(injector, getContext(), resource));
    }
}
