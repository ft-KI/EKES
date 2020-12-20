package com.evolution.simulator.BackEnd.ai.NeuronalNetwork.activationFunktions;

public class Sigmoid implements  ActivationFunktion{
    @Override
    public float activation(float value) {
        return (float)(1f / (1f +  Math.pow(Math.E, -value)));
    }

    @Override
    public float derivative(float value) {
        float sigmoid =  activation(value);
        return sigmoid*(1-sigmoid);
    }
}
