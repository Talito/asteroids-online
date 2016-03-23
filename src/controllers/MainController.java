package controllers;


import models.Player;
import views.MainFrame;

public class MainController {
	
	private static Player localPlayer;
	
	public MainController() {
		localPlayer = new Player();
		MainFrame.getInstance();
	}
	
	public static Player getPlayer() {
		return MainController.localPlayer;
	}
	
	public static void main(String[] args) {
		new MainController();
	}
	
}
