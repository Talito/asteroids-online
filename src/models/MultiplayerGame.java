package models;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

public class MultiplayerGame extends Observable {
    
    private int numPlayers;
    private int port;
    private Collection<Player> players;
	private Collection<Spectator> spectators;
    
    public MultiplayerGame(int numPlayers, Integer port) {
        this.setNumPlayers(numPlayers);
        this.setPort(port);
        this.players = Collections.synchronizedList(new LinkedList<Player>());
        this.spectators = Collections.synchronizedList(new LinkedList<Spectator>());
    }
    
    public void setPort(Integer port) throws IllegalArgumentException {
        if(port == null) {
            throw new IllegalArgumentException("Port must be an integer.");
        }
        this.port = (int) port;
    }
    
    public void setNumPlayers(int numPlayers) throws IllegalArgumentException {
        if(numPlayers < 2 || numPlayers > 9) {
            throw new IllegalArgumentException("Number of players must be between 2 and 9.");
        }
        this.numPlayers = numPlayers;
    }
    
    public void addPlayer(Player player) throws IllegalArgumentException {
        this.getPlayers().add(player);
        setChanged();
        notifyObservers();
    }
    
    public void addSpectator(Spectator spectator) throws IllegalArgumentException {
        for(Spectator s : getSpectators()) {
            if(s.getAddress() == spectator.getAddress() && s.getPort() == spectator.getPort()) {
                throw new IllegalArgumentException("There is already a spectator with this address.");
            }
        }
        this.getSpectators().add(spectator);
        setChanged();
        notifyObservers();
    }
    
    public int getPort() {
        return this.port;
    }
    
    public int getNumPlayers() {
        return this.numPlayers;
    }
    
    public Collection<Player> getPlayers() {
        return this.players;
    }
    
    public Collection<Spectator> getSpectators() {
    	return this.spectators;
    }
    
    public boolean isFull() {
    	return (this.getPlayers().size() == this.getNumPlayers());
    }
	
	/**
	 * Returns an iterator of the players.
	 * @return 
	 * 			The iterator.
	 */
	Iterator<Player> getPlayersIterator() {
		return this.players.iterator();
	}

	/**
	 * Checks if player exists already, returns true if the player exists.
	 * @param p
	 * 			The player.
	 * @return 
	 * 			The value.
	 */
	public boolean playerExists(Player p) {
		for(Player p2: players) {
			if (p2.getName().equals(p.getName())){
				return true;
			}
		}
		return false;
	}
	
	public Player getPlayerByName(String name) {
		for(Player p2: players) {
			if (p2.getName().equals(name)){
				return p2;
			}
		}
		return null;
	}
	
	public void removePlayer(Player p) {
		this.getPlayers().remove(p);
        setChanged();
        notifyObservers();
	}
	
	
	public Spectator getSpectatorByName(String name) {
		for(Spectator sp2: spectators) {
			if (sp2.getName().equals(name)){
				return sp2;
			}
		}
		return null;
	}
	
	public Spectator getSpectator(InetAddress address, int port) {
		for(Spectator sp2: spectators) {
			if (sp2.getAddress().equals(address) && sp2.getPort() == port){
				return sp2;
			}
		}
        return null;
	}

	public void removeSpectator(Spectator s) {
		this.spectators.remove(s);
        setChanged();
        notifyObservers();
	}
	
    
}
