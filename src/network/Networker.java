package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public abstract class Networker extends Thread {
	
	protected DatagramSocket socket = null;
	protected InetAddress latestAddress = null;
	protected int latestPort;
	
	public Networker() {
		
	}
	
	public Networker(DatagramSocket s) {
		this.socket = s;
	}
	
	protected <E> boolean sendObject(E obj, InetAddress address, int port) {
		byte[] buf = ByteConverter.convertToByteArray(obj);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
		try {
			socket.send(packet);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	
	protected <E> E receiveObject(Class<E> type) {
		E obj = null;
		byte[] buf = new byte[4096];
		DatagramPacket recv = new DatagramPacket(buf, buf.length);
		try {
			socket.receive(recv);
			obj = ByteConverter.getObjectFromByteArray(buf, type);
			this.setLatestNetworkData(recv);
		} catch(Exception e) {
			obj = null;
		}
		return obj;
	}
	
	protected void setLatestNetworkData(DatagramPacket dp) {
		this.latestAddress = dp.getAddress();
		this.latestPort = dp.getPort();
	}
	
	protected InetAddress getLatestAddress() {
		return this.latestAddress;
	}
	
	protected int getLatestPort() {
		return this.latestPort;
	}
	
	public DatagramSocket getSocket() {
		return this.socket;
	}
	
	public void closeSocket() {
		if(this.socket == null) {
			System.out.println("Trying to close null socket.");
			return;
		}
		this.socket.close();
	}
	
}
