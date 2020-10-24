package com.evolution.simulator.BackEnd;

public class Time {
    public final double TicksperYear=0.01f;
    public double year=0;
    public Time(){

    }
    public void Tick(){
        year+=TicksperYear;
    }
}
