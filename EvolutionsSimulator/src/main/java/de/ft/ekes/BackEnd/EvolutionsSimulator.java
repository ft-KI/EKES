package de.ft.ekes.BackEnd;

import de.ft.ekes.BackEnd.actors.Actor;
import de.ft.ekes.BackEnd.actors.kreatur.Kreatur2;
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
private boolean onetimeinfect = true;
    public double averageActorLifeTimeForSteps = 0;
    public int killedActors = 0;
    public double calcAverageActorLifeTimeForSteps=0;

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

        if(false){
            if(!done) {
                averageActorSizeforSteps = (int) (calcAverageActorSizeforSteps / calcBySteps);
                averageActorAgeforSteps = (float) (calcAverageActorAgeforSteps / calcBySteps);
                averageActorLifeTimeForSteps = (float) (calcAverageActorLifeTimeForSteps/killedActors);
                calcAverageActorAgeforSteps = 0;
                calcAverageActorSizeforSteps = 0;
                done = true;
                System.out.println(averageActorSizeforSteps + " " + String.valueOf(averageActorAgeforSteps).replace(".",",")+ " " +String.valueOf(averageActorLifeTimeForSteps).replace(".",","));
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
        if(Math.floor(time.year)>150) {

            System.out.println(actorManager.actors.size());

            if(!onetimeinfect) return;
            for(int i=0;i<10;i++) {
                ((Kreatur2) actorManager.actors.get((int) Math.floor(Math.random() * actorManager.getActors().size()))).setInfected(true);
            }
            onetimeinfect = false;
        }
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
