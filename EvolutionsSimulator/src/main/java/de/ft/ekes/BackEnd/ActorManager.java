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

    public ActorManager(EvolutionsSimulator es){
        this.es=es;

    }
    public void createRandomActor(){

            if(posCounter ==positions.length) posCounter =0;
            Kreatur testkreatur = new Kreatur(positions[posCounter][0], positions[posCounter][1], es);
            posCounter++;
            actors.add(testkreatur);

    }
    public void doStep(){
        if(actors.size()<100){
            createRandomActor();
        }
        float getha=0;
        float calcaverageage=0;
        float calcageOnDeathAverage=0;
        DeathesperStep=0;
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
        }
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
