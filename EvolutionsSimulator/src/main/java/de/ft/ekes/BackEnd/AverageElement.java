package de.ft.ekes.BackEnd;

public class AverageElement {
    float EntryCounter = 0;
    float average = 0;
    float tempCounter = 0;
    float average2 = 0;

    public AverageElement() {

    }

    public float getEntryCounter() {
        return EntryCounter;
    }

    public void setEntryCounter(float entryCounter) {
        this.EntryCounter = entryCounter;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public void add(float add) {
        this.EntryCounter += add;
    }
}
