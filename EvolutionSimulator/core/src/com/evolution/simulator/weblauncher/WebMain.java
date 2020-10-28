package com.evolution.simulator.weblauncher;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;


import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class WebMain {
    public static EvolutionsSimulator evolutionsSimulator;

    public static Thread simulationThread;
    public static void main(String[] args) throws UnknownHostException {
        evolutionsSimulator=new EvolutionsSimulator();


        simulationThread=new Thread(){
            @Override
            public void run() {
                while (true){
                    evolutionsSimulator.dostep();
                    try {
                        TimeUnit.MILLISECONDS.sleep(13);
                    } catch (InterruptedException e) {

                    }
                }
            }
        };
        simulationThread.start();


        SocketController s = new SocketController(8080);
        s.start();

    }
}
