package com.evolution.simulator.BackEnd;

import com.evolution.simulator.BackEnd.virtualtileworld.VirtualTileWorld;
import com.evolution.simulator.BackEnd.virtualtileworld.WorldGenerator;

public class EvolutionsSimulator {
    int worldWidth=150;
    int worldHeight=100;
    public ActorManager actorManager;
    public VirtualTileWorld world=new VirtualTileWorld(worldWidth,worldHeight,10);
    public Time time;

    public int calcBySteps=5000;
    public int averageActorSizeforSteps = 0;
    public double calcAverageActorSizeforSteps=0;

    public float averageActorAgeforSteps = 0;
    public double calcAverageActorAgeforSteps=0;
    public EvolutionsSimulator(){
        WorldGenerator worldGenerator=new WorldGenerator(System.getProperty("user.dir")+ "/Land.jpg",worldWidth,worldHeight);
        worldGenerator.generateWorld(world);
        world.Fruchtbarkeitenberechnen();
        world.calculateTileCounts();
        actorManager=new ActorManager(this);
        time=new Time();
    }

    /**
     * Macht den nächsten schritt der simulation
     */
    public void dostep(){
        //System.out.println(actorManager.actors.size());
        if(time.year % calcBySteps*time.TicksperYear == 0){
            averageActorSizeforSteps=(int)(calcAverageActorSizeforSteps/calcBySteps);
            averageActorAgeforSteps= (float) (calcAverageActorAgeforSteps/calcBySteps);
        }else{
            calcAverageActorSizeforSteps+= actorManager.getActors().size();
            calcAverageActorAgeforSteps+= actorManager.averageAge;
        }
        world.doStep();
        actorManager.doStep();
        time.Tick();
    }


    public int getWorldHeight() {
        return worldHeight;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public VirtualTileWorld getWorld() {
        return world;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }
}