package com.evolution.simulator.weblauncher;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.sun.rmi.rmid.ExecPermission;


import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class WebMain {
    public static EvolutionsSimulator evolutionsSimulator;

    public static Thread simulationThread;
    public static Thread sendingInterval;
    public static SocketController s;
    public static int sps = 30;
    public static void main(String[] args) throws UnknownHostException {
        evolutionsSimulator=new EvolutionsSimulator();


        simulationThread=new Thread(){
            @Override
            public void run() {
                while (true){
                    evolutionsSimulator.dostep();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000/sps);
                    } catch (InterruptedException e) {

                    }
                }
            }
        };
        simulationThread.start();
        s = new SocketController(8080);
        s.start();
        sendingInterval = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        s.broadcast(SendingPacker.packWorld());
                        s.broadcast(SendingPacker.packActors());
                        s.broadcast(SendingPacker.packInfos());
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
