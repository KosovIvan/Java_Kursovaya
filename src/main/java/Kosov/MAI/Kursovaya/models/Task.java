package Kosov.MAI.Kursovaya.models;

import Kosov.MAI.Kursovaya.util.City;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "population")
    @Min(value = 2, message = "Количество особей в популяции должно быть не менее 2")
    private int population;

    @Column(name = "iter")
    @Min(value = 1, message = "Количество итераций должно быть не менее 1")
    @Max(value = 10000, message = "Количество итераций должно быть не более 10000")
    private int iter;

    @Column(name = "survived")
    @Min(value = 0, message = "Требуется доля лучших особей попадающих в следующую популяцию от 0 до 1")
    @Max(value = 1, message = "Требуется доля лучших особей попадающих в следующую популяцию от 0 до 1")
    private double survived;

    @Column(name = "mutated")
    @Min(value = 0, message = "Требуется доля мутирующих особей в популяции от 0 до 1")
    @Max(value = 1, message = "Требуется доля доля мутирующих особей в популяции от 0 до 1")
    private double mutated;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "solution")
    private String solution;

    @Transient
    private List<City> cities;

    public Task() { }

    public Task(int population, int iter, double survived, double mutated, String conditions, String solution) {
        this.population = population;
        this.iter = iter;
        this.survived = survived;
        this.mutated = mutated;
        this.conditions = conditions;
        this.solution = solution;
    }

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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
