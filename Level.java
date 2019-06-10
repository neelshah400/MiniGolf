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

	// nondefault constructor
	public Level(int level){

		obstacles = new ArrayList<Obstacle>();
		if(level == 0 || level == 1){
			double [] xPoints = {250.0, 350.0, 300.0};
			double [] yPoints = {200.0, 200.0, 100.0};
			obstacles.add(new Obstacle(xPoints, yPoints));
		}

	}

	// getter methods
	public ArrayList<Obstacle> getObstacles(){ return obstacles; }

	// setter methods
	public void setObstacles(ArrayList<Obstacle> obstacles){ this.obstacles = obstacles; }

}