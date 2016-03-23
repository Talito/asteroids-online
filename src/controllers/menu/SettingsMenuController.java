package controllers.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Player;
import views.menu.SettingsMenuView;
import controllers.MainController;

public class SettingsMenuController extends MenuItemController {
	
	private SettingsMenuView view;

	public SettingsMenuController() {
		super("Settings");
		this.view = new SettingsMenuView(MainController.getPlayer());	
		registerHandler();
	}

	private void registerHandler() {
		this.view.getSaveButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveHandler sh = new SaveHandler();
				sh.handle(view.getUsername(), view.getColor());
				if(sh.success()) {
					view.showSuccessMessage();
				} else {
					view.update();
					view.showErrorMessage(sh.getError());
				}
			}
		});
	}

	public SettingsMenuView getView() {
		return view;
	}


	private class SaveHandler extends ActionHandler {

		public void handle(String name, Color color) {
			try {
				Player p = MainController.getPlayer();
				p.setName(name);
				p.setColor(color);
				setResult(true);
			} catch(Exception e) {
				setError(e.getMessage());
				setResult(false);
			}
			
		}

	}

}
