package com.evolution.simulator.weblauncher;


import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.Variables;
import com.evolution.simulator.BackEnd.actors.Actor;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur;
import com.evolution.simulator.BackEnd.actors.kreatur.Kreatur2;
import com.google.gson.ExclusionStrategy;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SocketController extends WebSocketServer {



    ArrayList<WebSocket> connections = new ArrayList<>();
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
        //conn.send("Welcome to the server!"); //This method sends a message to the new client
        connections.add(conn);
        System.out.println(
                conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Someone has left the room!");
        connections.remove(conn);

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        // broadcast(message);
        JSONObject jsonMessage = new JSONObject(message);
        if(jsonMessage.getString("type").contentEquals("param")) {

            WebMain.sps = jsonMessage.getInt("speed");
            for(Actor actor:WebMain.evolutionsSimulator.getActorManager().getActors()) {

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



            }

            Variables.moveFaktor =(jsonMessage.getFloat("movespeed"));
            Variables.moveCostMult = (jsonMessage.getFloat("movecost"));
            Variables.rotateCostMult=(jsonMessage.getFloat("rotcost"));
            Variables.rotatFaktor = (jsonMessage.getFloat("rotspeed"));
            Variables.eatcostMult =(jsonMessage.getFloat("eatcost"));
            Variables.eatMult =(jsonMessage.getFloat("eatspeed"));
            Variables.createChildEnergie =(jsonMessage.getFloat("childenergie"));
            Variables.createChildAge =(jsonMessage.getFloat("childage"));
            Variables.permanetcostland =(jsonMessage.getFloat("costland"));
            Variables.permanetcostwater =(jsonMessage.getFloat("costwater"));
            Variables.eatadmission =(jsonMessage.getFloat("eatadmission"));


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
