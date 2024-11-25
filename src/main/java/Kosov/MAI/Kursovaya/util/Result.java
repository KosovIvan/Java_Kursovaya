package Kosov.MAI.Kursovaya.util;

import java.util.List;

public class Result {

    private List<City> cities;

    private double length;

    public Result(List<City> cities, double length) {
        this.cities = cities;
        this.length = length;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
