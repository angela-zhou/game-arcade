package pong;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class Ball {
	
	double x, y;
	Circle ball;
	
	Ball(Group gameGroup, ArrayList<Ball> balls, double x, double y, double ballSize) {
		ball = new Circle(x, y, ballSize);
		balls.add(this);
		gameGroup.getChildren().add(ball);
		this.x = x;
		this.y = y;
	}
	
}
