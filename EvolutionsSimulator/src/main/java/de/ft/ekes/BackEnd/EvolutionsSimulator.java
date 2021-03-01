package de.ft.ekes.BackEnd;

import de.ft.ekes.BackEnd.virtualtileworld.VirtualTileWorld;
import de.ft.ekes.BackEnd.virtualtileworld.WorldGenerator;

public class EvolutionsSimulator {
    public ActorManager actorManager;
    public Time time;
    public int calcAverageBySteps = 500;
    public int averageActorSizeForSteps = 0;
    public double calcAverageActorSizeForSteps = 0;
    public float averageActorAgeForSteps = 0;
    public double calcAverageActorAgeForSteps = 0;
    public float averageFoodAvailable = 0;
    public float calcAverageFoodAvailable = 0;
    public int yearsCounter = 0;
    public boolean done = false;
    int worldWidth = 150;
    int worldHeight = 100;
    public VirtualTileWorld world = new VirtualTileWorld(worldWidth, worldHeight, 10);

    public EvolutionsSimulator() {
        WorldGenerator worldGenerator = new WorldGenerator(System.getProperty("user.dir") + "/Land.jpg", worldWidth, worldHeight);
        worldGenerator.generateWorld(world);
        world.Fruchtbarkeitenberechnen();
        world.calculateTileCounts();
        actorManager = new ActorManager(this);
        time = new Time();
    }

    /**
     * One Simulation Step
     */

    public void doStep() {

        //Calc average values for data logging
        updateAverageData();

        //Update Simulation
        world.doStep();
        actorManager.doStep();
        time.Tick();
    }


    private void updateAverageData() {
        if (Math.floor(time.year) % Math.floor(calcAverageBySteps * time.TicksPerYear) == 0) {
            if (!done) {
                averageActorSizeForSteps = (int) (calcAverageActorSizeForSteps / calcAverageBySteps);
                averageActorAgeForSteps = (float) (calcAverageActorAgeForSteps / calcAverageBySteps);
                averageFoodAvailable = calcAverageFoodAvailable / calcAverageBySteps;
                calcAverageFoodAvailable = 0;
                calcAverageActorAgeForSteps = 0;
                calcAverageActorSizeForSteps = 0;


                done = true;
                System.out.print(averageActorSizeForSteps + " " + String.valueOf(averageActorAgeForSteps).replace(".", ",") + " " + String.valueOf(averageFoodAvailable).replace(".", ","));
                for (int i = 0; i < actorManager.averageHiddenNeurons.size(); i++) {
                    System.out.print(" ");
                    actorManager.averageHiddenNeurons.get(i).average2 = actorManager.averageHiddenNeurons.get(i).tempCounter / calcAverageBySteps;
                    actorManager.averageHiddenNeurons.get(i).tempCounter = 0;
                    System.out.print(String.valueOf(actorManager.averageHiddenNeurons.get(i).average2).replace(".", ","));
                }
                System.out.println();

                yearsCounter++;

            }

        } else {
            done = false;
            calcAverageActorSizeForSteps += actorManager.getActors().size();
            calcAverageActorAgeForSteps += actorManager.averageAge;
            calcAverageFoodAvailable += world.getFoodavailable();

            for (int i = 0; i < actorManager.averageHiddenNeurons.size(); i++) {
                actorManager.averageHiddenNeurons.get(i).tempCounter += actorManager.averageHiddenNeurons.get(i).average;
            }


        }
    }


    public VirtualTileWorld getWorld() {
        return world;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }
}
