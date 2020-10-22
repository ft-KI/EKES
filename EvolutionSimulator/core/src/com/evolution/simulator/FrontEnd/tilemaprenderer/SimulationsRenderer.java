package com.evolution.simulator.FrontEnd.tilemaprenderer;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.Main;


public class SimulationsRenderer {
    private TileMapRenderer tileMapRenderer=new TileMapRenderer();
    private EvolutionsSimulator evolutionsSimulator;
    private ActorRenderer actorRenderer=new ActorRenderer();
    private InfoRenderer infoRenderer=new InfoRenderer();
    public SimulationsRenderer(EvolutionsSimulator evolutionsSimulator){
        this.evolutionsSimulator=evolutionsSimulator;
        this.infoRenderer.setEvolutionsSimulator(this.evolutionsSimulator);
    }
    public void draw(){
        tileMapRenderer.draw(evolutionsSimulator);
        actorRenderer.draw(evolutionsSimulator);
        infoRenderer.draw(Main.InfoBatch);
    }
}
