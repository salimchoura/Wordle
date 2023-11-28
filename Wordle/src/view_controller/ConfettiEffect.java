package view_controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The ConfettiEffect class represents a visual effect of confetti falling down on the screen.
 */
public class ConfettiEffect {

    /**
     * Creates a StackPane containing the confetti effect and the UI elements.
     *
     * @param parent The parent object for event handling.
     * @return The StackPane containing the confetti effect and UI elements.
     */
    public static StackPane createConfettiStackPane(Main parent) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);

        createConfetti(root, scene);
        Text winText = createWinText(scene);
        Button playAgainButton = createPlayAgainButton(root, scene);
        playAgainButton.setOnAction((e) -> {
            parent.createNewGame();
        });

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        winText.setTextAlignment(TextAlignment.CENTER);
        playAgainButton.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.setAlignment(playAgainButton, Pos.BOTTOM_CENTER);

        stackPane.getChildren().addAll(root, winText, playAgainButton);
        stackPane.setMargin(playAgainButton, new Insets(10));

        return stackPane;
    }

    private static void createConfetti(Group root, Scene scene) {
        // Create the confetti circles
        for (int i = 0; i < 500; i++) {
            Circle confetti = new Circle(5, Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            confetti.setEffect(new DropShadow());
            confetti.relocate(Math.random() * scene.getWidth(), -10);
            root.getChildren().add(confetti);

            // Animate the confetti falling down
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(confetti.translateYProperty(), scene.getHeight() + 10);
            KeyFrame kf = new KeyFrame(Duration.seconds(2 + Math.random() * 2), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    private static Text createWinText(Scene scene) {
        Text winText = new Text("WIN");
        winText.setFont(Font.font(72));
        return winText;
    }

    private static Button createPlayAgainButton(Group root, Scene scene) {
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(event -> {
            root.getChildren().clear();
            createConfetti(root, scene);
            Text winText = createWinText(scene);
            Button newPlayAgainButton = createPlayAgainButton(root, scene);
            root.getChildren().addAll(winText, newPlayAgainButton);
        });

        return playAgainButton;
    }

}
