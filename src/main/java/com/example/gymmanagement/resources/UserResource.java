package com.example.gymmanagement.resources;

import com.example.gymmanagement.dto.AttendanceDTO;
import com.example.gymmanagement.dto.ChangeMembershipDTO;
import com.example.gymmanagement.dto.MemberProgramDTO;
import com.example.gymmanagement.dto.UpdateMemberDTO;
import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.entities.UserRole;
import com.example.gymmanagement.services.AttendanceService;
import com.example.gymmanagement.services.MemberProgramService;
import com.example.gymmanagement.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Map;

@Path("/members")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;
    @Inject
    private AttendanceService attendanceService;
    @Inject
    private MemberProgramService memberProgramService;

    @Context
    private SecurityContext securityContext;

    @PUT
    @Path("/{id}/membership")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeMembership(@PathParam("id") Long id, ChangeMembershipDTO dto) {
        User user = userService.getUserById(id);
        if (user == null || !user.isMember()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userService.changeMembership(user, dto);
        User updatedUser = userService.updateUser(user);
        return Response.ok(updatedUser).build();
    }

    @PUT
    @Path("/{id}/pause-membership")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pauseMembership(@PathParam("id") Long id, Map<String, Integer> request) {
        Integer daysToPause = request.get("days");
        if (daysToPause == null || daysToPause <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "Το πεδίο 'days' είναι υποχρεωτικό και πρέπει να είναι θετικός αριθμός.")).build();
        }
        try {
            boolean isAdmin = securityContext.isUserInRole("ADMIN");
            User pausedUser = userService.pauseMembership(id, daysToPause, isAdmin);
            return Response.ok(pausedUser).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", e.getMessage())).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}/resume-membership")
    public Response resumeMembership(@PathParam("id") Long id) {
        try {
            boolean isAdmin = securityContext.isUserInRole("ADMIN");
            User resumedUser = userService.resumeMembership(id, isAdmin);
            return Response.ok(resumedUser).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", e.getMessage())).build();
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(Map.of("error", e.getMessage())).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}/account")
    public Response deleteOwnAccount(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userService.deleteUser(id);
        return Response.ok(Map.of("message", "Ο λογαριασμός διαγράφηκε με επιτυχία.")).build();
    }

    @GET
    public List<User> getAllMembers() { return userService.getAllMembers(); }
    @GET
    @Path("/{id}")
    public Response getMemberById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null || !user.isMember()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }
    @GET
    @Path("/{id}/attendance")
    public List<AttendanceDTO> getMemberAttendance(@PathParam("id") Long memberId) {
        return attendanceService.getAttendanceByMemberId(memberId);
    }
    @GET
    @Path("/{id}/programs")
    public List<MemberProgramDTO> getMemberPrograms(@PathParam("id") Long memberId) {
        return memberProgramService.getProgramsByMemberId(memberId);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMember(User user) {
        user.setRole(UserRole.MEMBER);
        User createdUser = userService.createUser(user);
        return Response.status(Response.Status.CREATED).entity(createdUser).build();
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMember(@PathParam("id") Long id, UpdateMemberDTO dto) {
        User userToUpdate = userService.getUserById(id);
        if (userToUpdate == null || !userToUpdate.isMember()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userToUpdate.setFirstName(dto.getFirstName());
        userToUpdate.setLastName(dto.getLastName());
        userToUpdate.setEmail(dto.getEmail());
        userToUpdate.setPhone(dto.getPhone());
        userToUpdate.setMembershipType(dto.getMembershipType());
        userToUpdate.setDateOfBirth(dto.getDateOfBirth());
        userToUpdate.setMembershipStartDate(dto.getMembershipStartDate());
        userToUpdate.setMembershipEndDate(dto.getMembershipEndDate());
        userToUpdate.setIsActive(dto.getIsActive());
        User updatedUser = userService.updateUser(userToUpdate);
        return Response.ok(updatedUser).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteMember(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null || !user.isMember()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userService.deleteUser(id);
        return Response.noContent().build();
    }
}