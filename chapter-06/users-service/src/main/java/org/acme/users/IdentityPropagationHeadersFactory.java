package org.acme.users;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

@RequestScoped
public class IdentityPropagationHeadersFactory implements ClientHeadersFactory {

    @Inject
    JsonWebToken token;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> outgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        result.putAll(outgoingHeaders);
        result.add("Authorization", "Bearer " + token.getRawToken());
        return result;
    }

}
