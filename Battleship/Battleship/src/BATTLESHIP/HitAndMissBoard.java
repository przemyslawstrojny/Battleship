package BATTLESHIP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HitAndMissBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    static JPanel panel;
    public static JPanel buttonPanel;
    public static JPanel yourProgressPanel;
    public static JPanel enemyProgressPanel;

    public static JLabel boatSizeLabel;
	
    public static JLabel yourHealthPercent; 
    public static JLabel enemyHealthPercent;

    
	
    static JButton button[][] = new JButton[10][10];
    GridBagConstraints gbc;

    static int rows;
    static int columns;
    static int gridSize;

    public HitAndMissBoard(int gridSize, int height, int width) {
        this.gridSize = gridSize;
        setBounds(500,0, 500, 500); //ustawienie pozycji okna
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
        setTitle("Battleship - Plansza przeciwnika"); 
        setResizable(false); 

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        buttonPanel = new JPanel();

        buttonPanel.setLayout(new GridLayout(10, 10));
        for (rows = 0; rows < gridSize; rows++) 
        {
            for (columns = 0; columns < gridSize; columns++) 
            {
                button[rows][columns] = new JButton();
                button[rows][columns].setBackground(Color.GRAY); 
                button[rows][columns].setPreferredSize(new Dimension(100, 100));
                button[rows][columns].addActionListener(new HitAndMissController(rows, columns)); //action listener linked to controller class
                buttonPanel.add(button[rows][columns]);
            }
        }

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        panel.add(buttonPanel, gbc);

        gbc.weighty = 0.15;
        gbc.gridy = 1;

        yourProgressPanel = new JPanel();
        yourProgressPanel.setLayout(new GridLayout());

        JLabel yourHealthLabel = new JLabel("Twoje statki: ");
        yourProgressPanel.add(yourHealthLabel);

        yourHealthPercent = new JLabel("100%");  
        yourProgressPanel.add(yourHealthPercent);
        panel.add(yourProgressPanel, gbc);

        gbc.weighty = 0.20;
        gbc.gridy = 2;

        enemyProgressPanel = new JPanel();
        enemyProgressPanel.setLayout(new GridLayout());

        JLabel enemyHealthLabel = new JLabel("Statki przeciwnika: ");
        enemyProgressPanel.add(enemyHealthLabel);

        enemyHealthPercent = new JLabel("100%");
        enemyProgressPanel.add(enemyHealthPercent);

        panel.add(enemyProgressPanel, gbc);

        setContentPane(panel);
        pack();
    }
}
