package com.evolution.simulator.FrontEnd.tilemaprenderer.infobox;

import com.evolution.simulator.Main;

import java.util.ArrayList;

public class InfoBox {
    private int Xposition=0;
    private int Yposition=0;
    private int abstand=5;
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
    public void draw(){
        int actualYposition=Yposition;
        for(int i=0;i<infos.size();i++){
            Main.InfoBatch.begin();
            Main.glyphLayout.setText(Main.infoFont, infos.get(i).getDescription());
            Main.infoFont.draw(Main.InfoBatch, Main.glyphLayout,this.Xposition,actualYposition);
            int xruckung=(int)Main.glyphLayout.width;
            Main.glyphLayout.setText(Main.infoFont, infos.get(i).getInfo());
            Main.infoFont.draw(Main.InfoBatch, Main.glyphLayout,this.Xposition+xruckung+10,actualYposition);
            Main.InfoBatch.end();
            actualYposition-=Main.glyphLayout.height+abstand;
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
