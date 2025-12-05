package com.example.gymmanagement.resources;

import com.example.gymmanagement.dto.LoginDTO;
import com.example.gymmanagement.dto.RegisterDTO;
import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.entities.UserRole;
import com.example.gymmanagement.repositories.UserRepository;
import com.example.gymmanagement.services.UserService;
import com.example.gymmanagement.utils.JwtUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AuthResource {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @POST
    @Path("/register")
    public Response register(RegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            return Response.status(Response.Status.CONFLICT).entity(Map.of("error", "Το όνομα χρήστη υπάρχει ήδη.")).build();
        }
        if (dto.getDateOfBirth() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "Η ημερομηνία γέννησης είναι υποχρεωτική.")).build();
        }
        if (dto.getDateOfBirth().isAfter(LocalDate.now().minusYears(18))) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "Οι χρήστες πρέπει να είναι άνω των 18 ετών.")).build();
        }

        UserRole userRole = UserRole.MEMBER;

        User newUser = new User(
                dto.getFirstName(), dto.getLastName(), dto.getEmail(),
                dto.getPhone(), dto.getPassword(), dto.getUsername(),
                userRole, dto.getDateOfBirth()
        );

        if (newUser.isMember()) {
            userService.setInitialMembershipFromDTO(newUser, dto);
        }
        userService.createUser(newUser);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("username", newUser.getUsername());
        response.put("role", newUser.getRole().name());

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername());

        if (user == null || !user.verifyPassword(dto.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Map.of("error", "Λάθος όνομα χρήστη ή κωδικός.")).build();
        }

        if (!user.getIsActive()) {
            return Response.status(Response.Status.FORBIDDEN).entity(Map.of("error", "Ο λογαριασμός σας είναι ανενεργός. Παρακαλούμε επικοινωνήστε με τη διαχείριση.")).build();
        }

        if (user.getRole() == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Ο λογαριασμός είναι κατεστραμμένος: λείπει ο ρόλος.")).build();
        }

        String userRoleString = user.getRole().name();
        String token = JwtUtil.generateToken(user.getUsername(), userRoleString);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("role", userRoleString);
        response.put("firstName", user.getFirstName());

        return Response.ok(response).build();
    }
}