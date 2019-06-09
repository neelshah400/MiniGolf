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
	private double posX, posY, size;
	private Circle hole;

	// nondefault constructor
	public Hole(double posX, double posY){

		this.posX = posX;
		this.posY = posY;
		size = 25.0;
		hole = new Circle(posX, posY, size);

	}

	// getter methods
	public double getPosX(){ return posX; }
	public double getPosY(){ return posY; }
	public double getSize(){ return size; }
	public Circle getHole(){ return hole; }

	// setter methods
	public void setPosX(double posX){ this.posX = posX; }
	public void setPosY(double posY){ this.posY = posY; }
	public void setSize(double size){ this.size = size; }
	public void setBall(Circle ball){ this.hole = hole; }

	// checks how many of the points specified are inside the hole
	public int countPoints(ArrayList<Point2D> list){

		int count = 0;
		for(Point2D point : list){
			if(getHole().contains(point))
				count++;
		}
		return count;

	}

}