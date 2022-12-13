package org.acme.rental;

import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rental")
public class RentalResource {

    private static final Logger LOGGER = Logger.getLogger(RentalResource.class);

    private final AtomicLong id = new AtomicLong(0);

    @Path("/start/{userId}/{reservationId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Rental start(String userId,
                        Long reservationId) {
        LOGGER.infof("Starting rental for %s with reservation %s", userId, reservationId);
        return new Rental(id.incrementAndGet(), userId, reservationId,
            LocalDate.now());
    }
}
