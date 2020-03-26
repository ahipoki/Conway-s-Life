/*
 *This program is an implementation of Conway's Life
 *
 *Author: Finn McKinley
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Life implements MouseListener, ActionListener, Runnable 
{
	//Variables and objects
	boolean[][] cells = new boolean[10][10];
	JFrame frame = new JFrame("Life Simulation");
	LifePanel panel = new LifePanel(cells);
	Container south = new Container();
	JButton step = new JButton("Step");
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	boolean running = false;

	//Constructor
	public Life() 
	{
		frame.setSize(600,600);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		panel.addMouseListener(this);
		//south container
		south.setLayout(new GridLayout(1,3));
		south.add(step);
		step.addActionListener(this);
		south.add(start);
		start.addActionListener(this);
		south.add(stop);
		stop.addActionListener(this);
		frame.add(south, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		new Life();
	}

	@Override
	public void mouseClicked(MouseEvent event) 
	{
	}

	@Override
	public void mousePressed(MouseEvent event) 
	{
	}

	@Override
	public void mouseReleased(MouseEvent event) 
	{//When the mouse is released
		System.out.println(event.getX() + "," + event.getY());
		//Get the X and Y coordinates
		double width = (double)panel.getWidth() / cells[0].length;
		//Fill the width of the squares
		double height = (double)panel.getHeight() / cells.length;
		//Fill the height of the squares
		int column = Math.min(cells[0].length - 1, (int)(event.getX() / width));
		//Columns of squares
		int row = Math.min(cells.length - 1, (int)(event.getY() / height));
		//Rows of squares
		System.out.println(column + "," + row);
		//Print out the rows and columns
		cells[row][column] = !cells[row][column];
		frame.repaint();
		//Repaint the screen
	}

	@Override
	public void mouseEntered(MouseEvent event) 
	{
	}

	@Override
	public void mouseExited(MouseEvent event) 
	{
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{//When a button is pressed
		if (event.getSource().equals(step)) 
		{//If the step button was pressed
			step();
			//Run the step method
		}
		if (event.getSource().equals(start)) 
		{//If the start button was pressed
			System.out.println("Start");
			//Output start
			if (running == false) {//If it's not running
				running = true;
				//Set running to true
				Thread t = new Thread(this);
				//Set up a thread to run this with the step
				t.start();
				//Start the thread
			}
		}
		if (event.getSource().equals(stop)) 
		{//If the stop buttong was pressed
			System.out.println("Stop");
			//Print out stop
			running = false;
			//Set running to false
		}
	}
	
	@Override
	public void run() 
	{//Run method
		while (running == true) 
		{//While the program is running
			step();
			//Step method
			try {//In case it fails
				Thread.sleep(500);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/*
	 * row-1,column-1	row-1,column	row-1,column+1
	 * row,column-1 	row,column		row,column+1
	 * row+1,column-1	row+1,column	row+1,column+1
	 */
	public void step() 
	{//Step method
		boolean[][] nextCells = new boolean[cells.length][cells[0].length];
		for (int row = 0; row < cells.length; row++) 
		{
			for (int column = 0; column < cells[0].length; column++) 
			{
				int neighborCount = 0;
				if (row > 0 && column > 0 && cells[row-1][column-1] == true) 
				{//up left
					neighborCount++;
				}
				if (row > 0 && cells[row-1][column] == true) 
				{//up
					neighborCount++;
				}
				if(row > 0 && column < cells[0].length-1 && cells[row-1][column+1] == true)
				{//
					neighborCount++;
				}
				if (column > 0 && cells[row][column-1] == true) 
				{//left
					neighborCount++;
				}
				if (cells[row][column] == true)
				{
					neighborCount++;
				}
				if (column < cells[0].length-1 && cells[row][column+1] == true) 
				{//right
					neighborCount++;
				}
				if (row < cells[0].length-1 && column > 0 && cells[row+1][column-1] == true) 
				{//down left
					neighborCount++;
				}
				if (row < cells[0].length-1 && cells[row+1][column] == true) 
				{//down
					neighborCount++;
				}
				if (row < cells.length-1 && column < cells[0].length-1 && cells[row+1][column+1] == true) 
				{
					neighborCount++;
				}
				//Rules of life
				if (cells[row][column] == true) 
				{//I'm alive!
					if (neighborCount == 2 || neighborCount == 3) 
					{
						nextCells[row][column] = true; //alive next time
					}
					else 
					{
						nextCells[row][column] = false; //dead next time
					}
				}
				else
				{//I'm dead right now
					if (neighborCount == 3) 
					{
						nextCells[row][column] = true; //alive next time
					}
					else 
					{
						nextCells[row][column] = false; //dead next time
					}
				}
			}
		}
		cells = nextCells;
		panel.setCells(nextCells);
		frame.repaint();
	}
}
