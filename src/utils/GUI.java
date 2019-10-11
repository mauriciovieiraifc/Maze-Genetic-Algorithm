/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;
import objects.Chromosome;
import objects.Maze;

/**
 *
 * @author mauricio
 */
public class GUI extends JComponent {

    Maze maze = new Maze();
    GeneticAlgorithm ga = new GeneticAlgorithm();
    ArrayList<Chromosome> population;

    private final int scale = 30;

    public void draw(ArrayList<Chromosome> population) {
        this.population = population;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /**
         * Paint the maze
         */
        g.translate(50, 10);
        for (int row = 0; row < maze.getMaze().length; row++) {
            for (int col = 0; col < maze.getMaze().length; col++) {
                Color color;
                switch (maze.getMaze()[row][col]) {
                    case 0:
                        color = Color.WHITE;
                        break;
                    case 1:
                        color = Color.BLACK;
                        break;
                    case 2:
                        color = Color.GREEN;
                        break;
                    case 3:
                        color = Color.RED;
                        break;
                    default:
                        color = Color.BLACK;
                        break;
                }
                g.setColor(color);
                g.fillRect(scale * col, scale * row, scale, scale);
                g.setColor(Color.YELLOW);
                g.drawRect(scale * col, scale * row, scale, scale);
            }
        }

        /**
         * Paint the chromosome genes
         * ----- I need to improve this -----
         */
        g.setColor(Color.BLUE);
        for (int j = 0; j < ga.getPopulationSize(); j++) {
            Chromosome chromosome = population.get(j);
            int row = chromosome.getRow();
            int col = chromosome.getCol();

            boolean collided = false;

            for (int i = 0; i < ga.getChromosomeSize(); i++) {

                if (collided) {
                    break;
                }

                switch (chromosome.getGene(i)) {
                    case 0:
                        if (row > 0) {
                            row--;
                            if (maze.isWall(row, col)) {
                                collided = true;
                            } else {
                                g.fillOval(scale * col, scale * row, scale, scale);
                            }
                        } else {
                            collided = true;
                        }
                        break;
                    case 1:
                        if (row < maze.getMaze().length) {
                            row++;
                            if (maze.isWall(row, col)) {
                                collided = true;
                            } else {
                                g.fillOval(scale * col, scale * row, scale, scale);
                            }
                        } else {
                            collided = true;
                        }
                        break;
                    case 2:
                        if (col > 0) {
                            col--;
                            if (maze.isWall(row, col)) {
                                collided = true;
                            } else {
                                g.fillOval(scale * col, scale * row, scale, scale);
                            }
                        } else {
                            collided = true;
                        }
                        break;
                    case 3:
                        if (col < maze.getMaze().length) {
                            col++;
                            if (maze.isWall(row, col)) {
                                collided = true;
                            } else {
                                g.fillOval(scale * col, scale * row, scale, scale);
                            }
                        } else {
                            collided = true;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
