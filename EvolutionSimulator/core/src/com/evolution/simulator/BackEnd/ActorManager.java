package com.evolution.simulator.BackEnd;

import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur;

import java.util.ArrayList;

public class ActorManager {
    ArrayList<Actor>actors=new ArrayList<>();
    EvolutionsSimulator es;
    Actor killed=null;
    public ActorManager(EvolutionsSimulator es){
        this.es=es;

    }
    public void createRandomActor(){
        Kreatur testkreatur = new Kreatur((int)(Math.random()*es.getWorld().getWidth()*es.getWorld().getTileSize()), (int)(Math.random()*es.getWorld().getHeight()*es.getWorld().getTileSize()), es);
        actors.add(testkreatur);
    }
    public void doStep(){
        if(actors.size()<60){
            createRandomActor();
        }
        for (int i = 0, actorsSize = actors.size(); i < actorsSize; i++) {
            Actor actor = actors.get(i);
            actor.doStep();
            if(actor.killed){
                actors.remove(actor);
                actorsSize--;
            }
        }
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

}
