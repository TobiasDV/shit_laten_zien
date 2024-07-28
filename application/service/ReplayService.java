package application.service;

import application.repository.IBattleSnakeRepository;
import application.repository.ILogger;
import application.service.response.HttpCode;
import application.service.response.ServiceResponse;
import domain.GameInstance;
import infrastructure.api.dto.ReplayListItem;
import infrastructure.persistence.MemoryBattleSnakeRepository;

import java.util.List;

public class ReplayService {
    private final IBattleSnakeRepository repository = MemoryBattleSnakeRepository.getInstance();

    public ServiceResponse<List<ReplayListItem>> getAllFromSnake(int snakeId) {
        var maybeSnake = repository.getById(snakeId);
        if(maybeSnake.isEmpty()) // If no snake found
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id "+snakeId+" not found");
        var snake = maybeSnake.get();
        // Return all finished games as replay list items
        return new ServiceResponse<>(snake.getFinishedGames().stream()
                .map(ReplayListItem::fromBattleSnake)
                .toList());
    }

    public ServiceResponse<GameInstance> getGame(int snakeId, String gameId) {
        var maybeSnake = repository.getById(snakeId);
        if(maybeSnake.isEmpty()) // If no snake found
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id "+snakeId+" not found");

        var snake = maybeSnake.get();
        var maybeGame = snake.getGameInstance(gameId);
        // If no game found
        return maybeGame.map(ServiceResponse::new).orElseGet(() ->
                new ServiceResponse<>(HttpCode.NOT_FOUND, "Game with id " + gameId + " not found"));
    }

    public ServiceResponse<Void> deleteGame(int snakeId, String gameId) {
        var maybeSnake = repository.getById(snakeId);
        if(maybeSnake.isEmpty()) // If no snake found
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id "+snakeId+" not found");

        var snake = maybeSnake.get();
        if(snake.deleteGameInstance(gameId)) {
            repository.update(snake);
            return new ServiceResponse<>(HttpCode.OK, "Game with id " + gameId + " deleted");
        }
        return new ServiceResponse<>(HttpCode.NOT_FOUND, "Game with id " + gameId + " not found");
    }
}
