package de.ft.ekes.BackEnd.actors.kreatur;

import de.ft.ekes.BackEnd.EvolutionsSimulator;
import de.ft.ekes.BackEnd.Variables;
import de.ft.ekes.BackEnd.actors.Actor;
import de.ft.ekes.BackEnd.ai.NeuronalNetwork.NeuronalNetwork;
import de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunktions.Sigmoid;
import de.ft.ekes.BackEnd.virtualtileworld.LandType;
import de.ft.ekes.BackEnd.virtualtileworld.Tile;

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
    private float moveFaktor= Variables.moveFaktor;
    private float rotatFaktor=Variables.rotatFaktor;
    private float moveCostMult=Variables.moveCostMult;
    private float rotateCostMult=Variables.rotateCostMult;
    private float eatMult=Variables.eatMult;
    private float permanetcostland=Variables.permanetcostland;
    private float permanetcostwater=Variables.permanetcostwater;
    private float eatcostMult=Variables.eatcostMult;
    private float createChildAge = Variables.createChildAge;
    private float createChildEnergie = Variables.createChildEnergie;
    private float eatadmission=Variables.eatadmission;
    private float mutation_percentage=Variables.mutation_percentage;
    private float mutation_neurons=Variables.mutation_neurons;

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
        for(int i=0;i<mutation_neurons;i++) {
            brain.randomMutate(mutation_percentage);
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
        if(energy>=createChildEnergie && age>=createChildAge) {
            Kreatur2 child = new Kreatur2(this);
            super.es.actorManager.getActors().add(child);
            energy-=createChildEnergie/2*costMult;
        }
    }
    public void eat(){
        Tile t=super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition());
        if(super.es.getWorld().getTilefromActorPosition(super.getXposition(),super.getYposition()).getLandType()==LandType.LAND) {
            float eaten;
            eaten = outEat*eatadmission;
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
        if(Math.abs(rotate)>0.3f){
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

    public float getMoveFaktor() {
        return moveFaktor;
    }

    public void setMoveFaktor(float moveFaktor) {
        this.moveFaktor = moveFaktor;
    }

    public float getRotatFaktor() {
        return rotatFaktor;
    }

    public void setRotatFaktor(float rotatFaktor) {
        this.rotatFaktor = rotatFaktor;
    }

    public float getMoveCostMult() {
        return moveCostMult;
    }

    public void setMoveCostMult(float moveCostMult) {
        this.moveCostMult = moveCostMult;
    }

    public float getRotateCostMult() {
        return rotateCostMult;
    }

    public void setRotateCostMult(float rotateCostMult) {
        this.rotateCostMult = rotateCostMult;
    }

    public float getEatMult() {
        return eatMult;
    }

    public void setEatMult(float eatMult) {
        this.eatMult = eatMult;
    }

    public float getPermanetcostland() {
        return permanetcostland;
    }

    public void setPermanetcostland(float permanetcostland) {
        this.permanetcostland = permanetcostland;
    }

    public float getPermanetcostwater() {
        return permanetcostwater;
    }

    public void setPermanetcostwater(float permanetcostwater) {
        this.permanetcostwater = permanetcostwater;
    }

    public float getEatcostMult() {
        return eatcostMult;
    }

    public void setEatcostMult(float eatcostMult) {
        this.eatcostMult = eatcostMult;
    }

    public float getCreateChildAge() {
        return createChildAge;
    }

    public void setCreateChildAge(float createChildAge) {
        this.createChildAge = createChildAge;
    }

    public float getCreateChildEnergie() {
        return createChildEnergie;
    }

    public void setCreateChildEnergie(float createChildEnergie) {
        this.createChildEnergie = createChildEnergie;
    }

    public void setFeelerlength(int feelerlength) {
        this.feelerlength = feelerlength;
    }

    public void setEatadmission(float eatadmission) {
        this.eatadmission = eatadmission;
    }

    public float getEatadmission() {
        return eatadmission;
    }

    public void setMutation_percentage(float mutation_percentage) {
        this.mutation_percentage = mutation_percentage;
    }

    public void setMutation_neurons(float mutation_neurons) {
        this.mutation_neurons = mutation_neurons;
    }
}
