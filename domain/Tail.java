package domain;

public enum Tail {
    DEFAULT("default"),
    BLOCKBUM("block-bum"),
    BOLT("bolt"),
    BONHOMME("bonhomme"),
    CURLED("curled"),
    FATRATTLE("fat-rattle"),
    DOSAMMY("do-sammy"),
    FLAKE("flake"),
    MLHGENE("mlh-gene"),
    NRBOOSTER("nr-booster"),
    RBCNECKTIE("rbc-necktie");

    private final String value;

    Tail(String value) { this.value = value; }
    public String getValue() { return value; }

    public static Tail fromString(String value) throws IllegalArgumentException {
        for (Tail tail : Tail.values())
            if (tail.getValue().equals(value)) return tail;
        return Tail.DEFAULT;
    }
}
