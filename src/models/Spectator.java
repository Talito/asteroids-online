package models;


public class Spectator extends Player {
	
	public boolean isPlayer() {
		return false;
	}
	
	public boolean isSpectator() {
		return true;
	}

	
	
}
