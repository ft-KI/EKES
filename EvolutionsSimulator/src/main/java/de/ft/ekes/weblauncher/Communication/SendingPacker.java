package de.ft.ekes.weblauncher.Communication;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import de.ft.ekes.BackEnd.AverageElement;
import de.ft.ekes.BackEnd.actors.kreatur.Creature;
import de.ft.ekes.BackEnd.virtualtileworld.LandType;
import de.ft.ekes.weblauncher.WebMain;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SendingPacker {
    private static final ArrayList<CreatureTransmit> creatureTransmits = new ArrayList<>();

    public static Gson gson = new GsonBuilder()
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

        for (int x = 0; x < WebMain.evolutionsSimulator.getWorld().getTiles().size(); x++) {
            JSONArray rows = new JSONArray();
            for (int y = 0; y < WebMain.evolutionsSimulator.getWorld().getTiles().get(x).size(); y++) {

                if (WebMain.evolutionsSimulator.getWorld().getTiles().get(x).get(y).getLandType() == LandType.WATER) {
                    rows.put(-1);
                } else {
                    rows.put(WebMain.evolutionsSimulator.getWorld().getTiles().get(x).get(y).getFoodValue());
                }

            }
            json.put(rows);

        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "world");
        jsonObject.put("world", json);
        jsonObject.put("height", WebMain.evolutionsSimulator.getWorld().getHeight());
        jsonObject.put("width", WebMain.evolutionsSimulator.getWorld().getWidth());
        jsonObject.put("tilesize", WebMain.evolutionsSimulator.getWorld().getTileSize());

        return jsonObject.toString();

    }

    public static String packActors() {
        creatureTransmits.clear();
        for (int i = 0; i < WebMain.evolutionsSimulator.getActorManager().getActors().size(); i++) {
            creatureTransmits.add(new CreatureTransmit(WebMain.evolutionsSimulator.getActorManager().getActors().get(i).getXPosition(), WebMain.evolutionsSimulator.getActorManager().getActors().get(i).getYPosition(), ((Creature) WebMain.evolutionsSimulator.getActorManager().getActors().get(i)).feelers, WebMain.evolutionsSimulator.getActorManager().getActors().get(i).generation));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("kreatures", gson.toJson(creatureTransmits));
        jsonObject.put("type", "creatures");

        return jsonObject.toString();

    }

    public static String packInfos() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ActorsSize", WebMain.evolutionsSimulator.actorManager.getActors().size());
        jsonObject.put("Timeinyears", WebMain.evolutionsSimulator.time.year);
        jsonObject.put("averageage", WebMain.evolutionsSimulator.actorManager.averageAge);

        String values = "";
        ArrayList<AverageElement> averageHiddenNeurons = WebMain.evolutionsSimulator.getActorManager().averageHiddenNeurons;
        for (int i = 0; i < averageHiddenNeurons.size(); i++) {
            AverageElement ae = averageHiddenNeurons.get(i);
            values += Math.round(ae.getAverage() * 100) / 100f;
            if(i!=averageHiddenNeurons.size()-1)
            values += " : ";
        }

        jsonObject.put("averagehidden", values);
        jsonObject.put("foodavailable", WebMain.evolutionsSimulator.getWorld().getFoodavailable());
        jsonObject.put("lastyears_averageage", WebMain.evolutionsSimulator.averageActorAgeForSteps);
        jsonObject.put("lastyears_ActorsSize", WebMain.evolutionsSimulator.averageActorSizeForSteps);
        jsonObject.put("type", "info");
        return jsonObject.toString();
    }
}
