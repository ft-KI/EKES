package com.evolution.simulator.weblauncher;

import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.actors.kreatur.Feeler;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur2;
import com.evolution.simulator.BackEnd.virtualtileworld.LandType;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.dnd.DropTarget;
import java.util.ArrayList;

@RestController
public class Controller {
    public static Gson gson=new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getAnnotation(Expose.class) != null;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();
    private static ArrayList<KreaturTransmit> kreaturTransmits = new ArrayList<>();
    @CrossOrigin()
    @RequestMapping("/getWorld")
    public static String getWorld() throws JSONException {
        JSONArray json = new JSONArray();
        for(int x=0;x<WebMain.evolutionsSimulator.getWorld().getTiles().size();x++) {
            JSONArray reihen=new JSONArray();
            for(int y=0;y<WebMain.evolutionsSimulator.getWorld().getTiles().get(x).size();y++) {

                if(WebMain.evolutionsSimulator.getWorld().getTiles().get(x).get(y).getLandType()== LandType.WATER) {
                   // json.put(-1);
                    reihen.put(-1);
                }else {
                   // json.put(WebMain.evolutionsSimulator.getWorld().getTiles().get(x).get(y).getFoodvalue());
                    reihen.put(WebMain.evolutionsSimulator.getWorld().getTiles().get(x).get(y).getFoodvalue());
                }

            }
            json.put(reihen);

        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("world",json);
jsonObject.put("height",WebMain.evolutionsSimulator.getWorld().getHeight());
jsonObject.put("width",WebMain.evolutionsSimulator.getWorld().getWidth());
jsonObject.put("tilesize",WebMain.evolutionsSimulator.getWorld().getTileSize());

        return jsonObject.toString() ;

    }
    @CrossOrigin()
    @RequestMapping("/getActors")
    public static String getActors() throws JSONException {
        kreaturTransmits.clear();
        for (int i = 0; i < WebMain.evolutionsSimulator.getActorManager().getActors().size(); i++) {

            kreaturTransmits.add(new KreaturTransmit(WebMain.evolutionsSimulator.getActorManager().getActors().get(i).getXposition(), WebMain.evolutionsSimulator.getActorManager().getActors().get(i).getYposition(), ((Kreatur2) WebMain.evolutionsSimulator.getActorManager().getActors().get(i)).feelers, ((Kreatur2) WebMain.evolutionsSimulator.getActorManager().getActors().get(i)).generation));

        }


        return gson.toJson(kreaturTransmits);


    }
    @CrossOrigin()
    @RequestMapping("/getActorById")
    public static String getActorById(@RequestParam String id){

        return gson.toJson(WebMain.evolutionsSimulator.getActorManager().getActors().get(Integer.parseInt(id)));

    }


    @CrossOrigin
    @RequestMapping("/getParams")
    public static String getParams() {

        JSONObject jsonObject = new JSONObject();


        jsonObject.put();


        return jsonObject.toString();
    }
}
