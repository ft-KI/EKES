package de.ft.ekes.BackEnd.actors;

import de.ft.ekes.BackEnd.EvolutionsSimulator;
import com.google.gson.annotations.Expose;

public abstract class Actor {
    public float Xposition=0;
    public float Yposition=0;

    @Expose
    public EvolutionsSimulator es;

    public float age=0;

    public boolean killed=false;

    public boolean israndom ;

    public int generation=1;

    public abstract void DoStep();

    public final void doStep(){
        DoStep();
        this.age+=es.time.TicksperYear;
    }

    public final int getXposition() {
        return (int)Xposition;
    }

    public final int getYposition() {
        return (int)Yposition;
    }
    public final void kill(){
        killed=true;
    }

    public final int getGeneration() {
        return generation;
    }
}
