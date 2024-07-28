package domain;

import domain.gamedata.Board;
import domain.gamedata.Snake;
import infrastructure.api.dto.GameMoveResponse;

public interface SnakeStrategy {
    GameMoveResponse getMove(Board board, Snake you);
}
