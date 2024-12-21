/*
 * Project Name: Perpetual Accelerated Motion
 * Author: Amanda Uccello
 * Date: December 2024
 * Course: Computer Science
 * Teacher: Ms. Kim
 * Description: Abstract class for obstacles in the game
 */


package com.amanda.Obstacles;

import javafx.scene.canvas.GraphicsContext;

abstract public class AbstractObstacle {

    double x, y; // Position

    // Constructor
    AbstractObstacle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Abstract methods to get the x and y positions
    abstract double getX();

    abstract double getY();

    // Abstract method to check collisions
    abstract boolean collidesWith(double ballX, double ballY, double ballRadius);

    // Abstract method to render the obstacle
    abstract void render(GraphicsContext gc);
    
}
