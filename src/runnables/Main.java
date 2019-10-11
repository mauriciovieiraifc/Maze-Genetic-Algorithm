/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnables;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import objects.Chromosome;
import utils.GeneticAlgorithm;
import utils.GUI;

/**
 *
 * @author mauricio
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here7

        GUI gui = new GUI();
        JFrame window = new JFrame();

        window.setTitle("Maze");
        window.setSize(700, 650);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gui);
        window.setVisible(true);

        GeneticAlgorithm ga = new GeneticAlgorithm();
        ArrayList<Chromosome> population = ga.createPopulation();

        int count = 0;

        while ((count < ga.getMaxGenerations()) && !ga.isGoal()) {            
            gui.draw(population);
            
            population = ga.fitness(population);
            population = ga.sort(population);
            population = ga.crossover(population);
            population = ga.mutate(population);

            if (ga.isGoal()) {
                System.out.println("\nGOAL - GENERATION " + (count + 1));
                ga.viewSolution();
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            count++;
        }
    }

}
