/*
 * Project Name: Perpetual Accelerated Motion
 * Author: Amanda Uccello
 * Date: December 2024
 * Course: Computer Science
 * Teacher: Ms. Kim
 * Description: A game where the player controls a ball with the arrow keys 
 * to collect points by colliding with other balls while avoiding obstacles. 
 * The game is set in a 2D environment with physics-based motion. 
 * The player has 60 seconds to get as many points as possible. 
 * The game features three levels with different obstacles and 
 * ball configurations. The game is built using JavaFX and Java.
 * 
 * App Class is the main class that runs the game. It contains the start method
 */

package com.amanda;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import com.amanda.Obstacles.CircularObstacle;
import com.amanda.Obstacles.RectObstacle;

public class App extends Application {

    private final double SPEED_INCREMENT = 2.0;
    private Ball ball = new Ball(250, 250, 15, 2, 2, 6);
    private List<RectObstacle> obstacles = new ArrayList<>(); // Rect Obstacles
    private CircularObstacle circleObstacleA = new CircularObstacle(500, 20, 15, 3, 3, Color.RED); // Circular Obstacle
    private CircularObstacle circleObstacleB = new CircularObstacle(30, 300, 15, 3, 3, Color.THISTLE);
    private CircularObstacle circleObstacleC = new CircularObstacle(500, 320, 15, 3, 3, Color.BROWN);
    private GraphicsContext gc;
    static private Canvas canvas;

    private long lastUpdate = 0; // Used to slow down the timer
    private int score = 0;
    Label scoreLabel = new Label("Score: " + score);
    private int timeRemaining = 60; // Countdown timer in seconds
    Label timerLabel = new Label("Time: " + timeRemaining);
    private Timeline countdownTimeline;
    Label instructionLabel = new Label("An object in motion stay in motion unless acted upon by the keyboard");

    // Main method
    public static void main(String[] args) {
        launch(args);
    }

    // Start method for the game
    @Override
    public void start(Stage primaryStage) {

        switchToNavScene(primaryStage);
    }

    // Sets up the Navigation Scene
    private void switchToNavScene(Stage stage) {
        VBox navRoot = new VBox();
        
        // Create MenuBar
        MenuBar menuBar = new MenuBar();
 

        // Create Menus
        Menu menu = new Menu("Menu");
        MenuItem helpItem = new MenuItem("Help");
        MenuItem creditItem = new MenuItem("Credits");
        MenuItem exitItem = new MenuItem("Exit");

        creditItem.setOnAction(
            e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Credits");
                alert.setHeaderText(null);
                alert.setContentText("Developed by: Amanda Uccello\n\nSpecial Thanks to: \n\n- Ms Kim\n- Stack Overflow\n- Google\n- Oracle\n- JavaFX Community \n\nThank you for playing!");
                alert.showAndWait();
            }
        );
        
        // Set action for helpItem
        helpItem.setOnAction(
            e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Help");
                alert.setHeaderText(null);
                alert.setContentText("Use Arrow Keys to change the motion of a ball. Avoid obstacles and collect points by colliding with the other balls. You have 60 seconds to get as many points as possible. \n\nYou can push an object into an obstacle or wall and force them to get stuck so you can collide with them easily. Be aware though they can do the same to you! \n\nThe mouse is a cheat code. Click and drag the ball to move it. \n\n Press 'X' or back button to return to the main menu. \n\n Good Luck!");
                alert.showAndWait();
            }
        );

        // Set action for exitItem
        exitItem.setOnAction(
            e -> {
                Platform.exit();
                System.exit(0);
            }
        );

        menu.getItems().add(helpItem);
        menu.getItems().add(creditItem);
        menu.getItems().add(exitItem);
        
        // Add Menus to MenuBar
        menuBar.getMenus().addAll(menu);

        // Pane navRoot = new Pane();
        Scene navScene = new Scene(navRoot, 600, 400, Color.LIGHTGRAY);

        Button map1Button = new Button("Load Map 1");
        map1Button.setLayoutX(250);
        map1Button.setLayoutY(100);
        map1Button.setOnAction(e -> {
            loadMap1();
            switchToGameScene(stage);
        });

        Button map2Button = new Button("Load Map 2");
        map2Button.setLayoutX(250);
        map2Button.setLayoutY(150);
        map2Button.setOnAction(e -> {
            loadMap2();
            switchToGameScene(stage);
        });

        Button map3Button = new Button("Load Map 3");
        map3Button.setLayoutX(250);
        map3Button.setLayoutY(200);
        map3Button.setOnAction(e -> {
            loadMap3();
            switchToGameScene(stage);
        });

        // Create a Pane for buttons and add buttons to it
        Pane buttonPane = new Pane();
        buttonPane.getChildren().addAll(map1Button, map2Button, map3Button);

        // Add MenuBar and buttonPane to the VBox
        navRoot.getChildren().addAll(menuBar, buttonPane);

        stage.setScene(navScene);
        stage.setTitle("Perpetual Accelerated Motion");
        stage.show();
    }

    // Switches to the Game Scene
    private void switchToGameScene(Stage stage) {

        BorderPane root = new BorderPane();
        
        // Top pane with buttons and text
        HBox topPane = new HBox();
        topPane.setSpacing(10);
        topPane.setPadding(new Insets(10));

        // Set border for topPane
        topPane.setBorder(new Border(new BorderStroke(
            Color.BLACK, 
            BorderStrokeStyle.SOLID, 
            CornerRadii.EMPTY, 
            BorderWidths.DEFAULT
        )));

        Button backButton = new Button("< Back");
        backButton.setOnAction(e -> {
            if (countdownTimeline != null) {
                countdownTimeline.stop();
            }
            switchToNavScene(stage);
        });

        // Set colors for scoreLabel and timerLabel
        scoreLabel.setTextFill(Color.GREEN);
        timerLabel.setTextFill(Color.BLUE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        topPane.getChildren().addAll(backButton, instructionLabel, spacer, scoreLabel, timerLabel);

        // Bottom pane for the game
        Pane gamePane = new Pane();
        canvas = new Canvas(600, 400);
        gc = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        // Add topPane and gamePane to the BorderPane
        root.setTop(topPane);
        root.setCenter(gamePane);

        // Set the scene to the stage
        Scene gameScene = new Scene(root, 600, 420, Color.GREY);
        stage.setScene(gameScene);
        stage.setTitle("Perpetual Accelerated Motion");
        stage.show();

        ball.setX(250);
        ball.setY(250);
        ball.setDx(2);
        ball.setDy(2);
        lastUpdate = 0;
        score = 0;
        timeRemaining = 60;

        circleObstacleA.setX(500);
        circleObstacleA.setY(20);
        circleObstacleB.setX(30);
        circleObstacleB.setY(300);
        circleObstacleC.setX(500);
        circleObstacleC.setY(320);

        instructionLabel.setText("An object in motion stay in motion unless acted upon by the keyboard");
        instructionLabel.setTextFill(Color.BLACK);

        timerLabel.setText("Time: " + timeRemaining);
        timerLabel.setTextFill(Color.BLUE);

        // Ensure the scene is focused to receive key events
        gameScene.setOnMouseClicked(event -> root.requestFocus());
        root.requestFocus();

        // Handle key inputs
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                // ballSpeedY = Math.max(-MAX_SPEED, ballSpeedY - SPEED_INCREMENT);
                ball.setDy(Math.max(-ball.getMaxSpeed(), ball.getDy() - SPEED_INCREMENT));
            } else if (event.getCode() == KeyCode.DOWN) {
                // ballSpeedY = Math.min(MAX_SPEED, ballSpeedY + SPEED_INCREMENT);
                ball.setDy(Math.min(ball.getMaxSpeed(), ball.getDy() + SPEED_INCREMENT));
            } else if (event.getCode() == KeyCode.LEFT) {
                // ballSpeedX = Math.max(-MAX_SPEED, ballSpeedX - SPEED_INCREMENT);
                ball.setDx(Math.max(-ball.getMaxSpeed(), ball.getDx() - SPEED_INCREMENT));
            } else if (event.getCode() == KeyCode.RIGHT) {
                // ballSpeedX = Math.min(MAX_SPEED, ballSpeedX + SPEED_INCREMENT);
                ball.setDx(Math.min(ball.getMaxSpeed(), ball.getDx() + SPEED_INCREMENT));
            } else if (event.getCode() == KeyCode.X) {
                if (countdownTimeline != null) {
                    countdownTimeline.stop();
                }
                switchToNavScene(stage);
            }
        });

        // Handle mouse dragging - Cheat Code
        canvas.setOnMousePressed(event -> {
            if (isInsideBall(event.getX(), event.getY())) {
                ball.setDx(0);
                ball.setDy(0);
            }
        });

        canvas.setOnMouseDragged(event -> {
            if (isInsideBall(event.getX(), event.getY())) {
                ball.setX(event.getX());
                ball.setY(event.getY());
                instructionLabel.setText("You're cheating!");
                instructionLabel.setTextFill(Color.RED);
            }
        });

        canvas.setOnMouseReleased(event -> {
            if (isInsideBall(event.getX(), event.getY())) {
                ball.setDx(2); // Set the ball's velocity back to its original value
                ball.setDy(2); // Set the ball's velocity back to its original value
                instructionLabel.setText("An object in motion stay in motion unless acted upon by the keyboard");
                instructionLabel.setTextFill(Color.BLACK);
            }
        });

        // Game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 36_000_000) {
                    updatePosition();
                    render(gc);
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        // Start the countdown timer
        startCountdownTimer();     
    }

    // Helper to determine if the mouse is inside the ball
    private boolean isInsideBall(double mouseX, double mouseY) {
        double dx = mouseX - ball.getX();
        double dy = mouseY - ball.getY();
        return dx * dx + dy * dy <= ball.getRadius() * ball.getRadius();
    }

    // Load map1
    private void loadMap1() {

        ball = new Ball(250, 250, 15, 2, 2, 6);
        
        obstacles.clear();
        obstacles.add(new RectObstacle(100, 50, 100, 150));
        obstacles.add(new RectObstacle(300, 200, 50, 100));

    }

    // Load map2
    private void loadMap2() {
        ball = new Ball(250, 250, 15, 2, 2, 6);

        obstacles.clear();
        obstacles.add(new RectObstacle(100, 100, 100, 100));
        obstacles.add(new RectObstacle(300, 200, 200, 50));
        obstacles.add(new RectObstacle(200, 300, 100, 30));

    }

    // Load map3
    private void loadMap3() {
        ball = new Ball(250, 250, 15, 2, 2, 6);

        obstacles.clear();
        obstacles.add(new RectObstacle(50, 50, 200, 150));
        obstacles.add(new RectObstacle(400, 100, 50, 250));
        obstacles.add(new RectObstacle(300, 300, 150, 30));

    }

    // Update the position of the ball and obstacles and determines collisions
    private void updatePosition() {

        ball.setX(ball.getX() + ball.getDx());
        ball.setY(ball.getY() + ball.getDy());

        circleObstacleA.setX(circleObstacleA.getX() + circleObstacleA.getDx());
        circleObstacleA.setY(circleObstacleA.getY() + circleObstacleA.getDy());

        circleObstacleB.setX(circleObstacleB.getX() + circleObstacleB.getDx());
        circleObstacleB.setY(circleObstacleB.getY() + circleObstacleB.getDy());

        circleObstacleC.setX(circleObstacleC.getX() + circleObstacleC.getDx());
        circleObstacleC.setY(circleObstacleC.getY() + circleObstacleC.getDy());

        // Ball Collides with walls
        if (ball.getX() - ball.getRadius() < 0 || ball.getX() + ball.getRadius() > 600) {
            ball.setDx(-ball.getDx());
        }
        if (ball.getY() - ball.getRadius() < 0 || ball.getY() + ball.getRadius() > 370) {
            ball.setDy(-ball.getDy());
        }

        // Circular obstacle A collides with walls
        if (circleObstacleA.getX() - circleObstacleA.getRadius() < 0 || circleObstacleA.getX() + circleObstacleA.getRadius() > 600) {
            circleObstacleA.setDx(-circleObstacleA.getDx());
        }
        if (circleObstacleA.getY() - circleObstacleA.getRadius() < 0 || circleObstacleA.getY() + circleObstacleA.getRadius() > 370) {
            circleObstacleA.setDy(-circleObstacleA.getDy());
        }

        // Circular obstacle B collides with walls
        if (circleObstacleB.getX() - circleObstacleB.getRadius() < 0 || circleObstacleB.getX() + circleObstacleB.getRadius() > 600) {
            circleObstacleB.setDx(-circleObstacleB.getDx());
        }
        if (circleObstacleB.getY() - circleObstacleB.getRadius() < 0 || circleObstacleB.getY() + circleObstacleB.getRadius() > 370) {
            circleObstacleB.setDy(-circleObstacleB.getDy());
        }

        // Circular obstacle C collides with walls
        if (circleObstacleC.getX() - circleObstacleC.getRadius() < 0 || circleObstacleC.getX() + circleObstacleC.getRadius() > 600) {
            circleObstacleC.setDx(-circleObstacleC.getDx());
        }
        if (circleObstacleC.getY() - circleObstacleC.getRadius() < 0 || circleObstacleC.getY() + circleObstacleC.getRadius() > 370) {
            circleObstacleC.setDy(-circleObstacleC.getDy());
        }

        // Ball Collides with  Rect obstacles
        for (RectObstacle obstacle : obstacles) {
            if (obstacle.collidesWith(ball.getX(), ball.getY(), ball.getRadius())) {
                
                        // Determine the side of the collision
                double ballLeft = ball.getX() - ball.getRadius();
                double ballRight = ball.getX() + ball.getRadius();
                double ballTop = ball.getY() - ball.getRadius();
                double ballBottom = ball.getY() + ball.getRadius();

                double obstacleLeft = obstacle.getX();
                double obstacleRight = obstacle.getX() + obstacle.getWidth();
                double obstacleTop = obstacle.getY();
                double obstacleBottom = obstacle.getY() + obstacle.getHeight();

                boolean collisionFromLeft = ballRight > obstacleLeft && ballLeft < obstacleLeft;
                boolean collisionFromRight = ballLeft < obstacleRight && ballRight > obstacleRight;
                boolean collisionFromTop = ballBottom > obstacleTop && ballTop < obstacleTop;
                boolean collisionFromBottom = ballTop < obstacleBottom && ballBottom > obstacleBottom;

                // Reverse direction based on the side of the collision
                if (collisionFromLeft || collisionFromRight) {
                    ball.setDx(-ball.getDx());
                }
                if (collisionFromTop || collisionFromBottom) {
                    ball.setDy(-ball.getDy());
                }
            }
        }

        // Circular obstacle A collides with Rect obstacles
        for (RectObstacle obstacle : obstacles) {
            if (obstacle.collidesWith(circleObstacleA.getX(), circleObstacleA.getY(), circleObstacleA.getRadius())) {
                // Reverse direction on collision
                if (circleObstacleA.getX() + circleObstacleA.getRadius() > obstacle.getX() && circleObstacleA.getX() - circleObstacleA.getRadius() < obstacle.getX() + obstacle.getWidth()) {
                    circleObstacleA.setDx(-circleObstacleA.getDx());
                }
                if (circleObstacleA.getY() + circleObstacleA.getRadius() > obstacle.getY() && circleObstacleA.getY() - circleObstacleA.getRadius() < obstacle.getY() + obstacle.getHeight()) {
                    circleObstacleA.setDy(-circleObstacleA.getDy());
                }
            }
        }

        // Circular obstacle B collides with Rect obstacles
        for (RectObstacle obstacle : obstacles) {
            if (obstacle.collidesWith(circleObstacleB.getX(), circleObstacleB.getY(), circleObstacleB.getRadius())) {
                // Reverse direction on collision
                if (circleObstacleB.getX() + circleObstacleB.getRadius() > obstacle.getX() && circleObstacleB.getX() - circleObstacleB.getRadius() < obstacle.getX() + obstacle.getWidth()) {
                    circleObstacleB.setDx(-circleObstacleB.getDx());
                }
                if (circleObstacleB.getY() + circleObstacleB.getRadius() > obstacle.getY() && circleObstacleB.getY() - circleObstacleB.getRadius() < obstacle.getY() + obstacle.getHeight()) {
                    circleObstacleB.setDy(-circleObstacleB.getDy());
                }
            }
        }

        // Circular obstacle C collides with Rect obstacles
        for (RectObstacle obstacle : obstacles) {
            if (obstacle.collidesWith(circleObstacleC.getX(), circleObstacleC.getY(), circleObstacleC.getRadius())) {
                // Reverse direction on collision
                if (circleObstacleC.getX() + circleObstacleC.getRadius() > obstacle.getX() && circleObstacleC.getX() - circleObstacleC.getRadius() < obstacle.getX() + obstacle.getWidth()) {
                    circleObstacleC.setDx(-circleObstacleC.getDx());
                }
                if (circleObstacleC.getY() + circleObstacleC.getRadius() > obstacle.getY() && circleObstacleC.getY() - circleObstacleC.getRadius() < obstacle.getY() + obstacle.getHeight()) {
                    circleObstacleC.setDy(-circleObstacleC.getDy());
                }
            }
        }

        // Ball Collides with Circular obstacle A
        if (circleObstacleA.collidesWith(ball.getX(), ball.getY(), ball.getRadius())) {
            // Reverse direction on collision
            double dx = ball.getX() - circleObstacleA.getX();
            double dy = ball.getY() - circleObstacleA.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double overlap = ball.getRadius() + circleObstacleA.getRadius() - distance;
            ball.setX(ball.getX() + overlap * dx / distance);
            ball.setY(ball.getY() + overlap * dy / distance);
            double dot = (ball.getDx() * dx + ball.getDy() * dy) / distance;
            ball.setDx(ball.getDx() - 2 * dot * dx / distance);
            ball.setDy(ball.getDy() - 2 * dot * dy / distance);
            ball.setColor(null);
            score++;
            scoreLabel.setText("Score: " + score);
            ball.changeColor();
        }

        // Ball Collides with Circular obstacle B
        if (circleObstacleB.collidesWith(ball.getX(), ball.getY(), ball.getRadius())) {
            // Reverse direction on collision
            double dx = ball.getX() - circleObstacleB.getX();
            double dy = ball.getY() - circleObstacleB.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double overlap = ball.getRadius() + circleObstacleB.getRadius() - distance;
            ball.setX(ball.getX() + overlap * dx / distance);
            ball.setY(ball.getY() + overlap * dy / distance);
            double dot = (ball.getDx() * dx + ball.getDy() * dy) / distance;
            ball.setDx(ball.getDx() - 2 * dot * dx / distance);
            ball.setDy(ball.getDy() - 2 * dot * dy / distance);
            ball.setColor(null);
            score++;
            scoreLabel.setText("Score: " + score);
            ball.changeColor();
        }

        // Ball Collides with Circular obstacle C
        if (circleObstacleC.collidesWith(ball.getX(), ball.getY(), ball.getRadius())) {
            // Reverse direction on collision
            double dx = ball.getX() - circleObstacleC.getX();
            double dy = ball.getY() - circleObstacleC.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double overlap = ball.getRadius() + circleObstacleC.getRadius() - distance;
            ball.setX(ball.getX() + overlap * dx / distance);
            ball.setY(ball.getY() + overlap * dy / distance);
            double dot = (ball.getDx() * dx + ball.getDy() * dy) / distance;
            ball.setDx(ball.getDx() - 2 * dot * dx / distance);
            ball.setDy(ball.getDy() - 2 * dot * dy / distance);
            ball.setColor(null);
            score++;
            scoreLabel.setText("Score: " + score);
            ball.changeColor();
        }

        // Circular obstacle A Collides with Circular obstacle B
        if (circleObstacleA.collidesWith(circleObstacleB.getX(), circleObstacleB.getY(), circleObstacleB.getRadius())) {
            // Reverse direction on collision
            double dx = circleObstacleA.getX() - circleObstacleB.getX();
            double dy = circleObstacleA.getY() - circleObstacleB.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double overlap = circleObstacleA.getRadius() + circleObstacleB.getRadius();
            circleObstacleA.setX(circleObstacleA.getX() + overlap * dx / distance);
            circleObstacleA.setY(circleObstacleA.getY() + overlap * dy / distance);
            circleObstacleA.setDx(-circleObstacleA.getDx());
            circleObstacleA.setDy(-circleObstacleA.getDy());
        }  
        
        // Circular obstacle A Collides with Circular obstacle C
        if (circleObstacleA.collidesWith(circleObstacleC.getX(), circleObstacleC.getY(), circleObstacleC.getRadius())) {
            // Reverse direction on collision
            double dx = circleObstacleA.getX() - circleObstacleC.getX();
            double dy = circleObstacleA.getY() - circleObstacleC.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double overlap = circleObstacleA.getRadius() + circleObstacleC.getRadius();
            circleObstacleA.setX(circleObstacleA.getX() + overlap * dx / distance);
            circleObstacleA.setY(circleObstacleA.getY() + overlap * dy / distance);
            circleObstacleA.setDx(-circleObstacleA.getDx());
            circleObstacleA.setDy(-circleObstacleA.getDy());
        }

        // Circular obstacle B Collides with Circular obstacle C
        if (circleObstacleB.collidesWith(circleObstacleC.getX(), circleObstacleC.getY(), circleObstacleC.getRadius())) {
            // Reverse direction on collision
            double dx = circleObstacleB.getX() - circleObstacleC.getX();
            double dy = circleObstacleB.getY() - circleObstacleC.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double overlap = circleObstacleB.getRadius() + circleObstacleC.getRadius();
            circleObstacleB.setX(circleObstacleB.getX() + overlap * dx / distance);
            circleObstacleB.setY(circleObstacleB.getY() + overlap * dy / distance);
            circleObstacleB.setDx(-circleObstacleB.getDx());
            circleObstacleB.setDy(-circleObstacleB.getDy());
        }

    }

    // Render the game objects
    private void render(GraphicsContext gc) {
        // Clear canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 600, 400);

        ball.render(gc);
        
        circleObstacleA.render(gc);
        circleObstacleB.render(gc);
        circleObstacleC.render(gc);

        // Draw Rect Obstacles
        for (RectObstacle obstacle : obstacles) {
            obstacle.render(gc);
        }
    }

    // Start the countdown timer
    private void startCountdownTimer() {
        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining);
            if (timeRemaining <= 15) {
                instructionLabel.setText("Time is running out!");
                instructionLabel.setTextFill(Color.RED);
                timerLabel.setTextFill(Color.RED);
            }
            if (timeRemaining <= 0) {
                countdownTimeline.stop();
                // Handle end of game, e.g., show a message or switch scenes
                showEndOfGameMessage();
            }
        }));
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);
        countdownTimeline.play();
    }

    // Show a message when the game ends
    private void showEndOfGameMessage() {
        Platform.runLater(() -> {
            // Show a message or switch scenes when the game ends
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Time's up! Your score is: " + score);
            alert.showAndWait();
            switchToNavScene((Stage) canvas.getScene().getWindow());
        });
    }

}
