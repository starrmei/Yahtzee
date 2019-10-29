package Yahtzee;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import java.util.Optional;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class Alerts extends Application {
	static Gameplay gameplay = new Gameplay(); 
	static BorderPane pane = new BorderPane();
	static GridPane rules = new GridPane();
	static GridPane end = new GridPane();
	
	static Button btnStart = new Button("Start");
	static Label lTitle = new Label("Rules");
	static Label lRules = new Label("Roll the dice and get the highest score possible!");
	static Label lUpper = new Label("\nUpper Section Scoring");
	static Label lUpperDetails = new Label("Add all dice values of the chosen die face."
			+ "\nRecieve 35 bonus points if the upper score reaches 63 points or higher."
			+ "\n");
	static Label lLower = new Label("\nLower Section Scoring");
	static Label lLowerDetails = new Label("3 and 4 of a kind - 3 or 4 dice of same value: add all dice values"
			+ "\nSmall and Large Straight - sequence of 4 or 5 consecutive dice values: 30 or 40 points"
			+ "\nFull House - 3 of a kind + pair: 25 points"
			+ "\nYahtzee - 5 dice of same value: 50 points (additional yahtzees worth 100 extra points)"
			+ "\nChance: add all dice values"
			+ "\n");

	static Label lGameOver = new Label("Game Over");
	static Label lScore = new Label("Score: " + gameplay.getScore());
	
	static Alert alStart = new Alert(AlertType.CONFIRMATION);
	static Alert alEnd = new Alert(AlertType.CONFIRMATION);
	
	public void start(Stage primaryStage) {
		//Show start message
		getStart();
		
	    Optional<ButtonType> result = alStart.showAndWait();
	    //Close
	    if (result.get() == null) {
	    	System.exit(0);
	    }
	    
	    //Start hit
	    else if (result.get() == ButtonType.OK) {	
	    	//Scene
	    	Scene scene = new Scene(gameplay.getGrid());
		    primaryStage.setTitle("Yahtzee"); 
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    gameplay.getGame();
	    }
	    
	    //Rules hit
	    else if(result.get() == ButtonType.CANCEL) {
	    	getRules();
	    	
	    	btnStart.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent e) {
		        	Scene scene = new Scene(gameplay.getGrid());
				    primaryStage.setTitle("Yahtzee"); 
				    primaryStage.setScene(scene);
				    primaryStage.show();
				    gameplay.getGame();
		        } 
		    });
	    	//Scene
	    	Scene scene = new Scene(pane, 500, 300);
		    primaryStage.setTitle("Yahtzee"); 
		    primaryStage.setScene(scene);
		    primaryStage.show();
		
		    //Request focus
		    rules.requestFocus();   
	    }
	    if (gameplay.getTurn() > 13) {
		    primaryStage.close();
	    }
	}
	
	public static void getStart() {
		//Start game alert
		alStart.setTitle("Yahtzee");
	  	alStart.setHeaderText("Let's Play Yahtzee!");
	    Button btnStart = (Button) alStart.getDialogPane().lookupButton(ButtonType.OK);
	    btnStart.setText("Start");
	    Button btnOptions = (Button) alStart.getDialogPane().lookupButton(ButtonType.CANCEL);
	    btnOptions.setText("Rules");	
	}
	
	public static void getRules() {
		//Labels
    	lTitle.setFont(new Font("Arial", 25));
		lRules.setFont(new Font("Arial", 17));
		lUpper.setFont(new Font("Arial", 15));
		lUpperDetails.setFont(new Font("Arial", 12));
		lLower.setFont(new Font("Arial", 15));
		lLowerDetails.setFont(new Font("Arial", 12));
		
    	rules.add(lTitle, 0, 0);
    	rules.add(lRules, 0, 1);
    	rules.add(lUpper, 0, 3);
    	rules.add(lUpperDetails, 0, 4);
    	rules.add(lLower, 0, 6);
    	rules.add(lLowerDetails, 0, 7);
    	rules.add(btnStart, 0, 9);
    	
    	pane.setTop(rules);
		rules.setAlignment(Pos.CENTER);

	}
	
	public static void getEnd() {
		gameplay.close();
		
		alEnd.setTitle("Game Over");
	  	alEnd.setHeaderText("Game Over");
	  	alEnd.setContentText("Score: " + gameplay.getScore());
	    Button btnRestart = (Button) alEnd.getDialogPane().lookupButton(ButtonType.OK);
	    btnRestart.setText("Replay");
	    Button btnClose = (Button) alEnd.getDialogPane().lookupButton(ButtonType.CANCEL);
	    btnClose.setText("Close");
	    Optional<ButtonType> result = alEnd.showAndWait();
	    //alEnd.showAndWait();
		
		if (result.get() == null) {
	    	System.exit(0);
	    }
	    
	    //Start hit
	    else if (result.get() == ButtonType.OK) {	
		    gameplay.reset();
		    
	    }
	    else if(result.get() == ButtonType.CANCEL) {
	    	System.exit(0);
	    }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

