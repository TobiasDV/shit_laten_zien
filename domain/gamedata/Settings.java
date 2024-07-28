package domain.gamedata;

public record Settings(int foodSpawnChance, int minimumFood, int hazardDamagePerTurn, String hazardMap, String hazardMapAuthor, Royale royale, Squad squad) {}