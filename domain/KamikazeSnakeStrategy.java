package domain;

import domain.gamedata.Board;
import domain.gamedata.Coordinate;
import domain.gamedata.Snake;
import infrastructure.api.dto.GameMoveResponse;

public class KamikazeSnakeStrategy implements SnakeStrategy{
    @Override
    public GameMoveResponse getMove(Board board, Snake you) {
        Coordinate head = you.body().get(0);
        int middleX = board.width() / 2;

        if(you.health() < 85)
            return new GameMoveResponse("Tenno Heika Banzai!", "down");
        

        String move;
        if (head.y() < board.height() - 1) {
            if (head.x() < middleX) {
                move = "right";
            } else if (head.x() > middleX) {
                move = "left";
            } else {
                move = "up";
            }
        } else {
            if (isPositionSafe(new Coordinate(head.x(), head.y() - 1), you, board)) {
                move = "down";
            } else {
                // If moving down is not safe, try moving left or right
                if (isPositionSafe(new Coordinate(head.x() - 1, head.y()), you, board)) {
                    move = "left";
                } else if (isPositionSafe(new Coordinate(head.x() + 1, head.y()), you, board)) {
                    move = "right";
                } else {
                    // If no move is safe, stay in place
                    move = "up";
                }
            }
        }
        return new GameMoveResponse("Watashi wa tobimasu", move);
    }
    private boolean isPositionSafe(Coordinate nextPosition, Snake you, Board board) {
        // Check if the position is within the board
        if (nextPosition.x() < 0 || nextPosition.x() >= board.width() || nextPosition.y() < 0 || nextPosition.y() >= board.height()) 
            return false;
        

        // Check if the position collides with the snake's body
        for (Coordinate bodyPart : you.body()) {
            if (bodyPart.equals(nextPosition)) 
                return false;
        }

        return true;
    }
}
