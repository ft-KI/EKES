package com.evolution.simulator.FrontEnd.tilemaprenderer.infobox;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class InfoBox {
    private int Xposition=0;
    private int Yposition=0;
    private int abstand=5;
    private BitmapFont infoFont=new BitmapFont();
    private GlyphLayout glyphLayout=new GlyphLayout();
    private ArrayList<Info>infos=new ArrayList<>();


    /**
     * Wird Links Oben positioniert
     * @param x
     * @param y
     */
    public InfoBox(int x,int y){
        this.Xposition=x;
        this.Yposition=y;
    }
    public void draw(SpriteBatch batch){
        int actualYposition=Yposition;
        for(int i=0;i<infos.size();i++){
            batch.begin();
            glyphLayout.setText(infoFont, infos.get(i).getDescription());
            infoFont.draw(batch, glyphLayout,this.Xposition,actualYposition);
            int xruckung=(int)glyphLayout.width;
            glyphLayout.setText(infoFont, infos.get(i).getInfo());
            infoFont.draw(batch, glyphLayout,this.Xposition+xruckung+10,actualYposition);
            batch.end();
            actualYposition-=glyphLayout.height+abstand;
        }
    }
    public void addInfo(Info info){
        infos.add(info);
    }

    /**
     * Wird Links Oben positioniert
     * @param x
     * @param y
     */
    public void setPosition(int x,int y){
        this.Xposition=x;
        this.Yposition=y;
    }
}
