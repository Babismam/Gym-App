package com.example.gymmanagement.resources;

import com.example.gymmanagement.dto.ScheduleDTO;
import com.example.gymmanagement.dto.UpdateTrainerDTO;
import com.example.gymmanagement.entities.Program;
import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.entities.UserRole;
import com.example.gymmanagement.services.ScheduleService;
import com.example.gymmanagement.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/trainers")
@Produces(MediaType.APPLICATION_JSON)
public class TrainerResource {

    @Inject
    private UserService userService;
    @Inject
    private ScheduleService scheduleService;

    @GET
    public List<User> getAllTrainers(@QueryParam("includeInactive") @DefaultValue("false") boolean includeInactive) {
        return userService.getAllTrainers(includeInactive);
    }

    @GET
    @Path("/{id}")
    public Response getTrainerById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null || !user.isTrainer()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @GET
    @Path("/{id}/schedule")
    public List<ScheduleDTO> getTrainerSchedule(@PathParam("id") Long instructorId) {
        return scheduleService.getScheduleByInstructorIdDTO(instructorId);
    }

    @GET
    @Path("/{id}/programs")
    public List<Program> getTrainerAssignedPrograms(@PathParam("id") Long instructorId) {
        return scheduleService.getDistinctProgramsByInstructorId(instructorId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTrainer(User user) {
        user.setRole(UserRole.TRAINER);
        User createdUser = userService.createUser(user);
        return Response.status(Response.Status.CREATED).entity(createdUser).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTrainer(@PathParam("id") Long id, UpdateTrainerDTO dto) {
        User userToUpdate = userService.getUserById(id);
        if (userToUpdate == null || !userToUpdate.isTrainer()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        userToUpdate.setFirstName(dto.getFirstName());
        userToUpdate.setLastName(dto.getLastName());
        userToUpdate.setEmail(dto.getEmail());
        userToUpdate.setPhone(dto.getPhone());
        userToUpdate.setDateOfBirth(dto.getDateOfBirth());
        userToUpdate.setIsActive(dto.getIsActive());
        userToUpdate.setBio(dto.getBio());
        userToUpdate.setSpecialties(dto.getSpecialties());

        User updatedUser = userService.updateUser(userToUpdate);
        return Response.ok(updatedUser).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTrainer(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null || !user.isTrainer()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userService.deleteUser(id);
        return Response.noContent().build();
    }
}