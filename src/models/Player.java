package models;

import java.awt.Color;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Observable;

public class Player extends Observable implements Serializable {
	
	private static final long serialVersionUID = 5L;
	
	private String name;
	private int score = 0;
	private Color color;
	private InetAddress address;
	private int port;
	
	public Player() {
		setInetAddress();
		setColor(Color.WHITE);
		setName(getAddress().getHostName());
	}
	
	public Player (String name, Color color, int port) {
		setInetAddress();
		setPort(port);
		setName(name);
		setColor(color);
	}
	
	public void setName(String name) throws IllegalArgumentException {
		if(name == null || name.equals("")) {
			throw new IllegalArgumentException("Name can't be zero.");
		}
		this.name = name;
		setChanged();
		notifyObservers();
	}
	
	public void setColor(Color color) throws IllegalArgumentException {
		if(color == null) {
			throw new IllegalArgumentException("Invalid color given.");
		}
		this.color = color;
		setChanged();
		notifyObservers();
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void incrementScore() {
		this.score++;
	}
	
	public Color getColor() {
		return this.color;
	}

	public boolean isPlayer() {
		return true;
	}
	
	public boolean isSpectator() {
		return false;
	}
	
	private void setInetAddress() throws IllegalArgumentException {
		try {
			this.address = InetAddress.getLocalHost();
		} catch(Exception e) {
			throw new IllegalArgumentException("Can't fetch the hostname of the system.");
		}
	}
	
	public int getPort() {
		return port;
	}
	
	public InetAddress getAddress() {
		return address;
	}

	public void setPort(int newPort) {
		this.port = newPort;
	}

	public void setAddress(InetAddress newAdd) {
		this.address = newAdd;
	}
	
}