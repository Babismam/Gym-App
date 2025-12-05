package com.example.gymmanagement.resources;

import com.example.gymmanagement.dto.DashboardStatsDTO;
import com.example.gymmanagement.services.ProgramService;
import com.example.gymmanagement.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    @Inject
    private UserService userService;

    @Inject
    private ProgramService programService;

    @GET
    @Path("/stats")
    public Response getStats() {
        long memberCount = userService.countMembers();
        long trainerCount = userService.countTrainers();
        long programCount = programService.countPrograms();

        DashboardStatsDTO stats = new DashboardStatsDTO(memberCount, trainerCount, programCount);

        return Response.ok(stats).build();
    }
}