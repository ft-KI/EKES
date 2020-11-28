package com.evolution.simulator.weblauncher;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.weblauncher.Communication.Parameter;
import com.evolution.simulator.weblauncher.Communication.SendingPacker;
import com.evolution.simulator.weblauncher.Communication.SocketController;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WebMain {
    public static EvolutionsSimulator evolutionsSimulator;

    public static Thread simulationThread;
    public static Thread sendingInterval;
    public static SocketController socketController;
    public static int sps = 30;
    public static long timer=0;
    public static float delay=0;
    public static ArrayList<Parameter>worldparams=new ArrayList<>();
    public static ArrayList<Parameter>actorparams=new ArrayList<>();
    public static ArrayList<Parameter>infoparams=new ArrayList<>();

    public static void main(String[] args) throws UnknownHostException {
        evolutionsSimulator=new EvolutionsSimulator();

        simulationThread=new Thread(){
            @Override
            public void run() {
                while (true){
                    delay=1000f/sps;
                    timer=System.currentTimeMillis();
                    evolutionsSimulator.dostep();
                    try {
                        if(Math.abs(System.currentTimeMillis()-timer)>delay){
                            System.out.println("Speed Warning: durchlauf hat: "+(System.currentTimeMillis()-timer-delay)+"ms zu lange gedauert");
                        }else {
                            TimeUnit.MILLISECONDS.sleep((int) (delay - (System.currentTimeMillis() - timer)));
                        }
                    } catch (InterruptedException e) {

                    }
                }
            }
        };
        simulationThread.start();
        socketController = new SocketController(8080);
        socketController.start();
        sendingInterval = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        socketController.broadcast(SendingPacker.packWorld());
                        socketController.broadcast(SendingPacker.packActors());
                        socketController.broadcast(SendingPacker.packInfos());
                    }catch (Exception e){
                        System.out.println("problem");
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        };

        sendingInterval.start();






    }
}
