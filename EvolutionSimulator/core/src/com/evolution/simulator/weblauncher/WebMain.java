package com.evolution.simulator.weblauncher;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class WebMain {
    public static EvolutionsSimulator evolutionsSimulator;


    public static Thread simulationThread;
    public static void main(String[] args) {
        SpringApplication.run(WebMain.class, args);
        evolutionsSimulator=new EvolutionsSimulator();

        simulationThread=new Thread(){
            @Override
            public void run() {
                while (true){
                    evolutionsSimulator.dostep();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {

                    }
                }
            }
        };
        simulationThread.start();
    }
}
