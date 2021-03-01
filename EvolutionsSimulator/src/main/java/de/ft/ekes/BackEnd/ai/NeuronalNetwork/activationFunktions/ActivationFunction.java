package de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunktions;

public interface ActivationFunction {
    float activation(float value);

    float derivative(float value);
}
