package game;

import java.awt.Color;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Board board;
	
	public GameFrame(){
		setTitle("TicTacToe");
        setSize(380, 580);
        setResizable(false);       
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        board = new Board();
        add(board);
        setBackground(Color.WHITE);
        revalidate();
    	repaint();
	}

	Board getBoard(){
		return board;
	}
}
