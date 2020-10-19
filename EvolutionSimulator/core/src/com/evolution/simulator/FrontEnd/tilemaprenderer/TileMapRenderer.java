package com.evolution.simulator.FrontEnd.tilemaprenderer;

import com.badlogic.gdx.graphics.Texture;
import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.Main;

public class TileMapRenderer {

    public void draw(EvolutionsSimulator evolutionsSimulator){
        for(int x=0;x<evolutionsSimulator.getWorldWidth();x++){
            for(int y=0;y< evolutionsSimulator.getWorldHeight();y++){
                Main.batch.begin();
                switch (evolutionsSimulator.getWorld().getTiles().get(x).get(y).getLandType()){
                    case NONE:{

                        break;
                    }
                    case LAND:{
                        Main.batch.draw(Main.LandTile, x*evolutionsSimulator.getWorld().getTileSize(),y*evolutionsSimulator.getWorld().getTileSize(),evolutionsSimulator.getWorld().getTileSize(),evolutionsSimulator.getWorld().getTileSize());
                        break;
                    }
                    case WATER:{
                        Main.batch.draw(Main.watertile, x*evolutionsSimulator.getWorld().getTileSize(),y*evolutionsSimulator.getWorld().getTileSize(),evolutionsSimulator.getWorld().getTileSize(),evolutionsSimulator.getWorld().getTileSize());
                        break;
                    }
                }
                Main.batch.end();
            }
        }
    }
}
