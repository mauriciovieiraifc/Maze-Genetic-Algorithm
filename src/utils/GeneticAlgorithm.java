/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import objects.Chromosome;
import objects.Maze;

/**
 *
 * @author mauricio
 */
public class GeneticAlgorithm {

    private final int MAX_GENERATIONS = 1000;
    private final int POPULATION_SIZE = 100;
    private final double MUTATION_CHANCE = 0.05;
    private final int ELITISM_COUNT = (int) (POPULATION_SIZE * 0.05);
    private final int CHROMOSOME_SIZE;
    
    private final int[] solution;
    private boolean isGoal;

    Random random = new Random();
    Maze maze = new Maze();

    /**
     * The solution is an array that represents a sequence
     * of moves to reach the maze exit
     */
    public GeneticAlgorithm() {
        this.solution = new int[] {3, 3, 0, 0, 0, 3, 3, 3, 0, 0, 0, 3, 0, 0, 3, 3, 3, 3,
        3, 3, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 2, 2, 1, 1, 1, 1, 3, 3, 3, 3, 0, 0, 0, 0, 0, 3};
        this.CHROMOSOME_SIZE = this.solution.length;
    }
    
    /**
     * Method responsible for creating the initial population
     * where each chromosome receives random genes
     * @return 
     */
    public ArrayList<Chromosome> createPopulation() {
        ArrayList<Chromosome> firstPopulation = new ArrayList<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            Chromosome chromosome = new Chromosome(CHROMOSOME_SIZE);
            chromosome.setRow(maze.getStart()[0]);
            chromosome.setCol(maze.getStart()[1]);
            chromosome.setFitness(0);
            for (int j = 0; j < CHROMOSOME_SIZE; j++) {
                chromosome.setGene(j, random.nextInt(4));
            }
            firstPopulation.add(chromosome);
        }

        return firstPopulation;
    }
    
    /**
     * Method responsible for setting the fitness value of each chromosome
     * Each chromosome gene on a chromosome is compared to the same index gene
     * in the solution, if they are equal, fitness is increased
     * If the fitness value of the chromosome being checked equals the size
     * of the solution, then the chromosome has reached the end of the maze
     * @param population
     * @return 
     */
    public ArrayList<Chromosome> fitness(ArrayList<Chromosome> population) {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            int fitness = 0;
            for (int j = 0; j < CHROMOSOME_SIZE; j++) {
                if (population.get(i).getGene(j) == this.solution[j]) {
                    fitness++;
                }
            }
            if (fitness == this.solution.length) {
                this.isGoal = true;
            }
            population.get(i).setFitness(fitness);
        }

        return population;
    }
    
    /**
     * Method responsible for ordering population by chromosome fitness value
     * @param population
     * @return 
     */
    public ArrayList<Chromosome> sort(ArrayList<Chromosome> population) {
        Collections.sort(population);

        return population;
    }
   
    /**
     * Method responsible for crossover
     * Receives an ordered population in ascending order
     * ELITISM_COUNT represents the number of individuals able to continue
     * to the next generation (wich have the highest fitness value)
     * the others are crossed generating new children
     * A single random cutoff point was defined
     * 
     * Example:
     * Point = 2;
     * Parent1 [0, 3 | 2, 1, 0, 0]
     * Parent2 [1, 0 | 3, 2, 2, 1]
     * 
     * Child1 [0, 3, 3, 2, 2, 1]
     * Child2 [1, 0, 2, 1, 0, 0]
     * @param population
     * @return 
     */
    public ArrayList<Chromosome> crossover(ArrayList<Chromosome> population) {
        ArrayList<Chromosome> newPopulation = new ArrayList<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (i < POPULATION_SIZE - ELITISM_COUNT) {
                int point = random.nextInt(CHROMOSOME_SIZE);

                Chromosome child1 = new Chromosome(CHROMOSOME_SIZE);
                child1.setRow(maze.getStart()[0]);
                child1.setCol(maze.getStart()[1]);
                child1.setFitness(0);

                Chromosome child2 = new Chromosome(CHROMOSOME_SIZE);
                child2.setRow(maze.getStart()[0]);
                child2.setCol(maze.getStart()[1]);
                child2.setFitness(0);

                for (int j = 0; j < CHROMOSOME_SIZE; j++) {
                    if (j < point) {
                        child1.setGene(j, population.get(i).getGene(j));
                        child2.setGene(j, population.get(i + 1).getGene(j));
                    } else {
                        child1.setGene(j, population.get(i + 1).getGene(j));
                        child2.setGene(j, population.get(i).getGene(j));
                    }
                }

                newPopulation.add(child1);
                newPopulation.add(child2);
            } else {
                newPopulation.add(population.get(i));
                newPopulation.add(population.get(i + 1));
            }

            i++;
        }

        return newPopulation;
    }
    
    /**
     * Method responsible for mutation
     * Each chromosome has a chance of being "raffled" to mutate
     * Only one random gene on the "raffled" chromosome mutates
     * @param population
     * @return 
     */
    public ArrayList<Chromosome> mutate(ArrayList<Chromosome> population) {
        double mutation_value;
        int index, gene;

        for (int i = 0; i < POPULATION_SIZE - ELITISM_COUNT; i++) {
            mutation_value = Math.random();
            if (mutation_value <= MUTATION_CHANCE) {
                index = random.nextInt(CHROMOSOME_SIZE - 1);
                do {
                    gene = random.nextInt(3);
                } while (gene == population.get(i).getGene(index));
                population.get(i).setGene(index, gene);
            }
        }

        return population;
    }

    public void viewSolution() {
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
    }

    public int getMaxGenerations() {
        return this.MAX_GENERATIONS;
    }
    
    public int getPopulationSize() {
        return this.POPULATION_SIZE;
    }
    
    public int getChromosomeSize() {
        return this.CHROMOSOME_SIZE;
    }

    public boolean isGoal() {
        return this.isGoal;
    }

}
