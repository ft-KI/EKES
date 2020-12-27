package de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunktions;

public interface ActivationFunktion {
    float activation(float value);
    float derivative(float value);
}
