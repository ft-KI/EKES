package de.ft.ekes.BackEnd.ai.NeuronalNetwork.neurons;


import de.ft.ekes.BackEnd.ai.NeuronalNetwork.Connection;
import de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunktions.ActivationFunction;
import de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunktions.Sigmoid;

import java.util.ArrayList;

public class WorkingNeuron implements Neuron {
    private final ArrayList<Connection> inputConnections = new ArrayList<>();
    private ActivationFunction activationFunction = new Sigmoid();
    private float smallDelta = 0;
    private float value = 0;
    private boolean isvalueset = false;

    @Override
    public float getOutputValue() {
        if (!isvalueset) {
            float sum = 0;
            for (Connection c : inputConnections) {
                sum += c.getOutputValue();
            }
            value = activationFunction.activation(sum);
            isvalueset = true;
        }
        return value;
    }

    public void addInputConnection(Connection ic) {
        inputConnections.add(ic);
    }

    public void calcualteOutputDelta(float should) {
        smallDelta = should - getOutputValue();
    }

    public void backpropagateSmallDelta() {
        for (Connection c : inputConnections) {
            Neuron n = c.getStartNeuron();
            if (c.getStartNeuron() instanceof WorkingNeuron) {
                WorkingNeuron wn = (WorkingNeuron) n;
                wn.smallDelta += this.smallDelta * c.getWeight();
            }

        }
    }

    public void reset() {
        smallDelta = 0;
        isvalueset = false;
    }

    public void randomMutate(float mutationrate) {
        int index = (int) (Math.random() * ((float) inputConnections.size()));
        inputConnections.get(index).setWeight(inputConnections.get(index).getWeight() + (((((float) Math.random()) - 0.5f) * mutationrate)));
    }

    public void deltaLearning(float epsilon) {
        float bigDeltaFaktorbestandteil = activationFunction.derivative(getOutputValue()) * epsilon * smallDelta;
        for (int i = 0; i < inputConnections.size(); i++) {
            float bigDelta = bigDeltaFaktorbestandteil * inputConnections.get(i).getStartNeuron().getOutputValue();
            inputConnections.get(i).addWeight(bigDelta);
        }
    }

    public ArrayList<Connection> getInputConnections() {
        return inputConnections;
    }

    public void setActivationFunktion(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }
}
