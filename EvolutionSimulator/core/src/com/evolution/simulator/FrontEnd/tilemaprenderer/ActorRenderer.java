package com.evolution.simulator.FrontEnd.tilemaprenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur2;
import com.evolution.simulator.Main;

public class ActorRenderer {
    public Actor FocusedActor=null;
    public void draw(EvolutionsSimulator es){
        for(Actor actor:es.actorManager.getActors()){
            int checkradius=10;
            if(Gdx.input.isButtonJustPressed(0)){
                if(Unproject.getMouseX()>actor.getXposition()-checkradius&&Unproject.getMouseX()<actor.getXposition()+checkradius && Unproject.getMouseY()>actor.getYposition()-checkradius && Unproject.getMouseY()<actor.getYposition()+checkradius){
                    FocusedActor=actor;
                    System.out.println("1234");
                }
            }
            if(FocusedActor!=null) {
                if (FocusedActor.killed) {
                    FocusedActor = null;
                }
            }
            Main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if(actor.getGeneration()<=10) {
                Main.shapeRenderer.setColor(1f-actor.generation/10f,actor.getGeneration()/10f,0,1);
            }else{
                Main.shapeRenderer.setColor(1,1,1,1);
            }
            if(FocusedActor==actor){
                Main.shapeRenderer.setColor(0,1,1,1);
            }

            Main.shapeRenderer.circle(actor.getXposition(),actor.getYposition(),5);
            if(actor instanceof Kreatur2) {
                Main.shapeRenderer.rectLine(actor.getXposition(),actor.getYposition(),((Kreatur2) actor).feelerone.getFeelerPosition().x,((Kreatur2) actor).feelerone.getFeelerPosition().y,2);
            }
            Main.shapeRenderer.end();
        }
    }
}
