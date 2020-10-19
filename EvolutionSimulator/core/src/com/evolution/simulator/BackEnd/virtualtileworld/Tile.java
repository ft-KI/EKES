package com.evolution.simulator.BackEnd.virtualtileworld;

public class Tile {
    private LandType landType;
    private float foodvalue=0;
    private float growspeed=0;
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

    public float getGrowspeed() {
        return growspeed;
    }

    public void setFoodvalue(float foodvalue) {
        this.foodvalue = foodvalue;
    }

    public void setGrowspeed(float growspeed) {
        this.growspeed = growspeed;
    }

}
