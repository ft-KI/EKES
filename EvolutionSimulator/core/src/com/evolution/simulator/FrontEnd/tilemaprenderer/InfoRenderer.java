package com.evolution.simulator.FrontEnd.tilemaprenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.FrontEnd.tilemaprenderer.infobox.Info;
import com.evolution.simulator.FrontEnd.tilemaprenderer.infobox.InfoBox;
import com.evolution.simulator.Main;

public class InfoRenderer {
    private InfoBox simulationsInfo=new InfoBox(0,0);
    private EvolutionsSimulator es;
    Info highestAge=new Info("Highest Age", "");
    Info populationSize=new Info("Population Size","");
    Info averageAge=new Info("average Age", "");
    Info simulationSpeed=new Info("simulations Beschleunigung","");
    public InfoRenderer(){
    }
    public void setEvolutionsSimulator(EvolutionsSimulator es){
        this.es=es;
        simulationsInfo.addInfo(highestAge);
        simulationsInfo.addInfo(populationSize);
        simulationsInfo.addInfo(averageAge);
        simulationsInfo.addInfo(simulationSpeed);
    }
    public void draw(SpriteBatch batch){
        this.highestAge.setInfo(Float.toString(es.getActorManager().highestAge));
        this.populationSize.setInfo(Integer.toString(es.getActorManager().getActors().size()));
        this.averageAge.setInfo(Float.toString(es.getActorManager().averageAge));
        this.simulationSpeed.setInfo(Integer.toString(Main.simulationbeschleunigen));
        simulationsInfo.setPosition(0, Gdx.graphics.getHeight());
        simulationsInfo.draw(batch);

    }
}
