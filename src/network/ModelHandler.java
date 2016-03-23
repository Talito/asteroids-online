package network;

import java.net.DatagramSocket;

import models.MultiplayerAsteroidsModel;
import models.Player;

public class ModelHandler extends Networker {
	
	public boolean run = true;
	private MultiplayerAsteroidsModel mam;
	
	public ModelHandler(DatagramSocket socket, MultiplayerAsteroidsModel mam) {
		super(socket);
		this.mam = mam;
	}
	
	public void run() {
		int refNum = 0;
		while(run) {
			MultiplayerAsteroidsModel mamInput = receiveObject(MultiplayerAsteroidsModel.class);
			if(mamInput != null && mamInput.getRefNumber() > refNum) {
				refNum = mamInput.getRefNumber();
				mam.setAsteroids(mamInput.getAsteroids());
				mam.setBullets(mamInput.getBullets());
				mam.setSpaceships(mamInput.getSpaceships());
				mam.setGameOver(mamInput.getGameOver());
				mam.updatedModel();
			}
			
		}
	}
	
	
}
