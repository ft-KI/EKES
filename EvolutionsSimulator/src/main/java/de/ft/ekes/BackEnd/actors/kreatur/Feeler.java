package de.ft.ekes.BackEnd.actors.kreatur;

import de.ft.ekes.BackEnd.Vector2;
import de.ft.ekes.BackEnd.virtualtileworld.Tile;
import de.ft.ekes.BackEnd.virtualtileworld.VirtualTileWorld;

public class Feeler {
    private float angle=0;
    private int feelerlength=15;
    private Vector2 feelerpos=new Vector2();
    public Feeler(float angle, int feelerlength){
        this.angle=angle;
        this.feelerlength=feelerlength;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setFeelerlength(int feelerlength) {
        this.feelerlength = feelerlength;
    }

    public float getAngle() {
        return angle;
    }

    public int getFeelerlength() {
        return feelerlength;
    }
    public void calculateFeelerPosition(float rotationangle, int x, int y){
        feelerpos.set((int)(x+Math.cos(rotationangle+this.angle)*feelerlength),(int)(y+Math.sin(rotationangle+this.angle)*feelerlength));
    }
    public Tile getFeelerTile(VirtualTileWorld vtw, float rotationangle, int x, int y){
        calculateFeelerPosition(rotationangle,x,y);
        return vtw.getTilefromActorPosition(feelerpos.x,feelerpos.y);
    }
    public Vector2 getFeelerPosition(){
        return feelerpos;
    }
}
