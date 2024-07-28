package infrastructure.api;

import application.service.BattleSnakeService;
import infrastructure.api.dto.CreateBattleSnakeRequest;
import infrastructure.api.dto.UpdateBattleSnakeRequest;
import infrastructure.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/battlesnake")
public class BattleSnakeController {
    private final BattleSnakeService service = new BattleSnakeService();

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        return service.getAll().toResponseObject();
    }

    @GET
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSnake(@PathParam("id") int id){
        return service.getSnake(id).toResponseObject();
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSnake(CreateBattleSnakeRequest request){
        return service.createSnake(
            request.author(),
            request.color(),
            request.head(),
            request.tail(),
            request.strategy()
        ).toResponseObject();
    }

    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSnake(UpdateBattleSnakeRequest request){
        return service.updateSnake(
            request.id(),
            request.author(),
            request.color(),
            request.head(),
            request.tail(),
            request.strategy()
        ).toResponseObject();
    }

    @DELETE
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSnake(@PathParam("id") int id){
        return service.deleteSnake(id).toResponseObject();
    }
}
