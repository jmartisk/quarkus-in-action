package orc.acme.users;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import orc.acme.users.model.Car;
import orc.acme.users.model.Reservation;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestQuery;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.Collection;

@Path("/reservations")
public class ReservationsResource {

    @Inject
    Template reservations;

    @Inject
    JsonWebToken jsonWebToken;

    @RestClient
    ReservationsClient client;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance get(@RestQuery LocalDate startDate,
                                @RestQuery LocalDate endDate) {
        if(startDate == null) {
            startDate = LocalDate.now().plusDays(1L);
        }
        if(endDate == null) {
            endDate = LocalDate.now().plusDays(7);
        }
        Collection<Reservation> reservationCollection = client.allReservations();
        Collection<Car> availableCars = client.availability(startDate, endDate);
        return reservations.data("name", jsonWebToken.getName())
            .data("startDate", startDate)
            .data("endDate", endDate)
            .data("reservations", reservationCollection)
            .data("cars", availableCars);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    @Path("/reserve")
    public TemplateInstance create(@RestForm LocalDate startDate,
                                   @RestForm LocalDate endDate,
                                   @RestForm Long carId) {
        Reservation reservation = new Reservation();
        reservation.startDay = startDate;
        reservation.endDay = endDate;
        reservation.carId = carId;
        Reservation createdReservation = client.make(reservation);
        return get(startDate, endDate);
    }

}
