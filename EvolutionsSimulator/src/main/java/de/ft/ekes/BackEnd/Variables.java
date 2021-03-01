package de.ft.ekes.BackEnd;

public class Variables {


    public static float moveFactor = 5;
    public static float rotateFactor = 2;
    public static float moveCostMult = 5;
    public static float rotateCostMult = 3;
    public static float eatMult = 50;
    public static float permanentCostLand = 0.04f;
    public static float permanentCostWater = 3f;
    public static float eatCostMult = 1;
    public static float createChildAge = 6;
    public static float createChildEnergy = 400;
    public static float eatAdmission = 0.8f;
    public static float mutation_percentage = 0.23f;
    public static int mutation_neurons = 4;

    public static void resetToDefault() {
        moveFactor = 5;
        rotateFactor = 2;
        moveCostMult = 5;
        rotateCostMult = 3;
        eatMult = 50;
        permanentCostLand = 0.04f;
        permanentCostWater = 3f;
        eatCostMult = 1;
        createChildAge = 6;
        createChildEnergy = 400;
        eatAdmission = 0.8f;
        mutation_percentage = 0.23f;
        mutation_neurons = 4;

    }
}
