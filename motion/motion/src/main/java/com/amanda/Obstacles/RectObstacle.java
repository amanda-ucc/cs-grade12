/*
 * Project Name: Perpetual Accelerated Motion
 * Author: Amanda Uccello
 * Date: December 2024
 * Course: Computer Science
 * Teacher: Ms. Kim
 * Description: Concrete class for rectangular obstacles in the game
 */

package com.amanda.Obstacles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectObstacle extends AbstractObstacle {
    private double width, height; // Width and height of the rectangle

    // Constructors
    public RectObstacle(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    // Getters and Setters
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Collision detection for the rectangle
    @Override
    public boolean collidesWith(double ballX, double ballY, double ballRadius) {
        // Check if the ball is within the rectangle
        return ballX + ballRadius > x && ballX - ballRadius < x + width && ballY + ballRadius > y && ballY - ballRadius < y + height;
    }

    // Render the Rectangles that represent the map obstacles
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.fillRect(x, y, width, height);
    }
    
}
