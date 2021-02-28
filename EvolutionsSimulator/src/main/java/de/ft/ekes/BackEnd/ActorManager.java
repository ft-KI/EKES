package de.ft.ekes.BackEnd;

import de.ft.ekes.BackEnd.actors.Actor;
import de.ft.ekes.BackEnd.actors.kreatur.Kreatur;

import java.util.ArrayList;

public class ActorManager {
    ArrayList<Actor>actors=new ArrayList<>();
    EvolutionsSimulator es;
    public float highestAge=0;
    public float averageAge=0;
    public float ageOnDeathaverage=0;
    public float DeathesperStep=0;
    public static int [][] positions = {{737,491},{990,670},{250,670},{400,290},{1300,230},{1100,700}};
    int posCounter =0;

    ArrayList<AverageElement>averageHiddenNeuroncounterarraylist=new ArrayList<>();


    public ActorManager(EvolutionsSimulator es){
        this.es=es;

    }
    public void createRandomActor(){

            if(posCounter ==positions.length) posCounter =0;
            Kreatur testkreatur = new Kreatur(positions[posCounter][0], positions[posCounter][1], es);
            posCounter++;
            actors.add(testkreatur);

    }
    long averageHiddenNeuroncounter;
    int average;
    public void doStep(){
        if(actors.size()<100){
            createRandomActor();
        }
        float getha=0;
        float calcaverageage=0;
        float calcageOnDeathAverage=0;
        DeathesperStep=0;

        averageHiddenNeuroncounter = 0;
        for(int i=0;i<averageHiddenNeuroncounterarraylist.size();i++) {
            averageHiddenNeuroncounterarraylist.get(i).tempcounter=0;
        }

        for (int i = 0, actorsSize = actors.size(); i < actorsSize; i++) {
            Actor actor = actors.get(i);
            actor.doStep();
            if(actor.killed){
                calcageOnDeathAverage+=actor.age;
                DeathesperStep++;
                actors.remove(actor);
                actorsSize--;
            }
                if(((Kreatur) actor).getAge()>getha){
                    getha=((Kreatur) actor).getAge();
                }

            calcaverageage+=actor.age;


                if(actor instanceof Kreatur) {
                    for(int hs=0;hs<((Kreatur) actor).getBrain().getHiddenNeurons().size();hs++){
                        if((averageHiddenNeuroncounterarraylist.size()-1)<hs){
                            averageHiddenNeuroncounterarraylist.add(new AverageElement());
                        }
                        averageHiddenNeuroncounterarraylist.get(hs).add(((Kreatur) actor).getBrain().getHiddenNeurons().get(hs).size());
                    }



                    int size =0;
                   for(int layer = 0;layer< ((Kreatur) actor).getBrain().getHiddenNeurons().size();layer++) {

                        size+=((Kreatur) actor).getBrain().getHiddenNeurons().get(layer).size();

                   }

                    averageHiddenNeuroncounter+=size;

                }


        }

        for(int i=0;i<averageHiddenNeuroncounterarraylist.size();i++) {
            averageHiddenNeuroncounterarraylist.get(i).setAverage(averageHiddenNeuroncounterarraylist.get(i).tempcounter / actors.size());
            //System.out.println(i+". hidden: "+averageHiddenNeuroncounterarraylist.get(i).average);
        }


        //System.out.println("Hidden-Schichten: "+averageHiddenNeuroncounter/(float)this.actors.size());
        averageAge=calcaverageage/actors.size();
        if(DeathesperStep>0) {
            ageOnDeathaverage = calcageOnDeathAverage / DeathesperStep;
        }
        highestAge=getha;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

}
