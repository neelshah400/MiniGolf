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

public class Obstacle{

	private int nPoints;
	private double [] xPoints, yPoints;
	private double [] coordinates;
	private Polygon polygon;

	// nondefault constructor
	public Obstacle(int nPoints, double minX, double minY, double maxX, double maxY){

		this.nPoints = nPoints;
		xPoints = new double[nPoints];
		yPoints = new double[nPoints];
		for(int i = 0; i < nPoints; i++){
			xPoints[i] = (Math.random() * maxX) + minX;
			yPoints[i] = (Math.random() * maxY) + minY;
		}
		coordinates = combineAlt(nPoints, xPoints, yPoints);
		polygon = new Polygon(coordinates);

	}

	// nondefault constructor
	public Obstacle(double [] xPoints, double [] yPoints){

		nPoints = xPoints.length;
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		coordinates = combineAlt(nPoints, xPoints, yPoints);
		polygon = new Polygon(coordinates);

	}

	// combine arrays in alternating order
	public double [] combineAlt(int n, double [] a, double [] b){

		double [] arr = new double[n * 2];
		for(int i = 0; i < n * 2; i++){
			if(i % 2 == 0)
				arr[i] = a[i / 2];
			else
				arr[i] = b[i / 2];
		}
		return arr;

	}

	// getter methods
	public int getNPoints(){ return nPoints; }
	public double [] getXPoints(){ return xPoints; }
	public double [] getYPoints(){ return yPoints; }
	public double [] getCoordinates(){ return coordinates; }
	public Polygon getPolygon(){ return polygon; }

	// setter methods
	public void setNPoints(int nPoints){ this.nPoints = nPoints; }
	public void setXPoints(double [] xPoints){ this.xPoints = xPoints; }
	public void setYPoints(double [] yPoints){ this.yPoints = yPoints; }
	public void setCoordinates(double [] coordinates){ this.coordinates = coordinates; }
	public void setPolygon(Polygon polygon){ this.polygon = polygon; }

}