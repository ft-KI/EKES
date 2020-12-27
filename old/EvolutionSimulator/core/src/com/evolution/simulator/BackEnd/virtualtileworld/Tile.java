package com.evolution.simulator.BackEnd.virtualtileworld;

public class Tile {
    private LandType landType;
    private float foodvalue=0;
    private float Fruchtbarkeit =0;
    public Tile(LandType landType){
        this.landType=landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }

    public LandType getLandType() {
        return landType;
    }

    public float getFoodvalue() {
        return foodvalue;
    }

    public float getFruchtbarkeit() {
        return Fruchtbarkeit;
    }

    public void setFoodvalue(float foodvalue) {
        this.foodvalue = foodvalue;
    }

    public void setFruchtbarkeit(float fruchtbarkeit) {
        this.Fruchtbarkeit = fruchtbarkeit;
    }

}
