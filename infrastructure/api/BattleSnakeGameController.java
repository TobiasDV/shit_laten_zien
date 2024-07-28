package infrastructure.api;

import infrastructure.api.dto.GameRequest;
import application.service.BattleSnakeGameService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/battlesnake/{id}/game")
public class BattleSnakeGameController {
    private final BattleSnakeGameService snakeService = new BattleSnakeGameService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSnakeInfo(@PathParam("id") int id) {
        return snakeService.getSnakeInfo(id).toResponseObject();
    }

    @POST()
    @Path("start")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response startGame(@PathParam("id") int id, GameRequest request) {
        return snakeService.startGame(id, request).toResponseObject();
    }

    @POST()
    @Path("move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response performMove(@PathParam("id") int id, GameRequest request) {
        return snakeService.calculateMove(id, request).toResponseObject();
    }

    @POST()
    @Path("end")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endGame(@PathParam("id") int id, GameRequest request) {
        return snakeService.endGame(id, request).toResponseObject();
    }
}
