package de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunctions;

public interface ActivationFunction {
    float activation(float value);

    float derivative(float value);
}
