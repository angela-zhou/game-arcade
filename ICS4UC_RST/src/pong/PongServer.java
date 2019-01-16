package pong;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PongServer {

	//https://www.pegaxchange.com/2017/12/07/simple-tcp-ip-server-client-java/
	
	static ArrayList<Socket> clients = new ArrayList<Socket>();
	static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();
		   
	public static void main(String[] args) throws IOException{
//		int portNumber = Integer.parseInt(args[0]);
		try(ServerSocket serverSocket = new ServerSocket(23)) {
			System.out.println("Server Started!\n" + serverSocket.getInetAddress().getHostAddress());
			while(true) {
				try(Socket client = serverSocket.accept()) {
					System.out.println("User Connected");
//					clients.add(client);
					PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//					writers.add(out);
					out.println("Hello");
				}
			}
		}

	}
}
