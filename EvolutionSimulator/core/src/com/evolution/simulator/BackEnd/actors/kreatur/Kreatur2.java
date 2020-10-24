package com.evolution.simulator.BackEnd.actors.kreatur;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.Vector2;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.NeuronalNetwork;
import com.evolution.simulator.BackEnd.ai.NeuronalNetwork.activationFunktions.Sigmoid;
import com.evolution.simulator.BackEnd.virtualtileworld.LandType;
import com.evolution.simulator.BackEnd.virtualtileworld.Tile;

import java.util.ArrayList;

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
    private float permanetcostwater=2f;
    private float eatcostMult=1;
    private float costMult=1;
    private float rotationangle=0;

    private float outMoveForward=0;
    private float outRotateRight=0;
    private float outRotateLeft=0;

    private float outEat=0;
    private float outGenerateChildren=0;
    private int feelerlength=15;
   // public Feeler feelerone=new Feeler(-10,feelerlength);
    public ArrayList<Feeler>feelers=new ArrayList<>();
    public Kreatur2(int x, int y, EvolutionsSimulator es){
        super.es=es;
        super.Xposition=x;
        super.Yposition=y;
        generateFeelers();
        this.rotationangle=(float)(Math.random()*Math.PI*2);
        brain=new NeuronalNetwork();
        brain.createInputNeurons(3+feelers.size()*2);
        brain.addHiddenLayer(20);
        brain.addHiddenLayer(20);
        brain.createOutputtNeurons(5);
        brain.connectRandomFullMeshed();
        brain.addBiasforallNeurons();
        brain.setAllActivationfunktions(new Sigmoid());
        this.israndom = true;
    }
    public Kreatur2(Kreatur2 parent){
        super.es=parent.es;
        super.Xposition=parent.getXposition()+10;
        super.Yposition= parent.getYposition()+10;
        generateFeelers();
        this.rotationangle=(float)(Math.random()*Math.PI*2);
        brain=parent.brain.cloneFullMeshed();
        for(int i=0;i<4;i++) {
            brain.randomMutate(0.23f);
        }
        this.israndom = false;
        super.generation=parent.generation+1;
        for(Feeler f:feelers) {
            f.calculateFeelerPosition(rotationangle, super.getXposition(), super.getYposition());
        }
    }
    private void generateFeelers(){
        //feelers.add(new Feeler((float)(20*Math.PI/180), feelerlength));
        //feelers.add(new Feeler((float)(-20*Math.PI/180), feelerlength));
        //feelers.add(new Feeler((float)(45*Math.PI/180), feelerlength));
        //feelers.add(new Feeler((float)(-45*Math.PI/180), feelerlength));
        //feelers.add(new Feeler((float)(135*Math.PI/180), feelerlength));
        //feelers.add(new Feeler((float)(-135*Math.PI/180), feelerlength));

        feelers.add(new Feeler((float)(0), feelerlength));
        feelers.add(new Feeler((float)(0), feelerlength*2));
        feelers.add(new Feeler((float)(0), feelerlength*3));










    }

    @Override
    public void DoStep() {
        brain.reset();
        float INpositionFoodValue=super.es.world.getTilefromActorPosition(super.getXposition(), super.getYposition()).getFoodvalue();
        float INpsitionladtype=super.es.world.getTilefromActorPosition(super.getXposition(),super.getYposition()).getLandType().getValue()*100f;
        float INenergy=energy;

      //  float INLandTypefromTileinViewDirection=feelerone.getFeelerTile(es.world,rotationangle,super.getXposition(),super.getYposition()).getLandType().getValue()*100f;
        //float INFoodValuefromTileinViewDirection=feelerone.getFeelerTile(es.world,rotationangle,super.getXposition(),super.getYposition()).getFoodvalue();
        brain.getInputNeurons().get(0).setValue(INpsitionladtype);
        brain.getInputNeurons().get(1).setValue(INpositionFoodValue);
        brain.getInputNeurons().get(2).setValue(INenergy);
        for(int i=0;i<feelers.size();i++){
            brain.getInputNeurons().get(3+i).setValue(feelers.get(i).getFeelerTile(es.getWorld(),rotationangle,super.getXposition(),super.getYposition()).getLandType().getValue()*100f);
        }
        for(int i=0;i<feelers.size();i++){
            brain.getInputNeurons().get(3+i+feelers.size()).setValue(feelers.get(i).getFeelerTile(es.getWorld(),rotationangle,super.getXposition(),super.getYposition()).getFoodvalue()*100f);
        }
        outMoveForward=brain.getOutputNeurons().get(0).getOutputValue();
        outRotateRight=brain.getOutputNeurons().get(1).getOutputValue();
        outRotateLeft=brain.getOutputNeurons().get(2).getOutputValue();
        outEat=brain.getOutputNeurons().get(3).getOutputValue();
        outGenerateChildren=brain.getOutputNeurons().get(4).getOutputValue();
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
        if(energy>=300 && age>=7) {
            Kreatur2 child = new Kreatur2(this);
            super.es.actorManager.getActors().add(child);
            energy-=300*costMult;
        }
    }
    public void eat(){
        Tile t=super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition());
        if(super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getLandType()==LandType.LAND) {
            float eaten;
            eaten = outEat*0.8f;
            if(super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getFoodvalue()<eaten){
                eaten+=super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getFoodvalue()-eaten;
            }
            energy += eaten*eatMult;
            t.setFoodvalue(t.getFoodvalue() - eaten);
        }
        energy-=outEat*eatcostMult*costMult;
    }
    public void Rotate(){
        float rotate=outRotateLeft-outRotateRight;
        if(rotate>0.3f){
            this.rotationangle+=rotate*rotatFaktor;
            energy-=Math.abs(rotate)*rotateCostMult*costMult;
        }

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


}
