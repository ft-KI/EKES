package com.evolution.simulator.BackEnd;

public class Variables {


    public static float moveFaktor=5;
    public static float rotatFaktor=2;
    public static float moveCostMult=5;
    public static float rotateCostMult=3;
    public static float eatMult=50;
    public static float permanetcostland=0.04f;
    public static float permanetcostwater=3f;
    public static float eatcostMult=1;
    public static float createChildAge = 6;
    public static float createChildEnergie = 400;

    public static void reset() {
        moveFaktor=5;
        rotatFaktor=2;
        moveCostMult=5;
        rotateCostMult=3;
        eatMult=50;
        permanetcostland=0.04f;
        permanetcostwater=3f;
        eatcostMult=1;
        createChildAge = 6;
        createChildEnergie = 400;
    }
}