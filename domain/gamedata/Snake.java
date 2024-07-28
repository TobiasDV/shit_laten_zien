package domain.gamedata;

import java.util.List;

public record Snake(
    String id,
    String name,
    int health,
    List<Coordinate> body,
    String latency,
    Coordinate head,
    int length,
    String shout,
    String squad,
    Customizations customizations
    ) {}