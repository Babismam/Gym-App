package com.example.gymmanagement.resources;

import com.example.gymmanagement.dto.BookingRequestDTO;
import com.example.gymmanagement.dto.AttendanceDTO; // Εισάγουμε το DTO
import com.example.gymmanagement.services.AttendanceService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/attendance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AttendanceResource {

    @Inject
    private AttendanceService attendanceService;

    @POST
    @RolesAllowed({"ADMIN", "TRAINER", "MEMBER"})
    public Response bookClass(BookingRequestDTO bookingRequest) {

        AttendanceDTO newAttendanceDto = attendanceService.createAttendance(
                bookingRequest.getMemberId(),
                bookingRequest.getScheduleId()
        );

        return Response.status(Response.Status.CREATED).entity(newAttendanceDto).build();
    }

    @DELETE
    @RolesAllowed({"ADMIN", "TRAINER", "MEMBER"})
    public Response cancelBooking(BookingRequestDTO bookingRequest) {
        try {
            attendanceService.deleteAttendance(bookingRequest.getMemberId(), bookingRequest.getScheduleId());
            return Response.ok(Map.of("message", "Η απεγγραφή ήταν επιτυχής.")).build();
        } catch (WebApplicationException e) {
            return e.getResponse();
        }
    }

    @DELETE
    @Path("/{scheduleId}/attendees/{memberId}")
    @RolesAllowed({"ADMIN", "TRAINER"})
    public Response removeAttendee(@PathParam("scheduleId") Long scheduleId, @PathParam("memberId") Long memberId) {
        attendanceService.deleteAttendance(memberId, scheduleId);
        return Response.noContent().build();
    }
}