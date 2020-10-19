package com.evolution.simulator.BackEnd.virtualtilemap;

import java.util.ArrayList;

public class VirtualTileMap {
    private ArrayList<ArrayList<Tile>>tiles=new ArrayList<ArrayList<Tile>>();
    private int height=0;
    private int width=0;
    private int TileSize=30;
    public VirtualTileMap(int height, int width, int TileSize){
        this.height=height;
        this.width=width;
        this.TileSize=TileSize;
        createMap();
    }
    private void createMap(){
        for(int x=0;x<width;x++){
            tiles.add(new ArrayList<Tile>());
            for(int y=0;y<height;y++){
                tiles.get(x).add(new Tile());
            }
        }
    }

    public void setTile(int x,int y,Tile tile){
        tiles.get(x).set(y,tile);
    }

    public Tile getTile(int x, int y){
        return tiles.get(x).get(y);
    }
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getTileSize() {
        return TileSize;
    }

    public void setTileSize(int tileSize) {
        TileSize = tileSize;
    }
}
