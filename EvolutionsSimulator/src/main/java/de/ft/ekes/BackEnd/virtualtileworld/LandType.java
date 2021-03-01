package de.ft.ekes.BackEnd.virtualtileworld;

public enum LandType {
    NONE(0), LAND(0), WATER(1);
    private final int value;

    LandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
