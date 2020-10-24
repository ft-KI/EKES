package com.evolution.simulator.weblauncher;

import com.evolution.simulator.BackEnd.actors.kreatur.Feeler;

import java.util.ArrayList;

public class KreaturTransmit {
    private int x;
    private int y;
    private ArrayList<Feeler> feelers = new ArrayList<>();
    private int gen;

    public KreaturTransmit(int x, int y, ArrayList<Feeler> feelers,int gen) {
        this.x = x;
        this.y = y;
        this.feelers = feelers;
        this.gen = gen;
    }

    public int getX() {
        return x;
    }

    public int getGen() {
        return gen;
    }

    public void setGen(int gen) {
        this.gen = gen;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Feeler> getFeelers() {
        return feelers;
    }

    public void setFeelers(ArrayList<Feeler> feelers) {
        this.feelers = feelers;
    }
}
