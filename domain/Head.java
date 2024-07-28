package domain;

public enum Head {
    DEFAULT("default"),
    BONHOMME("benhomme"),
    BELUGA("beluga"),
    BENDR("bendr"),
    CAFFEINE("caffeine"),
    DEAD("dead"),
    DOSAMMY("do-sammy"),
    EVIL("evil"),
    FANG("fang"),
    GAMER("gamer"),
    SCARF("scarf");

    private final String value;

    Head(String value) { this.value = value; }
    public String getValue() { return value; }

    public static Head fromString(String value) throws IllegalArgumentException {
        for (Head head : Head.values())
            if (head.getValue().equals(value)) return head;
        return Head.DEFAULT;
    }
}
