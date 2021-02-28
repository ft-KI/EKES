package de.ft.ekes.BackEnd;

public class AverageElement {
    float tempcounter=0;
    float average=0;
    float tempcounter2=0;
    float average2=0;
    public AverageElement(){

    }

    public float getTempcounter() {
        return tempcounter;
    }

    public void setTempcounter(float tempcounter) {
        this.tempcounter = tempcounter;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getAverage() {
        return average;
    }
    public void add(float add){
        this.tempcounter+=add;
    }
}
