package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.gamedata.Board;
import domain.gamedata.Game;
import domain.gamedata.Snake;
import infrastructure.api.dto.GameRequest;
import infrastructure.api.dto.GameMoveResponse;

public class GameInstance {
    private Game game;
    private int turn;
    private Board board;
    private Snake you;
    private boolean isGameOver = false;
    private SnakeStrategyType usedStrategy;

    public GameInstance() {} // JSON constructor

    public GameInstance(GameRequest request, SnakeStrategyType strategy) {
        this.updateGameInstance(request);
        this.usedStrategy = strategy;
    }

    public void updateGameInstance(GameRequest request) {
        this.game = request.game();
        this.turn = request.turn();
        this.board = request.board();
        this.you = request.you();
    }

    @JsonIgnore
    public String getId() { return game.id(); }

    public GameMoveResponse calculateMove(SnakeStrategyType type) {
        return getStrategy(type).getMove(board, you);
    }

    private SnakeStrategy getStrategy(SnakeStrategyType type) {
        if (type == SnakeStrategyType.AVERAGE) return new AverageSnakeStrategy();
        return new KamikazeSnakeStrategy(); // Default strategy
    }

    // Getters & setters (for JSON serialization)
    public boolean isGameOver() { return isGameOver; }
    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public int getTurn() { return turn; }
    public void setTurn(int turn) { this.turn = turn; }

    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }

    public Snake getYou() { return you; }
    public void setYou(Snake you) { this.you = you; }

    public SnakeStrategyType getUsedStrategy() { return usedStrategy; }
    public void setUsedStrategy(SnakeStrategyType usedStrategy) { this.usedStrategy = usedStrategy; }
}