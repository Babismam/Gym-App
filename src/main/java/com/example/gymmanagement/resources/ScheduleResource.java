package com.example.gymmanagement.resources;

import com.example.gymmanagement.dto.AttendeeDTO;
import com.example.gymmanagement.dto.ScheduleDTO;
import com.example.gymmanagement.dto.ScheduleRequestDTO;
import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.repositories.UserRepository;
import com.example.gymmanagement.services.AttendanceService;
import com.example.gymmanagement.services.ScheduleService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource {

    @Inject
    private ScheduleService scheduleService;
    @Inject
    private AttendanceService attendanceService;
    @Inject
    private UserRepository userRepository;

    @Context
    private SecurityContext securityContext;

    @GET
    public List<ScheduleDTO> getFullSchedule() {
        return scheduleService.getAllSchedules();
    }

    @GET
    @Path("/{id}/attendees")
    public List<AttendeeDTO> getScheduleAttendees(@PathParam("id") Long scheduleId) {
        return attendanceService.getAttendeesForSchedule(scheduleId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "TRAINER"})
    public Response createSchedule(ScheduleRequestDTO dto) {
        User instructor = findUserFromToken();
        scheduleService.createSchedule(dto, instructor.getId());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "TRAINER"})
    public Response updateSchedule(@PathParam("id") Long scheduleId, ScheduleRequestDTO dto) {
        User instructor = findUserFromToken();
        scheduleService.updateSchedule(scheduleId, dto, instructor.getId());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "TRAINER"})
    public Response deleteSchedule(@PathParam("id") Long scheduleId) {
        User instructor = findUserFromToken();
        scheduleService.deleteSchedule(scheduleId, instructor.getId());
        return Response.noContent().build();
    }

    private User findUserFromToken() {
        String username = securityContext.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException("User not found in database", Response.Status.UNAUTHORIZED);
        }
        return user;
    }
}