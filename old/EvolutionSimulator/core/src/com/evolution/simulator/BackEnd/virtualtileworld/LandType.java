package com.evolution.simulator.BackEnd.virtualtileworld;

public enum LandType {
    NONE(0),LAND(0),WATER(1);
    private int value;

    private LandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
