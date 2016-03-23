package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Spectator;
import network.SpectateClient;
import views.JoinView;
import views.MainFrame;
import views.TitleView;


public class SpectateController extends GameplayController {
	
	private JoinView joinView;
	private TitleView view;
	private SpectateClient sc;
	
	public SpectateController(String host, int port) {
		super(host, port);
		
		/* set up view */
		this.joinView = new JoinView();
		this.view = new TitleView(joinView);
		registerHandlers();
		MainFrame.getInstance().switchToView(this.view);
		
		startSpectatorClient();
		
	}
	
	protected void startSpectatorClient() {
		Spectator s = new Spectator();
		s.setName(MainController.getPlayer().getName());
		this.sc = new SpectateClient(s, host, port, this);
		this.sc.start();
	}

	@Override
	protected void registerHandlers() {
		this.joinView.getLeaveButton().addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {	
				sc.notifyLeaving();
				MainFrame.getInstance().switchToMenu();
			}
		});
	}

	public void notifyAccepted() {
		this.joinView.setAccepted();
	}
	
	public void notifyAbort() {
		showMessageDialog("The hoster has aborted the game.");
	}
	
	public void notifyDenied() {
		showMessageDialog("You have been denied access to the game. The server could be offline.");
	}
	
	public void notifyNoServer() {
		showMessageDialog("Cannot connect to the server.");
	}
	
	public void notifyUsernameTaken() { }
	
}