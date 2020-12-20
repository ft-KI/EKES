package com.evolution.simulator.BackEnd.ai.NeuronalNetwork;





import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.activationFunktions.ActivationFunktion;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.neurons.InputNeuron;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.neurons.WorkingNeuron;

import java.util.ArrayList;

public class NeuronalNetwork {
    private ArrayList<InputNeuron> inputNeurons=new ArrayList<>();
    private ArrayList<ArrayList<WorkingNeuron>>hiddenNeurons=new ArrayList<>();
    private ArrayList<WorkingNeuron>outputNeurons=new ArrayList<>();
    private boolean isBiasUsed=false;

    public NeuronalNetwork(){

    }

    public void backpropagation(float[] shoulds, float epsilon) {
        if(shoulds.length != outputNeurons.size()) {
            throw new IllegalArgumentException();
        }
        reset();

        for(int i=0;i<outputNeurons.size();i++){
            outputNeurons.get(i).calcualteOutputDelta(shoulds[i]);
        }

        if(hiddenNeurons.size()>0){
            for(int i=0;i<outputNeurons.size();i++){
                outputNeurons.get(i).backpropagateSmallDelta();
            }
            for(int a=1;a<hiddenNeurons.size();a++){
                for(int i=0;i<hiddenNeurons.get(a).size();i++){
                    hiddenNeurons.get(a).get(i).backpropagateSmallDelta();
                }
            }
        }

        for(int i=0;i<shoulds.length;i++){
            outputNeurons.get(i).deltaLearning(epsilon);
        }
        for(int i = hiddenNeurons.size() - 1; i >= 0; i--) {
            for (int a = 0; a < hiddenNeurons.get(i).size(); a++) {
                hiddenNeurons.get(i).get(a).deltaLearning(epsilon);
            }
        }

    }
    public void reset(){
        for(ArrayList<WorkingNeuron> a:hiddenNeurons){
            for(WorkingNeuron wn:a) {
                wn.reset();
            }
        }
        for(WorkingNeuron on:outputNeurons){
            on.reset();
        }
    }

    public void setAllActivationfunktions(ActivationFunktion af){
        for(ArrayList<WorkingNeuron> a:hiddenNeurons){
            for(WorkingNeuron wn:a) {
                wn.setActivationFunktion(af);
            }
        }
        for(WorkingNeuron n:outputNeurons){
            n.setActivationFunktion(af);
        }
    }
    public void clearConnections(){
        for(ArrayList<WorkingNeuron> a:hiddenNeurons){
            for(WorkingNeuron wn:a) {
                wn.getInputConnections().clear();
            }
        }
        for(WorkingNeuron n:outputNeurons){
            n.getInputConnections().clear();
        }
    }
    public void clearNetwork(){
        inputNeurons.clear();
        hiddenNeurons.clear();
        outputNeurons.clear();
        isBiasUsed=false;
    }
    public void createInputNeurons(int n){
        for(int i=0;i<n;i++){
            inputNeurons.add(new InputNeuron());
        }
    }
    public void addHiddenLayer(int n){
        ArrayList<WorkingNeuron>hidden=new ArrayList<>();
        hiddenNeurons.add(hidden);
        for(int i=0;i<n;i++){
            hidden.add(new WorkingNeuron());
        }
    }
    public void createOutputtNeurons(int n){
        for(int i=0;i<n;i++){
            outputNeurons.add(new WorkingNeuron());
        }
    }
    public void setInputValues(float... v){
        if(v.length!=inputNeurons.size()){
            throw new IndexOutOfBoundsException();
        }
        for(int i=0;i<inputNeurons.size();i++){
            inputNeurons.get(i).setValue(v[i]);
        }
    }
    public void createFullMesh(float... weights) {
        if(hiddenNeurons.size()==0) {
            if(weights.length != inputNeurons.size() * outputNeurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;



            for(WorkingNeuron wn : outputNeurons) {
                for(InputNeuron in : inputNeurons) {
                    wn.addInputConnection(new Connection(in, weights[index++]));
                }
            }
        }else{
            if(weights.length != inputNeurons.size() * hiddenNeurons.size() + hiddenNeurons.size() * outputNeurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;

            for(WorkingNeuron hidden : hiddenNeurons.get(0)) {
                for(InputNeuron in : inputNeurons) {
                    hidden.addInputConnection(new Connection(in, weights[index++]));
                }
            }

            for(int i=0;i<hiddenNeurons.size()-1;i++){
                for(int j=0;j<hiddenNeurons.get(i).size();j++){
                    for(int k=0;k<hiddenNeurons.get(i+1).size();k++){
                        hiddenNeurons.get(i+1).get(k).addInputConnection(new Connection(hiddenNeurons.get(i).get(j),weights[index++]));
                    }
                }
            }

            for(WorkingNeuron out : outputNeurons) {
                for(WorkingNeuron hidden : hiddenNeurons.get(hiddenNeurons.size()-1)) {
                    out.addInputConnection(new Connection(hidden, weights[index++]));
                }
            }
        }

    }

    private void connectFullMeshed(boolean random,float initWeights) {
        if(hiddenNeurons.size()==0) {
            for (WorkingNeuron wn : outputNeurons) {
                for (InputNeuron in : inputNeurons) {
                    if(random) {
                        wn.addInputConnection(new Connection(in, (float) Math.random()*2f-1f));
                    }else{
                        wn.addInputConnection(new Connection(in, initWeights));

                    }
                }
            }
        }else{
            for(WorkingNeuron hidden : hiddenNeurons.get(0)) {
                for(InputNeuron in : inputNeurons) {
                    if(random) {
                        hidden.addInputConnection(new Connection(in, (float) Math.random()*2f-1f));
                    }else{
                        hidden.addInputConnection(new Connection(in, initWeights));

                    }
                }
            }
/*
            for(int i=0;i<hiddenNeurons.size()-1;i++){
                for(int j=0;j<hiddenNeurons.get(i).size();j++){
                    for(int k=0;k<hiddenNeurons.get(i+1).size();k++){
                        if(random) {
                            hiddenNeurons.get(i + 1).get(k).addInputConnection(new Connection(hiddenNeurons.get(i).get(j), (float) Math.random()));
                        }else{
                            hiddenNeurons.get(i + 1).get(k).addInputConnection(new Connection(hiddenNeurons.get(i).get(j), initWeights));

                        }
                    }
                }
            }
            */

            for(int layer=1;layer<hiddenNeurons.size();layer++){
                for(int right=0;right<hiddenNeurons.get(layer).size();right++){
                    for(int left=0;left<hiddenNeurons.get(layer-1).size();left++){
                        if(random) {
                            hiddenNeurons.get(layer).get(right).addInputConnection(new Connection(hiddenNeurons.get(layer-1).get(left),(float) Math.random()*2f-1f));

                        }else{
                            hiddenNeurons.get(layer).get(right).addInputConnection(new Connection(hiddenNeurons.get(layer-1).get(left),initWeights));

                        }
                    }
                }
            }

            for(WorkingNeuron out : outputNeurons) {
                for(WorkingNeuron hidden : hiddenNeurons.get(hiddenNeurons.size()-1)) {
                    if(random) {
                        out.addInputConnection(new Connection(hidden, (float) Math.random()*2f-1f));
                    }else{
                        out.addInputConnection(new Connection(hidden, initWeights));

                    }
                }
            }

        }
    }


    public void addBiasforallNeurons(){
        InputNeuron bias = new InputNeuron();
        bias.setValue(1f);
        for(WorkingNeuron wn:outputNeurons){
            wn.addInputConnection(new Connection(bias,(float) Math.random()*2f-1f));
        }
        for(ArrayList<WorkingNeuron> awn:hiddenNeurons){
            for(WorkingNeuron wn:awn){
                wn.addInputConnection(new Connection(bias,(float) Math.random()*2f-1f));
            }
        }
        isBiasUsed=true;
    }


    public void connectFullMeshed() {
        connectFullMeshed(false,0.5f);
    }
    public void connectFullMeshed(float initWeights) {
        connectFullMeshed(false,initWeights);
    }

    public void connectRandomFullMeshed() {
        connectFullMeshed(true,-1);
    }


    public ArrayList<InputNeuron> getInputNeurons() {
        return inputNeurons;
    }

    public ArrayList<WorkingNeuron> getOutputNeurons() {
        return outputNeurons;
    }


    public NeuronalNetwork cloneFullMeshed() {
        NeuronalNetwork network = new NeuronalNetwork();
        network.createInputNeurons(this.inputNeurons.size());
        for (int i = 0; i < this.hiddenNeurons.size(); i++) {
            network.addHiddenLayer(this.hiddenNeurons.get(i).size());
        }
        network.createOutputtNeurons(this.outputNeurons.size());
        network.connectFullMeshed();

        if(isBiasUsed) {
            network.addBiasforallNeurons();
        }
        for(int i=0;i<this.outputNeurons.size();i++){
            for(int i1=0;i1<this.outputNeurons.get(i).getInputConnections().size();i1++) {
                    network.getOutputNeurons().get(i).getInputConnections().get(i1).setWeight(this.outputNeurons.get(i).getInputConnections().get(i1).getWeight());
            }
        }
        for(int i=0;i<this.hiddenNeurons.size();i++){
            for(int i1=0;i1<this.hiddenNeurons.get(i).size();i1++){
                for(int i2=0;i2<this.hiddenNeurons.get(i).get(i1).getInputConnections().size();i2++){
                    network.hiddenNeurons.get(i).get(i1).getInputConnections().get(i2).setWeight(this.hiddenNeurons.get(i).get(i1).getInputConnections().get(i2).getWeight());
                }
            }
        }


        return network;
    }
    public int NeuronHiddenandOutputCount(){
        int count=outputNeurons.size();
        for(int i=0;i<hiddenNeurons.size();i++){
            count+=hiddenNeurons.get(i).size();
        }
        return count;
    }
    public void randomMutate(float mutationrate){
        int index=(int)(Math.random()* ((float) NeuronHiddenandOutputCount()));
        if(index<outputNeurons.size()){
            outputNeurons.get(index).randomMutate(mutationrate);
        }
        for(int i=0;i<hiddenNeurons.size();i++){
            if(index<hiddenNeurons.get(i).size()){
                hiddenNeurons.get(i).get(index).randomMutate(mutationrate);
            }
        }
    }

    public ArrayList<ArrayList<WorkingNeuron>> getHiddenNeurons() {
        return hiddenNeurons;
    }

    public void setHiddenNeurons(ArrayList<ArrayList<WorkingNeuron>> hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
    }

    public void setInputNeurons(ArrayList<InputNeuron> inputNeurons) {
        this.inputNeurons = inputNeurons;
    }

    public void setOutputNeurons(ArrayList<WorkingNeuron> outputNeurons) {
        this.outputNeurons = outputNeurons;
    }
}
