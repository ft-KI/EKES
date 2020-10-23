package com.evolution.simulator.BackEnd.actors.kreatur;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.Vector2;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.NeuronalNetwork;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.activationFunktions.Sigmoid;
import com.evolution.simulator.BackEnd.virtualtileworld.LandType;
import com.evolution.simulator.BackEnd.virtualtileworld.Tile;

public class Kreatur2 extends Actor {
    /*
        Inputs:
            -Wasser auf den Tile wo sich die Kreatur Befindet
            -FoodValue von Tile auf dem sich Kreatur Befindet
            -Energy
        Output:
            -move Forward
            -move Rotate
            -Eat
            -generate Children
     */
    private NeuronalNetwork brain;
    private float energy=200;
    private float moveFaktor=5;
    private float rotatFaktor=2;
    private float moveCostMult=5;
    private float rotateCostMult=3;
    private float eatMult=50;
    private float permanetcostland=0.01f;
    private float permanetcostwater=1f;
    private float eatcostMult=1;
    private float costMult=1;
    private float rotationangle=0;

    private float outMoveForward=0;
    private float outRotate=0;
    private float outEat=0;
    private float outGenerateChildren=0;
    private float feelerlength=15;
    private Vector2 feelerListPos=new Vector2();
    public Kreatur2(int x, int y, EvolutionsSimulator es){
        super.es=es;
        super.Xposition=x;
        super.Yposition=y;
        this.rotationangle=(float)(Math.random()*Math.PI*2);
        brain=new NeuronalNetwork();
        brain.createInputNeurons(5);
        brain.addHiddenLayer(20);
        brain.addHiddenLayer(20);
        brain.createOutputtNeurons(4);
        brain.connectRandomFullMeshed();
        brain.addBiasforallNeurons();
        brain.setAllActivationfunktions(new Sigmoid());
        this.israndom = true;
    }
    public Kreatur2(Kreatur2 parent){
        super.es=parent.es;
        super.Xposition=parent.getXposition()+10;
        super.Yposition= parent.getYposition()+10;
        this.rotationangle=(float)(Math.random()*Math.PI*2);
        brain=parent.brain.cloneFullMeshed();
        for(int i=0;i<4;i++) {
            brain.randomMutate(0.23f);
        }
        this.israndom = false;
        super.generation=parent.generation+1;
        calcFeeler();
    }
    public void calcFeeler(){
        feelerListPos.set((int)(super.getXposition()+Math.cos(rotationangle)*feelerlength),(int)(super.getYposition()+Math.sin(rotationangle)*feelerlength));

    }
    @Override
    public void DoStep() {
        brain.reset();
        float INpositionFoodValue=super.es.world.getTilefromActorPosition(super.getXposition(), super.getYposition()).getFoodvalue();
        float INpsitionladtype=super.es.world.getTilefromActorPosition(super.getXposition(),super.getYposition()).getLandType().getValue()*100;
        float INenergy=energy;
        calcFeeler();
        float INLandTypefromTileinViewDirection=super.es.world.getTilefromActorPosition((int)(feelerListPos.x),(int)(feelerListPos.y)).getLandType().getValue()*100;
        float INFoodValuefromTileinViewDirection=super.es.world.getTilefromActorPosition((int)(feelerListPos.x),(int)(feelerListPos.y)).getFoodvalue();
        brain.setInputValues(INpsitionladtype,INFoodValuefromTileinViewDirection,INLandTypefromTileinViewDirection,INpositionFoodValue,INenergy);

        outMoveForward=brain.getOutputNeurons().get(0).getOutputValue();
        outRotate=brain.getOutputNeurons().get(1).getOutputValue();
        outEat=brain.getOutputNeurons().get(2).getOutputValue();
        outGenerateChildren=brain.getOutputNeurons().get(3).getOutputValue();
        Rotate();
        moveForward();
        eat();
        if(outGenerateChildren>0.5f){
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
        if(energy>=300 && age>=4) {
            Kreatur2 child = new Kreatur2(this);
            super.es.actorManager.getActors().add(child);
            energy-=200*costMult;
        }
    }
    public void eat(){
        Tile t=super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition());
        if(super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getLandType()==LandType.LAND) {
            float eaten;
            eaten = outEat;
            if(super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getFoodvalue()<eaten){
                eaten+=super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getFoodvalue()-eaten;
            }
            energy += eaten*eatMult;
            t.setFoodvalue(t.getFoodvalue() - eaten);
        }
        energy-=outEat*eatcostMult*costMult;
    }
    public void Rotate(){
        this.rotationangle+=0.5f-outRotate*rotatFaktor;
        energy-=outRotate*rotateCostMult*costMult;
    }
    public void moveForward(){
        Xposition+=Math.cos(this.rotationangle)*moveFaktor*outMoveForward;
        Yposition+=Math.sin(this.rotationangle)*moveFaktor*outMoveForward;
        energy-=(outMoveForward)*moveCostMult*costMult;

    }

    public float getAge() {
        return age;
    }

    public float getFeelerlength() {
        return feelerlength;
    }

    public Vector2 getFeelerListPos() {
        return feelerListPos;
    }
}
