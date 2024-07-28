package domain.gamedata;

public record Game(String id, Ruleset ruleset, String map, int timeout, String source) {}