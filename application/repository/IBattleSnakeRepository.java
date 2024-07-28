package application.repository;

import domain.BattleSnake;

import java.util.List;
import java.util.Optional;

public interface IBattleSnakeRepository {
    Optional<BattleSnake> save(BattleSnake snake);
    Optional<BattleSnake> getById(int id);
    Optional<BattleSnake> update(BattleSnake snake);
    List<BattleSnake> getAll();
    boolean delete(int id);

}
