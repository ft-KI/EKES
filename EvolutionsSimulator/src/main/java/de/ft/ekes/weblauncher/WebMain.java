package de.ft.ekes.weblauncher;

import de.ft.ekes.BackEnd.EvolutionsSimulator;
import de.ft.ekes.weblauncher.Communication.SendingPacker;
import de.ft.ekes.weblauncher.Communication.SocketController;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class WebMain {
    public static EvolutionsSimulator evolutionsSimulator;

    public static Thread simulationThread;
    public static Thread sendingInterval;
    public static SocketController socketController;
    public static int sps = 30;
    public static int stopby = -1;
    public static long timer = 0;
    public static float delay = 0;


    public static void main(String[] args) throws UnknownHostException {
        evolutionsSimulator = new EvolutionsSimulator();

        simulationThread = new Thread(() -> {
            while (true) {
                delay = 1000f / sps;
                timer = System.currentTimeMillis();
                if (stopby != -1)
                    if (evolutionsSimulator.time.year >= stopby) {
                        try {
                            if (Math.abs(System.currentTimeMillis() - timer) > delay) {
                                //Can display speed warning
                            } else {
                                TimeUnit.MILLISECONDS.sleep((int) (delay - (System.currentTimeMillis() - timer)));
                            }
                        } catch (InterruptedException e) {

                        }

                        continue;

                    }
                evolutionsSimulator.doStep();
                try {
                    if (Math.abs(System.currentTimeMillis() - timer) > delay) {
                        //Can display speed warning
                    } else {
                        TimeUnit.MILLISECONDS.sleep((int) (delay - (System.currentTimeMillis() - timer)));
                    }
                } catch (InterruptedException ignored) {

                }
            }
        });
        simulationThread.start();
        socketController = new SocketController(8080);
        socketController.start();
        sendingInterval = new Thread(() -> {
            while (true) {
                try {
                    socketController.broadcast(SendingPacker.packWorld());
                    socketController.broadcast(SendingPacker.packActors());
                    socketController.broadcast(SendingPacker.packInfos());
                } catch (Exception e) {
                    //something went wrong while sending -> doesnt matter so simulation goes on
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        sendingInterval.start();


    }
}
