package com.evolution.simulator.FrontEnd.tilemaprenderer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.Main;

public class ActorRenderer {
    public void draw(EvolutionsSimulator es){
        for(Actor actor:es.actorManager.getActors()){
            Main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Main.shapeRenderer.setColor(1,0,0,1);
            Main.shapeRenderer.circle(actor.getXposition(),actor.getYposition(),10);
            Main.shapeRenderer.end();
        }
    }
}
