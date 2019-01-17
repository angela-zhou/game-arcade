package pong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PongServer {

	//https://www.pegaxchange.com/2017/12/07/simple-tcp-ip-server-client-java/
	
	private ServerSocket server;
	
	public PongServer(String ipAddress) throws Exception {
		if(ipAddress != null && !ipAddress.isEmpty())
			this.server = new ServerSocket(0, 1, InetAddress.getByName(ipAddress));
		else
			this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
	}
	
	private void listen() throws Exception {
		String data = null;
		Socket client = this.server.accept();
		String clientAddress = client.getInetAddress().getHostAddress();
		System.out.println("\r\nNew connection from " + clientAddress);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		while((data = in.readLine()) != null) {
			System.out.println("\r\nMessage from " + clientAddress + ": " + data);
		}
	}
		 
	public InetAddress getSocketAddress() {
		return this.server.getInetAddress();
	}
	
	public int getPort() {
		return this.server.getLocalPort();
	}
	
	public static void main(String[] args) throws Exception {
		
		PongServer app = new PongServer("localhost");
		
		System.out.println("\r\nRunning Server: " + "\nHost: " + app.getSocketAddress().getHostAddress() + "\nPort: " + app.getPort());
		
		app.listen();
	}	
}
