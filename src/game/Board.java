package game;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Board extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private List<GamePiece> pieces;
	private List<Field> fields;
	private JLabel label;
	private int currentPlayer;
	
    public Board(){
    	setLayout(null);
    	pieces = new ArrayList<GamePiece>();
    	fields = new ArrayList<Field>();
    	
    	for(int players = 1; players<3; players++) {
    		for(int strength = 0; strength<5; strength++) {
    			pieces.add(new GamePiece(players, strength, this));
    		}
    	}
    	
    	for(int x = 0; x<3; x++) {
    		for(int y = 0; y<3; y++) {
    			fields.add(new Field(x, y, this));
    		}
    	}
    	
    	currentPlayer = 1;
    	label = new JLabel();    	
    	label.setBounds(110,470,200,80);
    	label.setForeground(GamePiece.PLAYER_1_COL);
    	label.setFont(new Font("Serif", Font.BOLD, 16));
    	label.setText("Player one's turn.");
    	add(label);
    }

    @Override
    public void paintComponent(Graphics g) 
    {  	
    	Graphics2D g2 = (Graphics2D) g;
        Line2D vert1 = new Line2D.Float(140, 40, 140, 340);
        Line2D vert2 = new Line2D.Float(240, 40, 240, 340);
        Line2D hor1 = new Line2D.Float(40, 140, 340, 140);
        Line2D hor2 = new Line2D.Float(40, 240, 340, 240);
        g2.setStroke(new BasicStroke(4));
        g2.draw(vert1);
        g2.draw(vert2);
        g2.draw(hor1);
        g2.draw(hor2);       
    }
    
    List<Field> getFields(){
    	return fields;
    }
    
    List<GamePiece> getPieces(){
    	return pieces;
    }
    
    GamePiece getSelected() {
    	for(GamePiece p : pieces) {
    		if(p.isSelected()) {
    			return p;
    		}
    	}
    	return null;
    }
    
    int getCurrentPlayer() {
    	return currentPlayer;
    }
    
    boolean allPiecesPlayed() {
    	boolean allPlayed = true;
    	for(GamePiece p : pieces) {
    		if(!p.isPlayed()) {
    			allPlayed = false;
    		}
    	}
    	return allPlayed;
    }
    
    boolean noMovePossible() {
    	List<GamePiece> piecesLeft = new ArrayList<GamePiece>();
    	for(GamePiece p : pieces) {
    		if(p.getPlayer() == currentPlayer && !p.isPlayed()) {
    			piecesLeft.add(p);
    		}
    	}
    	
    	for(Field f : fields) {
    		if(f.hasGamePiece()) {
    			int piecePlayer = f.getGamePiece().getPlayer();
    			int pieceStrength = f.getGamePiece().getStrength();
    			for(GamePiece p : piecesLeft) {
    				if(piecePlayer != currentPlayer && pieceStrength<p.getStrength()) {
    					return false;
    				}
    			}
    		} else {
    			return false;
    		}   		
    	}
    	return true;
    }
    
    void nextPlayer() {
    	if(currentPlayer == 2) {
        	label.setText("Player one's turn.");
        	label.setForeground(GamePiece.PLAYER_1_COL); 
        	currentPlayer = 1;
    	} else {
        	label.setText("Player two's turn.");
        	label.setForeground(GamePiece.PLAYER_2_COL);
        	currentPlayer = 2;
    	}
    	JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    	topFrame.revalidate();
    	topFrame.repaint();
    }
}
