package BATTLESHIP;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class HitAndMissController implements ActionListener {

    int rows;
    int columns;
    int[][] field;
    int[][] enemyField;
    JButton buttonclicked;

    public HitAndMissController(int rows, int columns) {
	this.rows = rows;
	this.columns = columns;
	this.field = Battleship.getEnemyBoatPositions();
	this.enemyField = Battleship.getBoatPositions();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        buttonclicked = (JButton) event.getSource();// just checking when button is clicked?
        buttonclicked.setEnabled(false);
        this.field = Battleship.getEnemyBoatPositions();
        this.enemyField = Battleship.getBoatPositions();
                               
        if (field[rows][columns] == 1 || field[rows][columns] == 2 || field[rows][columns] == 3 || field[rows][columns] == 4) 
        {
            System.out.println("HIT");
            buttonclicked.setBackground(Color.RED);
            Battleship.hitsAndMisses[rows][columns] = field[rows][columns];
            Battleship.enemysBoatsSunk++;
            HitAndMissBoard.enemyHealthPercent.setText(Integer.toString(100 - Battleship.enemysBoatsSunk * 100 / 20) + "%");            
        } else {
            System.out.println("MISS");
            buttonclicked.setBackground(Color.BLUE);
            Battleship.hitsAndMisses[rows][columns] = field[rows][columns];
        }               
        checkShinkObject();
	checkWin();
	computerTurn();
    }
        
    private void checkWin() 
    {
	if (Battleship.gameState != "gameFinished") 
        {
            System.out.println("Enemy boats fired: " + Battleship.playersBoatsSunk);

            if (Battleship.enemysBoatsSunk == 20) 
            {
		System.out.println("You win!");
		Battleship.gameState = "gameFinished";
		JOptionPane.showMessageDialog(null, "All ships sunk, you win!");
            }
            
            if (Battleship.playersBoatsSunk == 20) 
            {
		System.out.println("You lose!");
		Battleship.gameState = "gameFinished";
		JOptionPane.showMessageDialog(null, "Computer sunk all ships, you lose!");
            }

	} else if (Battleship.gameState == "gameFinished") 
        {
            for (int i = 0; i < BattleshipGUI.button.length; i++) 
            {
		for (int j = 0; j < BattleshipGUI.button[i].length; j++) 
                {
                    HitAndMissBoard.button[i][j].setEnabled(false);
		}
            }
	}               
    }

        
    private void computerTurn() 
    {     
	Boolean shotFired = false;
	Random randRow = new Random();
	Random randColumn = new Random();
	int row=-1;
	int column=-1;
        if (Battleship.count > 100) {clearTemp();}
        if (Battleship.tempField2[3][0]!=0 || Battleship.tempField2[3][1]!=0) clearTemp();
        if (Battleship.x>3) {clearTemp();}
        if (Battleship.x2>3) {clearTemp();}
              
        while (!shotFired) 
        {
            if (!Battleship.randomShot)
            {
                try
                {
                    if (Battleship.fate == 0){row = Battleship.tempField2[Battleship.x2][0]-1; column = Battleship.tempField2[Battleship.x2][1];}
                    if (Battleship.fate == 1){row = Battleship.tempField2[Battleship.x2][0]+1; column = Battleship.tempField2[Battleship.x2][1];}
                    if (Battleship.fate == 2){row = Battleship.tempField2[Battleship.x2][0]; column = Battleship.tempField2[Battleship.x2][1]-1;}
                    if (Battleship.fate == 3){row = Battleship.tempField2[Battleship.x2][0]; column = Battleship.tempField2[Battleship.x2][1]+1; Battleship.x2++;}
                } catch (ArrayIndexOutOfBoundsException e) {}
                Battleship.fate++;
                if (Battleship.fate>=4) Battleship.fate=0;                          
            } else {
                System.out.println("random");
                row = randRow.nextInt(9 - 0 + 1) + 0;
                column = randColumn.nextInt(9 - 0 + 1) + 0;
            }
                        
            System.out.println("Random row: " + row);
            System.out.println("Random column: " + column);
                        
            try
            {
		if (Battleship.enemyHitsAndMisses[row][column] == 0) 
                { //dzialmy jesli jeszcze tutaj nie strzelalismy
                    if (enemyField[row][column] == 1 || enemyField[row][column] == 2 || enemyField[row][column] == 3 || enemyField[row][column] == 4) 
                    {
                        System.out.println("Enemy hit: Row: " + row + " Column: " + column);
                        Battleship.enemyHitsAndMisses[row][column] = enemyField[row][column];
                        BattleshipGUI.button[row][column].setBackground(new Color(110, 25, 28));
                        BattleshipGUI.button[row][column].setText("X");

                        Battleship.fate=0;
                        Battleship.tempField2[Battleship.x][0]=row;
                        Battleship.tempField2[Battleship.x][1]=column;
                        Battleship.x++;

                        Battleship.playersBoatsSunk++;
                        HitAndMissBoard.yourHealthPercent.setText(Integer.toString(100 - Battleship.playersBoatsSunk * 100 / 20) + "%");
                    } else {
                        System.out.println("Enemy miss: Row: " + row + " Column: " + column);
                        Battleship.enemyHitsAndMisses[row][column] = -1;
                        BattleshipGUI.button[row][column].setBackground(Color.BLUE);
                        BattleshipGUI.button[row][column].setText("X");
                    }
                    shotFired = true;
		} else {
                    System.out.println("Already fired here.");
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
                      
	} 
        System.out.println("-KOMPUTER; pola");
	for (int i = 0; i < Battleship.tempField2.length; i++) 
        {
            System.out.println(Arrays.toString(Battleship.tempField2[i]));
	}
            System.out.println("x "+Battleship.x);
            System.out.println("x2 "+Battleship.x2);
            System.out.println("fate "+Battleship.fate);
            
            checkShinkObject2(row,column);
            checkWin();
            Battleship.count++;
    }//metoda

    public void checkShinkObject(){
        System.out.println("FIELD:"+field[rows][columns]);
        //jesli jedynka
        if (field[rows][columns]==1)
        {
            System.out.println("Zatopiona jedynka");  
            for (int i=-1; i<2; i++)
                {
                    for (int j=-1; j<2; j++)
                    {
                        if(i !=0 || j!=0){ //aby nie nadpisywac kliknietego
                            try
                            {
                                    HitAndMissBoard.button[rows-i][columns-j].setEnabled(false);
                                    HitAndMissBoard.button[rows-i][columns-j].setBackground(Color.BLUE);

                            } catch (ArrayIndexOutOfBoundsException e) {}
                        }
                        
                    }
                } 
                return;
            } 
        //przekatne
        if (field[rows][columns]==2 || field[rows][columns]==3 || field[rows][columns]==4)
            {
                //jesli jeszcze nie zatopiony
                for (int i=-1; i<2; i+=2){
                    for (int j=-1; j<2; j+=2){
                        try
                        {
                            HitAndMissBoard.button[rows-i][columns-j].setEnabled(false);
                            HitAndMissBoard.button[rows-i][columns-j].setBackground(Color.BLUE);
                        } catch (ArrayIndexOutOfBoundsException e) {}
                    }
                }
            }
        if (field[rows][columns]==4)
        {
            //jesli zatopiony
            for (int i=0; i<4;i++){ 
                try
                {       //poziomo
                    if (Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns-i] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns+1-i] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns+2-i] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns+3-i])
                    {   
                        try
                        {                           
                            HitAndMissBoard.button[rows][columns+4-i].setEnabled(false);
                            HitAndMissBoard.button[rows][columns+4-i].setBackground(Color.BLUE);
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            HitAndMissBoard.button[rows][columns-1-i].setEnabled(false);
                            HitAndMissBoard.button[rows][columns-1-i].setBackground(Color.BLUE);
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    } 
                        //pionow
                    if (Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows-i][columns] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows+1-i][columns] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows+2-i][columns] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows+3-i][columns])
                    {   
                        try
                        {                           
                            HitAndMissBoard.button[rows+4-i][columns].setEnabled(false);
                            HitAndMissBoard.button[rows+4-i][columns].setBackground(Color.BLUE);
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            HitAndMissBoard.button[rows-1-i][columns].setEnabled(false);
                            HitAndMissBoard.button[rows-1-i][columns].setBackground(Color.BLUE);
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    }                             
                } catch (ArrayIndexOutOfBoundsException e) {}
            }//petla sprawdzajaca
        }//if==4 
        //jesli==3
        if (field[rows][columns]==3)
        {
            //jesli zatopiony
            for (int i=0; i<3;i++){ 
                try
                {       //poziomo
                    if (Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns-i] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns+1-i] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns+2-i])
                    {   
                        try
                        {                           
                            HitAndMissBoard.button[rows][columns+3-i].setEnabled(false);
                            HitAndMissBoard.button[rows][columns+3-i].setBackground(Color.BLUE);
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            HitAndMissBoard.button[rows][columns-1-i].setEnabled(false);
                            HitAndMissBoard.button[rows][columns-1-i].setBackground(Color.BLUE);
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    } 
                        //pionow
                    if (Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows-i][columns] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows+1-i][columns] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows+2-i][columns])
                    {   
                        try
                        {                           
                            HitAndMissBoard.button[rows+3-i][columns].setEnabled(false);
                            HitAndMissBoard.button[rows+3-i][columns].setBackground(Color.BLUE);
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            HitAndMissBoard.button[rows-1-i][columns].setEnabled(false);
                            HitAndMissBoard.button[rows-1-i][columns].setBackground(Color.BLUE);
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    }                             
                } catch (ArrayIndexOutOfBoundsException e) {}
            }//petla sprawdzajaca
        }//if==3
        //jesli==2
        if (field[rows][columns]==2)
        {
            //jesli zatopiony
            for (int i=0; i<3;i++){ 
                try
                {       //poziomo
                    if (Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns-i] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows][columns+1-i])
                    {   
                        try
                        {                           
                            HitAndMissBoard.button[rows][columns+2-i].setEnabled(false);
                            HitAndMissBoard.button[rows][columns+2-i].setBackground(Color.BLUE);
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            HitAndMissBoard.button[rows][columns-1-i].setEnabled(false);
                            HitAndMissBoard.button[rows][columns-1-i].setBackground(Color.BLUE);
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    } 
                        //pionow
                    if (Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows-i][columns] && 
                        Battleship.hitsAndMisses[rows][columns]==Battleship.hitsAndMisses[rows+1-i][columns])
                    {   
                        try
                        {                           
                            HitAndMissBoard.button[rows+2-i][columns].setEnabled(false);
                            HitAndMissBoard.button[rows+2-i][columns].setBackground(Color.BLUE);
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            HitAndMissBoard.button[rows-1-i][columns].setEnabled(false);
                            HitAndMissBoard.button[rows-1-i][columns].setBackground(Color.BLUE);
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    }                             
                } catch (ArrayIndexOutOfBoundsException e) {}
            }//petla sprawdzajaca
        }//if==2
        
    }//metoda
    
    public void clearTemp(){
        Battleship.count=0;
        Battleship.randomShot=true;
        Battleship.fate=0;
        Battleship.x2=0;
        Battleship.x=0;
        for (int i=0; i<4; i++)
        {
            for (int j=0;j<2;j++){
                Battleship.tempField2[i][j]=0;
            }
            
        }
        
    }
    
    public void checkShinkObject2(int row, int column){
            System.out.println("enemyField:"+enemyField[row][column]);
            //jesli jedynka
            if (enemyField[row][column]==1)
            {
                clearTemp();
                System.out.println("Zatopiona jedynka");  
                for (int i=-1; i<2; i++)
                {
                    for (int j=-1; j<2; j++)
                    {
                        if(i !=0 || j!=0){ //aby nie nadpisywac kliknietego
                            try
                            {
                                    BattleshipGUI.button[row-i][column-j].setEnabled(false);
                                    BattleshipGUI.button[row-i][column-j].setBackground(Color.BLUE);
                                    Battleship.enemyHitsAndMisses[row-i][column-j] = -1;                                  
                            } catch (ArrayIndexOutOfBoundsException e) {}
                        }
                        
                    }
                } 
                return;
            } 
            //przekatne
            if (enemyField[row][column]==2 || enemyField[row][column]==3 || enemyField[row][column]==4)
            {
                Battleship.randomShot=false;
                System.out.println(Battleship.randomShot);
                
                //jesli jeszcze nie zatopiony
                for (int i=-1; i<2; i+=2){
                    for (int j=-1; j<2; j+=2){
                        try
                        {
                            BattleshipGUI.button[row-i][column-j].setEnabled(false);
                            BattleshipGUI.button[row-i][column-j].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row-i][column-j] = -1;
                        } catch (ArrayIndexOutOfBoundsException e) {}
                    }
                }
            }
        if (enemyField[row][column]==4)
        {
            //jesli zatopiony
            for (int i=0; i<4;i++){ 
                try
                {       //poziomo
                    if (Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column-i] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column+1-i] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column+2-i] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column+3-i])
                    {   
                        clearTemp();
                        try
                        {                           
                            BattleshipGUI.button[row][column+4-i].setEnabled(false);
                            BattleshipGUI.button[row][column+4-i].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row][column+4-i] = -1;
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            BattleshipGUI.button[row][column-1-i].setEnabled(false);
                            BattleshipGUI.button[row][column-1-i].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row][column-1-i] = -1;
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    } 
                        //pionow
                    if (Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row-i][column] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row+1-i][column] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row+2-i][column] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row+3-i][column])
                    {   
                       clearTemp();
                        try
                        {                           
                            BattleshipGUI.button[row+4-i][column].setEnabled(false);
                            BattleshipGUI.button[row+4-i][column].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row+4-i][column] = -1;
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            BattleshipGUI.button[row-1-i][column].setEnabled(false);
                            BattleshipGUI.button[row-1-i][column].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row-1-i][column] = -1;
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    }                             
                } catch (ArrayIndexOutOfBoundsException e) {}
            }//petla sprawdzajaca
        }//if==4 
        //jesli==3
        if (enemyField[row][column]==3)
        {
            //jesli zatopiony
            for (int i=0; i<3;i++){ 
                try
                {       //poziomo
                    if (Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column-i] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column+1-i] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column+2-i])
                    {   
                       clearTemp();
                        try
                        {                           
                            BattleshipGUI.button[row][column+3-i].setEnabled(false);
                            BattleshipGUI.button[row][column+3-i].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row+3-i][column] = -1;
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            BattleshipGUI.button[row][column-1-i].setEnabled(false);
                            BattleshipGUI.button[row][column-1-i].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row][column-1-i] = -1;
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    } 
                        //pionow
                    if (Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row-i][column] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row+1-i][column] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row+2-i][column])
                    {   
                        clearTemp();
                        try
                        {                           
                            BattleshipGUI.button[row+3-i][column].setEnabled(false);
                            BattleshipGUI.button[row+3-i][column].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row+3-i][column] = -1;
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            BattleshipGUI.button[row-1-i][column].setEnabled(false);
                            BattleshipGUI.button[row-1-i][column].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row-1-i][column] = -1;
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    }                             
                } catch (ArrayIndexOutOfBoundsException e) {}
            }//petla sprawdzajaca
        }//if==3
        //jesli==2
        if (enemyField[row][column]==2)
        {
            //jesli zatopiony
            for (int i=0; i<3;i++){ 
                try
                {       //poziomo
                    if (Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column-i] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row][column+1-i])
                    {   
                        clearTemp();
                        try
                        {                           
                            BattleshipGUI.button[row][column+2-i].setEnabled(false);
                            BattleshipGUI.button[row][column+2-i].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row][column+2-i] = -1;
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            BattleshipGUI.button[row][column-1-i].setEnabled(false);
                            BattleshipGUI.button[row][column-1-i].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row][column-1-i] = -1;
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    } 
                        //pionow
                    if (Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row-i][column] && 
                        Battleship.enemyHitsAndMisses[row][column]==Battleship.enemyHitsAndMisses[row+1-i][column])
                    {   
                        clearTemp();
                        try
                        {                           
                            BattleshipGUI.button[row+2-i][column].setEnabled(false);
                            BattleshipGUI.button[row+2-i][column].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row+2-i][column] = -1;
                            
                        } catch (ArrayIndexOutOfBoundsException e) {}   
                        
                        try
                        {
                            BattleshipGUI.button[row-1-i][column].setEnabled(false);
                            BattleshipGUI.button[row-1-i][column].setBackground(Color.BLUE);
                            Battleship.enemyHitsAndMisses[row-1-i][column] = -1;
                        }catch (ArrayIndexOutOfBoundsException e) {}
                    }                             
                } catch (ArrayIndexOutOfBoundsException e) {}
            }//petla sprawdzajaca
        }//if==2
        
    }//metoda
}
