/* 7/16/18
 */
package Yahtzee;

import java.util.Arrays;

public class Dice {
	private int value;
	
	public Dice() {
		value = 0;
	}
	
	public Dice(int newValue) {
		value = newValue;
	}
	
	public void setValue(int val) {
		value = val;
	}
	
	public int getValue() { return value; }
	
	public void Roll() {
		value = (int) (Math.random() * 6 + 1);
	}
	
	public void Hold() {
		value = value;
	}
	
	public static int[] makeArray(Dice dice1, Dice dice2, Dice dice3, Dice dice4, Dice dice5) {
		int [] diceArray = {dice1.getValue(), dice2.getValue(), dice3.getValue(), dice4.getValue(), dice5.getValue()};
		Arrays.sort(diceArray);
		return diceArray;
	}

}

