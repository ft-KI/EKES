package de.ft.ekes.BackEnd.actors.kreatur;

import de.ft.ekes.BackEnd.Vector2;
import de.ft.ekes.BackEnd.virtualtileworld.Tile;
import de.ft.ekes.BackEnd.virtualtileworld.VirtualTileWorld;

public class Feeler {
    private float angle;
    private int feelerLength;
    private final Vector2 feelerPos = new Vector2();

    public Feeler(float angle, int feelerLength) {
        this.angle = angle;
        this.feelerLength = feelerLength;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getFeelerLength() {
        return feelerLength;
    }

    public void setFeelerLength(int feelerLength) {
        this.feelerLength = feelerLength;
    }

    public void calculateFeelerPosition(float rotationAngle, int x, int y) {
        feelerPos.set((int) (x + Math.cos(rotationAngle + this.angle) * feelerLength), (int) (y + Math.sin(rotationAngle + this.angle) * feelerLength));
    }

    public Tile getFeelerTile(VirtualTileWorld vtw, float rotationAngle, int x, int y) {
        calculateFeelerPosition(rotationAngle, x, y);
        return vtw.getTilefromActorPosition(feelerPos.x, feelerPos.y);
    }

    public Vector2 getFeelerPosition() {
        return feelerPos;
    }
}
