package pong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class PongClient {

	private Socket socket;
	private Scanner scanner;
	
	private PongClient(String serverAddress, int serverPort) throws Exception {
		this.socket = new Socket(serverAddress, serverPort);
		this.scanner = new Scanner(System.in);
	}

	private void start() throws IOException {
		String input;
		
		while(true) {
			input = scanner.nextLine();
			
			PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
			out.println(input);
			out.flush();
		}
	}
	
	public static void main(String[] args) throws Exception{
		PongClient client = new PongClient("localhost", 52616);
		
		System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
		client.start();
	}
}
