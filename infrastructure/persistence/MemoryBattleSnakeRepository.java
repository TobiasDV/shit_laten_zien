package infrastructure.persistence;

import application.repository.IBattleSnakeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import domain.BattleSnake;
import domain.SnakeStrategyType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MemoryBattleSnakeRepository implements IBattleSnakeRepository {
    // All my sneeks
    private final List<BattleSnake> data;
    private final String repositoryName = "BattleSnakeInfoRepository";

    // This class is a singleton instance
    private static MemoryBattleSnakeRepository instance;
    public static MemoryBattleSnakeRepository getInstance() {
        if (instance == null) instance = new MemoryBattleSnakeRepository();
        return instance;
    }

    private MemoryBattleSnakeRepository() {
        var maybeData = JsonFileManager.readFromFile(repositoryName, new TypeReference<List<BattleSnake>>() {});
        data = maybeData.orElseGet(ArrayList::new);

        if(data.isEmpty()) { // If no sneeks, add default sneek
            var newSnake = BattleSnake.createBattleSnake("Default", "#FF0000", "evil", "fat-rattle", SnakeStrategyType.AVERAGE);
            newSnake.ifPresent(data::add);
        }
    }

    public void saveToFile() { JsonFileManager.writeToFile(data, repositoryName); }

    @Override
    public Optional<BattleSnake> getById(int id) {
      return data.stream()
            .filter(battleSnake -> battleSnake.getId() == id)
            .findFirst();
    }

    @Override
    public Optional<BattleSnake> update(BattleSnake snake) {
        // Attempt to get snake, return null if not found
        var maybeSnake = getById(snake.getId());
        if(maybeSnake.isEmpty()) return Optional.empty();

        // Update sneek
        var oldSnake = maybeSnake.get();
        oldSnake = snake;
        return Optional.of(oldSnake);
    }

    @Override
    public Optional<BattleSnake> save(BattleSnake snake) {
        snake.setId(getNewId());
        data.add(snake);
        return Optional.of(snake);
    }

    @Override
    public boolean delete(int id) {
        var maybeSnake = getById(id);
        if(maybeSnake.isEmpty()) return false;
        data.remove(maybeSnake.get());
        return true;
    }

    @Override
    public List<BattleSnake> getAll() { return data; }

    private int getNewId() {
        var highest = data.stream()
                .max(Comparator.comparingInt(BattleSnake::getId))
                .orElse(null);
        if(highest == null) return 1;
        else return highest.getId() + 1;
    }
}
