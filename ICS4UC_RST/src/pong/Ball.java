package pong;

import java.util.Random;
import javafx.scene.shape.Circle;

public class Ball extends Circle{
	
	static final double HEIGHT_CONSTANT = 39, WIDTH_CONSTANT = 16, HIT_DELAY = 250;
	double SCREEN_WIDTH, SCREEN_HEIGHT;
	double speed, xSpeed, ySpeed;
	Paddle p1, p2;
	long lastHit;
	
	Ball(double minSpeed, double maxSpeed, double ballSize, double sWidth, double sHeight) {
		
		this.SCREEN_WIDTH = sWidth - WIDTH_CONSTANT;
		this.SCREEN_HEIGHT = sHeight - HEIGHT_CONSTANT;
		this.setCenterX(SCREEN_WIDTH / 2);
		this.setCenterY(SCREEN_HEIGHT / 2);
		this.setRadius(ballSize);
		
		p1 = Main.getInstance().p1;
		p2 = Main.getInstance().p2;
		
		speed = rand(minSpeed, maxSpeed);
		xSpeed = speed / 2;
		ySpeed = 0;
		}
	
	public void move() {
		this.setCenterX(this.getCenterX() + xSpeed);
		this.setCenterY(this.getCenterY() + ySpeed);
		
		//Top/bottom boundary detection
		if(this.getCenterY() - this.getRadius() <= 0 || this.getCenterY() + this.getRadius() >= SCREEN_HEIGHT)
			ySpeed *= -1;
		
		//Paddle detection
		if(this.getBoundsInLocal().intersects(p1.getBoundsInLocal()))
			paddleHit(p1);
		if(this.getBoundsInLocal().intersects(p2.getBoundsInLocal()))
			paddleHit(p2);
		
		//Score detection
		
	}
	
	private void paddleHit(Paddle paddle) {
		
		//Double hit on paddle collision fix
		if(System.currentTimeMillis() - lastHit < HIT_DELAY)
			return;
		lastHit = System.currentTimeMillis();
		
		//Hitting side of paddle fix
		if(this.getCenterX() < p1.getX() + paddle.getWidth() || this.getCenterX() > p2.getX()) {
			ySpeed *= -1;
			return;
		}	
		
		//Proportional rebound mechanics 
		//d = distance from center  
		//r = ratio of d / paddle height    
		//a = angle of return (from perpendicular to paddle)
		double d = (paddle.getY() + paddle.getHeight() / 2) - this.getCenterY();
		double r = Math.abs(d / paddle.getHeight());
		System.out.println(r); //debug values
		double a = 0;
		
		if(d >= 0) {
			if(xSpeed >= 0)
				a = Math.PI + ((Math.PI / 2) * r);
			else
				a = 2 * Math.PI - ((Math.PI / 2) * r);
		}
		else {
			if(xSpeed >= 0)
				a = Math.PI - ((Math.PI / 2) * r);
			else
				a = (Math.PI / 2) * r;
		}
		
		xSpeed = Math.cos(a) * speed;
		ySpeed = Math.sin(a) * speed;
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
