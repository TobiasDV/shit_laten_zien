package infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import domain.gamedata.Board;
import domain.gamedata.Game;
import domain.gamedata.Snake;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GameRequest(Game game, int turn, Board board, Snake you) {}