package controllers.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import models.SingleplayerAsteroidsModel;
import views.GameView;
import views.MainFrame;
import views.SingleplayerGameView;
import views.menu.SingleplayerMenuView;
import controllers.MainController;
import controllers.SpaceshipController;

public class SingleplayerMenuController extends MenuItemController {
	
	private SingleplayerMenuView view;
	private static GameView gp;
	
	public SingleplayerMenuController() {
		super("Singleplayer");
		this.view = new SingleplayerMenuView();
		this.view.getStartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startSinglePlayer();
			}
		});
	}
	
	
	public void startSinglePlayer() {
		// Maak een SpaceshipController
		SpaceshipController sc = new SpaceshipController();
		// Maak een model
		SingleplayerAsteroidsModel model = new SingleplayerAsteroidsModel(sc, MainController.getPlayer());
		MainFrame.getInstance().setFocusable(true);
		MainFrame.getInstance().addKeyListener(sc);
		
		SingleplayerGameView gp = new SingleplayerGameView(model);
		MainFrame.getInstance().switchToView(gp);
		
		// Werk het model steeds bij
		new GameRunner(model).start();
			
		
	}
	
	private class GameRunner extends Thread {
		
		SingleplayerAsteroidsModel model;
		
		public GameRunner(SingleplayerAsteroidsModel model) {
			this.model = model;
		}
		
		public void run() {
			while(!model.getGameOver()) {
				model.nextStep();
			    try {
			    	Thread.sleep(40);
			    } catch (InterruptedException e) {
			    	e.printStackTrace();
			    }
			}
			handleGameFinished();
		}
		
		private void handleGameFinished() {
			Object[] possibilities = {"Restart", "Return to menu"};
			int n = JOptionPane.showOptionDialog(
			                    MainFrame.getInstance(),
			                    "Do you want to play a new game?",
			                    "Game finished",
			                    JOptionPane.YES_NO_OPTION,
			                    JOptionPane.QUESTION_MESSAGE,
			                    null,
			                    possibilities,
			                    possibilities[0]);
			
			if (n == 0) {
		      	model.getSpaceship().setAlive(true);
		      	model.restartGame();
		      	this.run();
			} else {
				MainFrame.getInstance().switchToMenu();
			}
		}
		
	}
	
	public SingleplayerMenuView getView() {
		return view;
	}

	
}
