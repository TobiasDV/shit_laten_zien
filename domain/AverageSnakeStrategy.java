package domain;

import domain.gamedata.Board;
import domain.gamedata.Coordinate;
import domain.gamedata.Snake;
import infrastructure.api.dto.GameMoveResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This snake algorithm is the average one, but it is the best one I have ; __ ;
 */
public class AverageSnakeStrategy implements SnakeStrategy {
    private Board board;
    private Snake you;

    @Override
    public GameMoveResponse getMove(Board board, Snake you) {
        this.board = board;
        this.you = you;
        return new GameMoveResponse("I'm the average snake :(", findBestMove());
    }

    private String findBestMove() {
        Map<String, Integer> moveScores = new HashMap<>();
        Coordinate snakeHead = you.body().get(0);
        Coordinate snakeNeck = you.body().get(1); // Get the second segment of the snake's body

        List<String> possibleMoves = List.of("up", "down", "left", "right");
        for (String move : possibleMoves) {
            Coordinate nextPosition = getNextPosition(snakeHead, move);
            if (nextPosition.equals(snakeNeck)) {
                continue; // Skip this move if it would make the snake go backwards
            }
            if (isPositionSafe(nextPosition, snakeHead)) {
                int distanceToFood = calculateDistanceToClosestFood(nextPosition);
                int score = 1000 - distanceToFood;  // Prefer closer food
                moveScores.put(move, score);
            } else {
                moveScores.put(move, Integer.MIN_VALUE); // Penalize unsafe moves heavily
            }
        }

        return moveScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("up"); // Default move if none are safe
    }

    private boolean isPositionSafe(Coordinate nextPosition, Coordinate snakeHead) {
        if (!isInsideBoard(nextPosition)) {
            return false;
        }

        List<Coordinate> simulatedBody = new ArrayList<>(you.body());
        simulatedBody.add(0, nextPosition); // Add new head
        simulatedBody.remove(simulatedBody.size() - 1); // Remove last segment to simulate movement

        if (simulatedBody.subList(1, simulatedBody.size()).contains(nextPosition)) {
            return false; // Check for collision with self
        }

        // Check for collision with other snakes
        for (Snake snake : board.snakes()) {
            if (!snake.id().equals(you.id()) && snake.body().contains(nextPosition)) {
                return false;
            }
        }

        return true; // No collisions detected
    }

    private boolean isInsideBoard(Coordinate position) {
        return position.x() >= 0 && position.x() < board.width() &&
                position.y() >= 0 && position.y() < board.height();
    }

    private Coordinate getNextPosition(Coordinate head, String direction) {
        return switch (direction) {
            case "up" -> new Coordinate(head.x(), head.y() + 1);
            case "down" -> new Coordinate(head.x(), head.y() - 1);
            case "left" -> new Coordinate(head.x() - 1, head.y());
            case "right" -> new Coordinate(head.x() + 1, head.y());
            default -> head; // This should not happen
        };
    }

    private int calculateDistanceToClosestFood(Coordinate position) {
        return board.food().stream()
                .mapToInt(food -> Math.abs(food.x() - position.x()) + Math.abs(food.y() - position.y()))
                .min().orElse(Integer.MAX_VALUE);
    }
}
