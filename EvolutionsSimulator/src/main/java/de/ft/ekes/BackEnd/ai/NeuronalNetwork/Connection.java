package de.ft.ekes.BackEnd.ai.NeuronalNetwork;


import de.ft.ekes.BackEnd.ai.NeuronalNetwork.neurons.Neuron;

public class Connection {
    private Neuron startNeuron;
    private float weight;
    private float momentum=0;
    public Connection(Neuron StartNeuron, float weight){
        this.startNeuron=StartNeuron;
        this.weight=weight;
    }
    public float getOutputValue(){
        return this.startNeuron.getOutputValue()*weight;
    }

    public float getWeight() {
        return weight;
    }

    public Neuron getStartNeuron() {
        return startNeuron;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
    public void addWeight(float deltaweight){
        momentum+=deltaweight;
        momentum*=0.9f;
        this.weight+=deltaweight+momentum;
    }

    public void setStartNeuron(Neuron startNeuron) {
        this.startNeuron = startNeuron;
    }
}
