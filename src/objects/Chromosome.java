/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author mauricio
 */
public class Chromosome implements Comparable<Chromosome> {
    
    private int[] genes;
    private int fitness;
    private int row, col;
    
    /**
     * The chromosome is represented by a set of genes
     * Each gene is a value from 0 to 3 representing a possible
     * movement within the maze
     * 0 - Top
     * 1 - Down
     * 2 - Left
     * 3 - Right
     * @param size 
     */
    public Chromosome(int size) {
        this.genes = new int[size];
    }
            
    public int[] getGenes() {
        return this.genes;
    }
    
    public int getGene(int index) {
        return this.genes[index];
    }
    
    public void setGene(int index, int gene) {
        this.genes[index] = gene;
    }
    
    public int getFitness() {
        return this.fitness;
    }
    
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
    
    public int getRow() {
        return this.row;
    }
    
    public void setRow(int row) {
        this.row = row;
    } 
    
    public int getCol() {
        return this.col;
    }
    
    public void setCol(int col) {
        this.col = col;
    }
    
    /**
     * Method responsible for compare the fitness value between
     * two different chromosomes
     * @param otherChromosome
     * @return 
     */
    @Override
    public int compareTo(Chromosome otherChromosome) {
        if (this.fitness < otherChromosome.getFitness()) {
            return -1;
        }
        
        if (this.fitness > otherChromosome.getFitness()) {
            return 1;
        }
        
        return 0;
    }
    
}
