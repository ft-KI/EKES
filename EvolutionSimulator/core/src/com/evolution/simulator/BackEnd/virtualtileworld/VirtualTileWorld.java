package com.evolution.simulator.BackEnd.virtualtileworld;

import java.util.ArrayList;

public class VirtualTileWorld {
    private ArrayList<ArrayList<Tile>>tiles=new ArrayList<ArrayList<Tile>>();
    private int height=0;
    private int width=0;
    private int TileSize=30;
    public VirtualTileWorld(int width, int height, int TileSize){
        this.height=height;
        this.width=width;
        this.TileSize=TileSize;
        createMap();
    }
    public void update(){
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                
            }
        }
    }
    private void createMap(){
        for(int x=0;x<width;x++){
            tiles.add(new ArrayList<Tile>());
            for(int y=0;y<height;y++){
                tiles.get(x).add(new Tile(LandType.NONE));
            }
        }
    }
    public void generateWorld(int width, int height,ArrayList<ArrayList<Tile>> world){
        this.width=width;
        this.height=height;
        tiles.clear();
        createMap();
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

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }
}
