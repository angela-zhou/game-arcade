package pong;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class PongClient {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String serverIP;
	private Socket server;
	
	public PongClient(String serverIP) {
		this.serverIP = serverIP;
	}
	
	public void run() {
		try {
			connectToServer();
			setupStreams();
			whilePlaying();
		} catch(EOFException eofException) {
			System.out.println("Connection Ended");
		} catch(IOException error) {
			error.printStackTrace();
		} finally {
			endStream();
		}
	}
	
	private void connectToServer() throws IOException{
		System.out.println("Attempting Connection...");
		server = new Socket(InetAddress.getByName(serverIP), 6789);
		System.out.println("Connected to: " + server.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(server.getOutputStream());
		output.flush();
		input = new ObjectInputStream(server.getInputStream());
		System.out.println("Streams Setup!");
	}
	
	private void endStream() {
		System.out.println("Closing Connection...");
		try {
			output.close();
			input.close();
			server.close();
		} catch(IOException error) {
			error.printStackTrace();
		}
	}
	
	private void whilePlaying() throws IOException{
		GameState serverState = new GameState(1);
		do {
			try {
				serverState = (GameState) input.readObject();
			} catch(ClassNotFoundException classNotFoundException) {
				System.out.println("Server didn't send game-state.");
			}
		}while(!serverState.getEnd());
	}
	
	public void sendState(GameState state) {
		try {
			output.writeObject(state);
		} catch(IOException error) {
			System.out.println("GameState Send Error.");
		}
	}
}
