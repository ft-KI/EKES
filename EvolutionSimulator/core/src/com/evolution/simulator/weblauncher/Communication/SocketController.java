package com.evolution.simulator.weblauncher.Communication;


import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.Variables;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur2;
import com.evolution.simulator.weblauncher.WebMain;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;


public class SocketController extends WebSocketServer {



    //ArrayList<WebSocket> connections = new ArrayList<>();
    public SocketController(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public SocketController(InetSocketAddress address) {
        super(address);

    }

    public SocketController(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        //connections.add(conn);

        JSONObject currentParams = new JSONObject();
        currentParams.put("type","biparam");
        currentParams.put("clients",this.getConnections().size());

        currentParams.put("speed",WebMain.sps);
        currentParams.put("movespeed",Variables.moveFaktor);
        currentParams.put("movecost",Variables.moveCostMult);
        currentParams.put("rotcost",Variables.rotateCostMult);
        currentParams.put("rotspeed",Variables.rotatFaktor);
        currentParams.put("eatcost",Variables.eatcostMult);
        currentParams.put("eatspeed",Variables.eatMult);
        currentParams.put("childenergie",Variables.createChildEnergie);
        currentParams.put("childage",Variables.createChildAge);
        currentParams.put("costland",Variables.permanetcostland);
        currentParams.put("costwater",Variables.permanetcostwater);
        currentParams.put("eatadmission",Variables.eatadmission);
        currentParams.put("mutation_percentage",Variables.mutation_percentage);
        currentParams.put("mutation_neurons",Variables.mutation_neurons);


        conn.send(currentParams.toString());

        System.out.println(
                conn.getRemoteSocketAddress().getAddress().getHostAddress() + " hat verbindung aufgebaut");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Eine verbindung wurde ");
        //connections.remove(conn);

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        // broadcast(message);
        JSONObject jsonMessage = new JSONObject(message);
        if(jsonMessage.getString("type").contentEquals("param")) {


    try {
        WebMain.sps = jsonMessage.getInt("speed");

        Variables.moveFaktor = (jsonMessage.getFloat("movespeed"));
        Variables.moveCostMult = (jsonMessage.getFloat("movecost"));
        Variables.rotateCostMult = (jsonMessage.getFloat("rotcost"));
        Variables.rotatFaktor = (jsonMessage.getFloat("rotspeed"));
        Variables.eatcostMult = (jsonMessage.getFloat("eatcost"));
        Variables.eatMult = (jsonMessage.getFloat("eatspeed"));
        Variables.createChildEnergie = (jsonMessage.getFloat("childenergie"));
        Variables.createChildAge = (jsonMessage.getFloat("childage"));
        Variables.permanetcostland = (jsonMessage.getFloat("costland"));
        Variables.permanetcostwater = (jsonMessage.getFloat("costwater"));
        Variables.eatadmission = (jsonMessage.getFloat("eatadmission"));
        Variables.mutation_percentage = (jsonMessage.getFloat("mutation_percentage"));
        Variables.mutation_neurons = (jsonMessage.getInt("mutation_neurons"));


        jsonMessage.put("type","biparam");
        jsonMessage.put("clients",this.getConnections().size());
        System.out.println("sended biparam");

        for (Actor actor : WebMain.evolutionsSimulator.getActorManager().getActors()) {

            ((Kreatur2) actor).setMoveFaktor(jsonMessage.getFloat("movespeed"));
            ((Kreatur2) actor).setMoveCostMult(jsonMessage.getFloat("movecost"));
            ((Kreatur2) actor).setRotateCostMult(jsonMessage.getFloat("rotcost"));
            ((Kreatur2) actor).setRotatFaktor(jsonMessage.getFloat("rotspeed"));
            ((Kreatur2) actor).setEatcostMult(jsonMessage.getFloat("eatcost"));
            ((Kreatur2) actor).setEatMult(jsonMessage.getFloat("eatspeed"));
            ((Kreatur2) actor).setCreateChildEnergie(jsonMessage.getFloat("childenergie"));
            ((Kreatur2) actor).setCreateChildAge(jsonMessage.getFloat("childage"));
            ((Kreatur2) actor).setPermanetcostland(jsonMessage.getFloat("costland"));
            ((Kreatur2) actor).setPermanetcostwater(jsonMessage.getFloat("costwater"));
            ((Kreatur2) actor).setEatadmission(jsonMessage.getFloat("eatadmission"));
            ((Kreatur2) actor).setMutation_percentage(jsonMessage.getFloat("mutation_percentage"));
            ((Kreatur2) actor).setMutation_neurons(jsonMessage.getFloat("mutation_neurons"));


        }


    }catch (Exception e) {


    }



    for(WebSocket ws:this.getConnections()) {
        if(ws==conn) continue;
        ws.send(jsonMessage.toString());
    }



        }else if(jsonMessage.getString("type").contentEquals("reset")){

            WebMain.evolutionsSimulator = new EvolutionsSimulator();
            Variables.reset();
            WebMain.sps = 30;


        }
        System.out.println(conn + ": " + message);
    }



    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}
