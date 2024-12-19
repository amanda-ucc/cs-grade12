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
 */

module com.amanda {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.amanda to javafx.fxml;
    exports com.amanda;
}
