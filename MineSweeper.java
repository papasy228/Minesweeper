/* 

e * MineSweeper.java 
 * 
 * Version: 
 *     $Id: MineSweeper.java,v 1.1 2012/10/07 23:23:47 pyn5108 Exp pyn5108 $ 
 * 
 * Revisions: 
 *     $Log: MineSweeper.java,v $
 *     Revision 1.1  2012/10/07 23:23:47  pyn5108
 *     Initial revision
 *
 *     Revision 1.4  2012/10/07 23:16:40  pyn5108
 *     final check in before submission
 *
 *     Revision 1.3  2012/10/07 22:32:27  pyn5108
 *     More comments added. tweeked cheat method
 *
 *     Revision 1.2  2012/10/07 21:58:19  pyn5108
 *     more comments added
 *
 *     Revision 1.1  2012/10/07 21:22:12  pyn5108
 *     Initial revision
 * 
 */

/**
 * This program attempts to create a MineSweeper game
 * 
 * @author      Papa Yaw Ntorinkansah
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class MineSweeper implements ActionListener {
	// JFrame used in game
	JFrame j;
	//if true, cell is bomb 
	boolean Bombs[][];
	
	private int row;
	private int column;
	private int currentX = 0;
	//number of cells clicked 
	private int moves = 0;
	// used to determine winning case
	private int magicNumber = 0;
	//total number of bombs. gets set to remaining int parameter
	private static int numBombs = 0;
	//used to reset remaining int parameter 
	private int num = 0;
	//number of bombs remaining
	private int remaining = 0;
	//num of bombs exposed
	private int bmbexposed = 0;
	private int currentY = 0;
	//number of cells exposed
	boolean cellShown[][] ;
	// stores value of cells adjacent to bombs
	int adjCells[][] ;
	JButton mButtons [];
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;
	JButton menubtn1;
	JButton menubtn2;
	JButton menubtn3;
	JButton menubtn4;
	JButton menubtn5;
	JButton rtntoMenu;
	private JToolBar ToolBar = null;
	/**
	 * Constructor for a MineSweeper object.
	 * 
	 * @param       size    size of the gameboard. gets set to row and column
	 * @param       Bombs   position of bombs processed from bombfile.txt
	 * @param       num     total number of bombs
	 * 
	 */
	
	public MineSweeper(int size, boolean Bombs[][] , int num){
		this.row = size;
		this.column = size;
		this.Bombs = Bombs;
		this.num = num;
		this.remaining = num;
		boolean cellShown[][] = new boolean [row][column];
		this.cellShown = cellShown;
		int adjCells[][] = new int [row][column];
		this.adjCells = adjCells;
		
		this.mButtons= new JButton [row * column];
		
		j = new JFrame ("MineSweeper");
		j.setSize(size * 50,size * 30);
		j.setLocation(200,100);
		j.getContentPane().setLayout(new BorderLayout());
		
		menu();
		
		j.setVisible(true);
		j.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	
	}
	/**
	 * Builds game cells. Resets the games Stats.Creates new JPanel
	 * object, loops through button array, creates mouse listener,
	 * adds buttons to JPanel
	 * 
	 * 
	 * 
	 */
	private void gameboard(){
		moves = 0;  
		remaining = num;
		bmbexposed = 0;
		magicNumber = (row * column) - num;
		
		
		
		
			
		JPanel gamebrd = new JPanel(new GridLayout(row,column));
		
		int i = 0;
		for(int x = 0; x < row; x++ )
		{
			for(int y = 0; y < column; y++ )
			{
				currentX = x;
				currentY = y;
							
				mButtons[i] = new JButton("");
				mButtons[i].addMouseListener(new java.awt.event.MouseAdapter(){
					public void mouseReleased(java.awt.event.MouseEvent e) {
						if(e.getModifiers() == InputEvent.BUTTON1_MASK)
						{
							showCell(e);
							
						}
					}
				});
				gamebrd.add(mButtons[i]);
				i++;
			}
		}

		// calculates value of adjcells and sets it
		// also sets all cells to !shown
		for(int x = 0; x < row; x++)
		{
			for(int y = 0; y < column; y++)
			{
				adjCells[x][y] = countBomb(x, y);
				cellShown[x][y] = false; 
			}
		}
		//removes JPannel if any and replaces it with correct one.
		j.getContentPane().removeAll();
	    j.repaint();
		j.validate();
		j.add(createBar(), BorderLayout.NORTH);
		j.add(gamebrd, BorderLayout.CENTER);
		j.repaint();
		j.validate();
		
	}
	/**
	 * Creates a new JToolBar object. This method is called in gameboard() 
	 * toolbar has a button that restarts the game.
	 * 
	 * 
	 */
	private JToolBar createBar(){
		ToolBar = new JToolBar();
		ToolBar.setFloatable(false);
		rtntoMenu = new JButton("Return to Menu");
		rtntoMenu.addActionListener(this);
		ToolBar.add(rtntoMenu);
		
		return ToolBar;
		
	}
	/**
	 * Main menu of game
	 * 
	 * 
	 * 
	 */
	private void menu(){
		
		JPanel pMenu = new JPanel(new GridLayout(5,2));
		
		l1 = new JLabel ("Show Game Board");		
		l2 = new JLabel ("Show Cheat Board");
		l3 = new JLabel ("Display Stats");
		l4 = new JLabel ("Display this Menu");
		l5 = new JLabel ("Exit");
		
	    menubtn1 = new JButton ("B");  
	    menubtn2 = new JButton ("C");   
	    menubtn3 = new JButton ("S");   
        menubtn4 = new JButton ("M");   
	    menubtn5 = new JButton ("E");
	    
	    menubtn1.addActionListener(this);
	    menubtn2.addActionListener(this);
	    menubtn3.addActionListener(this);
	    menubtn4.addActionListener(this);
	    menubtn5.addActionListener(this);
	    
	    pMenu.add(menubtn1);
	    pMenu.add(l1);
	    pMenu.add(menubtn2);
	    pMenu.add(l2);
	    pMenu.add(menubtn3);
	    pMenu.add(l3);
	    pMenu.add(menubtn4);
	    pMenu.add(l4);
	    pMenu.add(menubtn5);
	    pMenu.add(l5);
	    
	    j.getContentPane().removeAll();
	    j.repaint();
		j.validate();
	    j.add(pMenu);
	    j.repaint();
		j.validate();
	    
	}
	/**
	 * sets the cells adjacent to bombs with an integer
	 * integer gets returned as Count
	 * 
	 */
	private int countBomb(int x, int y){
		int Count = 0;

		
		for(int r = -1; r <= 1; r++)
		{
			for(int c = -1; c <= 1; c++)
			{
				int newx = x + r;
				int newy = y + c;
				if(inBounds(newx, newy))
				{
					if(Bombs[newx][newy] == true)
					{
						Count++;
					}
				}
			}
	}
	return Count;
	}
	/**
	 * Displays stats of game
	 * 
	 */
	private void showStats(){
		JOptionPane.showMessageDialog(null,"Number of Game Moves: " + moves + "\n" +
											"Number of Bombs Remaining on the Board: " + remaining + "\n" +
											"Number of Bombs Exposed: " + bmbexposed,"Minesweeper", JOptionPane.INFORMATION_MESSAGE);
		
		
		
	}
	/**
	 * When a button gets clicked, the stats are updated, if cell
	 * is a bomb, it gets disabled and its txt gets set to "X",user is shown prompt
	 * that tells them the number of bombs revealed. If number of bombs exposed is 3,
	 * game is over. if clicked cell isn't a bomb, number of moves gets updated,
	 * if cell value is greater than zero, its integer value gets shown, if it is equal 
	 * to zero, all adjacent cells with values that are zero gets revealed.
	 * 
	 */
	
	private void showCell(java.awt.event.MouseEvent e){
		JButton clkdButton =  (JButton)e.getSource();
		
			if(clkdButton.isEnabled()){
				clkdButton.setEnabled(false);
				if(isBomb(clkdButton))
				{					
					remaining--;
					bmbexposed++;
					moves++;
					clkdButton.setEnabled(false);
					clkdButton.setText("X");
					JOptionPane.showMessageDialog(null,"Number of bombs revealed so far " + bmbexposed ,"Minesweeper", JOptionPane.INFORMATION_MESSAGE);
					if (bmbexposed == 3)
					{
						JOptionPane.showMessageDialog(null,"Game Over!","Minesweeper", JOptionPane.INFORMATION_MESSAGE);
						gameover();
					}
					
				}
			
				
			else{
					moves++;
					if(adjCells[currentX][currentY] > 0){
							clkdButton.setText(Integer.toString(adjCells[currentX][currentY]));
							magicNumber--;
					}else if(adjCells[currentX][currentY] == 0){ 
							
						adjCells(currentX, currentY);
					}
					
					if(magicNumber == 0){
						JOptionPane.showMessageDialog(null,"Congratulations you won!!!!!","Minesweeper", JOptionPane.INFORMATION_MESSAGE);
						gameover();
					}
				}
			}
		}
	/**
	 * When the game is over, the cheatboard and game stats get shown
	 * 
	 * 
	 */		
	
	private void gameover(){
	
		cheatBoard();
		showStats();
	
		
	}
		
	/**
	 * creates a loop and checks if cell is bomb.
	 * returns true if it is a bomb and false otherwise
	 * 
	 */		
	
private boolean isBomb(JButton jButton){
	int i = 0;
	for(int x = 0; x < row; x++)
	{
		for(int y = 0; y < column; y++)
		{
			if(jButton == mButtons[i])
			{
				currentX = x;
				currentY = y;
				
				return Bombs[x][y];
			}
				i++;
			}
		}
		return false;
}
/**
 * returns true if cell is on the boundary of the gameboard.
 * 
 * 
 */
	private boolean inBounds(int x, int y){
		
		return 0 <= x && x < adjCells.length && 0 <= y && y < adjCells[x].length;
	}
	
	/**
	 * Reveals integer value of cell and all adjacent cells with zero values 
	 * 
	 * 
	 */
	private void adjCells(int x, int y){
		
		if (inBounds(x,y))
		{		
			if (!cellShown[x][y] && Bombs[x][y] == false){	
				JButton b = mButtons[(x*row+y)];
				cellShown[x][y] = true;
				
				
			if (adjCells[x][y] > 0){
				b.setText(Integer.toString(adjCells[x][y]));
				magicNumber--;
			}else{
				b.setText("");
				magicNumber--;
			}
			b.setSelected(true);
			b.setEnabled(false);
			
			if (adjCells[x][y] == 0)
			{
			for(int r = -1; r <= 1; r++)
			{
				for(int c = -1; c <= 1; c++)
				{
					adjCells(x + r, y + c);
				}
			}
		}
	}
  }
}
		
	/**
	 * shows cheat board
	 * 
	 * 
	 */
	private void cheatBoard(){
		for(int x = 0; x < row; x++)
		{
			for(int y = 0; y < column; y++)
			{
				if(Bombs[x][y] == true)
				{
					JButton jButton = mButtons[(x*row+y)];
					
					
						jButton.setText("B");
						jButton.setSelected(true);
						jButton.setEnabled(false);
				}else {
					if (Bombs[x][y] == false){
						
						JButton jButton = mButtons[(x*row+y)];
						for(int r = -1; r <= 1; r++)
						{
							for(int c = -1; c <= 1; c++)
							{
								int newx = x + r;
								int newy = y + c;
								if(inBounds(newx, newy))
								{
									jButton.setText(Integer.toString(adjCells[x][y]));
								}
							}
					}
						jButton.setSelected(true);	
						jButton.setEnabled(false);
				}
			}}
		}
	}
	/**
	 * Performs appropriate action when button from menu is pressed.
	 * 
	 * 
	 */
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource().equals(menubtn1)){
			gameboard();
			
		}
	   if(e.getSource().equals(rtntoMenu)){
		   menu();
	   }
		if (e.getSource().equals(menubtn2)){
			gameboard();
			cheatBoard();
		}
		if (e.getSource().equals(menubtn3)){
			showStats();
		}
		if (e.getSource().equals(menubtn5)){
			System.exit(0);
		}
	   
	}
	
	public static void main(String[] args) {
		int num = 0;
		if (args.length != 2) 			//validating user parameters
		{
			System.err.println("Usage: java Minesweeper size of board Bombfile");
			System.exit(1);
		}
		int boardSize = Validate1(args[0]);
		
		boolean  Bombs [][] = new boolean[boardSize][boardSize];
		// after bombfile gets validated, it gets set to Bombs array.
		Bombs = Validate2(args[1],  Bombs, boardSize);
		
		
		//creates an instance of the MineSweeper object
		MineSweeper g = new MineSweeper(boardSize , Bombs , numBombs );

	}
	/**
	   * This method takes user input(args[0]) and tries to convert it to an int.
	   * if it failes, it prints out an error msg and exits. if it succeeds it returns it to 
	   * the main method as board size. if the int is less than six or greater than 30, it gets
	   * set to 6.
	   *
	   * @param    s    this is users requested size of board
	   */
	private static int Validate1(String s) {
		int num = 0;
		try {
			num = Integer.parseInt(s);
		}catch (Exception e)
		{
			System.err.println("The size of board parameter must be an integer");
			System.exit(1);
		}

		if (num < 6 || num > 30)
		{
			num =6;

		}
		return num;

	}
	/**
	   * This method tries to open the bombfile. if it failes, the program exits.
	   * if it succeeds, the first line is read into input. All spaces get removed from input, 
	   * then it is split into a temp array. temp[0] & temp[1] gets converted into x and y. if x & y
	   * are bigger than one less the size of the gameboard, the program exits. if x & y are
	   * within the bounds of the board, they get the value of true. The gameboard b gets 
	   * returned to the main method. So all bomb cells have the value of true and others have the value of false
	   *
	   *
	   * @param    str    bombfile name
	   * @param    b      gameboard array 	
	   * @param    size   user specified boardsize	
	   * @param    input  the line read from the bombfile.
	   * @param    temp   array of string from bombfile
	   */
	private static boolean [][] Validate2(String Filename, boolean b [][], int size){
		String input = "";
		//int numBombs = 0;
		int x = 0;
		int y = 0;
		
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			{
				b[i][j]=false;
			}
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(Filename));
			input = in.readLine();
		
		
		while (input !=null)	 
		{
							
			input = input.replaceAll("\\s","");
			String[] temp = input.split(",");
			try{
				
				x=Integer.parseInt(temp[0]);
				y=Integer.parseInt(temp[1]);
				if( x > (size -1) || y > (size-1))
				{System.err.println("An integer in the bombfile is bigger than the size of the board");
				System.exit(1);}
				//System.out.println(x+ " " +y);	//for testing
				}catch (Exception e)
				{
					System.out.println("Invalid characters in bomb file" + e.getMessage());
					e.printStackTrace();
					System.exit(1);
				}
				
				b[x][y]=true;
				numBombs++;
				input = in.readLine();
			} 
		}
		catch(Exception e){
			System.err.println("The file " + Filename + "cannot be read" + "\n" + e.getMessage());
			System.exit(1);
		}
		
		return b;  
	}

}
