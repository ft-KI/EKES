package com.evolution.simulator.BackEnd.actors.kreatur;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.NeuronalNetwork;
import com.evolution.simulator.BackEnd.virtualtileworld.LandType;
import com.evolution.simulator.BackEnd.virtualtileworld.Tile;
import com.sun.org.apache.bcel.internal.generic.LADD;

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
    public Kreatur(int x, int y, EvolutionsSimulator es){
        super.es=es;
        super.Xposition=x;
        super.Yposition=y;
        brain=new NeuronalNetwork();
        brain.createInputNeurons(3);
        brain.addHiddenLayer(6);
        brain.createOutputtNeurons(4);
        brain.connectRandomFullMeshed();
    }
    public Kreatur(Kreatur parent){
        super.es=parent.es;
        super.Xposition=parent.getXposition()+10;
        super.Yposition= parent.getYposition()+10;
        brain=parent.brain.cloneFullMeshed();
        for(int i=0;i<4;i++) {
            brain.randomMutate(0.23f);
        }


    }

    @Override
    public void doStep() {
        float positionFoodValue=super.es.world.getTilefromActorPosition(super.getXposition(), super.getYposition()).getFoodvalue();
        float isWater=0;
        if(super.es.world.getTilefromActorPosition(super.getXposition(), super.getYposition()).getLandType()== LandType.WATER){
            isWater=1;
        }
        brain.setInputValues(isWater,positionFoodValue,energy);
        move();
        eat();
        if(brain.getOutputNeurons().get(3).getOutputValue()>0.5f){
            createChild();
        }

        if(energy<60){
            super.kill();
        }
    }
    public void createChild(){
        if(energy>=250) {
            Kreatur child = new Kreatur(this);
            super.es.actorManager.getActors().add(child);
            energy-=50;
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
            energy += eaten;
            System.out.println(eaten);
            t.setFoodvalue(t.getFoodvalue() - eaten);
        }
    }
    public void move(){
        Xposition+=(0.5f-brain.getOutputNeurons().get(0).getOutputValue()*moveFaktor);
        Yposition+=(0.5f-brain.getOutputNeurons().get(1).getOutputValue())*moveFaktor;
       // energy-=Math.abs(brain.getOutputNeurons().get(0).getOutputValue())+Math.abs(brain.getOutputNeurons().get(0).getOutputValue());

    }
}
