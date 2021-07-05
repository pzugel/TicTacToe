package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePiece {
	static final int SIZE = 45;
	static final Color PLAYER_1_COL = new Color(204, 51, 51);
	static final Color PLAYER_1_COL_HOVER = new Color(255, 150, 150);
	
	static final Color PLAYER_2_COL = new Color(51, 204, 51);
	static final Color PLAYER_2_COL_HOVER = new Color(150, 255, 150);
	
	private boolean selected;
	private boolean played;
	
	private int player_num;
	private int stren;
	private JButton btn;
	private Board board;
	
	GamePiece(int player, int strength, JPanel panel){
		selected = false;
		player_num = player;
		stren = strength;
		board = (Board) panel;
		btn = new JButton(String.valueOf(strength));
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		if(player_num == 1) {
    		btn.setBackground(PLAYER_1_COL);
    	} else {
    		btn.setBackground(PLAYER_2_COL);
    	}
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				unselectOtherPieces();
				select();
			}
        });
		
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
        		if(!selected && !played) {
                	if(player_num == 1) {
                		btn.setBackground(PLAYER_1_COL_HOVER);
                	} else {
                		btn.setBackground(PLAYER_2_COL_HOVER);
                	}
            	}	       	
            }

            public void mouseExited(MouseEvent evt) {
            	if(!selected && !played) {
	            	if(player_num == 1) {
	            		btn.setBackground(PLAYER_1_COL);
	            	} else {
	            		btn.setBackground(PLAYER_2_COL);
	            	}
            	}
            }
        });
             
		int Y_POS;
		if(player == 1) {
			Y_POS = 370;
		} else {
			Y_POS = 430;
		}
		
		int X_POS = SIZE + strength * 60;
		
		btn.setBounds(X_POS, Y_POS, SIZE, SIZE);
		panel.add(btn);
	}
	
	void select() {
		if(!played) {
			if(selected) {
				selected = false;
				if(player_num == 1) {
		    		btn.setBackground(PLAYER_1_COL);
		    	} else {
		    		btn.setBackground(PLAYER_2_COL);
		    	}
			} else {
				selected = true;
				if(player_num == 1) {
		    		btn.setBackground(PLAYER_1_COL_HOVER);
		    	} else {
		    		btn.setBackground(PLAYER_2_COL_HOVER);
		    	}
			}	
		}		
	}
	
	void unselect() {
		selected = false;
		if(!played) {
			if(player_num == 1) {
	    		btn.setBackground(PLAYER_1_COL);
	    	} else {
	    		btn.setBackground(PLAYER_2_COL);
	    	}
		}
	}
	
	void unselectOtherPieces() {
		for(GamePiece p : board.getPieces()) {
			p.unselect();
		}
	}
	
	void play() {		
		played = true;
		btn.setBackground(Color.BLACK);
		btn.setText("X");		
	}
	
	boolean isSelected() {
		return selected;
	}
	
	boolean isPlayed() {
		return played;
	}
	
	int getPlayer() {
		return player_num;
	}
	
	int getStrength() {
		return stren;
	}
}
