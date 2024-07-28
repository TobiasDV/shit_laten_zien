package domain.gamedata;

import java.util.List;

public record Board(int height, int width, List<Coordinate> food, List<Coordinate> hazards, List<Snake> snakes) {}