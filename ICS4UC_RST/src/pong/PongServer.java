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
import java.net.ServerSocket;
import java.net.Socket;

public class PongServer {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket client;

	public void run() {
		try {
			server = new ServerSocket(6789, 100);
			while(true) {
				try {
					waitForPlayer();
					setupStreams();
					whilePlaying();
				} catch(EOFException disconnect) {
					System.out.println("Connection Ended");
				} finally {
					endStream();
				}
			}
		} catch(IOException error) {
			error.printStackTrace();
		}
	}
	
	private void waitForPlayer() throws IOException {
		System.out.println("Waiting for player...");
		client = server.accept();
		System.out.println("Player connected: " + client.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(client.getOutputStream());
		output.flush();
		input = new ObjectInputStream(client.getInputStream());
		System.out.println("Streams Setup Complete");
	}
	
	private void endStream() {
		System.out.println("Closing connection...");
		try {
			output.close();
			input.close();
			client.close();
		} catch(IOException error) {
			error.printStackTrace();
		}
	}
	
	private void whilePlaying() throws IOException {
		GameState clientState = new GameState(2);
		do {
			try {
				clientState = (GameState) input.readObject();
				System.out.println(clientState.bSize);
			} catch(ClassNotFoundException classNotFoundException){
				System.out.println("User didn't send game-state");
			}
		} while(!clientState.getEnd());
	}
}
