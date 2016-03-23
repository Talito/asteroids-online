package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import network.GameServer;
import network.SetupServer;
import models.MultiplayerAsteroidsModel;
import models.MultiplayerGame;
import models.Player;
import models.Spaceship;
import views.HostView;
import views.MainFrame;
import views.MultiplayerGameView;
import views.TitleView;

public class HostController extends GameplayController {

	private MultiplayerGame mpg;
	private TitleView view;
	private HostView hostView;
	
	private SetupServer server;
	
	public HostController(MultiplayerGame mpg) {
		
		super("localhost", mpg.getPort());
		this.mpg = mpg;
		
		/* set up the view */
		this.hostView = new HostView(mpg);
		this.view = new TitleView(hostView);
		registerHandlers();
		MainFrame.getInstance().switchToView(this.view);
		
		/* start a client and a server */
		this.server = new SetupServer(mpg, this);
		this.server.start();
		
		startPlayerClient();
		
		
	}
	
	protected void registerHandlers() {
		hostView.getAbortButton().addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				abort();
			}
		});
		hostView.getStartButton().addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				startMultiplayerGame();
			}
		});
	}
	
	
	public void notifyNoSocket() {
		showMessageDialog("Cant create the server. Probably a network error.");
	}
	
	
	private void abort() {
		this.server.abort();
		MainFrame.getInstance().switchToMenu();
	}
	
	/**
	 * @pre MultiplayerGame object is fully loaded.
	 */
	private void startMultiplayerGame() {
		
		this.server.startGame();
		
		MultiplayerAsteroidsModel m = new MultiplayerAsteroidsModel(mpg.getPlayers());
		m.setSpectators(mpg.getSpectators());
		ArrayList<Spaceship> spaceships = new ArrayList<Spaceship>();
		for(Player p : m.getPlayers()) {
			spaceships.add(new Spaceship(new SpaceshipController(), m, p));
		}
		m.setSpaceships(spaceships);
		
		GameServer srv = new GameServer(m, mpg.getPort());
		srv.start();
		
	}
	
	/**
	 * These are not necessary since the host joins automatically.
	 */
	public void notifyAccepted() { }
	public void notifyAbort() { }
	public void notifyDenied() { }
	public void notifyNoServer() { }
	public void notifyUsernameTaken() { }
	
}
