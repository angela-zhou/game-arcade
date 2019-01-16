package pong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class PongClient {

	public static void main(String[] args) throws IOException{
        System.out.println("Enter the IP address and then port of a machine running the date server: ");
        String serverAddress = new Scanner(System.in).nextLine();
        String port = new Scanner(System.in).nextLine();
        Socket socket = new Socket(serverAddress, Integer.parseInt(port));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = in.readLine();
        System.out.println("Server response: " + response);
	}
}
