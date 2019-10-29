/* 
 * 7/5/19
 * DONE! (decided against dice images)
 * 
 * 7/2/19
 * Finished:
 *    Game Over, Restart
 * To Add:
 *   Additional yahtzee
 *   Dice
 *   
 * 6/14/19
 * Finished:
 *   Home Screen, Rules Screen, 1/2 Game Over
 * To Add:
 *   Debug additional yahtzee
 *   Game Over Screen
 *   Dice
 * 
 * 6/5/19
 * Finished:
 *   Reset holds, Bonus, Disabling Radio Buttons when used, additional yahtzee(?)
 * To add:
 *   Game Over Screen --> Home, Start
 *   Home Screen --> Rules, Start
 *   Dice animations
 *   
 * 5/23/19
 * Finished:
 * Base scoring of each category
 * Made all algorithms more efficient and implemented use of arrays
 * 
 * Need to add:
 * 	Upper bonus (based on radio buttons of top category?)
 * 	Additional Yahtzee
 * 	Removal of Categories once chosen
 *  Reset Hold Buttons w/ start and submit
 *  Rules Screen
 *  Game Over Screen (based on radio buttons or turns)
 *  Dice pictures/animations
 *  Additional Players?
 * 
 * 8/25/18
 * Finished:
 * Base scoring for all but the straights
 * 
 * 7/16/18
 * Started
 * 
 * RULES:
 * 	1-4 players (Start with one)
 * 	5 dice
 * 	13 turns -- up to 3 rolls per turn
 * 	Highest point value wins
 * 
 * SCORING:
 * 	Upper level:
 * 		Add total of specified dice
 * 		If total is >= 63, add Bonus of 35
 * 
 *  Lower Level:
 * 		3 of a kind: add all dice
 * 		4 of a kind: add all dice
 * 		Small Straight: 30 points
 * 		Large Straight: 40 points
 * 		Full House: 25 points
 * 		Yahtzee: 50 points
 * 		Additional Yahtzee: 100 points & must also score for additional unfilled category w/ upper levels getting priority
 * 		Chance: add dice
 * 
 * 	Can Scratch anything for 0 points
 *   
 */

package Yahtzee;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Gameplay extends Application {
	static int roll, turn, points, score, upperScore, additionalYahtzee;
	static Dice dice1 = new Dice(), dice2 = new Dice(), dice3 = new Dice(), dice4 = new Dice(), dice5 = new Dice();
	static boolean bonus = false, firstYahtzee = false;
	
	//CREATE LABELS
	Label lTitle = new Label("Yahtzee");
	static Label lBonus = new Label("Bonus: NO - Current: " + upperScore + "/63");
	static Label lYahtzee = new Label("Additonal Yahtzee: NO");
	//static Label lTurn = new Label("Turn: " + turn);
	static Label lRoll = new Label("Roll: " + roll);
	static Label lScore = new Label("Score: " + score);
	static Label lPoints = new Label("" + points);
	
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
	Button btnSubmit = new Button("Sumbit");
	
	//Debug Buttons
	//Button btnStart = new Button("Start");
	//Button Y = new Button("Yahtzee");
	//Button E = new Button("End");
	
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
	
	static GridPane gridPane = new GridPane();
	
	protected GridPane getGrid() {
		//CREATE GRID PANE
		//GridPane gridPane = new GridPane();
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
		gridPane.add(lBonus, 0, 8, 2, 1);
		gridPane.add(lYahtzee, 0, 9, 2, 1);

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
		//gridPane.add(btnStart, 0, 11);
		//gridPane.add(btnSubmit, 6, 9);
		gridPane.add(btnSubmit, 6, 11);
		gridPane.add(btnRoll, 0, 11);
		//gridPane.add(lTurn, 0, 14);
		//gridPane.add(lUpperScore, 0, 14);
		//gridPane.add(lRoll, 3, 14);
		gridPane.add(lRoll, 0, 14);
		gridPane.add(lScore, 6, 14);
		
		/*
		//Test Yahtzee and End
		gridPane.add(Y, 0, 0);
		Y.setOnAction(e -> makeYahtzee());
		gridPane.add(E, 6, 0);
		E.setOnAction(e -> turn = 12);
		*/
		
		/*
		//Start & Restart
		btnStart.setOnAction(e -> getGame());
		*/
		
		//Rerolls and Holds
		btnRoll.setOnAction(e -> roll());
		
		//Radio Buttons & Scoring
		//Create toggle group (only one rb can be active at a time)
		ToggleGroup tgChoose = new ToggleGroup();
		rbOnes.setToggleGroup(tgChoose);
		rbTwos.setToggleGroup(tgChoose);
		rbThrees.setToggleGroup(tgChoose);
		rbFours.setToggleGroup(tgChoose);
		rbFives.setToggleGroup(tgChoose);
		rbSixes.setToggleGroup(tgChoose);
		rbKind3.setToggleGroup(tgChoose);
		rbKind4.setToggleGroup(tgChoose);
		rbFH.setToggleGroup(tgChoose);
		rbSS.setToggleGroup(tgChoose);
		rbLS.setToggleGroup(tgChoose);
		rbYahtzee.setToggleGroup(tgChoose);
		rbChance.setToggleGroup(tgChoose);
		
		//Set Effects
		rbOnes.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbOnes.setSelected(true);
		        	rbOnes.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getOnes();
		            gridPane.add(lPoints, 1, 2);
		        }
		    }
		});
		
		rbTwos.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbTwos.setSelected(true);
		        	rbTwos.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getTwos();
		            gridPane.add(lPoints, 1, 3);
		        }
		    }
		});
		
		rbThrees.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbThrees.setSelected(true);
		        	rbThrees.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getThrees();
		            gridPane.add(lPoints, 1, 4);
		        }
		    }
		});

		rbFours.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbFours.setSelected(true);
		        	rbFours.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getFours();
		            gridPane.add(lPoints, 1, 5);
		        }
		    }
		});
		
		rbFives.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbFives.setSelected(true);
		        	rbFives.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getFives();
		            gridPane.add(lPoints, 1, 6);
		        }
		    }
		});
		
		rbSixes.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbSixes.setSelected(true);
		        	rbSixes.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getSixes();
		            gridPane.add(lPoints, 1, 7);
		        }
		    }
		});
		
		rbKind3.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbKind3.setSelected(true);
		        	rbKind3.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getKind3();
		            gridPane.add(lPoints, 5, 2);
		        }
		    }
		});
		
		rbKind4.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbKind4.setSelected(true);
		        	rbKind4.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getKind4();
		            gridPane.add(lPoints, 5, 3);
		        }
		    }
		});
		
		rbFH.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbFH.setSelected(true);
		        	rbFH.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getFullHouse();
		            gridPane.add(lPoints, 5, 4);
		        }
		    }
		});
		
		rbSS.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbSS.setSelected(true);
		        	rbSS.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getSmallStraight();
		            gridPane.add(lPoints, 5, 5);
		        }
		    }
		});
		
		rbLS.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbLS.setSelected(true);
		        	rbLS.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getLargeStraight();
		            gridPane.add(lPoints, 5, 6);
		        }
		    }
		});
		
		rbYahtzee.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbYahtzee.setSelected(true);
		        	rbYahtzee.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getYahtzee();
		            gridPane.add(lPoints, 5, 7);
		        }
		    }
		});
		
		rbChance.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	rbChance.setSelected(true);
		        	rbChance.requestFocus();
		            gridPane.getChildren().remove(lPoints);
		            getChance();
		            gridPane.add(lPoints, 5, 8);
		        }
		    }
		});
		
		
		//Submit
		if (turn <= 13) {
			btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	//Update score, turn, roll
			    	score += points;
			    	turn++;
			    	roll = 0;
			    	lRoll.setText("Roll: " + roll);
			    	
			    	//Remove Chosen Option
			    	updateOptions();
			    	gridPane.getChildren().remove(lPoints);
			    	
			    	//Check Bonus
			    	if (bonus == false) {
			    		if (upperScore >= 63) {
			    			score += 35;
			    			bonus = true;
			    			lBonus.setText("Bonus: YES - Score +35");
			    		}
			    		else {
			    			lBonus.setText("Bonus: NO - Current: " + upperScore + "/63");
			    		}
			    	}
			    	
			    	//Check additional Yahtzee
			    	checkAdditionalYahtzee();
			    	
					//Update Score
					lScore.setText("Score: " + score);
					
			    	//Check if end of game
					if (turn > 13) {
						Alerts.getEnd();
					}
					
					//reset the holds and start next turn
			    	resetCB();
			    	roll();
			    }
			});
		}
		else btnSubmit = null;
		
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
		//Deselect Boxes	
		resetCB();
		
		//Roll
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
		lRoll.setText("Roll: " + turn);
		score = 0;
		lScore.setText("Score: " + score);
		
		upperScore = 0;
		points = 0;
		additionalYahtzee = 0;
		lBonus.setText("Bonus: NO - Current: " + upperScore + "/63");
		lYahtzee.setText("Additonal Yahtzee: NO");
	}
	
	public static void roll() {
		points = 0;
		if (roll < 3) {
			//Dice 1
			if (cbHold1.isSelected()) {
				dice1.Hold();
			}
			else {
				dice1.Roll();
				tDice1.setText("" + dice1.getValue());
			}
			//Dice 2
			if (cbHold2.isSelected()) {
				dice2.Hold();
			}
			else {
				dice2.Roll();
				tDice2.setText("" + dice2.getValue());
			}
			//Dice 3
			if (cbHold3.isSelected()) {
				dice3.Hold();
			}
			else {
				dice3.Roll();
				tDice3.setText("" + dice3.getValue());
			}
			//Dice 4
			if (cbHold4.isSelected()) {
				dice4.Hold();
			}
			else {
				dice4.Roll();
				tDice4.setText("" + dice4.getValue());
			}
			//Dice 5
			if (cbHold5.isSelected()) {
				dice5.Hold();
			}
			else {
				dice5.Roll();
				tDice5.setText("" + dice5.getValue());
			}
			
			//Increase Roll
			roll++;
			lRoll.setText("Roll: " + roll);
			
			firstYahtzee = false;
		}
	}
	
	public static void getOnes() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		for (int i = 0; i < tempArray.length; i++) {
			if (tempArray[i] == 1) {
				points += 1;
			}
		}
		upperScore += points;
		lPoints.setText("" + points);
	}

	public static void getTwos() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		for (int i = 0; i < tempArray.length; i++) {
			if (tempArray[i] == 2) {
				points += 2;
			}
		}
		upperScore += points;
		lPoints.setText("" + points);
	}
	
	public static void getThrees() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		for (int i = 0; i < tempArray.length; i++) {
			if (tempArray[i] == 3) {
				points += 3;
			}
		}
		upperScore += points;
		lPoints.setText("" + points);
	}
	
	public static void getFours() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		for (int i = 0; i < tempArray.length; i++) {
			if (tempArray[i] == 4) {
				points += 4;
			}
		}
		upperScore += points;
		lPoints.setText("" + points);
	}
	
	public static void getFives() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		for (int i = 0; i < tempArray.length; i++) {
			if (tempArray[i] == 5) {
				points += 5;
			}
		}
		upperScore += points;
		lPoints.setText("" + points);
	}
	
	public static void getSixes() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		for (int i = 0; i < tempArray.length; i++) {
			if (tempArray[i] == 6) {
				points += 6;
			}
		}
		upperScore += points;
		lPoints.setText("" + points);
	}
	
	public static void getKind3() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		if (tempArray[0] == tempArray[1] && tempArray[1] == tempArray[2]) {
			for (int i = 0; i < tempArray.length; i++) {
				points += tempArray[i];
			}
		}
		else if (tempArray[1] == tempArray[2] && tempArray[2] == tempArray[3]) {
			for (int i = 0; i < tempArray.length; i++) {
				points += tempArray[i];
			}
		}
		else if (tempArray[2] == tempArray[3] && tempArray[3] == tempArray[4]) {
			for (int i = 0; i < tempArray.length; i++) {
				points += tempArray[i];
			}
		}
		
		lPoints.setText("" + points);
	}

	public static void getKind4() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		if (tempArray[0] == tempArray[1] && tempArray[1] == tempArray[2] && tempArray[2] == tempArray[3]) {
			for (int i = 0; i < tempArray.length; i++) {
				points += tempArray[i];
			}
		}
		else if (tempArray[1] == tempArray[2] && tempArray[2] == tempArray[3] && tempArray[3] == tempArray[4]) {
			for (int i = 0; i < tempArray.length; i++) {
				points += tempArray[i];
			}
		}
		
		lPoints.setText("" + points);
	}
	
	public static void getFullHouse() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		
		if (tempArray[0] == tempArray[1]) {
			if (tempArray[1] == tempArray[2]) {
				if (tempArray[3] == tempArray[4]) {
					points = 25;
				}
			}
			else if (tempArray[2] == tempArray[3] && tempArray[3] == tempArray[4]) {
				points = 25;
			}
		}
		
		lPoints.setText("" + points);
	}
	
	public static void getSmallStraight() {
		int [] array1 = {1,2,3,4};
		int [] array2 = {2,3,4,5};
		int [] array3 = {3,4,5,6};
		int [] array4 = {1,2,3,4,5};
		int [] array5 = {2,3,4,5,6};
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		
		//Remove duplicates
		LinkedHashSet<Integer> tempSet = new LinkedHashSet<Integer>();
		for (int i = 0; i < tempArray.length; i++) {
			tempSet.add(tempArray[i]);
		}
		Integer[] arrayNoDups = tempSet.toArray(new Integer [] {});
		
		int [] tempArray2 = new int [arrayNoDups.length];
		for (int i = 0; i < arrayNoDups.length; i++) {
			tempArray2[i] = arrayNoDups[i];
		}
		
		//Compare with possible straights
		if (Arrays.equals(tempArray2, array1) == true) {
			points = 30;
		}
		else if (Arrays.equals(tempArray2, array2) == true) {
			points = 30;
		}
		else if (Arrays.equals(tempArray2, array3) == true) {
			points = 30;
		}
		else if (Arrays.equals(tempArray2, array4) == true) {
			points = 30;
		}
		else if (Arrays.equals(tempArray2, array5) == true) {
			points = 30;
		}
		
		lPoints.setText("" + points);
	}
	
	public static void getLargeStraight() {
		int [] array1 = {1,2,3,4,5};
		int [] array2 = {2,3,4,5,6};
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		if (Arrays.equals(tempArray, array1) == true || Arrays.equals(tempArray, array2) == true) {
			points = 40;
		}
		
		lPoints.setText("" + points);
	}
	
	public static void getChance() {
		points = 0;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		for (int i = 0; i < tempArray.length; i++) {
			points += tempArray[i];
		}
		
		lPoints.setText("" + points);
	}
	
	public static void getYahtzee() {
		points = 0;
		boolean tempYahtzee = false;
		
		int [] tempArray = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
		for (int i = 0; i < (tempArray.length - 1); i++) {
			if (tempArray[i] == tempArray[i+1]) {
				tempYahtzee = true;
			}
			else {
				tempYahtzee = false;
				break;
			}
		}
		
		if (tempYahtzee == true) {
			points = 50;
			firstYahtzee = true;
		}
		
		lPoints.setText("" + points);
	}
	
	public static void checkAdditionalYahtzee() {
		boolean tempYahtzee = false;
		if (firstYahtzee == false) {
			int [] tempArray1 = Dice.makeArray(dice1, dice2, dice3, dice4, dice5);
			for (int i = 0; i < (tempArray1.length - 1); i++) {
				if (tempArray1[i] == tempArray1[i+1]) {
					tempYahtzee = true;
				}
				else {
					tempYahtzee = false;
					break;
				}
			}
			if (tempYahtzee == true) {
				additionalYahtzee++;
				score += 100;
				
				int yahtzeeScore;
				yahtzeeScore = additionalYahtzee * 100;
				lYahtzee.setText("Additonal Yahtzee: YES - Score +" + Integer.toString(yahtzeeScore));
			}
		}
	}
	
	public static void resetCB() {
		cbHold1.setSelected(false);
		cbHold2.setSelected(false);
		cbHold3.setSelected(false);
		cbHold4.setSelected(false);
		cbHold5.setSelected(false);
	}

	public static void updateOptions() {
	   	if (rbOnes.isSelected() == true) {
    		rbOnes.setDisable(true);
		}
    	if (rbTwos.isSelected() == true) {
			rbTwos.setDisable(true);
		}
    	if (rbThrees.isSelected() == true) {
			rbThrees.setDisable(true);
		}
    	if (rbFours.isSelected() == true) {
    		rbFours.setDisable(true);
		}
    	if (rbFives.isSelected() == true) {
			rbFives.setDisable(true);
		}
    	if (rbSixes.isSelected() == true) {
			rbSixes.setDisable(true);
		}
    	if (rbKind3.isSelected() == true) {
			rbKind3.setDisable(true);
		}
    	if (rbKind4.isSelected() == true) {
			rbKind4.setDisable(true);
		}
    	if (rbFH.isSelected() == true) {
			rbFH.setDisable(true);
		}
    	if (rbSS.isSelected() == true) {
			rbSS.setDisable(true);
		}
    	if (rbLS.isSelected() == true) {
			rbLS.setDisable(true);
		}
    	if (rbYahtzee.isSelected() == true) {
			rbYahtzee.setDisable(true);
		}
    	if (rbChance.isSelected() == true) {
			rbChance.setDisable(true);
		}
	}
	
	public static void makeYahtzee() {
		dice1.setValue(1);
		dice2.setValue(1);
		dice3.setValue(1);
		dice4.setValue(1);
		dice5.setValue(1);
	}

	public static int getTurn() {
		return turn;
	}
	
	public String getScore() {
		return Integer.toString(score);
	}
	
	public void close() {
		gridPane.setDisable(true);
	}
	
	public void reset() {
		gridPane.setDisable(false);
		getGame();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
