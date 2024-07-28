package domain;

import infrastructure.api.dto.GameRequest;
import infrastructure.api.dto.GameMoveResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BattleSnake {
    private int id = 0;
    private String apiVersion;
    private String author;
    private String color;
    private Head head;
    private Tail tail;
    private String version;
    private SnakeStrategyType strategy;
    private List<GameInstance> games = new ArrayList<>();

   public static Optional<BattleSnake> createBattleSnake(
        String author,
        String color,
        String head,
        String tail,
        SnakeStrategyType strategy
    )
    {
        // Check for null values
        if (author == null || color == null || head == null || tail == null || strategy == null)
            return Optional.empty();

        var newSnake = new BattleSnake();
        newSnake.author = author;
        newSnake.color = color;
        newSnake.strategy = strategy;
        // Enums have a default value, in case the string is not found
        newSnake.head = Head.fromString(head);
        newSnake.tail = Tail.fromString(tail);
        // Set default values
        newSnake.apiVersion = "1";
        newSnake.version = "1";

        return Optional.of(newSnake);
    }

    public Optional<BattleSnake> updateBattleSnake(
        String author,
        String color,
        String head,
        String tail,
        SnakeStrategyType strategy
    )
    {
        // Check for null values
        if (author == null || color == null || head == null || tail == null || strategy == null)
            return Optional.empty();

        this.author = author;
        this.color = color;
        this.strategy = strategy;
        // Enums have a default value, in case the string is not found
        this.head = Head.fromString(head);
        this.tail = Tail.fromString(tail);

        return Optional.of(this);
    }

    public void startGame(GameRequest request) { games.add(new GameInstance(request, strategy)); }

    public Optional<GameMoveResponse> calculateMove(GameRequest request){
        // Attempt to get game, if not found return null
        var maybeGame = getGameInstance(request.game().id());
        if(maybeGame.isEmpty()) return Optional.empty();
        // Update game instance and calculate move
        var gameInstance = maybeGame.get();
        gameInstance.updateGameInstance(request);
        return Optional.of(gameInstance.calculateMove(strategy));
    }

    public boolean endGame(GameRequest request) {
        // Attempt to get game, if not found return null
        var maybeGame = getGameInstance(request.game().id());
        if (maybeGame.isEmpty()) return false;
        // Update game instance and set game over
        var gameInstance = maybeGame.get();
        gameInstance.updateGameInstance(request);
        gameInstance.setGameOver(true);
        return true;
    }

    public Optional<GameInstance> getGameInstance(String id) {
        return games.stream()
            .filter(game -> game.getGame().id().equals(id))
            .findFirst();
    }

    public boolean deleteGameInstance(String id) {
        var maybeGame = getGameInstance(id);
        if (maybeGame.isEmpty()) return false;
        games.remove(maybeGame.get());
        return true;
    }

     /*************** Json properties for serialization *****************/

    public BattleSnake() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getApiVersion(){ return this.apiVersion; }
    public void setApiVersion(String apiversion) { this.apiVersion = apiversion; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getHead() { return head.getValue(); }
    public void setHead(String head) { this.head = Head.fromString(head); }

    public String getTail() { return tail.getValue(); }
    public void setTail(String tail) { this.tail = Tail.fromString(tail); }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public SnakeStrategyType getStrategy() { return strategy; }
    public void setStrategy(SnakeStrategyType strategy) { this.strategy = strategy; }

    public List<GameInstance> getGames() { return games; }
    public void setGames(List<GameInstance> games) { this.games = games; }
    public List<GameInstance> getFinishedGames() {
        return games.stream()
                .filter(GameInstance::isGameOver)
                .collect(Collectors.toList());
    }
}