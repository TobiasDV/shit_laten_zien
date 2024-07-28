package application.service;

import application.repository.IBattleSnakeRepository;
import application.repository.ILogger;
import application.service.response.HttpCode;
import application.service.response.ServiceResponse;
import infrastructure.api.dto.GameInfoResponse;
import infrastructure.api.dto.GameRequest;
import infrastructure.api.dto.GameMoveResponse;
import infrastructure.log.ConsoleLogger;
import infrastructure.persistence.MemoryBattleSnakeRepository;

public class BattleSnakeGameService {
    private final IBattleSnakeRepository repository = MemoryBattleSnakeRepository.getInstance();

    public ServiceResponse<GameInfoResponse> getSnakeInfo(int id) {
        var maybeSnake = repository.getById(id);
        if(maybeSnake.isEmpty())
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id "+id+" not found");

        var snake = maybeSnake.get();
        var info = new GameInfoResponse(
                snake.getApiVersion(),
                snake.getVersion(),
                snake.getAuthor(),
                snake.getColor(),
                snake.getHead(),
                snake.getTail(),
                snake.getStrategy()
        );

        return new ServiceResponse<>(info);
    }

    public ServiceResponse<Void> startGame(int id, GameRequest request) {
        var maybeSnake = repository.getById(id);
        if(maybeSnake.isEmpty()) // If no snake found
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id "+id+" not found");
        var snake = maybeSnake.get();
        snake.startGame(request);
        return new ServiceResponse<>(HttpCode.OK, "Game started");
    }

    public ServiceResponse<GameMoveResponse> calculateMove(int id, GameRequest request) {
        var maybeSnake = repository.getById(id);
        if(maybeSnake.isEmpty()) // If no snake found
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id "+id+" not found");

        // Attempt to calculate move
        var snake = maybeSnake.get();
        var maybeMove = snake.calculateMove(request);
        return maybeMove.map(ServiceResponse::new
        ).orElseGet(() ->
            new ServiceResponse<>(HttpCode.BAD_REQUEST, "Couldn't calculate move with given request data")
        );
    }

    public ServiceResponse<Void> endGame(int id, GameRequest request) {
        var maybeSnake = repository.getById(id);
        if(maybeSnake.isEmpty()) // If no snake found
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id "+id+" not found");

        // Attempt to end game
        var snake = maybeSnake.get();
        if(snake.endGame(request))
            return new ServiceResponse<>(HttpCode.OK, "Successfully ended game");

        return new ServiceResponse<>(HttpCode.BAD_REQUEST, "Couldn't end game with given request data");
    }
}
