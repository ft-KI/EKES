package com.evolution.simulator.BackEnd.virtualtileworld;

public class Tile {
    LandType landType;
    public Tile(LandType landType){
        this.landType=landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }

    public LandType getLandType() {
        return landType;
    }
}
