package pong;

public class GameState {

	boolean end = false;
	int playerNum;
	double p1X, p2X, p1Y, p2Y, bX, bY; //top-left positions (paddle), center position (ball)
	int pHeight, pWidth, bSize;
	
	public GameState(int playerNum) {
		this.playerNum = playerNum;
	}
	
	public boolean getEnd() {
		return end;
	}
	
	public void setBallSize(int size) {
		bSize = size;
	}
	
}
