package de.ft.ekes.BackEnd.actors.kreatur;

import de.ft.ekes.BackEnd.EvolutionsSimulator;
import de.ft.ekes.BackEnd.Variables;
import de.ft.ekes.BackEnd.actors.Actor;
import de.ft.ekes.BackEnd.ai.NeuronalNetwork.NeuronalNetwork;
import de.ft.ekes.BackEnd.ai.NeuronalNetwork.activationFunctions.Sigmoid;
import de.ft.ekes.BackEnd.virtualtileworld.LandType;
import de.ft.ekes.BackEnd.virtualtileworld.Tile;

import java.util.ArrayList;

public class Creature extends Actor {
    /*
        Output:
            -move Forward
            -move Rotate
            -Eat
            -generate Children
     */
    private final NeuronalNetwork brain;
    public ArrayList<Feeler> feelers = new ArrayList<>();
    private float energy = 200;
    private float moveFactor = Variables.moveFactor;
    private float rotateFactor = Variables.rotateFactor;
    private float moveCostMult = Variables.moveCostMult;
    private float rotateCostMult = Variables.rotateCostMult;
    private float eatMult = Variables.eatMult;
    private float permanentCostLand = Variables.permanentCostLand;
    private float permanentCostWater = Variables.permanentCostWater;
    private float eatCostMult = Variables.eatCostMult;
    private float createChildAge = Variables.createChildAge;
    private float createChildEnergy = Variables.createChildEnergy;
    private float eatAdmission = Variables.eatAdmission;
    private float mutation_percentage = Variables.mutation_percentage;
    private float mutation_neurons = Variables.mutation_neurons;
    private float costMult = 1;
    private float rotationAngle;
    private float outMoveForward = 0;
    private float outRotateRight = 0;
    private float outRotateLeft = 0;
    private float outEat = 0;
    private int feelerLength = 15;

    public Creature(int x, int y, EvolutionsSimulator es) {
        super.es = es;
        super.XPosition = x;
        super.YPosition = y;
        generateFeelers();
        this.rotationAngle = (float) (Math.random() * Math.PI * 2);
        brain = new NeuronalNetwork();
        brain.createInputNeurons(3 + feelers.size() * 2);
        brain.addHiddenLayer(20);
        brain.addHiddenLayer(20);

        brain.createOutputNeurons(5);
        brain.connectRandomFullMeshed();
        brain.addBiasForAllNeurons();
        brain.setAllActivationFunction(new Sigmoid());
        this.isRandom = true;

    }

    public Creature(Creature parent) {
        super.es = parent.es;
        super.XPosition = parent.getXPosition() + 10;
        super.YPosition = parent.getYPosition() + 10;
        generateFeelers();
        this.rotationAngle = (float) (Math.random() * Math.PI * 2);
        int manipulateHiddenNeurons = 0;
        if (Math.random() > 0.80f) {
            if (Math.random() >= 0.5f) {
                manipulateHiddenNeurons = 1;
            } else {
                manipulateHiddenNeurons = -1;
            }
        }

        brain = parent.brain.cloneFullMeshed(manipulateHiddenNeurons);
        for (int i = 0; i < mutation_neurons; i++) {
            brain.randomMutate(mutation_percentage);
        }
        //neuron add/remove
        this.isRandom = false;
        super.generation = parent.generation + 1;
        for (Feeler f : feelers) {
            f.calculateFeelerPosition(rotationAngle, super.getXPosition(), super.getYPosition());
        }
    }

    private void generateFeelers() {

        feelers.add(new Feeler((float) (0), feelerLength));
        feelers.add(new Feeler((float) (0), feelerLength * 2));
        feelers.add(new Feeler((float) (0), feelerLength * 3));


    }

    @Override
    public void DoStep() {
        brain.reset();
        float InPositionFoodValue = super.es.world.getTilefromActorPosition(super.getXPosition(), super.getYPosition()).getFoodValue();
        float InPositionLandTyp = super.es.world.getTilefromActorPosition(super.getXPosition(), super.getYPosition()).getLandType().getValue() * 100f;
        float InEnergy = energy;

        brain.getInputNeurons().get(0).setValue(InPositionLandTyp);
        brain.getInputNeurons().get(1).setValue(InPositionFoodValue);
        brain.getInputNeurons().get(2).setValue(InEnergy);
        for (int i = 0; i < feelers.size(); i++) {
            brain.getInputNeurons().get(3 + i).setValue(feelers.get(i).getFeelerTile(es.getWorld(), rotationAngle, super.getXPosition(), super.getYPosition()).getLandType().getValue() * 100f);
        }
        for (int i = 0; i < feelers.size(); i++) {
            brain.getInputNeurons().get(3 + i + feelers.size()).setValue(feelers.get(i).getFeelerTile(es.getWorld(), rotationAngle, super.getXPosition(), super.getYPosition()).getFoodValue() * 100f);
        }
        outMoveForward = brain.getOutputNeurons().get(0).getOutputValue();
        outRotateRight = brain.getOutputNeurons().get(1).getOutputValue();
        outRotateLeft = brain.getOutputNeurons().get(2).getOutputValue();
        outEat = brain.getOutputNeurons().get(3).getOutputValue();
        float outGenerateChildren = brain.getOutputNeurons().get(4).getOutputValue();
        Rotate();
        moveForward();
        eat();
        if (outGenerateChildren > 0.5f) {
            createChild();
        }
        if (super.es.world.getTilefromActorPosition(super.getXPosition(), super.getYPosition()).getLandType() == LandType.LAND) {
            costMult = permanentCostLand;
        } else {
            costMult = permanentCostWater;
        }
        energy -= permanentCostLand * costMult;
        costMult += age * 0.1f;
        if (energy < 100) {
            super.kill();
        }
    }

    public void createChild() {
        if (energy >= createChildEnergy && age >= createChildAge) {
            Creature child = new Creature(this);
            super.es.actorManager.getActors().add(child);
            energy -= createChildEnergy / 2 * costMult;
        }
    }

    public void eat() {
        Tile t = super.es.getWorld().getTilefromActorPosition(super.getXPosition(), super.getYPosition());
        if (super.es.getWorld().getTilefromActorPosition(super.getXPosition(), super.getYPosition()).getLandType() == LandType.LAND) {
            float eaten;
            eaten = outEat * eatAdmission;
            if (super.es.getWorld().getTilefromActorPosition(super.getXPosition(), super.getYPosition()).getFoodValue() < eaten) {
                eaten += super.es.getWorld().getTilefromActorPosition(super.getXPosition(), super.getYPosition()).getFoodValue() - eaten;
            }
            energy += eaten * eatMult;
            t.setFoodValue(t.getFoodValue() - eaten);
        }
        energy -= outEat * eatCostMult * costMult;
    }

    public void Rotate() {
        float rotate = outRotateLeft - outRotateRight;
        if (Math.abs(rotate) > 0.3f) {
            this.rotationAngle += rotate * rotateFactor;
            energy -= Math.abs(rotate) * rotateCostMult * costMult;
        }

    }

    public void moveForward() {
        XPosition += Math.cos(this.rotationAngle) * moveFactor * outMoveForward;
        YPosition += Math.sin(this.rotationAngle) * moveFactor * outMoveForward;
        energy -= (outMoveForward) * moveCostMult * costMult;

    }

    public float getAge() {
        return age;
    }

    public float getFeelerLength() {
        return feelerLength;
    }

    public void setFeelerLength(int feelerLength) {
        this.feelerLength = feelerLength;
    }

    public float getMoveFactor() {
        return moveFactor;
    }

    public void setMoveFactor(float moveFactor) {
        this.moveFactor = moveFactor;
    }

    public float getRotateFactor() {
        return rotateFactor;
    }

    public void setRotateFactor(float rotateFactor) {
        this.rotateFactor = rotateFactor;
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

    public float getPermanentCostLand() {
        return permanentCostLand;
    }

    public void setPermanentCostLand(float permanentCostLand) {
        this.permanentCostLand = permanentCostLand;
    }

    public float getPermanentCostWater() {
        return permanentCostWater;
    }

    public void setPermanentCostWater(float permanentCostWater) {
        this.permanentCostWater = permanentCostWater;
    }

    public float getEatCostMult() {
        return eatCostMult;
    }

    public void setEatCostMult(float eatCostMult) {
        this.eatCostMult = eatCostMult;
    }

    public float getCreateChildAge() {
        return createChildAge;
    }

    public void setCreateChildAge(float createChildAge) {
        this.createChildAge = createChildAge;
    }

    public float getCreateChildEnergy() {
        return createChildEnergy;
    }

    public void setCreateChildEnergy(float createChildEnergy) {
        this.createChildEnergy = createChildEnergy;
    }

    public float getEatAdmission() {
        return eatAdmission;
    }

    public void setEatAdmission(float eatAdmission) {
        this.eatAdmission = eatAdmission;
    }

    public void setMutation_percentage(float mutation_percentage) {
        this.mutation_percentage = mutation_percentage;
    }

    public void setMutation_neurons(float mutation_neurons) {
        this.mutation_neurons = mutation_neurons;
    }

    public NeuronalNetwork getBrain() {
        return brain;
    }
}
