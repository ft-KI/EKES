package com.evolution.simulator.BackEnd.actors;

import com.evolution.simulator.BackEnd.EvolutionsSimulator;

public abstract class Actor {
    public float Xposition=0;
    public float Yposition=0;
    public EvolutionsSimulator es;
    public boolean killed=false;
    public boolean israndom ;
    public int generation=1;

    public abstract void doStep();

    public int getXposition() {
        return (int)Xposition;
    }

    public int getYposition() {
        return (int)Yposition;
    }
    public void kill(){
        killed=true;
    }

    public int getGeneration() {
        return generation;
    }
}
