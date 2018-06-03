package BATTLESHIP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BattleshipGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    JPanel panel = new JPanel();
    JPanel buttonPanel;
    JPanel directionPanel;
    JPanel progressPanel;
    JPanel directionPanel2;

    GridBagConstraints gbc;

    public static JLabel boatSizeLabel;
    public static JButton button[][] = new JButton[10][10];
    public static JButton vertical;
    public static JButton horizontal;
    public static JButton restart;
    
    static int rows;
    static int columns;
    static int gridSize;  
        
	
    public BattleshipGUI(int gridSize, int height, int width) {
        this.gridSize = gridSize;
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setPreferredSize(new Dimension(width, height));
	setVisible(true);
	setTitle("Battleship - Plansza gracza");
	setResizable(false); //zmiana rozmiaru okna

	panel = new JPanel();
	panel.setLayout(new GridBagLayout()); 

	buttonPanel = new JPanel();
	buttonPanel.setLayout(new GridLayout(10, 10));
	
	//tworzenie pola, kostka 100x100
	for (rows = 0; rows < gridSize; rows++) {
            for (columns = 0; columns < gridSize; columns++) {
		button[rows][columns] = new JButton();
		button[rows][columns].setBackground(Color.GRAY); //kolor kafelka
		button[rows][columns].setPreferredSize(new Dimension(100, 100));			
		//sluchacz do kafelkow, dodany do kazdego
		button[rows][columns].addActionListener(new TilePressed(rows, columns));
		buttonPanel.add(button[rows][columns]);
            }
	}     
                
        //setting up specific dimension/constraints for the grid layout manager
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
	gbc.weighty = 1.0;
                
	panel.add(buttonPanel, gbc);

	gbc.weighty = 0.05;
	gbc.gridy = 1;

	directionPanel = new JPanel();
	directionPanel.setLayout(new GridLayout());

	horizontal = new JButton("Horizontal");
	horizontal.addActionListener(new gameMenuListener());
	horizontal.setPreferredSize(new Dimension(30, 50));
	horizontal.setEnabled(false);
	directionPanel.add(horizontal);
	vertical = new JButton("Vertical");
	vertical.addActionListener(new gameMenuListener());
	vertical.setPreferredSize(new Dimension(30, 50));
	directionPanel.add(vertical);
	panel.add(directionPanel, gbc);

	gbc.weighty = 0.20;
	gbc.gridy = 2;

	progressPanel = new JPanel();
	progressPanel.setLayout(new GridLayout());

	JLabel textLabel = new JLabel("Currently Placing Boat Sized: ");
	progressPanel.add(textLabel);

	boatSizeLabel = new JLabel(Integer.toString(Battleship.boatSize[Battleship.whichBoat]));
	progressPanel.add(boatSizeLabel);

	panel.add(progressPanel, gbc);

        ////
	gbc.weighty = 0.10;
	gbc.gridy = 3;
               
	directionPanel = new JPanel();
	directionPanel.setLayout(new GridLayout());

	restart = new JButton("Restart");
	restart.addActionListener(new gameMenuListener());
	restart.setPreferredSize(new Dimension(30, 50));
	restart.setEnabled(true);
	directionPanel.add(restart);
                
	panel.add(directionPanel, gbc);
        //////
                
	setContentPane(panel);
	pack();
    }
}

class gameMenuListener implements ActionListener {
    
    public gameMenuListener() {}

    @Override
    public void actionPerformed(ActionEvent event) {
	if (event.getSource() == BattleshipGUI.vertical) { 
            Battleship.vertical = true;
            Battleship.horizontal = false;
            BattleshipGUI.vertical.setEnabled(false);  
            BattleshipGUI.horizontal.setEnabled(true);  
        } else if (event.getSource() == BattleshipGUI.horizontal) { 
            Battleship.vertical = false;
            Battleship.horizontal = true;
            BattleshipGUI.vertical.setEnabled(true); 
            BattleshipGUI.horizontal.setEnabled(false);
        }
        if (event.getSource() == BattleshipGUI.restart){
            Battleship.resetGame();
        }
    }

}
