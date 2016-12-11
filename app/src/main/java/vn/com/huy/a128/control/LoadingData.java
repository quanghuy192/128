package vn.com.huy.a128.control;

import java.util.Random;

import android.util.Log;

/**
 * Created by Huy on 12/26/2014.
 */
public class LoadingData {

	// private static LoadingData INSTANCE_LOADING_DATA = new LoadingData();
	private int LENGTH = 8;
	private boolean[][] sunLogicArray = new boolean[LENGTH][LENGTH];
	private int[][] sunValueArray = new int[LENGTH][LENGTH];
	private int currentBomb = 11;
	private int MAX_VALUE = 2;
	private int[] valueOfRow = new int[LENGTH];
	private int[] valueOfColumn = new int[LENGTH];
	private int level;

	private Random random = new Random();

	public LoadingData() {
		// throw new AssertionError();

	}

	public int totalScoreInGame() {
		int values = 0;
		for (int i = 1; i < sunValueArray.length; i++) {
			for (int j = 1; j < sunValueArray.length; j++) {
				if (sunLogicArray[i][j] == false)
					values += sunValueArray[i][j];
			}
		}
		return values;
	}

	// Singleton pattern
	/*
	 * public synchronized static LoadingData getInstance() { return
	 * INSTANCE_LOADING_DATA; }
	 */

	// Reset matrix

	public void resetMatrix() {

		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				sunLogicArray[i][j] = false;
			}
		}

		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				sunValueArray[i][j] = 0;
			}
		}

		for (int i = 0; i < LENGTH; i++) {
			valueOfRow[i] = 0;
			valueOfColumn[i] = 0;
		}
	}

	// return data
	// create data an return array

	public void setLevel(int level) {
		this.level = level;
		switch (level) {
		case 0:
		case 1: {
			MAX_VALUE = 3;
			currentBomb = 11;
		}

			break;
		case 2:

		case 3: {
			MAX_VALUE = 3;
			currentBomb = 13;
		}

			break;
		case 4: {
			MAX_VALUE = 4;
			currentBomb = 17;

		}

			break;
		case 5: {
			MAX_VALUE = 4;
			currentBomb = 19;
		}

			break;
		case 6: {
			MAX_VALUE = 5;
			currentBomb = 23;
		}

			break;

		default:
			break;
		}

	}

	public int getCountSun() {
		return currentBomb;
	}

	public final int[][] getDataList() {

		resetMatrix();
		createDataList();
		createValueOfDataList();
		createValueOfColumn();
		createValueOfRow();
		test();
		return sunValueArray;
	}

	public void createValueOfColumn() {
		// TODO Auto-generated method stub
		for (int i = 1; i < LENGTH; i++) {
			for (int j = 1; j < LENGTH; j++) {
				sunValueArray[0][j] += sunValueArray[i][j];

				/*
				 * if (sunLogicArray[i][j] == true) sunValueArray[0][j] += 1;
				 */
				// Log.d("TAG>>>>>>>", String.valueOf(sunValueArray[i][j]));

			}
		}

	}

	public void createValueOfRow() {
		// TODO Auto-generated method stub
		for (int i = 1; i < LENGTH; i++) {
			for (int j = 1; j < LENGTH; j++) {
				sunValueArray[i][0] += sunValueArray[i][j];

				// if (sunLogicArray[i][j] == true)
				// sunValueArray[i][0] += 1;
				// Log.d("TAG>>>>>>>", String.valueOf(sunValueArray[i][j]));

			}
		}

	}

	private void createDataList() {
		int count = currentBomb;
		int row, column;
		while (count > 0) {
			row = random.nextInt(LENGTH);
			column = random.nextInt(LENGTH);
			if (row != 0 && column != 0) {
				if (sunLogicArray[row][column] == false) {
					sunLogicArray[row][column] = true;
					count--;
				}
			}
		}
	}

	/**
	 * @param level
	 */
	private final void createValueOfDataList() {

		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				if (sunLogicArray[i][j] != true && i != 0 && j != 0) {
					int value = random.nextInt(MAX_VALUE);
					if (value == 0)
						value++;
					sunValueArray[i][j] = value;

				}
			}
		}

	}

	public void test() {
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				Log.d("TEST", String.valueOf(sunLogicArray[i][j]));
			}
		}

		Log.d("SCORES>>>>>", String.valueOf(totalScoreInGame()));
	}

}
