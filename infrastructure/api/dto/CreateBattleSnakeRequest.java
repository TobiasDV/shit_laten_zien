package infrastructure.api.dto;

import domain.SnakeStrategyType;

public record CreateBattleSnakeRequest(String author, String color, String head, String tail, SnakeStrategyType strategy) {
}
