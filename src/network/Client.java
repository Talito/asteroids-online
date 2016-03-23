package network;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client extends Networker {
	
	protected int port;
	protected InetAddress address = null;
	protected String host;
	
	public Client(String host, int port) {
		this.port = port;
		this.host = host;
	}
	
	protected boolean createSocket(String host) {
		boolean success = false;
		try {
			address = InetAddress.getByName(host);
			socket = new DatagramSocket();
			socket.setSoTimeout(5000);
			success =true;
		} catch(Exception e) {
			return false;
		}
		return success;
	}
	
}
