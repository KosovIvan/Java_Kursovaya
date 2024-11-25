package Kosov.MAI.Kursovaya.dto;

import Kosov.MAI.Kursovaya.util.City;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

public class TaskInputDTO {
    @Min(value = 2, message = "Количество особей в популяции должно быть не менее 2")
    private int population;

    @Min(value = 1, message = "Количество итераций должно быть не менее 1")
    @Max(value = 10000, message = "Количество итераций должно быть не более 10000")
    private int iter;

    @Min(value = 0, message = "Требуется доля лучших особей попадающих в следующую популяцию от 0 до 1")
    @Max(value = 1, message = "Требуется доля лучших особей попадающих в следующую популяцию от 0 до 1")
    private double survived;

    @Min(value = 0, message = "Требуется доля мутирующих особей в популяции от 0 до 1")
    @Max(value = 1, message = "Требуется доля доля мутирующих особей в популяции от 0 до 1")
    private double mutated;

    private List<City> cities;

    public int getIter() {
        return iter;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setIter(int iter) {
        this.iter = iter;
    }

    public double getSurvived() {
        return survived;
    }

    public void setSurvived(double survived) {
        this.survived = survived;
    }

    public double getMutated() {
        return mutated;
    }

    public void setMutated(double mutated) {
        this.mutated = mutated;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
