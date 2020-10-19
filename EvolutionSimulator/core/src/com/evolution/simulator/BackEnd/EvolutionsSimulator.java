package com.evolution.simulator.BackEnd;

import com.badlogic.gdx.Gdx;
import com.evolution.simulator.BackEnd.virtualtileworld.VirtualTileWorld;
import com.evolution.simulator.BackEnd.virtualtileworld.WorldGenerator;

public class EvolutionsSimulator {
    int worldWidth=20;
    int worldHeight=20;
    public VirtualTileWorld world=new VirtualTileWorld(worldWidth,worldHeight,10);
    public EvolutionsSimulator(){
        WorldGenerator worldGenerator=new WorldGenerator("C:\\Users\\Felix\\Documents\\GitHub\\EvolutionSimulator\\EvolutionSimulator\\core\\assets\\Land.jpg",worldWidth,worldHeight);
        worldGenerator.generateWorld(world);
    }

    /**
     * Macht den nächsten schritt der simulation
     */
    public void dostep(){

    }
}
