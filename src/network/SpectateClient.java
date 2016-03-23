package network;
import java.net.DatagramSocket;
import java.net.InetAddress;

import models.MultiplayerAsteroidsModel;
import models.Player;
import models.Spectator;
import controllers.GameplayController;
import controllers.SpaceshipController;


public class SpectateClient extends Client {
	
	private Spectator spectator;
	private GameplayController gpc;
	
	public SpectateClient(Spectator s, String host, int port, GameplayController gpc) {
		super(host, port);
		this.spectator = s;
		this.gpc = gpc;
	}
	
	
	public void run() {
		
		if(createSocket(this.host) == false) {
			gpc.notifyNoServer();
			return;
		}
		
		/** Send the player itself to the game.  */
		this.sendObject(spectator, address, port);
		
		/** receive answer */
		String result = receiveObject(String.class);
		if(result == null || !result.startsWith("added")) {
			this.gpc.notifyDenied();
			socket.close();
			return;
		}
		
		/** the player is in the game! */		
		gpc.notifyAccepted();
		/** wait for the server to start the game. */
		boolean wait = true;
		String signal = null;
		while(wait) {
			signal = this.receiveObject(String.class);
			if(signal != null) {
				wait = false;
			}
		}
		
		/* Received starting signal! Go start the game! */
		if(signal.startsWith("start")) {
			startGame();
		} else if(signal.startsWith("abort")) {
			this.gpc.notifyAbort();
		}
		
		socket.close();
		
	}
	
	public void notifyLeaving() {
		sendObject(spectator, address, port);
	}
	
	private void startGame() {
		
		MultiplayerAsteroidsModel m = null;
		while(m == null) {
			m = receiveObject(MultiplayerAsteroidsModel.class);
		}
		
		this.gpc.initMultiplayerGame(m, null);
		
		ModelHandler mh = new ModelHandler(this.getSocket(), m);
		mh.start();
		
		while(!m.getGameOver()) {
			m.nextStep();
			try {
				Thread.sleep(40);
			} catch(Exception e) {
				
			}
		}
		
		mh.run = false;
		m.updatedModel();
		resetGame();
		
	}
	
	private void resetGame() {
		int i = 5;
		while(i > 0) {
			try {
				i--;
				Thread.sleep(1000);
			} catch(Exception e) {
				resetGame();
			}
		}
		startGame();
	}
	
}
