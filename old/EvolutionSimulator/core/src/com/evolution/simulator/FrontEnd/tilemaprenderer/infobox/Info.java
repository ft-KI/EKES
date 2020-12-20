package com.evolution.simulator.FrontEnd.tilemaprenderer.infobox;

public class Info {
    private String description="";
    private String Info="";
    public Info(String description, String Info){
        this.description=description;
        this.Info=Info;
    }

    public String getDescription() {
        return description;
    }

    public String getInfo() {
        return Info;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInfo(String info) {
        Info = info;
    }

}
