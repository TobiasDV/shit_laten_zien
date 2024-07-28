package application.service;

import application.repository.IBattleSnakeRepository;
import application.service.response.HttpCode;
import application.service.response.ServiceResponse;
import domain.BattleSnake;
import domain.SnakeStrategyType;
import infrastructure.api.dto.BattleSnakeResponse;
import infrastructure.persistence.MemoryBattleSnakeRepository;

import java.util.List;

public class BattleSnakeService {
    private final IBattleSnakeRepository repository = MemoryBattleSnakeRepository.getInstance();

    public ServiceResponse<List<BattleSnakeResponse>> getAll(){
        return new ServiceResponse<>(repository.getAll().stream().map(BattleSnakeResponse::fromBattleSnake).toList());
    }

    public ServiceResponse<BattleSnakeResponse> getSnake(int id){
        var maybeSnake = repository.getById(id);
        // If no snake found
        if(maybeSnake.isEmpty())
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id " + id + " not found");

        return maybeSnake
            .map(battleSnake -> new ServiceResponse<>(BattleSnakeResponse.fromBattleSnake(battleSnake)))
            .orElseGet(() -> new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id " + id + " not found"));
    }

    public ServiceResponse<BattleSnakeResponse> createSnake(
        String author,
        String color,
        String head,
        String tail,
        SnakeStrategyType strategy
    ){
        // Attempt to construct snake with new values
        var maybeSnake = BattleSnake.createBattleSnake(author, color, head, tail, strategy);
        if(maybeSnake.isEmpty())
            return new ServiceResponse<>(HttpCode.BAD_REQUEST, "Couldn't create snake with given values");

        // Save snake to repository
        repository.save(maybeSnake.get());
        return new ServiceResponse<>(HttpCode.CREATED, BattleSnakeResponse.fromBattleSnake(maybeSnake.get()));
    }

    public ServiceResponse<BattleSnakeResponse> updateSnake(
        int id,
        String author,
        String color,
        String head,
        String tail,
        SnakeStrategyType strategy
    ){
        var maybeSnake = repository.getById(id);
        if(maybeSnake.isEmpty())
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id " + id + " not found");

        var snake = maybeSnake.get();
        maybeSnake = snake.updateBattleSnake(author, color, head, tail, strategy);
        if(maybeSnake.isEmpty())
            return new ServiceResponse<>(HttpCode.BAD_REQUEST, "Couldn't update snake with given values");

        // Update snake in repository
        var updatedSnake = repository.update(maybeSnake.get());
        return updatedSnake.map(battleSnake -> new ServiceResponse<>(BattleSnakeResponse.fromBattleSnake(battleSnake)))
            .orElseGet(() -> new ServiceResponse<>(HttpCode.BAD_REQUEST, "Couldn't update snake with given values"));
    }

    public ServiceResponse<Void> deleteSnake(int id){
        var maybeSnake = repository.getById(id);
        if(maybeSnake.isEmpty())
            return new ServiceResponse<>(HttpCode.NOT_FOUND, "Snake with id " + id + " not found");

        if(!repository.delete(id)) // If snake couldn't be deleted
            return new ServiceResponse<>(HttpCode.BAD_REQUEST, "Couldn't delete snake with id: " + id);

        return new ServiceResponse<>(HttpCode.OK, "Successfully deleted snake with id: " + id);
    }
}
