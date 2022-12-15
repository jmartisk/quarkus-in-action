package org.acme.users;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.jboss.resteasy.reactive.RestQuery;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/whoami")
public class WhoAmIResource {

    @Inject
    Template whoami;

    @Inject
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@RestQuery String name) {
        String userId = securityContext.getUserPrincipal() != null ? securityContext.getUserPrincipal().getName() : null;
        return whoami.data("name", userId);
    }

}
