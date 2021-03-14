package de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunctions;

public class Sigmoid implements ActivationFunction {
    @Override
    public float activation(float value) {
        return (float) (1f / (1f + Math.pow(Math.E, -value)));
    }

    @Override
    public float derivative(float value) {
        float sigmoid = activation(value);
        return sigmoid * (1 - sigmoid);
    }
}
