package ru.mipt.bit.platformer.entity.objects.generators;

public enum StrategyGenerate {
    RANDOM("RANDOM"),
    FROM_FILE_PLAIN_TEXT("FROM_FILE_PLAIN_TEXT"),
    ;

    private final String name;

    StrategyGenerate(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
