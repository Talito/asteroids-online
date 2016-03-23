package controllers.menu;

import javax.swing.JPanel;

import views.TitleView;
import views.menu.MenuView;


public class MenuController {

	private TitleView view;
	private MenuItemController[] menuItems; 

	public MenuController() {
		
		this.menuItems = new MenuItemController[4];
		menuItems[0] = new SingleplayerMenuController();
		menuItems[1] = new HostMenuController();
		menuItems[2] = new JoinMenuController();
		menuItems[3] = new SettingsMenuController();

		this.view = new TitleView(new MenuView(menuItems));

	}

	public JPanel getView() {
		return view;
	}


}
