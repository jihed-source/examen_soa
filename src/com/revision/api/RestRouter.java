// FILE: RestRouter.java (UPDATED)
package com.revision.api;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revision.entities.Person;
import com.revision.service.PersonServiceImpl;

@Path("/api")
public class RestRouter {
    
    PersonServiceImpl personService = new PersonServiceImpl();
    
    // Person Endpoints
    
    @Path("/persons")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        Map<String, String> result = personService.addPerson(person);
        return Response.status(Response.Status.OK).entity(result).build();
    }
    
    @Path("/persons/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") Long id) {
        boolean result = personService.deletePerson(id);
        if (result) {
            return Response.status(Response.Status.OK)
                .entity("{\"status\":\"success\",\"message\":\"Person deleted successfully\"}")
                .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"status\":\"error\",\"message\":\"Person not found\"}")
                .build();
        }
    }
    
    @Path("/persons/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") Long id, Person person) {
        Map<String, String> result = personService.updatePerson(id, person);
        return Response.status(Response.Status.OK).entity(result).build();
    }
    
    @Path("/persons/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonById(@PathParam("id") Long id) {
        Person person = personService.getPersonById(id);
        if (person != null) {
            return Response.status(Response.Status.OK).entity(person).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"status\":\"error\",\"message\":\"Person not found\"}")
                .build();
        }
    }
    
    @Path("/persons/search/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonByName(@PathParam("name") String name) {
        List<Person> persons = personService.getPersonByName(name);
        return Response.status(Response.Status.OK).entity(persons).build();
    }
    
    @Path("/persons")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return Response.status(Response.Status.OK).entity(persons).build();
    }
}