package com.evolution.simulator.FrontEnd.tilemaprenderer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur2;
import com.evolution.simulator.Main;

public class ActorRenderer {
    public void draw(EvolutionsSimulator es){
        for(Actor actor:es.actorManager.getActors()){
            Main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if(actor.getGeneration()<=10) {
                Main.shapeRenderer.setColor(1f-actor.generation/10f,actor.getGeneration()/10f,0,1);
            }else{
                Main.shapeRenderer.setColor(1,1,1,1);
            }

            Main.shapeRenderer.circle(actor.getXposition(),actor.getYposition(),5);
            if(actor instanceof Kreatur2) {
                Main.shapeRenderer.rectLine(actor.getXposition(),actor.getYposition(),((Kreatur2) actor).getFeelerListPos().x,((Kreatur2) actor).getFeelerListPos().y,2);
            }
            Main.shapeRenderer.end();
        }
    }
}
