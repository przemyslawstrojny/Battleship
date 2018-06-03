package BATTLESHIP;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class Battleship {

    static int gridSize = 10;
    static int[][] boatPositions = new int[gridSize][gridSize];
    static int[][] enemyBoatPositions = new int[gridSize][gridSize];
    public static int[][] hitsAndMisses = new int[gridSize][gridSize];
    public static int[][] enemyHitsAndMisses = new int[gridSize][gridSize];

    public static int whichBoat; //do zliczania do 20stu statkow
    public static int[] boatSize = { 1, 1, 1, 1, 2, 2, 2, 3, 3, 4 };
    public static int currentBoatProgress;

    public static Boolean vertical = false;
    public static Boolean horizontal = true;
    
    

    public static int[][] tempField = new int[gridSize][gridSize];
    public static String gameState = "placeBoats";

    public static int enemysBoatsSunk; 
    public static int playersBoatsSunk; 

    public static Boolean randomShot = true;
    public static int x=0;
    public static int x2=0;
    public static int[][] tempField2 = new int[4][2]; 
    public static int fate=0;
    public static int count=0;

    public static BattleshipGUI game;
    public static HitAndMissBoard game2;

    public static void main(String[] args) { 
        resetGame();
    }
    

    public static void resetGame() {
        try {
            game.dispose(); game=null;
            game2.dispose(); game2=null;
	} catch (NullPointerException e) {}
        for (int rows = 0; rows < gridSize; rows++) 
        {
            for (int columns = 0; columns < gridSize; columns++) 
            {
                try{
                    boatPositions[rows][columns] = 0;
                } catch (ArrayIndexOutOfBoundsException e) {}
                try{
                    enemyBoatPositions[rows][columns] = 0;
                } catch (ArrayIndexOutOfBoundsException e) {}
                try {
                    hitsAndMisses[rows][columns] = 0;
                } catch (ArrayIndexOutOfBoundsException e) {}
                try {
                    enemyHitsAndMisses[rows][columns] = 0;
                } catch (ArrayIndexOutOfBoundsException e) {}
		try {
                    tempField[rows][columns] = 0;
		} catch (ArrayIndexOutOfBoundsException e) {}
                try {
                    tempField2[rows][columns] = 0;
		} catch (ArrayIndexOutOfBoundsException e) {}

            }
	}
	gameState = "placeBoats";
	whichBoat = 0; 
        Battleship.enemysBoatsSunk=0;
        Battleship.playersBoatsSunk=0;
            
        game = new BattleshipGUI(10,600,500);
        game2 = new HitAndMissBoard(10,600,500);
        setSampleEnemyBoatBoard();
    }

    public static int[][] getBoatPositions() {
	return boatPositions;
    }


    public static void setBoatPositions(int[][] updateBoats) {
	boatPositions = updateBoats;
    }


    public static int[][] getEnemyBoatPositions() {
	return enemyBoatPositions;
    }


    public static void setEnemyBoatPositions(int[][] updateBoats) {
        enemyBoatPositions = updateBoats;
    }

    private static void setSampleEnemyBoatBoard() {
	Random rand = new Random();
	int board = rand.nextInt(3);
	if (board == 0) { 
            int[] array0 = { -1, -1, -1, 0, 0, 0, 0, 0, 0, 0 };
            enemyBoatPositions[0] = array0;
            int[] array1 = { 2, 2, -1, 0, 0, 0, 0, 0, 0, 0 };
            enemyBoatPositions[1] = array1;
            int[] array2 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[2] = array2;
            int[] array3 = { 4, 4, 4, 4, -1, -1, 3, 3, 3, -1 };
            enemyBoatPositions[3] = array3;
            int[] array4 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[4] = array4;
            int[] array5 = { -1, 3, 3, 3, -1, -1, 2, -1, -1, -1 };
            enemyBoatPositions[5] = array5;
            int[] array6 = { -1, -1, -1, -1, -1, -1, 2, -1, 2, 2 };
            enemyBoatPositions[6] = array6;
            int[] array7 = { 0, 0, 0, 0, 0, -1, -1, -1, -1, -1 };
            enemyBoatPositions[7] = array7;
            int[] array8 = { -1, -1, -1, -1, -1, -1, -1, -1, 0, 0 };
            enemyBoatPositions[8] = array8;
            int[] array9 = { 1, -1, 1, -1, 1, -1, 1, -1, 0, 0 };
            enemyBoatPositions[9] = array9;
        } else if (board == 1) {
            int[] array0 = { 1, -1, 1, -1, 1, -1, 1, -1, 0, 0 };
            enemyBoatPositions[0] = array0;
            int[] array1 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[1] = array1;
            int[] array2 = { 0, -1, 2, 2, -1, -1, -1, -1, 2, 2 };
            enemyBoatPositions[2] = array2;
            int[] array3 = { -1, -1, -1, -1, -1, 2, 2, -1, -1, -1 };
            enemyBoatPositions[3] = array3;
            int[] array4 = { 3, 3, 3, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[4] = array4;
            int[] array5 = { -1, -1, -1, -1, 0, -1, 3, 3, 3, -1 };
            enemyBoatPositions[5] = array5;
            int[] array6 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[6] = array6;
            int[] array7 = { -1, 4, 4, 4, 4, -1, 0, 0, 0, 0 };
            enemyBoatPositions[7] = array7;
            int[] array8 = { -1, -1, -1, -1, -1, -1, 0, 0, 0, 0 };
            enemyBoatPositions[8] = array8;
            int[] array9 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            enemyBoatPositions[9] = array9;
        } else {
            int[] array0 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[0] = array0;
            int[] array1 = { -1, 1, -1, -1, 2, 2, -1, -1, 1, -1 };
            enemyBoatPositions[1] = array1;
            int[] array2 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[2] = array2;
            int[] array3 = { -1, 3, -1, 3, 3, 3, -1, -1, -1, -1 };
            enemyBoatPositions[3] = array3;
            int[] array4 = { -1, 3, -1, -1, -1, -1, -1, -1, 2, -1 };
            enemyBoatPositions[4] = array4;
            int[] array5 = { -1, 3, -1, 4, 4, 4, 4, -1, 2, -1 };
            enemyBoatPositions[5] = array5;
            int[] array6 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[6] = array6;
            int[] array7 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[7] = array7;
            int[] array8 = { -1, 1, -1, -1, 2, 2, -1, -1, 1, -1 };
            enemyBoatPositions[8] = array8;
            int[] array9 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            enemyBoatPositions[9] = array9;
        }
    }//metoda
}
