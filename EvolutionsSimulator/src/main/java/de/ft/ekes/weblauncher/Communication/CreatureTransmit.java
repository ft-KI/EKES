package de.ft.ekes.weblauncher.Communication;

import de.ft.ekes.BackEnd.actors.kreatur.Feeler;

import java.util.ArrayList;

public class CreatureTransmit {
    private int x;
    private int y;
    private ArrayList<Feeler> feelers = new ArrayList<>();
    private int gen;

    public CreatureTransmit(int x, int y, ArrayList<Feeler> feelers, int gen) {
        this.x = x;
        this.y = y;
        this.feelers = feelers;
        this.gen = gen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getGen() {
        return gen;
    }

    public void setGen(int gen) {
        this.gen = gen;
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
