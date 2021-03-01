package de.ft.ekes.BackEnd;

import de.ft.ekes.BackEnd.actors.Actor;
import de.ft.ekes.BackEnd.actors.kreatur.Creature;

import java.util.ArrayList;

public class ActorManager {
    public static int[][] positions = {{737, 491}, {990, 670}, {250, 670}, {400, 290}, {1300, 230}, {1100, 700}};
    public float highestAge = 0;
    public float averageAge = 0;
    public float ageOnDeathAverage = 0;
    public float DeathesPerStep = 0;
    ArrayList<Actor> actors = new ArrayList<>();
    EvolutionsSimulator es;
    int posCounter = 0;

    ArrayList<AverageElement> averageHiddenNeurons = new ArrayList<>();
    long averageHiddenNeuronCounter;

    public ActorManager(EvolutionsSimulator es) {
        this.es = es;

    }

    public void createRandomActor() {

        if (posCounter == positions.length) posCounter = 0;
        Creature randomActor = new Creature(positions[posCounter][0], positions[posCounter][1], es);
        posCounter++;
        actors.add(randomActor);

    }

    public void doStep() {
        if (actors.size() < 100) {
            createRandomActor();
        }
        float getha = 0;
        float calcaverageage = 0;
        float calcAgeOnDeathAverage = 0;
        DeathesPerStep = 0;

        averageHiddenNeuronCounter = 0;
        for (AverageElement hiddenNeuron : averageHiddenNeurons) {
            hiddenNeuron.EntryCounter = 0;
        }

        for (int i = 0, actorsSize = actors.size(); i < actorsSize; i++) {
            Actor actor = actors.get(i);
            actor.doStep();
            if (actor.killed) {
                calcAgeOnDeathAverage += actor.age;
                DeathesPerStep++;
                actors.remove(actor);
                actorsSize--;
            }
            if (((Creature) actor).getAge() > getha) {
                getha = ((Creature) actor).getAge();
            }

            calcaverageage += actor.age;


            for (int hs = 0; hs < ((Creature) actor).getBrain().getHiddenNeurons().size(); hs++) {
                if ((averageHiddenNeurons.size() - 1) < hs) {
                    averageHiddenNeurons.add(new AverageElement());
                }
                averageHiddenNeurons.get(hs).add(((Creature) actor).getBrain().getHiddenNeurons().get(hs).size());
            }


            int size = 0;
            for (int layer = 0; layer < ((Creature) actor).getBrain().getHiddenNeurons().size(); layer++) {

                size += ((Creature) actor).getBrain().getHiddenNeurons().get(layer).size();

            }

            averageHiddenNeuronCounter += size;


        }

        for (AverageElement averageHiddenNeuron : averageHiddenNeurons) {
            averageHiddenNeuron.setAverage(averageHiddenNeuron.EntryCounter / actors.size());
        }

        averageAge = calcaverageage / actors.size();
        if (DeathesPerStep > 0) {
            ageOnDeathAverage = calcAgeOnDeathAverage / DeathesPerStep;
        }
        highestAge = getha;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

}
