package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import models.Board;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class GUI {
	private static JFrame frame;

	public GUI() {
		frame = new JFrame("Connect 4");
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(frame.getSize());
		frame.add(new MultiDraw(frame.getSize()));
		frame.pack();
		frame.setVisible(true);
	}

	public static class MultiDraw extends JPanel implements MouseListener {
		int startX = 10;
		int startY = 10;
		int cellWidth = 40;
		int turn = 2;
		int rows = 6;
		int cols = 7;
		boolean gameOver = false;
		int win = 0;
		Color[][] grid = new Color[rows][cols];

		public MultiDraw(Dimension dimension) {
			setSize(dimension);
			setPreferredSize(dimension);
			addMouseListener(this);

			int x = 0;
			for (int row = 0; row < grid.length; row++) {
				for (int col = 0; col < grid[0].length; col++) {
					grid[row][col] = new Color(255, 255, 255);
				}
			}

		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, d.width, d.height);
			startX = 0;
			startY = 0;

			for (int row = 0; row < grid.length; row++) {
				for (int col = 0; col < grid[0].length; col++) {
					g2.setColor(grid[row][col]);
					g2.fillOval(startX, startY, cellWidth, cellWidth);
					startX += cellWidth;
				}
				startX = 0;
				startY += cellWidth;
			}

			g2.setColor(new Color(255, 255, 255));
			if (turn % 2 == 0) {
				if(gameOver) {
					g2.drawString("Game Over Yellow wins", 400, 20);
					frame.setVisible(false);
					frame.disable();
				}else {
					g2.drawString("Red's Turn", 400, 20);					
				}
			} else {
				if(gameOver) {
					g2.drawString("Game Over Red wins", 400, 20);
					frame.setVisible(false);
					frame.disable();
				}else {
					g2.drawString("Yellows Turn", 400, 20);					
				}
			}

		}

		public boolean isGameOver() {
			return gameOver;
		}
		
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int xSpot = x / cellWidth;
			int ySpot = y / cellWidth;
			ySpot = testForOpenShot(xSpot);

			if (turn % 2 == 0) {
				grid[ySpot][xSpot] = new Color(255, 0, 0);
			} else {
				grid[ySpot][xSpot] = new Color(255, 255, 0);
			}

			if (turn % 2 == 0) {
				gameOver = HorizontalWinCheck(ySpot, xSpot, new Color(255, 0, 0));
				gameOver = VerticalWinCheck(ySpot, xSpot, new Color(255, 0, 0));
				gameOver = DiagonalWinCheck(ySpot, xSpot, new Color(255, 0, 0));
			} else {
				gameOver = HorizontalWinCheck(ySpot, xSpot, new Color(255, 255, 0));
				gameOver = VerticalWinCheck(ySpot, xSpot, new Color(255, 255, 0));
				gameOver = DiagonalWinCheck(ySpot, xSpot, new Color(255, 255, 0));
			}
			turn++;
			repaint();
		}

		public int testForOpenShot(int xSpot) {
			int ySpot = rows - 1;
			while (!(grid[ySpot][xSpot].equals(new Color(255, 255, 255)) || ySpot < 0)) {
				ySpot--;
			}
			return ySpot;
		}

		private boolean HorizontalWinCheck(int row, int col, Color color) {
			win = 1;
			// Horizontal Right Win Condition
			for (int i = col + 1; i < 7; i++) {
				if (grid[row][i].equals(color)) {
					win += 1;
				}else {
					break;
				}
			}

			// Horizontal Left Win Condition
			for (int i = col - 1; i >= 0; i--) {
				if (grid[row][i].equals(color)) {
					win += 1;
				}else {
					break;
				}
			}

			if (win == 4) {
				gameOver = true;
			}
			return gameOver;
		}

		private boolean VerticalWinCheck(int row, int col, Color color) {
			int win = 0;
			
			for(int i = row; i < 6; i++) {
				if (grid[i][col].equals(color)) {
					win += 1;
				} else {
					break;
				}
			}
			
			if (win == 4) {
				gameOver = true;
			}
			return gameOver;
		}
		
		private boolean DiagonalWinCheck(int row, int col, Color color) {
			int win = 1;
			int j = col + 1;
			
			// Up-Right
			for(int i = row - 1; i >= 0; i--) {
				if(j > 7 - 1) {
					break;
				}
				
				if (grid[i][j].equals(color)) {
					win += 1;
					j++;
				} else {
					break;
				}
			}
			
			// Down-Left
			j = col - 1;
			for(int i = row + 1; i < 6; i++) {
				if(j < 0) {
					break;
				}
				
				if (grid[i][j].equals(color)) {
					win += 1;
					j--;
				} else {
					break;
				}
			}

			if (win == 4) {
				gameOver = true;
			}
			
			// Up-Left
			win = 1;
			j = col - 1;
			for(int i = row - 1; i >= 0; i--) {
				if(j < 0) {
					break;
				}
				
				if (grid[i][j].equals(color)) {
					win += 1;
					j--;
				} else {
					break;
				}
			}
			
			// Down-Right
			j = col + 1;
			for(int i = row + 1; i < 6; i++) {
				if(j > 7 - 1) {
					break;
				}
				
				if (grid[i][j].equals(color)) {
					win += 1;
					j++;
				} else {
					break;
				}
			}
			
			if (win == 4) {
				gameOver = true;
			}
			return gameOver;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
}