package network;

import java.net.DatagramSocket;

import models.MultiplayerAsteroidsModel;
import controllers.SpaceshipController;

public class SpaceshipControllerHandler extends Networker {
	
	public boolean run = true;
	private MultiplayerAsteroidsModel mam;
	
	public SpaceshipControllerHandler(DatagramSocket socket, MultiplayerAsteroidsModel mam) {
		super(socket);
		this.mam = mam;
	}
	
	public void run() {
		while(run) {
			SpaceshipController sc = null;
			while(sc == null) {
				sc = receiveObject(SpaceshipController.class);
			}
			mam.setSpaceshipController(sc, getLatestAddress(), getLatestPort());
		}
	}
	
	
}
