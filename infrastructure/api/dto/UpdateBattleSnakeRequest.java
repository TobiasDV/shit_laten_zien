package infrastructure.api.dto;

import domain.SnakeStrategyType;

public record UpdateBattleSnakeRequest(int id, String author, String color, String head, String tail, SnakeStrategyType strategy) {
}
