package de.ft.ekes.BackEnd.virtualtileworld;

public class Tile {
    private LandType landType;
    private float foodValue = 0;
    private float fertility = 0;

    public Tile(LandType landType) {
        this.landType = landType;
    }

    public LandType getLandType() {
        return landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }

    public float getFoodValue() {
        return foodValue;
    }

    public void setFoodValue(float foodValue) {
        this.foodValue = foodValue;
    }

    public float getFertility() {
        return fertility;
    }

    public void setFertility(float fertility) {
        this.fertility = fertility;
    }

}
