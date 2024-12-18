package com.amanda.Obstacles;

import javafx.scene.canvas.GraphicsContext;

abstract public class AbstractObstacle {

    double x, y; // Position

    AbstractObstacle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    abstract double getX();

    abstract double getY();

    // Abstract method to check collisions
    abstract boolean collidesWith(double ballX, double ballY, double ballRadius);

    // Abstract method to render the obstacle
    abstract void render(GraphicsContext gc);
    
}
