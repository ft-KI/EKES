package de.ft.ekes.BackEnd.ai.NeuronalNetwork.neurons;

public class InputNeuron implements Neuron {
    private float value;

    @Override
    public float getOutputValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
