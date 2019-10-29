package Yahtzee;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class gameplayText extends Application {
	
	static int roll, turn, points, score, upperScore;
	static Dice dice1 = new Dice(), dice2 = new Dice(), dice3 = new Dice(), dice4 = new Dice(), dice5 = new Dice();
	
	//CREATE LABELS
	Label lTitle = new Label("Yahtzee");
	Label lBonus = new Label("Bonus");
	static Label lTurn = new Label("Turn: " + turn);
	static Label lRoll = new Label("Roll: " + roll);
	static Label lScore = new Label("Score: " + score);
	
	//CREATE RADIO BUTTONS
	//Upper Section
	static RadioButton rbOnes = new RadioButton("Ones");
	static RadioButton rbTwos = new RadioButton("Twos");
	static RadioButton rbThrees = new RadioButton("Threes");
	static RadioButton rbFours = new RadioButton("Fours");
	static RadioButton rbFives = new RadioButton("Fives");
	static RadioButton rbSixes = new RadioButton("Sixes");
	//Lower Section
	static RadioButton rbKind3 = new RadioButton("3 of a Kind");
	static RadioButton rbKind4 = new RadioButton("4 of a Kind");
	static RadioButton rbFH = new RadioButton("Full House");
	static RadioButton rbSS = new RadioButton("Small Straight");
	static RadioButton rbLS = new RadioButton("Large Straight");
	static RadioButton rbYahtzee = new RadioButton("Yahtzee");
	static RadioButton rbChance = new RadioButton("Chance");
	
	//CREATE BUTTONS
	Button btnRoll = new Button("Roll");
	Button btnSubmit = new Button("Submit");
	Button btnStart = new Button("Start");
	
	//CREATE HOLD CHECK BOXES
	static CheckBox cbHold1 = new CheckBox("Hold");
	static CheckBox cbHold2 = new CheckBox("Hold");
	static CheckBox cbHold3 = new CheckBox("Hold");
	static CheckBox cbHold4 = new CheckBox("Hold");
	static CheckBox cbHold5 = new CheckBox("Hold");
	
	//CREATE DICE TEXT
	static Text tDice1 = new Text("" + dice1.getValue());
	static Text tDice2 = new Text("" + dice2.getValue());
	static Text tDice3 = new Text("" + dice3.getValue());
	static Text tDice4 = new Text("" + dice4.getValue());
	static Text tDice5 = new Text("" + dice5.getValue());	
	
	protected GridPane getGrid() {
		//CREATE GRID PANE
		GridPane gridPane = new GridPane();
		//Set gaps between columns and rows
		gridPane.setHgap(80);
		gridPane.setVgap(15);
		//Set column width
		gridPane.getColumnConstraints().add(new ColumnConstraints(60)); //Column 0
		gridPane.getColumnConstraints().add(new ColumnConstraints(100)); //Column 1
		gridPane.getColumnConstraints().add(new ColumnConstraints(100)); //Column 2
		gridPane.getColumnConstraints().add(new ColumnConstraints(100)); //Column 3
		gridPane.getColumnConstraints().add(new ColumnConstraints(100)); //Column 4
		gridPane.getColumnConstraints().add(new ColumnConstraints(100)); //Column 5
		gridPane.getColumnConstraints().add(new ColumnConstraints(100)); //Column 6
		//Set Padding --> distance between top, right, bottom, left sides of scene
		gridPane.setPadding(new Insets(20, 0, 20, 50));
		//Set Alignment 
		gridPane.setAlignment(Pos.CENTER);
				
		//ADD THE NODES
		//Left (Upper Section)
		gridPane.add(rbOnes, 0, 2);
		gridPane.add(rbTwos, 0, 3);
		gridPane.add(rbThrees, 0, 4);
		gridPane.add(rbFours, 0, 5);
		gridPane.add(rbFives, 0, 6);
		gridPane.add(rbSixes, 0, 7);
		gridPane.add(lBonus, 0, 8);
		//Right (Lower Section)
		gridPane.add(rbKind3, 4, 2);
		gridPane.add(rbKind4, 4, 3);
		gridPane.add(rbFH, 4, 4);
		gridPane.add(rbSS, 4, 5);
		gridPane.add(rbLS, 4, 6);
		gridPane.add(rbYahtzee, 4, 7);
		gridPane.add(rbChance, 4, 8);
		//Dice w/ Hold
		//Dice 1
		gridPane.add(tDice1, 1, 11);
		gridPane.add(cbHold1, 1, 12);
		//Dice 2
		gridPane.add(tDice2, 2, 11);
		gridPane.add(cbHold2, 2, 12);
		//Dice 3
		gridPane.add(tDice3, 3, 11);
		gridPane.add(cbHold3, 3, 12);
		//Dice 4
		gridPane.add(tDice4, 4, 11);
		gridPane.add(cbHold4, 4, 12);
		//Dice 5
		gridPane.add(tDice5, 5, 11);
		gridPane.add(cbHold5, 5, 12);
				
		//Other
		gridPane.add(lTitle, 3, 0);
		gridPane.add(btnStart, 0, 11);
		gridPane.add(btnSubmit, 6, 9);
		gridPane.add(btnRoll, 6, 11);
		gridPane.add(lTurn, 0, 14);
		gridPane.add(lRoll, 3, 14);
		gridPane.add(lScore, 6, 14);
			
		//Start
		btnStart.setOnAction(e -> getGame());
		
		//Rerolls and Holds
		btnRoll.setOnAction(e -> roll());
				
		return gridPane;
	}
	
	public void start(Stage primaryStage) throws Exception {
		//CREATE A SCENE
		Scene scene = new Scene(getGrid());  
		//Setting title 
		primaryStage.setTitle("Yahtzee"); 
		//Adding scene to the stage 
		primaryStage.setScene(scene); 
		//Displaying the stage 
		primaryStage.show(); 	 
	}
		
	public static void getGame() {
		//Turn 1
		dice1.Roll();
		tDice1.setText("" + dice1.getValue());
		dice2.Roll();
		tDice2.setText("" + dice2.getValue());
		dice3.Roll();
		tDice3.setText("" + dice3.getValue());
		dice4.Roll();
		tDice4.setText("" + dice4.getValue());
		dice5.Roll();
		tDice5.setText("" + dice5.getValue());
		turn = 1;
		roll = 1;
		score = 0;
		upperScore = 0;
		points = 0;
	}
	
	public static void roll() {
		dice1.Roll();
		tDice1.setText("" + dice1.getValue());
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
