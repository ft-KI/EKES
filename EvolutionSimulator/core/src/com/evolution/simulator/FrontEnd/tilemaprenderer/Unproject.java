package com.evolution.simulator.FrontEnd.tilemaprenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.evolution.simulator.Main;

public class Unproject {
    public static int getMouseX(){
        return (int)Main.viewport.unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY())).x;
    }
    public static int getMouseY(){
        return (int)Main.viewport.unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY())).y;
    }
}
