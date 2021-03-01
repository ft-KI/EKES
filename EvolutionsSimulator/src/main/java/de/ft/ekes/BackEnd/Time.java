package de.ft.ekes.BackEnd;

public class Time {
    public final double TicksPerYear = 0.01f;
    public double year = 0;

    public Time() {

    }

    public void Tick() {
        year += TicksPerYear;
    }
}
