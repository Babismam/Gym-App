package com.example.gymmanagement.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
public class HelloRest {

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "{\"message\":\"Γεια σου από το REST API!\"}";
    }
}
