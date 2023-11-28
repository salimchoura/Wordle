package view_controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.interfaces.Loggable;

/**
 * This class represents the view for the login screen of the application.
 * It extends the GridPane class.
 */
public class LoginView extends GridPane {
    private Label usernameLabel;
    private Label passwordLabel;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginBtn;
    private Button createBtn;
    private ImageView img;
    private Loggable authentication;
    private Main parent;

    /**
     * Constructs a LoginView object.
     * @param parent The parent Main object.
     */
    public LoginView(Main parent) 
    {
    	this.parent=parent;
    	getStyleClass().add("login-view");
    	usernameLabel = new Label("Username:");
    	passwordLabel = new Label("Password:");
    	usernameField = new TextField();
    	passwordField = new PasswordField();
    	loginBtn = new Button("Login");
    	createBtn = new Button("Create Account");
    	img = new ImageView(new Image("file:images/wordle.png"));
    	img.setFitWidth(250);
    	img.setFitHeight(50);
    	
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        
        Font font = new Font("Arial", 14);
        usernameLabel.setFont(font);
        passwordLabel.setFont(font);

        
        add(img, 0, 0, 2, 1);
        add(usernameLabel, 0, 1);
        add(usernameField, 1, 1);
        add(passwordLabel, 0, 2);
        add(passwordField, 1, 2);
        
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(15);
        buttonPane.add(loginBtn, 0, 0);
        buttonPane.add(createBtn,1, 0);
        add(buttonPane, 1, 3);
        
        loginBtn.setOnAction((e) -> {logInHandler();});
        createBtn.setOnAction((e)->{createAccountHandler();});
    }

    /**
     * Handles the login button click event.
     * It checks the entered username and password and performs the necessary actions.
     */
    private void logInHandler()
    {
    	int result=authentication.logIn(getUsername(), getPassword());
    	if (result==0)
    	{
    		parent.showWarning("Some is logged in.");
    	}
    	
    	else if (result==1)
    	{
    		parent.showWarning("The user does not exist");
    	}
    	
    	else if(result==2)
    	{
    		parent.showWarning("The passwords do not match.");
    	}
    	
    	else if (result == 4) 
    	{
    		parent.showWarning("Please make sure that both fields are not empty.");
    	}
    	
    	else
    	{
    		parent.createGameView();
    	}
    	
    }
    
    /**
     * Handles the create account button click event.
     * It creates a new account using the entered username and password.
     */
    private void createAccountHandler()
    {
    	int result= authentication.createAccount(getUsername(), getPassword());
    	
    	if (result==0) 
    	{
    		parent.showWarning("The account already exists.");
			usernameField.clear();
			passwordField.clear();
    	}
    	
    	else if (result == 1)
    	{
    		parent.showWarning("Success! Your account hass been created.");
    		usernameField.clear();
    		passwordField.clear();
    	}
    	else if (result == 2) 
    	{
    		parent.showWarning("Please make sure that both fields are not empty.");
    		usernameField.clear();
    		passwordField.clear();
    	}
    	
    }
    
   /**
    * This method gets the user name.
    * @return
    */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * This method gets the password.
     * @return
     */
    public String getPassword() {
        return passwordField.getText();
    }
    
    /**
     * This method sets the authentication service for the view.
     * @param authentication Authentication service.
     */
    public void setLoginListener(Loggable authentication) {
    	this.authentication = authentication;
    }
    
}
