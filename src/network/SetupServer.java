package network;

import models.MultiplayerGame;
import models.Player;
import models.Spectator;
import controllers.HostController;

public class SetupServer extends Server {
	
	private boolean run = true;
	private MultiplayerGame mpg;
	private HostController hc;
	
	public SetupServer(MultiplayerGame mpg, HostController hc) {
		this.mpg = mpg;
		this.hc = hc;
		createSocket(mpg.getPort());
	}
	
	protected void notifyNoSocket() {
		run = false;
		this.hc.notifyNoSocket();
	}
	
	/**
	 * This method waits for the joining players. When someone joins with a name thats already taken, 
	 * the user is denied. If the user joins twice, it is deleted from the list. If the max amount of players
	 * is reached, the user is also denied. Otherwise is is added an a success message is send back.
	 */
	public void run() {	
		while(run) {
			/* If p is not null, it is a joining or leaving player. */
			Player p = receiveObject(Player.class);
			if(p != null) {
				p.setAddress(this.getLatestAddress());
				p.setPort(this.getLatestPort());
				if(p.isPlayer()) {
					handleIncomingPlayer(p);
				} else {
					handleIncomingSpectator((Spectator) p);
				}
			}
		}

		if(socket != null && socket.isClosed() == false) {
			closeSocket();
		}
		
	}
	
	private void handleIncomingPlayer(Player p) {
		String result;
		Player p2 = this.mpg.getPlayerByName(p.getName());
		if(p2 != null) {
			/* user notifies leaving. */
			if(p2.getAddress().equals(p.getAddress()) && p2.getPort() == p.getPort()) {
				this.mpg.removePlayer(p2);
				result = new String("success");
			/* username already taken */
			} else {
				result = new String("usernametaken");
			}
		} else if(this.mpg.isFull()) {
			result = new String("denied");
		} else {
			this.mpg.addPlayer(p);
			result = new String("added");
		}
		sendObject(result, this.getLatestAddress(), this.getLatestPort());
	}
	
	private void handleIncomingSpectator(Spectator s) {
		String result;
		Spectator s2 = this.mpg.getSpectator(s.getAddress(), s.getPort());
		if(s2 != null) {
			this.mpg.removeSpectator(s2);
			result = "success";
		} else {
			this.mpg.addSpectator(s);
			result ="added";
		}
		sendObject(result, this.getLatestAddress(), this.getLatestPort());
	}
	
	
	public void startGame() {
		sendMessage(new String("start"));
		run = false;
		closeSocket();
	}
	
	private void sendMessage(String msg) {
		for(Player p : this.mpg.getPlayers()) {
			sendObject(msg, p.getAddress(), p.getPort());
		}
		for(Spectator s : this.mpg.getSpectators()) {
			sendObject(msg, s.getAddress(), s.getPort());
		}
	}
	
	public void abort() {
		sendMessage(new String("abort"));
		run = false;
		closeSocket();
	}
	
}

