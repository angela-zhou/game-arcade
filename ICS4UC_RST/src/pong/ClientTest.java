package pong;

public class ClientTest {

	public static void main(String[] args) {
		PongClient pong = new PongClient("127.0.0.1");
		pong.run();
		GameState player = new GameState(2);
		player.setBallSize(10);
		pong.sendState(player);
	}
}
