package de.ft.ekes.weblauncher.Communication;


import de.ft.ekes.BackEnd.EvolutionsSimulator;
import de.ft.ekes.BackEnd.Variables;
import de.ft.ekes.BackEnd.actors.Actor;
import de.ft.ekes.BackEnd.actors.kreatur.Creature;
import de.ft.ekes.weblauncher.WebMain;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.ArrayList;


public class SocketController extends WebSocketServer {


    ArrayList<WebSocket> sockets = new ArrayList<>();

    public SocketController(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        sockets.add(conn);
        JSONObject currentParams = new JSONObject();
        currentParams.put("type", "biparam");
        currentParams.put("clients", this.getConnections().size());

        packParams(currentParams);
        currentParams.put("permission", this.sockets.size() - 1);


        conn.send(currentParams.toString());

        System.out.println(
                conn.getRemoteSocketAddress().getAddress().getHostAddress() + " hat verbindung aufgebaut");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Eine verbindung wurde ");
        this.sockets.remove(conn);

        for (WebSocket socket : sockets) {
            JSONObject currentParams = new JSONObject();
            currentParams.put("type", "biparam");
            currentParams.put("clients", this.getConnections().size());
            packParams(currentParams);
            currentParams.put("permission", this.sockets.indexOf(socket));


            socket.send(currentParams.toString());
        }


    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        // broadcast(message);
        JSONObject jsonMessage = new JSONObject(message);
        if (jsonMessage.getString("type").contentEquals("param")) {


            try {
                WebMain.sps = jsonMessage.getInt("speed");
                WebMain.stopby = jsonMessage.getInt("stopby");

                Variables.moveFactor = (jsonMessage.getFloat("movespeed"));
                Variables.moveCostMult = (jsonMessage.getFloat("movecost"));
                Variables.rotateCostMult = (jsonMessage.getFloat("rotcost"));
                Variables.rotateFactor = (jsonMessage.getFloat("rotspeed"));
                Variables.eatCostMult = (jsonMessage.getFloat("eatcost"));
                Variables.eatMult = (jsonMessage.getFloat("eatspeed"));
                Variables.createChildEnergy = (jsonMessage.getFloat("childenergie"));
                Variables.createChildAge = (jsonMessage.getFloat("childage"));
                Variables.permanentCostLand = (jsonMessage.getFloat("costland"));
                Variables.permanentCostWater = (jsonMessage.getFloat("costwater"));
                Variables.eatAdmission = (jsonMessage.getFloat("eatadmission"));
                Variables.mutation_percentage = (jsonMessage.getFloat("mutation_percentage"));
                Variables.mutation_neurons = (jsonMessage.getInt("mutation_neurons"));


                jsonMessage.put("type", "biparam");
                jsonMessage.put("clients", this.getConnections().size());
                System.out.println("sended biparam");

                for (Actor actor : WebMain.evolutionsSimulator.getActorManager().getActors()) {

                    ((Creature) actor).setMoveFactor(jsonMessage.getFloat("movespeed"));
                    ((Creature) actor).setMoveCostMult(jsonMessage.getFloat("movecost"));
                    ((Creature) actor).setRotateCostMult(jsonMessage.getFloat("rotcost"));
                    ((Creature) actor).setRotateFactor(jsonMessage.getFloat("rotspeed"));
                    ((Creature) actor).setEatCostMult(jsonMessage.getFloat("eatcost"));
                    ((Creature) actor).setEatMult(jsonMessage.getFloat("eatspeed"));
                    ((Creature) actor).setCreateChildEnergy(jsonMessage.getFloat("childenergie"));
                    ((Creature) actor).setCreateChildAge(jsonMessage.getFloat("childage"));
                    ((Creature) actor).setPermanentCostLand(jsonMessage.getFloat("costland"));
                    ((Creature) actor).setPermanentCostWater(jsonMessage.getFloat("costwater"));
                    ((Creature) actor).setEatAdmission(jsonMessage.getFloat("eatadmission"));
                    ((Creature) actor).setMutation_percentage(jsonMessage.getFloat("mutation_percentage"));
                    ((Creature) actor).setMutation_neurons(jsonMessage.getFloat("mutation_neurons"));


                }


            } catch (Exception ignored) {


            }

            for (WebSocket ws : this.getConnections()) {
                if (ws == conn) continue;
                jsonMessage.put("permission", sockets.indexOf(ws));
                ws.send(jsonMessage.toString());
            }


        } else if (jsonMessage.getString("type").contentEquals("reset")) {

            WebMain.evolutionsSimulator = new EvolutionsSimulator();
            Variables.resetToDefault();
            WebMain.sps = 30;
            WebMain.stopby = -1;


        }
        System.out.println(conn + ": " + message);
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }


    private void packParams(JSONObject currentParams) {


        currentParams.put("speed", WebMain.sps);
        currentParams.put("stopby", WebMain.stopby);
        currentParams.put("movespeed", Variables.moveFactor);
        currentParams.put("movecost", Variables.moveCostMult);
        currentParams.put("rotcost", Variables.rotateCostMult);
        currentParams.put("rotspeed", Variables.rotateFactor);
        currentParams.put("eatcost", Variables.eatCostMult);
        currentParams.put("eatspeed", Variables.eatMult);
        currentParams.put("childenergie", Variables.createChildEnergy);
        currentParams.put("childage", Variables.createChildAge);
        currentParams.put("costland", Variables.permanentCostLand);
        currentParams.put("costwater", Variables.permanentCostWater);
        currentParams.put("eatadmission", Variables.eatAdmission);
        currentParams.put("mutation_percentage", Variables.mutation_percentage);
        currentParams.put("mutation_neurons", Variables.mutation_neurons);

    }
}
