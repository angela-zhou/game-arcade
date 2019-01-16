package pong;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PongServer {

	static ArrayList<Socket> clients = new ArrayList<Socket>();
	static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();
		   
	public static void main(String[] args) throws IOException{
//		int portNumber = Integer.parseInt(args[0]);
		try(ServerSocket serverSocket = new ServerSocket(9090)) {
			System.out.println("Server Started!");
			while(true) {
				try(Socket client = serverSocket.accept()) {
					clients.add(client);
					PrintWriter out = new PrintWriter(client.getOutputStream(), true);
					writers.add(out);
					out.println("Hello");
				}
			}
		}

	}
}
