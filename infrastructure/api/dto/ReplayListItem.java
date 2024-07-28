package infrastructure.api.dto;

import domain.BattleSnake;
import domain.GameInstance;
import domain.SnakeStrategyType;

public record ReplayListItem(String id, SnakeStrategyType type, int turn) {
    public static ReplayListItem fromBattleSnake(GameInstance instance) {
        return new ReplayListItem(
                instance.getId(),
                instance.getUsedStrategy(),
                instance.getTurn()
        );
    }
}
