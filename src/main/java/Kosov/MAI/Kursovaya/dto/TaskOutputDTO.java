package Kosov.MAI.Kursovaya.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class TaskOutputDTO {

    private int id;

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

    private String conditions;

    private String solution;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getIter() {
        return iter;
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

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}