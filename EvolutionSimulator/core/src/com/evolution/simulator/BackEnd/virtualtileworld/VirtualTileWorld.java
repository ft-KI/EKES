package com.evolution.simulator.BackEnd.virtualtileworld;

import java.util.ArrayList;

public class VirtualTileWorld {
    private ArrayList<ArrayList<Tile>>tiles=new ArrayList<ArrayList<Tile>>();
    private int height=0;
    private int width=0;
    private int TileSize=30;
    private float globalGrowSpeed=0.01f;
    private float maxfoodvalue=1f;
    public VirtualTileWorld(int width, int height, int TileSize){
        this.height=height;
        this.width=width;
        this.TileSize=TileSize;
        createMap();
    }
    public Tile getTilefromActorPosition(int x,int y){
        Tile t;
        if(x>=0&&y>=0 && x<width*TileSize && y<height*TileSize) {
            t = tiles.get(x / TileSize).get(y / TileSize);
        }else{
            t=new Tile(LandType.NONE);
        }
        return t;
    }
    public void doStep(){
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                Grow(tiles.get(x).get(y));
            }
        }
    }
    private void Grow(Tile tile){
        tile.setFoodvalue(tile.getFoodvalue()+tile.getFruchtbarkeit()*globalGrowSpeed);
        if(tile.getFoodvalue()>=maxfoodvalue){
            tile.setFoodvalue(maxfoodvalue);
        }
    }
    public void Fruchtbarkeitenberechnen(){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles.get(x).get(y).getLandType() == LandType.WATER) {
                    tiles.get(x).get(y).setFruchtbarkeit(1f);
                }
            }
        }
        for(int i=0;i<20;i++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {


                    float fruchtbarkeitdurchschnit = 0;
                    for (int mx = -1; mx <= 1; mx = mx + 1) {
                        for (int my = -1; my <= 1; my = my + 1) {
                            if (x + mx >= 0 && y + my >= 0 && x + mx < width && y + my < height) {
                                fruchtbarkeitdurchschnit += tiles.get(x + mx).get(y + my).getFruchtbarkeit();
                            }
                        }
                    }
                    fruchtbarkeitdurchschnit/=8;
                   // System.out.println(fruchtbarkeitdurchschnit);
                    if (tiles.get(x).get(y).getLandType() == LandType.LAND) {
                        tiles.get(x).get(y).setFruchtbarkeit(fruchtbarkeitdurchschnit * 0.90f);
                    }

                }
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
        this.tiles=world;
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
