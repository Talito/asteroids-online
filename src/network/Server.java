package network;
import java.net.DatagramSocket;

public abstract class Server extends Networker {
	
	protected void createSocket(int port) {
		try {
			socket = new DatagramSocket(port);
			socket.setSoTimeout(5000);
		} catch(Exception e) {
			notifyNoSocket();
		}
	}
	
	protected abstract void notifyNoSocket();
	
}
