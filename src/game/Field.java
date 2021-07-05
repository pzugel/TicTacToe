package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.HashSet;

public class Field {
	
	static final int SIZE = 96;
	static final Color PLAYER_1_COL_HOVER = new Color(255, 204, 204);
	static final Color PLAYER_2_COL_HOVER = new Color(204, 255, 204);
	
	static final Set<String> firstCol = new HashSet<String>(Arrays.asList("00","01","02"));
	static final Set<String> secondCol = new HashSet<String>(Arrays.asList("10","11","12"));
	static final Set<String> thirdCol = new HashSet<String>(Arrays.asList("20","21","22"));
	
	static final Set<String> firstRow = new HashSet<String>(Arrays.asList("00","10","20"));
	static final Set<String> secondRow = new HashSet<String>(Arrays.asList("01","11","21"));
	static final Set<String> thirdRow = new HashSet<String>(Arrays.asList("02","12","22"));
	
	static final Set<String> firstDiag = new HashSet<String>(Arrays.asList("00","11","22"));
	static final Set<String> secondDiag = new HashSet<String>(Arrays.asList("02","11","20"));
	
	static final List<Set<String>> winningConfigs = Arrays.asList(
			firstRow,secondRow,thirdRow,firstCol,secondCol,thirdCol,firstDiag,secondDiag);

	private JButton btn;
	private GamePiece playedPiece;
	private Board board;
	private boolean hasGamePiece;
	private String config;
	
	Field(int x, int y, JPanel panel){
		board = (Board) panel;
		
		btn = new JButton("");
		btn.setVisible(true);
		btn.setBackground(Color.WHITE);
		int X_POS = 42 + x*100;
		int Y_POS = 42 + y*100;
		btn.setBounds(X_POS, Y_POS, SIZE, SIZE);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		panel.add(btn);
		playedPiece = null;
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				GamePiece p = board.getSelected();
				validateMove(p);			
			}
        });
		hasGamePiece = false;
		config = String.valueOf(x) + String.valueOf(y);
	}
	
	void validateMove(GamePiece p){
		if(p.getPlayer() == board.getCurrentPlayer()){		
			if(playedPiece == null) {
				p.play();
				playedPiece = p;
				btn.setText(String.valueOf(p.getStrength()));
				if(p.getPlayer() == 1) {
					btn.setBackground(PLAYER_1_COL_HOVER);
				} else {
					btn.setBackground(PLAYER_2_COL_HOVER);
				}
				hasGamePiece = true;
				checkWinning();
				checkWinning();
				checkDraw();
				board.nextPlayer();
			} else {
				int playedPieceStrength = playedPiece.getStrength();
				if(playedPieceStrength < p.getStrength()) {
					p.play();
					playedPiece = p;
					btn.setText(String.valueOf(p.getStrength()));
					if(p.getPlayer() == 1) {
						btn.setBackground(PLAYER_1_COL_HOVER);
					} else {
						btn.setBackground(PLAYER_2_COL_HOVER);
					}
					hasGamePiece = true;
					checkWinning();
					checkDraw();
					board.nextPlayer();
				}
			}	
		}
	}
	
	boolean hasGamePiece() {
		return hasGamePiece;
	}
	
	GamePiece getGamePiece() {
		if(hasGamePiece()) {
			return playedPiece;
		}
		return null;
	}
	
	String getConfig() {
		return config;
	}
	
	void setBackground(Color c) {
		btn.setBackground(c);
	}
	
	void checkDraw() {
		if(board.allPiecesPlayed() || board.noMovePossible()) {
			JOptionPane.showMessageDialog(board,
				    "Draw!",
				    "",
				    JOptionPane.INFORMATION_MESSAGE);
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(board);
			topFrame.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));	
		}
	}
	
	void checkWinning() {
		List<Field> fieldList = board.getFields();
		Set<String> playerOne_Configs = new HashSet<String>();
		Set<String> playerTwo_Configs = new HashSet<String>();
		List<Field> pressedFields = new ArrayList<Field>();
		
		for(Field f : fieldList) {
			if(f.hasGamePiece()) {
				pressedFields.add(f);
				int player = f.getGamePiece().getPlayer();
				if(player == 1) {
					playerOne_Configs.add(f.getConfig());
				} else {
					playerTwo_Configs.add(f.getConfig());
				}
			}
		}		
		
		for(Set<String> conf : winningConfigs) {			
			if(playerOne_Configs.containsAll(conf)) {
				for(Field f : pressedFields) {
					if(conf.contains(f.getConfig())){
						f.setBackground(GamePiece.PLAYER_1_COL);
					}
				}
				JOptionPane.showMessageDialog(board,
					    "Player 1 Wins!",
					    "Win!",
					    JOptionPane.INFORMATION_MESSAGE);
				
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(board);
				topFrame.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));
				
			}			
			if(playerTwo_Configs.containsAll(conf)) {
				for(Field f : pressedFields) {
					if(conf.contains(f.getConfig())){
						f.setBackground(GamePiece.PLAYER_2_COL);
					}
				}
				JOptionPane.showMessageDialog(board,
					    "Player 2 Wins!",
					    "Win!",
					    JOptionPane.INFORMATION_MESSAGE);
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(board);
				topFrame.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));				
			}
			
		}
	}
}
