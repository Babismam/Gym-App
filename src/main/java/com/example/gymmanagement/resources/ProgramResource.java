package com.example.gymmanagement.resources;

import com.example.gymmanagement.entities.Program;
import com.example.gymmanagement.services.ProgramService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/programs")
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {

    @Inject
    private ProgramService programService;

    @GET
    public List<Program> getAllPrograms() {
        return programService.getAllPrograms();
    }

    @GET
    @Path("/{id}")
    public Response getProgramById(@PathParam("id") Long id) {
        Program program = programService.getProgramById(id);
        if (program == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(program).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProgram(Program program) {
        Program createdProgram = programService.createProgram(program);
        return Response.status(Response.Status.CREATED).entity(createdProgram).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProgram(@PathParam("id") Long id, Program program) {
        if (programService.getProgramById(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        program.setId(id);
        Program updatedProgram = programService.updateProgram(program);
        return Response.ok(updatedProgram).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProgram(@PathParam("id") Long id) {
        if (programService.getProgramById(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        programService.deleteProgram(id);
        return Response.noContent().build();
    }
}