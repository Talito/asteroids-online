package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import models.MultiplayerAsteroidsModel;
import models.Player;
import models.Spaceship;

public class MultiplayerGameView extends GameView {
	
	JPanel up = null;
	
	public MultiplayerGameView(MultiplayerAsteroidsModel model) {
		super(model);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintScores((Graphics2D) g);
		paintGameover((Graphics2D) g);
	}
	
	public void paintScores(Graphics2D g) {
		int i = 0;
		int startX = 50;
		int startY = 50;

		MultiplayerAsteroidsModel m = (MultiplayerAsteroidsModel) getModel();
		for(Player p : m.getPlayers()) {
			g.setColor(p.getColor());
			g.drawString(p.getName() + " " + p.getScore(), startX, startY + (i*20));
			i++;
		}
		
	}
	
	public void paintGameover(Graphics2D g) {
		MultiplayerAsteroidsModel m = (MultiplayerAsteroidsModel) getModel();
		if(m.getGameOver()) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced",Font.BOLD, 20));
			g.drawString("Game Over. Restarting soon...", 200, 300);
		}
	}
	
	@Override
	public void paintSpaceships(Graphics2D g) {
		MultiplayerAsteroidsModel m = (MultiplayerAsteroidsModel) getModel();
		for(Spaceship s : m.getSpaceships()) {
			if(s.isAlive()){
				paintSpaceship(s, g);
			}
		}
	}

}
