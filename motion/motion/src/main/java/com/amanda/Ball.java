package com.amanda;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Ball {
    
    private double x, y; // Position

    private double radius;

    private double dx, dy; // Velocity

    private double maxSpeed = 6.0;

    private Paint color = Color.BLUE; 

    private static final Paint[] COLORS = {Color.BLUE, Color.ORANGE, Color.GREEN, Color.BLACK, Color.PURPLE};

    private int currentColorIndex = 0;

    public Ball(double x, double y, double radius, double dx, double dy, double maxSpeed, Paint color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    public Ball(double x, double y, double radius, double dx, double dy, double maxSpeed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        if (dx > maxSpeed) {
            this.dx = maxSpeed;
        } else {
            this.dx = dx;
        }
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {

        if (dy > maxSpeed) {
            this.dy = maxSpeed;
        } else {
            this.dy = dy;
        }
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public void changeColor() {
        currentColorIndex = (currentColorIndex + 1) % COLORS.length;
        this.setColor(COLORS[currentColorIndex]);
    }

    private void drawStar(GraphicsContext gc, double centerX, double centerY, double outerRadius, double innerRadius) {
        gc.setFill(Color.YELLOW);
        double angle = Math.PI / 5;
        double[] xPoints = new double[10];
        double[] yPoints = new double[10];

        for (int i = 0; i < 10; i++) {
            double r = (i % 2 == 0) ? outerRadius : innerRadius;
            xPoints[i] = centerX + r * Math.cos(i * angle);
            yPoints[i] = centerY - r * Math.sin(i * angle);
        }

        gc.fillPolygon(xPoints, yPoints, 10);
    }

    // Abstract method to render the obstacle
    void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        drawStar(gc, x, y, radius/2, radius/2);
    }

}
