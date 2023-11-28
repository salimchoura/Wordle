package view_controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import model.AuthenticationService;
import model.UserAccount;
import model.Wordle;
import view_controller.KeyboardView;

public class Main extends Application {

	LoginView loginView;
	AttemptView attemptView;
	KeyboardView keyboardView;
	AuthenticationService authService;
	private MenuBar menuBar;
	private Stage stage;
	private Wordle game;
	private Scene scene;
	private BorderPane pane;

	@Override
	public void start(Stage stage) throws Exception {

		authService = new AuthenticationService();
		loginView = new LoginView(this);
		loginView.setLoginListener(authService);
		this.stage = stage;
		scene = new Scene(loginView, 400, 300);
		stage.setTitle("Wordle Login");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This is a starting point of the application.
	 * @param args Command line inputs.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public void showWarning(String warning) {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Warning");
		ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.setContentText(warning);
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.showAndWait();
	}

	protected void createGameView() {
		makeMenu();

		pane=new BorderPane();
		VBox innerPane=new VBox(10);
		
		
		game = authService.getCurrentUser().getUserData();
		//game = new Wordle("vests");
		attemptView = new AttemptView(game);
		keyboardView = new KeyboardView(game);
		
		innerPane.getChildren().addAll(attemptView, keyboardView);
		
		innerPane.setMargin(attemptView, new Insets(10, 5, 0, 5));
		innerPane.setMargin(keyboardView, new Insets(0, 5, 0, 5));
		pane.setTop(menuBar);
		pane.setCenter(innerPane);
		scene = new Scene(pane, 600, 500);
		stage.setTitle("Wordle");
		stage.setScene(scene);
		game.addObserver(attemptView);
		game.addObserver(keyboardView);
		scene.setOnKeyPressed((e) -> {
			handleKeyPress(e);
		});
		stage.setOnCloseRequest((event) -> {
			logOut();
		});
	}
	
	protected void createNewGame()
	{
		pane=new BorderPane();

		VBox innerPane=new VBox(10);
		
		authService.getCurrentUser().newGame();
		game=authService.getCurrentUser().getUserData();
		// game = new Wordle("frame");
		attemptView = new AttemptView(game);
		keyboardView = new KeyboardView(game);
		innerPane.getChildren().addAll(attemptView, keyboardView);
		
		innerPane.setMargin(attemptView, new Insets(10, 5, 0, 5));
		innerPane.setMargin(keyboardView, new Insets(0, 5, 0, 5));
		pane.setTop(menuBar);
		pane.setCenter(innerPane);
		scene = new Scene(pane, 600, 500);
		stage.setTitle("Wordle");
		stage.setScene(scene);
		game.addObserver(attemptView);
		game.addObserver(keyboardView);
		scene.setOnKeyPressed((e) -> {
			handleKeyPress(e);
		});
		stage.setOnCloseRequest((event) -> {
			logOut();
		});
		stage.show();
	}
	
	protected void handleKeyPress(KeyEvent event) {
		attemptView.requestFocus();
		KeyCode keyCode = event.getCode();
		// System.out.println(keyCode.toString());
		// System.out.println("hello 1");

		if (keyCode.isLetterKey()) {
			// System.out.println("hello2");
			game.addAttempt(event.getText());
		}

		else if (keyCode == KeyCode.BACK_SPACE) {
			// System.out.println("hello3");
			game.addAttempt("backspace");
		}

		else if (keyCode == KeyCode.ENTER) {
			// System.out.println("hello");
			game.addAttempt("enter");

			if (game.hasWon()) {
				setWinView();
			}

			if (game.hasLost()) {
				setLostView(game);
			}

		}

		else
			return;
	}

	protected void setWinView() {
		String path = "victory.mp3";
		File file = new File(path);
		URI uri = file.toURI();
		Media media = new Media(uri.toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		authService.getCurrentUser().incrementWins();
		StackPane winPane = ConfettiEffect.createConfettiStackPane(this);
		scene = new Scene(winPane, 800, 600);
		stage.setTitle("Win!");
		stage.setScene(scene);
		stage.show();
	}

	protected void setLostView(Wordle game) {
		StackPane lostView = new StackPane();
		
		

		Text lostText = new Text("Game Over \n The correct word was: \n" + game.getCorrect());
		lostText.setFont(Font.font(72));
		lostText.setTextAlignment(TextAlignment.CENTER);

		Button playAgainButton = new Button("play again");

		lostView.getChildren().addAll(lostText, playAgainButton);
		lostView.setAlignment(playAgainButton, Pos.BOTTOM_CENTER);
		lostView.setMargin(playAgainButton, new Insets(10));

		playAgainButton.setOnAction((e) -> {
			createNewGame();
		});
		scene = new Scene(lostView, 800, 600);
		stage.setTitle("Lost!");
		stage.setOnCloseRequest((event) -> {
			authService.getCurrentUser().newGame(); authService.logOut();
		});
		stage.setScene(scene);
		stage.show();

	}

	protected void logOut() {
		authService.logOut();
		loginView = new LoginView(this);
		loginView.setLoginListener(authService);
		scene = new Scene(loginView, 400, 300);
//		scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
		stage.setTitle("Wordle Login");
		stage.setScene(scene);
		stage.show();
	}

	private void makeMenu() {
		menuBar = new MenuBar();
		Menu settings = new Menu("Settings");
		Menu modes = new Menu("Modes");
		MenuItem lightMode = new MenuItem("Light Mode");
		MenuItem darkMode = new MenuItem("Dark Mode");
		MenuItem statistics = new MenuItem("Statistics");
		MenuItem help = new MenuItem("Help");
		MenuItem logout = new MenuItem("Logout");
//		MenuItem home = new MenuItem("Home");
		
		modes.getItems().add(lightMode);
		modes.getItems().add(darkMode);
//		settings.getItems().add(home);
		settings.getItems().add(statistics);
		settings.getItems().add(modes);
		settings.getItems().add(help);
		settings.getItems().add(logout);
		menuBar.getMenus().add(settings);
		
//		home.setOnAction((e) -> {
//			createGameView();
//		});
		
		statistics.setOnAction((e) -> {
			
//			pane = new BorderPane();
//			game = authService.getCurrentUser().getUserData();
//			UserAccount user = authService.getCurrentUser();
//		    StatsView statsView = new StatsView(game, user);
//		    
//		    pane.setTop(menuBar);
//		    pane.setCenter(statsView);
//		    
//		    scene = new Scene(pane, 400, 400);
//		    stage.setTitle("Wordle");
//		    stage.setScene(scene);
//		    game.addObserver(statsView);
//		    stage.setOnCloseRequest((event) -> {
//				logOut();
//			});
		});
		
		
		statistics.setOnAction((e)->{handleStatisticsClick();});
		
		help.setOnAction((e) -> {
			try {
				File file = new File("Wordle Help.pdf");
				if (file.exists() && Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(file);
				}
			} catch (IOException ex) {
				System.out.println("File not found");
			}
		});
		
		darkMode.setOnAction((e) -> {
			attemptView.setDark();
			keyboardView.setDark();
			pane.setStyle("-fx-background-color:rgb(55,55,55);");
		});
		
		lightMode.setOnAction((e) -> {
			attemptView.setLight();
			keyboardView.setLight();
			pane.setStyle(null);
		});
		
		logout.setOnAction((e) -> {
			logOut();
		});
	}
	
	private void handleStatisticsClick() 
	{
		BorderPane statView=new StatsViewV2(authService.getCurrentUser(), this);
		
		scene = new Scene(statView, 500, 600);
//		scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
		stage.setTitle("Statistics");
		stage.setScene(scene);
		stage.show();
	}
}
