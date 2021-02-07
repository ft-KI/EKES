package de.ft.ekes.weblauncher.Communication;

import de.ft.ekes.BackEnd.actors.kreatur.Kreatur;
import de.ft.ekes.BackEnd.virtualtileworld.LandType;
import de.ft.ekes.weblauncher.WebMain;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SendingPacker {
    private static ArrayList<KreaturTransmit> kreaturTransmits = new ArrayList<>();

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

    public static String packWorld() {

        JSONArray json = new JSONArray();

        for(int x = 0; x< WebMain.evolutionsSimulator.getWorld().getTiles().size(); x++) {
            JSONArray reihen=new JSONArray();
            for(int y=0;y<WebMain.evolutionsSimulator.getWorld().getTiles().get(x).size();y++) {

                if(WebMain.evolutionsSimulator.getWorld().getTiles().get(x).get(y).getLandType()== LandType.WATER) {
                    reihen.put(-1);
                }else {
                    reihen.put(WebMain.evolutionsSimulator.getWorld().getTiles().get(x).get(y).getFoodvalue());
                }

            }
            json.put(reihen);

        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","world");
        jsonObject.put("world",json);
        jsonObject.put("height",WebMain.evolutionsSimulator.getWorld().getHeight());
        jsonObject.put("width",WebMain.evolutionsSimulator.getWorld().getWidth());
        jsonObject.put("tilesize",WebMain.evolutionsSimulator.getWorld().getTileSize());

        return jsonObject.toString();

    }

    public static String packActors() {
        kreaturTransmits.clear();
        for (int i = 0; i < WebMain.evolutionsSimulator.getActorManager().getActors().size(); i++) {
            kreaturTransmits.add(new KreaturTransmit(WebMain.evolutionsSimulator.getActorManager().getActors().get(i).getXposition(), WebMain.evolutionsSimulator.getActorManager().getActors().get(i).getYposition(), ((Kreatur) WebMain.evolutionsSimulator.getActorManager().getActors().get(i)).feelers, WebMain.evolutionsSimulator.getActorManager().getActors().get(i).generation));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("kreatures",gson.toJson(kreaturTransmits));
        jsonObject.put("type","creatures");

        return jsonObject.toString();

    }
    public static String packInfos(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ActorsSize", WebMain.evolutionsSimulator.actorManager.getActors().size());
        jsonObject.put("Timeinyears", WebMain.evolutionsSimulator.time.year);
        jsonObject.put("averageage", WebMain.evolutionsSimulator.actorManager.averageAge);
        jsonObject.put("foodavailable", WebMain.evolutionsSimulator.getWorld().getFoodavailable());
        jsonObject.put("lastyears_averageage", WebMain.evolutionsSimulator.averageActorAgeforSteps);
        jsonObject.put("lastyears_ActorsSize", WebMain.evolutionsSimulator.averageActorSizeforSteps);
        jsonObject.put("type","info");
        return jsonObject.toString();
    }
}
