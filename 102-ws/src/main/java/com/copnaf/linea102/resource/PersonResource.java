package com.copnaf.linea102.resource;


import com.copnaf.linea102.request.CreatePersonRequest;
import com.copnaf.linea102.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Andres.
 */
@Component
@Path(PersonResource.PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class PersonResource {

    public static final String PATH = "/person";
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

    @Autowired
    private IPersonService personService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response create(@NotNull @Valid CreatePersonRequest request) throws Exception {
        LOGGER.debug("Person Resource - Create new Person Service. Request parameters: Person={}", request);
            Long personId = personService.create(request.getName(), request.getLastName(), request.getDni(),
                    request.getAddress(), request.getPhoneNumber(), request.getPersonType());
            return Response.ok().entity(personId).build();
    }
}
