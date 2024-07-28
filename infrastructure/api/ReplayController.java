package infrastructure.api;

import application.service.ReplayService;
import infrastructure.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/battlesnake/{snakeId}/replay")
public class ReplayController {
    private final ReplayService service = new ReplayService();

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFromSnake(@PathParam("snakeId") int snakeId) {
        return service.getAllFromSnake(snakeId).toResponseObject();
    }

    @GET
    @Secured
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReplay(@PathParam("snakeId") int snakeId, @PathParam("id") String id) {
        return service.getGame(snakeId, id).toResponseObject();
    }

    @DELETE
    @Secured
    @Path("{id}")
    public Response deleteReplay(@PathParam("snakeId") int snakeId, @PathParam("id") String id) {
        return service.deleteGame(snakeId, id).toResponseObject();
    }
}
