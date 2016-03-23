package controllers.menu;

import javax.swing.JPanel;

abstract public class MenuItemController {

	private String name;

	public MenuItemController(String name) {
		this.name = name;
	}

	public String getName() {
	    return this.name;
	}

	abstract public JPanel getView();
	

}