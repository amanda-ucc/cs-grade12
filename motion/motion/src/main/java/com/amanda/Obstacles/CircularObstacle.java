/*
 * Project Name: Perpetual Accelerated Motion
 * Author: Amanda Uccello
 * Date: December 2024
 * Course: Computer Science
 * Teacher: Ms. Kim
 * Description: Concrete class for circular obstacles in the game
 */

package com.amanda.Obstacles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CircularObstacle extends AbstractObstacle {
    private double radius; // Radius of the circle

    private double dx, dy; // Velocity

    private Paint color = Color.RED; // Color of the circular obstacle

    // Constructors
    public CircularObstacle(double x, double y, double radius) {
        super(x, y);
        this.radius = radius;
    }

    public CircularObstacle(double x, double y, double radius, double dx, double dy, Paint color) {
        super(x, y);
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    // Getters and Setters
    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean collidesWith(double ballX, double ballY, double ballRadius) {
        // Check if the ball is within the circle
        return Math.sqrt(Math.pow(ballX - x, 2) + Math.pow(ballY - y, 2)) < radius + ballRadius;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
    
}
