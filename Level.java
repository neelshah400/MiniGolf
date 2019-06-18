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
	private ArrayList<Heart> hearts;


	// nondefault constructor
	public Level(int part){


		obstacles = new ArrayList<Obstacle>();
		hearts = new ArrayList<Heart>();

		if(part == 0 || part == 1){
			obstacles.add(new Obstacle(new double[]{300.0, 525.0, 300.0, 75.0}, new double[]{125.0, 300.0, 475.0, 300.0}));
			hearts.add(new Heart(150.0, 150.0));
		}
		else if(part == 2){
			obstacles.add(new Obstacle(new double[]{0.0, 0.0, 425.0}, new double[]{150.0, 550.0, 150.0}));
			obstacles.add(new Obstacle(new double[]{600.0, 600.0, 175.0}, new double[]{500.0, 50.0, 500.0}));
			hearts.add(new Heart(200.0, 400.0));
			hearts.add(new Heart(300.0, 300.0));
			hearts.add(new Heart(400.0, 200.0));
		}
		else if(part == 3){
			obstacles.add(new Obstacle(new double[]{0.0, 400.0, 400.0, 0.0}, new double[]{350.0, 350.0, 500.0, 500.0}));
			obstacles.add(new Obstacle(new double[]{600.0, 500.0, 500.0, 600.0}, new double[]{350.0, 350.0, 500.0, 500.0}));
			obstacles.add(new Obstacle(new double[]{0.0, 100.0, 100.0, 0.0}, new double[]{150.0, 150.0, 300.0, 300.0}));
			obstacles.add(new Obstacle(new double[]{600.0, 200.0, 200.0, 600.0}, new double[]{150.0, 150.0, 300.0, 300.0}));
			hearts.add(new Heart(150.0, 200.0));
			hearts.add(new Heart(450.0, 400.0));
		}
		else if(part == 4){
			obstacles.add(new Obstacle(new double[]{0.0, 400.0, 400.0, 0.0}, new double[]{400.0, 400.0, 500.0, 500.0}));
			obstacles.add(new Obstacle(new double[]{600.0, 500.0, 500.0, 600.0}, new double[]{400.0, 400.0, 500.0, 500.0}));
			obstacles.add(new Obstacle(new double[]{0.0, 100.0, 100.0, 0.0}, new double[]{250.0, 250.0, 350.0, 350.0}));
			obstacles.add(new Obstacle(new double[]{600.0, 200.0, 200.0, 600.0}, new double[]{250.0, 250.0, 350.0, 350.0}));
			obstacles.add(new Obstacle(new double[]{0.0, 300.0, 300.0, 0.0}, new double[]{100.0, 100.0, 200.0, 200.0}));
			obstacles.add(new Obstacle(new double[]{600.0, 400.0, 400.0, 600.0}, new double[]{100.0, 100.0, 200.0, 200.0}));
			hearts.add(new Heart(350.0, 150.0));
			hearts.add(new Heart(150.0, 300.0));
			hearts.add(new Heart(450.0, 450.0));
		}
		else if(part == 5){
			obstacles.add(new Obstacle(new double[]{0.0, 150.0, 250.0, 250.0, 375.0, 350.0, 0.0}, new double[]{100.0, 100.0, 200.0, 300.0, 400.0, 500.0, 500.0}));
			obstacles.add(new Obstacle(new double[]{600.0, 225.0, 350.0, 350.0, 450.0, 450.0, 600.0}, new double[]{100.0, 100.0, 200.0, 300.0, 400.0, 500.0, 500.0}));
			hearts.add(new Heart(250.0, 150.0));
			hearts.add(new Heart(300.0, 300.0));
			hearts.add(new Heart(400.0, 450.0));
		}

	}

	// getter methods
	public ArrayList<Obstacle> getObstacles(){ return obstacles; }
	public ArrayList<Heart> getHearts(){ return hearts; }

	// setter methods
	public void setObstacles(ArrayList<Obstacle> obstacles){ this.obstacles = obstacles; }
	public void setHearts(ArrayList<Heart> hearts){ this.hearts = hearts; }

	// checks how many of the points specified are intersected by the rectangle
	public int countPoints(Heart heart, ArrayList<Point2D> list){

		int count = 0;
		for(Point2D point : list){
			if(heart.getRectangle().contains(point))
				count++;
		}
		return count;

	}

	// remove hearts when hitten by ball
	public int removeHearts(GolfBall ball){

		int removed = 0;
		int i = 0;
		while(i < hearts.size()){
			if(countPoints(hearts.get(i), ball.getPoints()) > 0){
				removed++;
				hearts.remove(i);
			}
			else
				i++;
		}
		return removed;

	}

}