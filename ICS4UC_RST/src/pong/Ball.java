package pong;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class Ball extends Circle{
	
	static final double HEIGHT_CONSTANT = 39, WIDTH_CONSTANT = 16;
	double SCREEN_WIDTH, SCREEN_HEIGHT;
	double speed, xSpeed, ySpeed;
	
	Ball(double minSpeed, double maxSpeed, double ballSize, double sWidth, double sHeight) {
		
		this.SCREEN_WIDTH = sWidth - WIDTH_CONSTANT;
		this.SCREEN_HEIGHT = sHeight - HEIGHT_CONSTANT;
		this.setCenterX(SCREEN_WIDTH / 2);
		this.setCenterY(SCREEN_HEIGHT / 2);
		this.setRadius(ballSize);
//		speed = rand(minSpeed, maxSpeed);
		speed = 10;
		Double angle = rand(0, 2 * Math.PI);
//		xSpeed = Math.cos(angle) * speed;
		xSpeed = 0;
		ySpeed = Math.sin(angle) * speed;
		}
	
	public void move() {
		this.setCenterX(this.getCenterX() + xSpeed);
		this.setCenterY(this.getCenterY() + ySpeed);
		if(this.getCenterY() - this.getRadius() < 0 || this.getCenterY() + this.getRadius() > SCREEN_HEIGHT)
			ySpeed *= -1;
		
	}
	
	private double rand(double min, double max) {
		Random rand = new Random();
		return (rand.nextDouble() * (max - min)) + min;
	}
	
	public void updateScreen(double width, double height) {
		SCREEN_WIDTH = width - WIDTH_CONSTANT;
		SCREEN_HEIGHT = height - HEIGHT_CONSTANT;
	}
	
}
