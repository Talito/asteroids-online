package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import network.PlayerClient;
import views.JoinView;
import views.MainFrame;
import views.TitleView;

public class JoinController extends GameplayController {
	

	private JoinView joinView;
	private TitleView view;
	
	public JoinController(String host, int port) {
		
		super(host, port);
		
		/* set up view */
		this.joinView = new JoinView();
		this.view = new TitleView(joinView);
		registerHandlers();
		MainFrame.getInstance().switchToView(this.view);
		
		startPlayerClient();
	}
	
	protected void registerHandlers() {
		this.joinView.getLeaveButton().addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {	
				getPlayerClient().notifyLeaving();
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
		showMessageDialog("You have been denied access to the game. The server could be offline or already full.");
	}
	
	public void notifyNoServer() {
		showMessageDialog("Cannot connect to the server.");
	}
	
	public void notifyUsernameTaken() {
		showMessageDialog("This username has already been taken!");
	}
	

}
