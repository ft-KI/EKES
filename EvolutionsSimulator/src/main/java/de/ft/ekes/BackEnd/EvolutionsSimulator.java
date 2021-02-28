package de.ft.ekes.BackEnd;

import de.ft.ekes.BackEnd.virtualtileworld.VirtualTileWorld;
import de.ft.ekes.BackEnd.virtualtileworld.WorldGenerator;

public class EvolutionsSimulator {
    int worldWidth=150;
    int worldHeight=100;
    public ActorManager actorManager;
    public VirtualTileWorld world=new VirtualTileWorld(worldWidth,worldHeight,10);
    public Time time;
    public int calcBySteps=500;
    public int averageActorSizeforSteps = 0;
    public double calcAverageActorSizeforSteps=0;

    public float averageActorAgeforSteps = 0;
    public double calcAverageActorAgeforSteps=0;
    public float averageFoodAvailable=0;
    public float calcAverageFoodAvailable=0;
    public int yearscounter = 0;
    public boolean done = false;
    public int testcounter=0;
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
                System.out.print(averageActorSizeforSteps + " " + String.valueOf(averageActorAgeforSteps).replace(".",",")+ " "+ String.valueOf(averageFoodAvailable).replace(".",","));
                for(int i=0;i<actorManager.averageHiddenNeuroncounterarraylist.size();i++){
                    System.out.print(" ");
                    actorManager.averageHiddenNeuroncounterarraylist.get(i).average2=actorManager.averageHiddenNeuroncounterarraylist.get(i).tempcounter2/testcounter;
                    actorManager.averageHiddenNeuroncounterarraylist.get(i).tempcounter2=0;
                    System.out.print(String.valueOf(actorManager.averageHiddenNeuroncounterarraylist.get(i).average2).replace(".",","));
                }
                System.out.println();

                yearscounter++;
                //System.out.println(testcounter);
                testcounter=0;
            }

        }else{
            testcounter++;
            done = false;
            calcAverageActorSizeforSteps+= actorManager.getActors().size();
            calcAverageActorAgeforSteps+= actorManager.averageAge;
            calcAverageFoodAvailable+=world.getFoodavailable();

            for(int i=0;i<actorManager.averageHiddenNeuroncounterarraylist.size();i++){
                actorManager.averageHiddenNeuroncounterarraylist.get(i).tempcounter2+=actorManager.averageHiddenNeuroncounterarraylist.get(i).average;
            }



        }
        //System.out.println(actorManager.actors.size());
        world.doStep();
        actorManager.doStep();
        time.Tick();
    }



    public VirtualTileWorld getWorld() {
        return world;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }
}
