package com.example.gymmanagement.resources;

import com.example.gymmanagement.entities.OpeningHours;
import com.example.gymmanagement.services.OpeningHoursService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/opening-hours")
@Produces(MediaType.APPLICATION_JSON)
public class OpeningHoursResource {
    @Inject
    private OpeningHoursService service;

    @GET
    public List<OpeningHours> getHours() {
        return service.getOpeningHours();
    }
}