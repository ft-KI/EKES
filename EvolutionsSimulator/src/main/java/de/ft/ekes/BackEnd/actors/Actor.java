package de.ft.ekes.BackEnd.actors;

import com.google.gson.annotations.Expose;
import de.ft.ekes.BackEnd.EvolutionsSimulator;

public abstract class Actor {
    public float XPosition = 0;
    public float YPosition = 0;

    @Expose
    public EvolutionsSimulator es;

    public float age = 0;

    public boolean killed = false;

    public boolean isRandom;

    public int generation = 1;

    public abstract void calculateBrain();

    public final void doStep() {
        calculateBrain();
        this.age += es.time.TicksPerYear;
    }

    public final int getXPosition() {
        return (int) XPosition;
    }

    public final int getYPosition() {
        return (int) YPosition;
    }

    public final void kill() {
        killed = true;
    }

    public final int getGeneration() {
        return generation;
    }
}
