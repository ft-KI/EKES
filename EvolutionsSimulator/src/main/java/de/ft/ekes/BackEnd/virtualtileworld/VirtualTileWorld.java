package de.ft.ekes.BackEnd.virtualtileworld;

import java.util.ArrayList;

public class VirtualTileWorld {
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    private int height = 0;
    private int width = 0;
    private int TileSize = 30;
    private final float globalGrowSpeed = 0.005f;
    private final float maxfoodvalue = 1f;
    private int LandTilesCount = 0;
    private int WaterTilesCount = 0;
    private float foodavailable = 0;

    public VirtualTileWorld(int width, int height, int TileSize) {
        this.height = height;
        this.width = width;
        this.TileSize = TileSize;
        createMap();
    }

    public void calculateTileCounts() {
        for (int i = 0; i < tiles.size(); i++) {
            for (int b = 0; b < tiles.get(i).size(); b++) {
                if (tiles.get(i).get(b).getLandType() == LandType.LAND) {
                    LandTilesCount++;
                } else if (tiles.get(i).get(b).getLandType() == LandType.WATER) {
                    WaterTilesCount++;
                }
            }
        }
    }

    public Tile getTilefromActorPosition(int x, int y) {
        Tile t;
        if (x >= 0 && y >= 0 && x < width * TileSize && y < height * TileSize) {
            t = tiles.get(x / TileSize).get(y / TileSize);
        } else {
            t = new Tile(LandType.NONE);
        }
        return t;
    }

    public void doStep() {
        float calcfoodavailable = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = tiles.get(x).get(y);
                Grow(tile);
                if (tile.getLandType() == LandType.LAND) {
                    calcfoodavailable += tile.getFoodValue();
                }
            }
        }
        foodavailable = calcfoodavailable / LandTilesCount;
    }

    private void Grow(Tile tile) {
        tile.setFoodValue(tile.getFoodValue() + tile.getFertility() * globalGrowSpeed);
        if (tile.getFoodValue() >= maxfoodvalue) {
            tile.setFoodValue(maxfoodvalue);
        }
    }

    public void Fruchtbarkeitenberechnen() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles.get(x).get(y).getLandType() == LandType.WATER) {
                    tiles.get(x).get(y).setFertility(1f);
                }
            }
        }
        for (int i = 0; i < 20; i++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {


                    float fruchtbarkeitdurchschnit = 0;
                    for (int mx = -1; mx <= 1; mx = mx + 1) {
                        for (int my = -1; my <= 1; my = my + 1) {
                            if (x + mx >= 0 && y + my >= 0 && x + mx < width && y + my < height) {
                                fruchtbarkeitdurchschnit += tiles.get(x + mx).get(y + my).getFertility();
                            }
                        }
                    }
                    fruchtbarkeitdurchschnit /= 8;
                    // System.out.println(fruchtbarkeitdurchschnit);
                    if (tiles.get(x).get(y).getLandType() == LandType.LAND) {
                        tiles.get(x).get(y).setFertility(fruchtbarkeitdurchschnit * 0.90f);
                    }

                }
            }
        }
    }

    private void createMap() {
        for (int x = 0; x < width; x++) {
            tiles.add(new ArrayList<Tile>());
            for (int y = 0; y < height; y++) {
                tiles.get(x).add(new Tile(LandType.NONE));
            }
        }
    }

    public void generateWorld(int width, int height, ArrayList<ArrayList<Tile>> world) {
        this.width = width;
        this.height = height;
        tiles.clear();
        createMap();
        this.tiles = world;
    }

    public void setTile(int x, int y, Tile tile) {
        tiles.get(x).set(y, tile);
    }

    public Tile getTile(int x, int y) {
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

    public int getLandTilesCount() {
        return LandTilesCount;
    }

    public int getWaterTilesCount() {
        return WaterTilesCount;
    }

    public float getFoodavailable() {
        return foodavailable;
    }
}
