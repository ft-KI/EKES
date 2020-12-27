package com.evolution.simulator.BackEnd.actors.kreatur;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.NeuronalNetwork;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.activationFunktions.Identity;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.activationFunktions.Sigmoid;
import com.evolution.simulator.BackEnd.virtualtileworld.LandType;
import com.evolution.simulator.BackEnd.virtualtileworld.Tile;

public class Kreatur extends Actor {
    /*
        Inputs:
            -Wasser auf den Tile wo sich die Kreatur Befindet
            -FoodValue von Tile auf dem sich Kreatur Befindet
            -Energy
        Output:
            -move X
            -move Y
            -Eat
            -generate Children
     */
    private NeuronalNetwork brain;
    private float energy=200;
    private float moveFaktor=5;
    private float moveCostMult=5;
    private float eatMult=50;
    private float permanetcostland=0.01f;
    private float permanetcostwater=1f;
    private float eatcostMult=1;
    private float costMult=1;
    public Kreatur(int x, int y, EvolutionsSimulator es){
        super.es=es;
        super.Xposition=x;
        super.Yposition=y;
        brain=new NeuronalNetwork();
        brain.createInputNeurons(3);
        brain.addHiddenLayer(6);
        brain.addHiddenLayer(6);
        brain.createOutputtNeurons(4);
        brain.connectRandomFullMeshed();
        brain.addBiasforallNeurons();
        brain.setAllActivationfunktions(new Sigmoid());
        this.israndom = true;
    }
    public Kreatur(Kreatur parent){
        super.es=parent.es;
        super.Xposition=parent.getXposition()+10;
        super.Yposition= parent.getYposition()+10;
        brain=parent.brain.cloneFullMeshed();
        for(int i=0;i<4;i++) {
            brain.randomMutate(0.23f);
        }
        this.israndom = false;


    }

    @Override
    public void DoStep() {
        brain.reset();
        float positionFoodValue=super.es.world.getTilefromActorPosition(super.getXposition(), super.getYposition()).getFoodvalue();

        brain.setInputValues(super.es.world.getTilefromActorPosition(super.getXposition(),super.getYposition()).getLandType().getValue()*100,positionFoodValue*100,energy);
        move();
        eat();
        if(brain.getOutputNeurons().get(3).getOutputValue()>0.5f){
            createChild();
        }
        if(super.es.world.getTilefromActorPosition(super.getXposition(), super.getYposition()).getLandType()== LandType.LAND){
            costMult=permanetcostland;
        }else{
            costMult=permanetcostwater;
        }
        energy-=permanetcostland*costMult;
        costMult+=age*0.1f;
        if(energy<100){
            super.kill();
        }
    }
    public void createChild(){
        if(energy>=200 && age>=2) {
            Kreatur child = new Kreatur(this);
            super.es.actorManager.getActors().add(child);
            energy-=200*costMult;
        }
    }
    public void eat(){
        Tile t=super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition());
        if(super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getLandType()==LandType.LAND) {
            float eaten;
            eaten = brain.getOutputNeurons().get(2).getOutputValue();
            if(super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getFoodvalue()<eaten){
                eaten+=super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getFoodvalue()-eaten;
            }
            energy += eaten*eatMult;
            t.setFoodvalue(t.getFoodvalue() - eaten);
        }
        energy-=brain.getOutputNeurons().get(2).getOutputValue()*eatcostMult*costMult;
    }
    public void move(){
        Xposition+=((0.5f-brain.getOutputNeurons().get(0).getOutputValue())*moveFaktor);
        Yposition+=(0.5f-brain.getOutputNeurons().get(1).getOutputValue())*moveFaktor;
        energy-=(Math.abs(brain.getOutputNeurons().get(0).getOutputValue())/2+Math.abs(brain.getOutputNeurons().get(0).getOutputValue())/2)*moveCostMult*costMult;

    }
}
