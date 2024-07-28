package infrastructure.api.dto;

import domain.BattleSnake;
import domain.SnakeStrategyType;

public record BattleSnakeResponse(int id, String apiversion, String author, String color, String head, String tail, String version, SnakeStrategyType strategy) {
    public static BattleSnakeResponse fromBattleSnake(BattleSnake snake) {
        return new BattleSnakeResponse(
                snake.getId(),
                snake.getApiVersion(),
                snake.getAuthor(),
                snake.getColor(),
                snake.getHead(),
                snake.getTail(),
                snake.getVersion(),
                snake.getStrategy()
        );
    }
}
