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

public class Hole{

	// instance variables
	private Point2D position;
	private double radius;
	private Circle circle;

	// nondefault constructor
	public Hole(double x, double y){

		position = new Point2D(x, y);
		radius = 25.0;
		circle = new Circle(x, y, radius);

	}

	// getter methods
	public Point2D getPosition(){ return position; }
	public double getRadius(){ return radius; }
	public Circle getCircle(){ return circle; }

	// checks how many of the points specified are inside the hole
	public int countPoints(ArrayList<Point2D> list){

		int count = 0;
		for(Point2D point : list){
			if(getCircle().contains(point))
				count++;
		}
		return count;

	}

}