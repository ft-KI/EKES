package de.ft.ekes.BackEnd;

import de.ft.ekes.BackEnd.actors.kreatur.Kreatur;
import de.ft.ekes.BackEnd.virtualtileworld.VirtualTileWorld;
import de.ft.ekes.BackEnd.virtualtileworld.WorldGenerator;

public class EvolutionsSimulator {
    int worldWidth=150;
    int worldHeight=100;
    public ActorManager actorManager;
    public VirtualTileWorld world=new VirtualTileWorld(worldWidth,worldHeight,10);
    public Time time;
    public int calcBySteps=2500;
    public int averageActorSizeforSteps = 0;
    public double calcAverageActorSizeforSteps=0;

    public float averageActorAgeforSteps = 0;
    public double calcAverageActorAgeforSteps=0;
    public float averageFoodAvailable=0;
    public float calcAverageFoodAvailable=0;
    public int yearscounter = 0;
    public boolean done = false;
    public EvolutionsSimulator(){
        WorldGenerator worldGenerator=new WorldGenerator(System.getProperty("user.dir")+ "/Land.jpg",worldWidth,worldHeight);
        worldGenerator.generateWorld(world);
        world.Fruchtbarkeitenberechnen();
        world.calculateTileCounts();
        actorManager=new ActorManager(this);
        time=new Time();
    }

    /**
     * One Simulation Step
     */

    public void doStep(){


        if(Math.floor(time.year) % Math.floor(calcBySteps*time.TicksperYear) == 0){
            if(!done) {
                averageActorSizeforSteps = (int) (calcAverageActorSizeforSteps / calcBySteps);
                averageActorAgeforSteps = (float) (calcAverageActorAgeforSteps / calcBySteps);
                averageFoodAvailable= calcAverageFoodAvailable / calcBySteps;
                calcAverageFoodAvailable=0;
                calcAverageActorAgeforSteps = 0;
                calcAverageActorSizeforSteps = 0;
                done = true;
                System.out.println(averageActorSizeforSteps + " " + String.valueOf(averageActorAgeforSteps).replace(".",",")+ " "+ String.valueOf(averageFoodAvailable).replace(".",","));
                yearscounter++;
            }
        }else{
            done = false;
            calcAverageActorSizeforSteps+= actorManager.getActors().size();
            calcAverageActorAgeforSteps+= actorManager.averageAge;
            calcAverageFoodAvailable+=world.getFoodavailable();

        }
        //System.out.println(actorManager.actors.size());
        world.doStep();
        actorManager.doStep();
        time.Tick();

        float dh=0;
        for(int i=0;i<actorManager.getActors().size();i++){
            if(actorManager.getActors().get(i) instanceof Kreatur) {
                //dh+=((Kreatur) actorManager.getActors().get(i)).getBrain().getHiddenNeurons().size();
                if(((Kreatur) actorManager.getActors().get(i)).getBrain().getHiddenNeurons().size()<3){
                    dh++;
                  //  System.out.println(((Kreatur) actorManager.getActors().get(i)).getBrain().getOutputNeurons().get(3).getInputConnections().get(5).getWeight());

                }
            }
        }
        System.out.println("asdf: "+dh);
    }



    public VirtualTileWorld getWorld() {
        return world;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }
}
