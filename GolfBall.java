// JavaFX imports
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

// other imports
import java.util.ArrayList;

public class GolfBall{

	// instance variables
	private Point2D position, velocity, acceleration;
	private double radius;
	private Circle circle;
	private double factor;
	private AudioClip bounce;

	// nondefault constructor
	public GolfBall(double x, double y){

		position = new Point2D(x, y);
		velocity = new Point2D(0.0, 0.0);
		acceleration = new Point2D(0.0, 0.0);
		radius = 15.0;
		circle = new Circle(x, y, radius);
		factor = 0.01;
		bounce = new AudioClip(getClass().getResource("Bounce.mp3").toString());

	}

	// getter methods
	public Point2D getPosition(){ return position; }
	public Point2D getVelocity(){ return velocity; }
	public Point2D getAcceleration(){ return acceleration; }
	public double getRadius(){ return radius; }
	public Circle getCircle(){ return circle; }

	// setter methods
	public void setPosition(Point2D position){ this.position = position; }
	public void setVelocity(Point2D velocity){ this.velocity = velocity; }
	public void setAcceleration(Point2D acceleration){ this.acceleration = acceleration; }

	// movement of golf ball
	public void move(Level level, Point2D launch, double fieldX, double fieldY, double fieldW, double fieldH){

		// account for collisions
		if(colliding(level, launch, fieldX, fieldY, fieldW, fieldH))
			bounce(level, launch, fieldX, fieldY, fieldW, fieldH);

		// movement
		acceleration = velocity.multiply(-1.0 * factor);
		velocity = velocity.add(acceleration);
		position = position.add(velocity);

		// print
		//System.out.println(position + "" + velocity + "" + acceleration);

	}

	// check if golf ball hits obstacle
	public boolean colliding(Level level, Point2D launch, double fieldX, double fieldY, double fieldW, double fieldH){

		// left/right walls
		if(position.getX() - radius <= fieldX || position.getX() >= fieldW - radius)
			return true;

		// top/bottom walls
		if(position.getY() - radius <= fieldY || position.getY() >= fieldH - radius)
			return true;

		// obstacles
		for(Obstacle obstacle : level.getObstacles()){
			for(Point2D point : getPoints()){
				if(obstacle.getPolygon().contains(point)){
					//System.out.println("true");
					return true;
				}
			}
		}

		// no collision
		return false;

	}

	public void bounce(Level level, Point2D launch, double fieldX, double fieldY, double fieldW, double fieldH){

		// audio
		bounce.play();

		// left/right walls
		if(position.getX() - radius <= fieldX || position.getX() >= fieldW - radius)
			velocity = new Point2D(-1.0 * velocity.getX(), velocity.getY());

		// top/bottom walls
		else if(position.getY() - radius <= fieldY || position.getY() >= fieldH - radius)
			velocity = new Point2D(velocity.getX(), -1.0 * velocity.getY());

		// obstacles
		else{
			double minDistance = (double)(Integer.MAX_VALUE);
			Line closestLine;
			double closestSlope = 0.0;
			double closestY_int = 0.0;
			for(Obstacle obstacle : level.getObstacles()){
				for(Line line : obstacle.getLines()){
					double slope = (line.getEndY() - line.getStartY()) / (line.getEndX() - line.getStartX());
					double y_int = line.getStartY();
					double distance = Math.abs(slope * position.getX() + position.getY() + y_int) / Math.sqrt(Math.pow(slope, 2) + 1.0);
					if(distance < minDistance){
						minDistance = distance;
						closestLine = line;
						closestSlope = slope;
						closestY_int = y_int;
					}
				}
			}
			double angle = Math.toRadians(position.angle(new Point2D(300.0, ((300.0 * closestSlope) + closestY_int)), launch));
			velocity = (new Point2D(velocity.getX() * Math.cos(angle), velocity.getY() * Math.sin(angle))).multiply(-1.0);
		}

		// changing acceleration
		if(factor == 0.01)
			factor = 0.03;

		// keep ball away from wall
		while(colliding(level, launch, fieldX, fieldY, fieldW, fieldH)){
			acceleration = velocity.multiply(-1.0 * factor);
			velocity = velocity.add(acceleration);
			position = position.add(velocity);
		}

	}

	// return points from a circle
	public ArrayList<Point2D> getPoints(){

		ArrayList<Point2D> list = new ArrayList<Point2D>();
		//list.add(new Point2D(position.getX(), position.getY())); // center
		list.add(new Point2D(position.getX() - radius, position.getY())); // left
		list.add(new Point2D(position.getX() + radius, position.getY())); // right
		list.add(new Point2D(position.getX(), position.getY() - radius)); // top
		list.add(new Point2D(position.getX(), position.getY() + radius)); // bottom
		list.add(new Point2D(position.getX() - (radius * 0.7), position.getY() - radius)); // left-top
		list.add(new Point2D(position.getX() + (radius * 0.7), position.getY() - radius)); // right-top
		list.add(new Point2D(position.getX() - (radius * 0.7), position.getY() + radius)); // left-bottom
		list.add(new Point2D(position.getX() + (radius * 0.7), position.getY() + radius)); // right-bottom
		return list;

	}

}