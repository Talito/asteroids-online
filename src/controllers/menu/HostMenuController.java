package controllers.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.menu.HostMenuView;
import models.MultiplayerGame;
import controllers.HostController;

public class HostMenuController extends MenuItemController {
	
	private HostMenuView view;
	
	
	public HostMenuController() {
		super("Host");
		this.view = new HostMenuView();
		registerHandler();
	}
	
	private void registerHandler() {
		this.view.getStartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartHandler sh = new StartHandler();
				sh.handle(view.getNumberOfPlayers(), view.getPort());
				if(sh.success()) {
					startHosting(sh.getMultiplayerGame());
				} else {
					view.showErrorMessage(sh.getError());
				}
			}
		});
	}
	
	private void startHosting(MultiplayerGame mpg) {
		HostController hc = new HostController(mpg);
	}
	
	public HostMenuView getView() {
		return view;
	}
	
	public class StartHandler extends ActionHandler {

		private MultiplayerGame mpg;
		
		public void handle(int numPlayers, Integer port) {
			try {
				this.mpg = new MultiplayerGame(numPlayers, port);
				setResult(true);
			} catch(Exception e) {
				setError(e.getMessage());
				setResult(false);
			}
		}

		public MultiplayerGame getMultiplayerGame() {
			return this.mpg;
		}

	}

}
