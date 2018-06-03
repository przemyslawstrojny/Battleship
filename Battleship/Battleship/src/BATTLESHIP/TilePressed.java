package BATTLESHIP;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TilePressed implements ActionListener {
    int rows;
    int columns;
    int[][] field;
    String direction;

    Boolean vertical;
    Boolean horizontal;

    public TilePressed(int rows, int columns) {
        this.rows = rows;
	this.columns = columns;
	this.field = Battleship.getBoatPositions();
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        this.field = Battleship.getBoatPositions();        
	this.vertical = Battleship.vertical;
	this.horizontal = Battleship.horizontal;
                
	for (int row = 0; row < Battleship.gridSize; row++) {
            for (int column = 0; column < Battleship.gridSize; column++) {
		if (field[row][column] != 1 || field[row][column] != 2 || field[row][column] != 3 || field[row][column] != 4) 
                {
                    Battleship.hitsAndMisses[row][column] = field[row][column];
		}
            }
	}
        
        System.out.println("Row: " + rows + "  Column:" + columns);
                
	if (Battleship.gameState == "placeBoats") 
        {
            if (Battleship.whichBoat < Battleship.boatSize.length) 
            {
		placeWholeBoat(event);
            } else {
		Battleship.gameState = "gameStarted";
            }
            
	System.out.println(Battleship.gameState);
        
	} else if (Battleship.gameState == "gameStarted") 
        {
            for (int i = 0; i < BattleshipGUI.button.length; i++) 
            {
                for (int j = 0; j < BattleshipGUI.button[i].length; j++) 
                {
                    BattleshipGUI.button[i][j].setEnabled(false);
		}
            }
	}
    }//metoda

    private Boolean freeSpot() {
	if (Battleship.getBoatPositions()[rows][columns] == 0) 
        {
            return true;
	}
            return false;
	}

    private Boolean validSpot(int boatNumber) 
    {
	try {
            if (vertical) 
            {
		for (int i = 1; i < boatNumber; i++) 
                {
                    if (field[rows + i][columns] != 0) 
                    {
			return false;
                    }
		}
            } else if (horizontal) 
            {
		for (int i = 1; i < boatNumber; i++) 
                {
                    if (field[rows][columns + i] != 0) 
                    {
			return false;
                    }
		}
            }
            return true;
            } catch (ArrayIndexOutOfBoundsException e) 
            {
		return false;
            }
    }

	
    private void placeWholeBoat(ActionEvent event) 
    {
        JButton buttonclicked = (JButton) event.getSource();												
//////////////////////////przygotowanie do rozpoczecia//////////                                                                                                                       
	System.out.println("Boat Size: " + Battleship.boatSize[Battleship.whichBoat]);
	if (Battleship.boatSize[Battleship.whichBoat] == 1 && freeSpot() && validSpot(Battleship.boatSize[Battleship.whichBoat])) 
        {
            Battleship.tempField[rows][columns] = 1;
            buttonclicked.setBackground(Color.RED);
            buttonclicked.setEnabled(false);

            blockSurroundSpace();
            Battleship.setBoatPositions(field);

            System.out.println("----------Final:");
            for (int i = 0; i < field.length; i++) 
            {
		System.out.println(Arrays.toString(field[i]));
            }
	    System.out.println("----------------");

            Battleship.whichBoat++;
            if (Battleship.gameState == "placeBoats") 
            {
		BattleshipGUI.boatSizeLabel.setText(Integer.toString(Battleship.boatSize[Battleship.whichBoat]));
            } else {
                BattleshipGUI.boatSizeLabel.setText("All boats placed.");
            }
	} else if (Battleship.boatSize[Battleship.whichBoat] == 2 && freeSpot() && validSpot(Battleship.boatSize[Battleship.whichBoat])) 
        {
            try 
            {
		Battleship.tempField[rows][columns] = 2;

		if (vertical) 
                {
                    Battleship.tempField[rows + 1][columns] = 2;
                } else if (horizontal) 
                {
                    Battleship.tempField[rows][columns + 1] = 2;
		}

		blockSurroundSpace();
                Battleship.setBoatPositions(field);

		System.out.println("New:");
		for (int i = 0; i < field.length; i++) 
                {
                    System.out.println(Arrays.toString(field[i]));
		}
		System.out.println("**************");

		buttonclicked.setBackground(Color.RED);
		buttonclicked.setEnabled(false);
		if (vertical) 
                {
                    BattleshipGUI.button[rows + 1][columns].setBackground(Color.RED);
                    BattleshipGUI.button[rows + 1][columns].setEnabled(false);

		} else if (horizontal) 
                {
                    BattleshipGUI.button[rows][columns + 1].setBackground(Color.RED);
                    BattleshipGUI.button[rows][columns + 1].setEnabled(false);
		}
                    Battleship.whichBoat++;
                    if (Battleship.gameState == "placeBoats") 
                    {
                        BattleshipGUI.boatSizeLabel.setText(Integer.toString(Battleship.boatSize[Battleship.whichBoat]));
                    } else {
			BattleshipGUI.boatSizeLabel.setText("All boats placed.");
                    }
            } catch (ArrayIndexOutOfBoundsException e) {}

	} else if (Battleship.boatSize[Battleship.whichBoat] == 3 && freeSpot() && validSpot(Battleship.boatSize[Battleship.whichBoat])) 
        {
            try 
            {
		Battleship.tempField[rows][columns] = 3;

		if (vertical) 
                {
                    Battleship.tempField[rows + 1][columns] = 3;
                    Battleship.tempField[rows + 2][columns] = 3;

                } else if (horizontal) 
                {
                    Battleship.tempField[rows][columns + 1] = 3;
                    Battleship.tempField[rows][columns + 2] = 3;
		}

		blockSurroundSpace();

		Battleship.setBoatPositions(field);

		System.out.println("New:");
		for (int i = 0; i < field.length; i++) 
                {
                    System.out.println(Arrays.toString(field[i]));
		}
                    System.out.println("**************");

		buttonclicked.setBackground(Color.RED);
		buttonclicked.setEnabled(false);
		if (vertical) 
                {
                    BattleshipGUI.button[rows + 1][columns].setBackground(Color.RED);
                    BattleshipGUI.button[rows + 2][columns].setBackground(Color.RED);
                    BattleshipGUI.button[rows + 1][columns].setEnabled(false);
                    BattleshipGUI.button[rows + 2][columns].setEnabled(false);
		} else if (horizontal) 
                {
                    BattleshipGUI.button[rows][columns + 1].setBackground(Color.RED);
                    BattleshipGUI.button[rows][columns + 2].setBackground(Color.RED);
                    BattleshipGUI.button[rows][columns + 1].setEnabled(false);
                    BattleshipGUI.button[rows][columns + 2].setEnabled(false);
		}
		Battleship.whichBoat++;
		if (Battleship.gameState == "placeBoats") 
                {
                    BattleshipGUI.boatSizeLabel.setText(Integer.toString(Battleship.boatSize[Battleship.whichBoat]));
		} else {
                    BattleshipGUI.boatSizeLabel.setText("All boats placed.");
		}
            } catch (ArrayIndexOutOfBoundsException e) {}
	} else if (Battleship.boatSize[Battleship.whichBoat] == 4 && freeSpot() && validSpot(Battleship.boatSize[Battleship.whichBoat])) 
        {
            try 
            {
		Battleship.tempField[rows][columns] = 4;

		if (vertical) 
                {
                    Battleship.tempField[rows + 1][columns] = 4;
                    Battleship.tempField[rows + 2][columns] = 4;
                    Battleship.tempField[rows + 3][columns] = 4;
		} else if (horizontal) 
                {
                    Battleship.tempField[rows][columns + 1] = 4;
                    Battleship.tempField[rows][columns + 2] = 4;
                    Battleship.tempField[rows][columns + 3] = 4;
		}

		blockSurroundSpace();
                Battleship.setBoatPositions(field);

		System.out.println("New:");
		for (int i = 0; i < field.length; i++) 
                {
                    System.out.println(Arrays.toString(field[i]));
		}
		System.out.println("**************");

		buttonclicked.setBackground(Color.RED);
		buttonclicked.setEnabled(false);
		if (vertical) 
                {
                    BattleshipGUI.button[rows + 1][columns].setBackground(Color.RED);
                    BattleshipGUI.button[rows + 2][columns].setBackground(Color.RED);
                    BattleshipGUI.button[rows + 3][columns].setBackground(Color.RED);
                    BattleshipGUI.button[rows + 1][columns].setEnabled(false);
                    BattleshipGUI.button[rows + 2][columns].setEnabled(false);
                    BattleshipGUI.button[rows + 3][columns].setEnabled(false);

		} else if (horizontal) 
                {
                    BattleshipGUI.button[rows][columns + 1].setBackground(Color.RED);
                    BattleshipGUI.button[rows][columns + 2].setBackground(Color.RED);
                    BattleshipGUI.button[rows][columns + 3].setBackground(Color.RED);
                    BattleshipGUI.button[rows][columns + 1].setEnabled(false);
                    BattleshipGUI.button[rows][columns + 2].setEnabled(false);
                    BattleshipGUI.button[rows][columns + 3].setEnabled(false);
		}
		Battleship.whichBoat++;
		BattleshipGUI.boatSizeLabel.setText("All boats placed.");
            } catch (ArrayIndexOutOfBoundsException e) {}
	}
        //jesli wszystkie 10statkow zostalo ustawione, zaczynamy
	if (Battleship.whichBoat >= 10) 
        {
            Battleship.gameState = "gameStarted";
            for (int i = 0; i < BattleshipGUI.button.length; i++) 
            {
		for (int j = 0; j < BattleshipGUI.button[i].length; j++) {
                    BattleshipGUI.button[i][j].setEnabled(false);
		}
            }
	}
                
    }
          
    public void blockSurroundSpace() 
    {
	for (int row = 0; row < Battleship.gridSize; row++) 
        {
            for (int column = 0; column < Battleship.gridSize; column++) 
            {
		if (Battleship.tempField[row][column] == Battleship.boatSize[Battleship.whichBoat]) 
                {
                    field[row][column] = Battleship.boatSize[Battleship.whichBoat];
                    try 
                    {
			if (field[row - 1][column - 1] != Battleship.boatSize[Battleship.whichBoat]) 
                        {
                            field[row - 1][column - 1] = -1;
			}
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try 
                    {
			if (field[row - 1][column + 1] != Battleship.boatSize[Battleship.whichBoat]) 
                        {
                            field[row - 1][column + 1] = -1;
			}
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try 
                    {
			if (field[row - 1][column] != Battleship.boatSize[Battleship.whichBoat]) 
                        {
                            field[row - 1][column] = -1;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try 
                    {
			if (field[row + 1][column] != Battleship.boatSize[Battleship.whichBoat]) 
                        {
                            field[row + 1][column] = -1;
			}
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try 
                    {
			if (field[row + 1][column - 1] != Battleship.boatSize[Battleship.whichBoat]) 
                        {
                            field[row + 1][column - 1] = -1;
                    }
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try 
                    {
			if (field[row + 1][column + 1] != Battleship.boatSize[Battleship.whichBoat]) 
                        {
                            field[row + 1][column + 1] = -1;
			}
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try 
                    {
			if (field[row][column - 1] != Battleship.boatSize[Battleship.whichBoat]) 
                        {
                            field[row][column - 1] = -1;
			}
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try 
                    {
			if (field[row][column + 1] != Battleship.boatSize[Battleship.whichBoat]) 
                        {
                            field[row][column + 1] = -1;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {}
		}
            }
        }
    }

}
