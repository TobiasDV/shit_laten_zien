package infrastructure.api;

import infrastructure.security.User;
import infrastructure.security.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class SecurityController {
    private final UserService userService = new UserService();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) {
        if (userService.authenticate(user.getUsername(), user.getPassword())) {
            String token = userService.issueToken(user.getUsername());
            return Response.ok("{\"token\":\"" + token + "\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User authentication failed").build();
        }
    }
}
