package de.ft.ekes.BackEnd;

import de.ft.ekes.BackEnd.virtualtileworld.VirtualTileWorld;
import de.ft.ekes.BackEnd.virtualtileworld.WorldGenerator;

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
     * Macht den nächsten schritt der simulation
     */
    public void dostep(){

        if(Math.floor(time.year) % Math.floor(calcBySteps*time.TicksperYear) == 0){
            if(!done) {
                averageActorSizeforSteps = (int) (calcAverageActorSizeforSteps / calcBySteps);
                averageActorAgeforSteps = (float) (calcAverageActorAgeforSteps / calcBySteps);
                calcAverageActorAgeforSteps = 0;
                calcAverageActorSizeforSteps = 0;
                done = true;
                System.out.println(Math.round(calcBySteps*time.TicksperYear)+"Year: " + yearscounter + " Size: " + averageActorSizeforSteps + " Age: " + averageActorAgeforSteps);
                yearscounter++;
            }
        }else{
            done = false;
            calcAverageActorSizeforSteps+= actorManager.getActors().size();
            calcAverageActorAgeforSteps+= actorManager.averageAge;

        }
        //System.out.println(actorManager.actors.size());
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
