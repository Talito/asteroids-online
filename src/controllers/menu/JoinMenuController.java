package controllers.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.menu.JoinMenuView;
import controllers.JoinController;
import controllers.SpectateController;

public class JoinMenuController extends MenuItemController {

	private JoinMenuView view;
	
	public JoinMenuController() {
		super("Spectate/Join");
		this.view = new JoinMenuView();
		registerJoinHandler();
	}
	
	private void registerJoinHandler() {
		
		this.view.getJoinButton().addActionListener(new JoinSpectateListener());
		this.view.getSpectateButton().addActionListener(new JoinSpectateListener());
	}
	

	private void join(String host, int port) {
		JoinController jc = new JoinController(host, port);
	}
	
	private void spectate(String host, int port) {
		new SpectateController(host, port);
	}

	@Override
	public JoinMenuView getView() {
		return this.view;
	}
	
	private class JoinSpectateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JoinHandler jh = new JoinHandler();
			jh.handle(view.getHost(), view.getPort());
			if(jh.success()) {
				if(e.getActionCommand() == "join") {
					join(view.getHost(), (int) view.getPort());
				} else {
					spectate(view.getHost(), (int) view.getPort());
				}
			} else {
				view.showErrorMessage(jh.getError());
			}
		}
	}
	
	private class JoinHandler extends ActionHandler {
		
		public void handle(String host, Integer port) {
			try {
				if(port == null) {
					throw new Exception("Port must be an integer.");
				}
				if(host.equals("") || host == null) {
					throw new Exception("Host must be filled in.");
				}
				setResult(true);
			} catch(Exception e) {
				setError(e.getMessage());
				setResult(false);
			}
		}
		
	}
	
	
	

}
