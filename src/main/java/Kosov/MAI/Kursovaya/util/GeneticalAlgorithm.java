package Kosov.MAI.Kursovaya.util;

import java.util.*;
import java.util.Random;

public class GeneticalAlgorithm {
    public int population;
    private final int iter;
    private final double s;
    private final double m;
    private final List<City> cities;
    private List<List<City>> entities;
    private List<Double> fitness;
    private List<Double> percents;
    private final Random rnd;

    public GeneticalAlgorithm(int population, int iter, double s, double m, List<City> cities) {
        this.population = population;
        this.iter = iter;
        this.s = s;
        this.m = m;
        this.cities = cities;
        rnd = new Random();
    }

    public Result solve() {
        if (s == 0) return null;
        if (cities.size() < 3) {
            if (cities.size() <= 1) return new Result(cities, 0);
            List<City> result = new ArrayList<>();
            result.add(cities.get(1));
            return new Result(result, distance(cities.get(0), cities.get(1)));
        }
        else {
            init();
            for (int i = 0; i < iter; i++) {
                computeFitness();
                computePercents();
                if ((cities.size() > 3)&&(population > 1)) crossover();
                mutation();
                computeFitness();
                computePercents();
                selection();
            }
            computeFitness();
            double min = fitness.get(0);
            List<City> result = entities.get(0);
            for (int i = 0; i < fitness.size(); i++) {
                if (fitness.get(i) < min) {
                    min = fitness.get(i);
                    result = entities.get(i);
                }
            }
            return new Result(result, min);
        }
    }

    private void init() {
        entities = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            List<City> entity = new ArrayList<>(cities.size() - 1);
            int a = cities.size() - 1;
            int[] arr = new int[cities.size() - 1];
            while (a > 0) {
                int r = rnd.nextInt(cities.size() - 1);
                if (arr[r] == 0) {
                    arr[r] = a;
                    --a;
                }
            }
            for (int j = 0; j < cities.size() - 1; j++) {
                entity.add(cities.get(arr[j]));
            }
            entities.add(entity);
        }
    }

    public static double distance(City a, City b) {
        return Math.sqrt(Math.pow(b.getX()-a.getX(), 2) + Math.pow(b.getY()-a.getY(), 2));
    }

    private void computeFitness() {
        fitness = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            double sum = distance(cities.get(0), entities.get(i).get(0));
            for (int j = 1; j < cities.size() - 1; j++) {
                sum += distance(entities.get(i).get(j-1),entities.get(i).get(j));
            }
            sum += distance(entities.get(i).get(cities.size() - 2), cities.get(0));
            fitness.add(sum);
        }
    }

    private void computePercents() {
        percents = new ArrayList<>();
        double koef = 0;
        for (Double aDouble : fitness) koef += 1 / aDouble;
        percents.add((1/fitness.get(0))/koef);
        for (int i = 1; i < fitness.size(); i++) percents.add(percents.get(i-1)+(1/fitness.get(i))/koef);
    }

    private void mutation() {
        int m_count = (int) Math.ceil(m * population);
        for (int i = 0; i < m_count; i++) {
            int n = rnd.nextInt(population);
            List<City> mutated = entities.get(n);
            n = rnd.nextInt(mutated.size());
            int m = rnd.nextInt(mutated.size());
            while (n == m) {
                m = rnd.nextInt(mutated.size());
            }
            City c = mutated.get(n);
            mutated.set(n, mutated.get(m));
            mutated.set(m, c);
        }
    }

    private void crossover() {
        for (int i = 0; i < population; i++) {
            double r = rnd.nextDouble();
            int male = 0;
            int female = 0;
            for (int j = 0; j < percents.size(); j++) {
                if (r <= percents.get(j)) {
                    male = j;
                    break;
                }
            }
            do {
                r = rnd.nextDouble();
                for (int j = 0; j < percents.size(); j++) {
                    if (r < percents.get(j)) {
                        female = j;
                        break;
                    }
                }
            }
            while (male == female);

            int r2 = rnd.nextInt(cities.size() - 3) + 1;
            List<City> child = new ArrayList<>();
            List<City> mother = new ArrayList<>(entities.get(female));
            for (int j = 0; j < r2; j++) {
                child.add(entities.get(male).get(j));
                mother.remove(entities.get(male).get(j));
            }
            child.addAll(mother);
            entities.add(child);
        }
        population *= 2;
    }

    private void selection() {
        int s_count = (int)Math.ceil(s * population);
        List<List<City>> new_population = new ArrayList<>();
        for (int i = 0; i < s_count; i++) {
            double r = rnd.nextDouble();
            for (int j = 0; j < percents.size(); j++) {
                if (r <= percents.get(j)) {
                    new_population.add(entities.get(j));
                    break;
                }
            }
        }
        population = s_count;
        entities = new_population;
    }
}