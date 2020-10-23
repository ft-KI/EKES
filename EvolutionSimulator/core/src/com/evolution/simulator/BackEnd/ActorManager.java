package com.evolution.simulator.BackEnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur2;
import com.evolution.simulator.BackEnd.virtualtileworld.LandType;

import java.util.ArrayList;

public class ActorManager {
    ArrayList<Actor>actors=new ArrayList<>();
    EvolutionsSimulator es;
    public float highestAge=0;
    public float averageAge=0;
    public float ageOnDeathaverage=0;
    public float DeathesperStep=0;
    public ActorManager(EvolutionsSimulator es){
        this.es=es;

    }
    public void createRandomActor(){
        int x=(int)(Math.random()*es.getWorld().getWidth()*es.getWorld().getTileSize());
        int y= (int)(Math.random()*es.getWorld().getHeight()*es.getWorld().getTileSize());
        if(es.getWorld().getTilefromActorPosition(x,y).getLandType()== LandType.LAND) {
            Kreatur2 testkreatur = new Kreatur2(x, y, es);
            actors.add(testkreatur);
        }
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
                if(((Kreatur2) actor).getAge()>getha){
                    getha=((Kreatur2) actor).getAge();
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
