package network;

import models.MultiplayerAsteroidsModel;
import models.Player;
import models.Spectator;

public class GameServer extends Server {
	
	private MultiplayerAsteroidsModel model;
	
	public GameServer(MultiplayerAsteroidsModel model, int port) {
		this.model = model;
		createSocket(port);
	}
	
	protected void notifyNoSocket() {
		
	}

	public void run() {
		
		SpaceshipControllerHandler sh = new SpaceshipControllerHandler(this.getSocket(), model);
		sh.start();
		
		while(!model.getGameOver()) {
			model.nextStep();
			sendModel();
			try {
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		sh.run = false;
		resetGame();
	}
	
	private void sendModel() {

		for(Player p : model.getPlayers()) {
			sendObject(model, p.getAddress(), p.getPort());
		}
		for(Spectator s : model.getSpectators()) {
			sendObject(model, s.getAddress(), s.getPort());
		}
		
	}
	
	
	private void resetGame() {
		try {
			Thread.sleep(5000);
		} catch(Exception e) {
			resetGame();
		}
		model.restartGame();
		run();
	}
	
	
}
