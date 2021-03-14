package de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunctions;

public class Identity implements ActivationFunction {

    public float activation(float input) {
        return input;
    }

    @Override
    public float derivative(float value) {
        return 1;
    }

}
