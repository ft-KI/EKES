package com.evolution.simulator.BackEnd;

import com.badlogic.gdx.Gdx;
import com.evolution.simulator.BackEnd.virtualtileworld.VirtualTileWorld;
import com.evolution.simulator.BackEnd.virtualtileworld.WorldGenerator;

public class EvolutionsSimulator {
    int worldWidth=150;
    int worldHeight=100;
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

    public int getWorldHeight() {
        return worldHeight;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public VirtualTileWorld getWorld() {
        return world;
    }
}
