package views;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.SingleplayerAsteroidsModel;

public class SingleplayerGameView extends GameView {
	
	public SingleplayerGameView(SingleplayerAsteroidsModel model) {
		super(model);
	}
	
	@Override
	public void paintSpaceships(Graphics2D g) {
		SingleplayerAsteroidsModel m = (SingleplayerAsteroidsModel) getModel();
		paintSpaceship(m.getSpaceship(), g);
	}


}