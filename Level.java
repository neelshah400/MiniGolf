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

public class Level{

	// instance variables
	private ArrayList<Obstacle> obstacles;

	// nondefault constructor
	public Level(int minObstacles, int maxObstacles, int minPoints, int maxPoints, double minX, double minY, double maxX, double maxY){

		int nObstacles = (int)(Math.random() * (maxObstacles - minObstacles + 1)) + minObstacles;
		obstacles = new ArrayList<Obstacle>();
		for(int i = 0; i < nObstacles; i++){
			int nPoints = (int)(Math.random() * (maxPoints - minPoints + 1)) + minPoints;
			Obstacle obstacle = new Obstacle(nPoints, minX, minY, maxX, maxY);
			obstacles.add(obstacle);
		}

	}

	// getter methods
	public ArrayList<Obstacle> getObstacles(){ return obstacles; }

	// setter methods
	public void setObstacles(ArrayList<Obstacle> obstacles){ this.obstacles = obstacles; }

	/*// fill obstacles
	public void fillObstacles(double minX, double minY, double maxX, double maxY){

		for(int i = 0; i < numObstacles; i++){
			int numPoints = (int)(Math.random() * (maxPoints - minPoints + 1) + minPoints);
			double [] xPoints = new double[numPoints];
			double [] yPoints = new double[numPoints];
			for(int j = 0; j < numPoints; j++){
				xPoints[j] = (int)(Math.random() * maxX) + minX;
				yPoints[j] = (int)(Math.random() * maxY) + minY;
			}
			double [] coordinates = new double[numPoints * 2];
			for(int j = 0; j < coordinates.length; j++){
				if(j % 2 == 0)
					coordinates[j] = xPoints[j / 2];
				else
					coordinates[j] = yPoints[j / 2];
			}
			Polygon polygon = new Polygon(coordinates);
			obstacles.add(polygon);
		}

	}*/

}