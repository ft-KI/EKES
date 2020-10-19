package com.evolution.simulator.FrontEnd.tilemaprenderer;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;


public class SimulationsRenderer {
    private TileMapRenderer tileMapRenderer=new TileMapRenderer();
    private EvolutionsSimulator evolutionsSimulator;
    public SimulationsRenderer(EvolutionsSimulator evolutionsSimulator){
        this.evolutionsSimulator=evolutionsSimulator;
    }
    public void draw(){
        tileMapRenderer.draw(evolutionsSimulator);
    }
}
