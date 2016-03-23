package controllers;

import javax.swing.JOptionPane;

import network.PlayerClient;
import models.MultiplayerAsteroidsModel;
import views.MainFrame;
import views.MultiplayerGameView;

public abstract class GameplayController {
	
	protected String host;
	protected int port;
	protected PlayerClient pc;
	protected MultiplayerGameView mgv;
	
	public GameplayController(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	protected void startPlayerClient() {
		this.pc = new PlayerClient(MainController.getPlayer(), host, port, this);
		this.pc.start();
	}
	
	protected PlayerClient getPlayerClient() {
		return this.pc;
	}
	
	protected void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(MainFrame.getInstance(), msg, "Asteroids error!", JOptionPane.ERROR_MESSAGE);
		MainFrame.getInstance().switchToMenu();
	}
	
	public void initMultiplayerGame(MultiplayerAsteroidsModel model, SpaceshipController sc) {
		mgv = new MultiplayerGameView(model);
		MainFrame.getInstance().setFocusable(true);
		if(sc != null) {
			model.updateSpaceship(MainController.getPlayer(), sc);
			MainFrame.getInstance().addKeyListener(sc);
		}
		MainFrame.getInstance().switchToView(mgv);
	}

	public MultiplayerAsteroidsModel getModel() {
		return (MultiplayerAsteroidsModel) mgv.getModel();
	}
	
	protected abstract void registerHandlers();
	
	public abstract void notifyAccepted();
	public abstract void notifyAbort();
	public abstract void notifyDenied();
	public abstract void notifyNoServer();
	public abstract void notifyUsernameTaken();
	
}
