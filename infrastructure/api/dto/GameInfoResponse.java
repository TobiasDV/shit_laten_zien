package infrastructure.api.dto;

import domain.SnakeStrategyType;

public record GameInfoResponse(String apiVersion, String version, String author, String color, String head, String tail, SnakeStrategyType strategy) {}
