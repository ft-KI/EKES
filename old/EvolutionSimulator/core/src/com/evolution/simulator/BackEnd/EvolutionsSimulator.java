package com.evolution.simulator.BackEnd;

import com.evolution.simulator.BackEnd.virtualtileworld.VirtualTileWorld;
import com.evolution.simulator.BackEnd.virtualtileworld.WorldGenerator;

public class EvolutionsSimulator {
    int worldWidth=150;
    int worldHeight=100;
    public ActorManager actorManager;
    public VirtualTileWorld world=new VirtualTileWorld(worldWidth,worldHeight,10);
    public Time time;
    public EvolutionsSimulator(){
        WorldGenerator worldGenerator=new WorldGenerator(System.getProperty("user.dir")+ "/Land.jpg",worldWidth,worldHeight);
        worldGenerator.generateWorld(world);
        world.Fruchtbarkeitenberechnen();
        world.calculateTileCounts();
        actorManager=new ActorManager(this);
        time=new Time();
    }

    /**
     * Macht den nächsten schritt der simulation
     */
    public void dostep(){
        //System.out.println(actorManager.actors.size());
        world.doStep();
        actorManager.doStep();
        time.Tick();
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

    public ActorManager getActorManager() {
        return actorManager;
    }
}
