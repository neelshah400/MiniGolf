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

public class Ball{

	// instance variables
	private double posX, posY, size, velX, velY, accX, accY;
	private Circle ball;
	private double factor;

	// nondefault constructor
	public Ball(double posX, double posY){

		this.posX = posX;
		this.posY = posY;
		size = 15.0;
		velX = 0.0;
		velY = 0.0;
		accX = 0.0;
		accY = 0.0;
		ball = new Circle(posX, posY, size);
		factor = 0.01;

	}

	// getter methods
	public double getPosX(){ return posX; }
	public double getPosY(){ return posY; }
	public double getSize(){ return size; }
	public double getVelX(){ return velX; }
	public double getVelY(){ return velY; }
	public double getAccX(){ return accX; }
	public double getAccY(){ return accY; }
	public Circle getBall(){ return ball; }

	// setter methods
	public void setPosX(double posX){ this.posX = posX; }
	public void setPosY(double posY){ this.posY = posY; }
	public void setSize(double size){ this.size = size; }
	public void setVelX(double velX){ this.velX = velX; }
	public void setVelY(double velY){ this.velY = velY; }
	public void setAccX(double accX){ this.accX = accX; }
	public void setAccY(double accY){ this.accY = accY; }
	public void setBall(Circle ball){ this.ball = ball; }

	// position, velocity, and acceleration
	public void move(){

		/*if(velX < 0)
			accX = 0.02;
		else if(velX > 0)
			accX = -0.02;
		else
			accX = 0.0;*/
		accX = -1.0 * factor * velX;
		velX += accX;
		/*if(velY < 0)
			accY = 0.02;
		else if(velY > 0)
			accY = -0.02;
		else
			accY = 0.0;*/
		accY = -1.0 * factor * velY;
		velY += accY;
		posX += velX;
		posY += velY;

		// print
		//System.out.println("pos: (" + (double)Math.round(posX * 100) / 100 + ", " + (double)Math.round(posY * 100) / 100 + ")\tvel: (" + (double)Math.round(velX * 100) / 100 + ", " + (double)Math.round(velY * 100) / 100 + ")\tacc: (" + (double)Math.round(accX * 100) / 100 + ", " + (double)Math.round(accY * 100) / 100 + ")");

	}

	// bounce off walls
	public void bounce(Level level, Point2D launch, double fieldX, double fieldY, double fieldW, double fieldH){

		boolean collision = false;
		double minDistance = (double)(Integer.MAX_VALUE);
		Line nearestLine = new Line(0.0, 0.0, 0.0, 0.0);
		for(Obstacle obstacle : level.getObstacles()){
			for(Point2D point : getPoints()){
				if(obstacle.getPolygon().contains(point)){
					collision = true;
					ArrayList<Line> lines = obstacle.getLines();
					for(Line line : lines){
						double [] distances = new double[3];
						distances[0] = point.distance(line.getStartX(), line.getStartY());
						distances[1] = point.distance((line.getStartX() + line.getEndX()) / 2, (line.getStartY() + line.getEndY() / 2));
						distances[2] = point.distance(line.getEndX(), line.getEndY());
						for(double distance : distances){
							if(distance < minDistance){
								minDistance = distance;
								nearestLine = line;
							}
						}
					}
					//velX = 0.0;
					//velY = 0.0;
					/*velX *= -1.0;
					accX *= -1.0;
					velY *= -1.0;
					accY *= -1.0;
					if(factor == 0.01)
						factor = 0.03;*/
				}
			}
		}
		if(collision){
			//double angle = (new Point2D(1, 0)).angle(nearestPoint.subtract(launch));
			double slopeLine = (nearestLine.getEndY() - nearestLine.getStartY()) / (nearestLine .getEndX() - nearestLine.getStartX());
			double slopeLaunch = (getPosY() - launch.getY()) / (getPosX() - launch.getX());
			System.out.println(slopeLine + " " + slopeLaunch);
			double slope = -1.0 * slopeLaunch / slopeLine;
			System.out.println(slope);
			if(slope > 0){
				velX *= -1.0;
			}
			else{
				velY *= -1.0;
			}
			//velX = -1.0 * slopeLaunch * slopeLine;
			//velY = -1.0 * slopeLaunch * slopeLine;
		}

		if(posX - size <= fieldX || posX >= fieldW - size){
			velX *= -1.0;
			//velX *= 0.7;
			//velY *= 0.7;
			accX *= -1.0;
			if(factor == 0.01)
				factor = 0.03;
		}
		if(posY - size <= fieldY || posY >= fieldH - size){
			velY *= -1.0;
			//velX *= 0.7;
			//velY *= 0.7;
			accY *= -1.0;
			if(factor == 0.01)
				factor = 0.03;
		}


	}

	// return points from a circle
	public ArrayList<Point2D> getPoints(){

		ArrayList<Point2D> list = new ArrayList<Point2D>();
		//list.add(new Point2D(posX, posY)); // center
		list.add(new Point2D(posX - size, posY)); // left
		list.add(new Point2D(posX + size, posY)); // right
		list.add(new Point2D(posX, posY - size)); // top
		list.add(new Point2D(posX, posY + size)); // bottom
		list.add(new Point2D(posX - size, posY - size)); // left-top
		list.add(new Point2D(posX + size, posY - size)); // right-top
		list.add(new Point2D(posX - size, posY + size)); // left-bottom
		list.add(new Point2D(posX + size, posY + size)); // right-bottom
		return list;

	}

}