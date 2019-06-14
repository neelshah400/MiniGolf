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

public class NeelShah extends Application implements EventHandler<InputEvent>{

	// color variables
	Color bg, light, dark;

	// font variables
	Font xs, s, m, l, xl;

	// position variables
	int fieldX, fieldY, fieldW, fieldH;
	Rectangle2D field;

	// graphics setup variables
	GraphicsContext gc;
	AnimateObjects animate;

	// graphics variables
	Level level;
	Hole hole;
	Ball ball;

	// other variables
	int part;
	boolean status;
	int wait;
	Point2D launch;

	public static void main(String[]args){

		launch();

	}

	public void start(Stage stage){

		// color variables
		bg = Color.web("#00E676");
		light = Color.web("#66FFA6");
		dark = Color.web("#00B248");

		// font variables
		xs = Font.font("Roboto", FontWeight.NORMAL, 18);
		s = Font.font("Roboto", FontWeight.NORMAL, 24);
		m = Font.font("Roboto", FontWeight.NORMAL, 36);
		l = Font.font("Roboto", FontWeight.NORMAL, 48);
		xl = Font.font("Roboto", FontWeight.NORMAL, 64);

		// position variables
		fieldX = 0;
		fieldY = 0;
		fieldW = 600;
		fieldH = 600;

		// other variables
		part = 0;
		status = false;
		wait = 0;

		// graphics setup
		stage.setTitle("MiniGolf");
		Group root = new Group();
		Canvas canvas = new Canvas(fieldW, fieldH);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);

		// graphics
		gc = canvas.getGraphicsContext2D();
		field = new Rectangle2D(fieldX, fieldY, fieldW * 2, fieldH * 2);
		gc.setFill(bg);
		gc.fillRect(fieldX, fieldY, fieldW, fieldH);
		//level = new Level(2, 2, 3, 6, fieldX, fieldY, fieldW, fieldH);
		level = new Level(part);
		hole = new Hole(300.0, 50.0);
		ball = new Ball(300.0, 540.0);

		// animations setup
		animate = new AnimateObjects();
		animate.start();

		// event handlers
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
		//scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, this);

		// audio setup
		URL resource = getClass().getResource("test.wav");
		AudioClip clip = new AudioClip(resource.toString());
		//clip.play();

		// show stage
		stage.show();

	}

	public class AnimateObjects extends AnimationTimer{

		public void handle(long now){

			// background
			gc.clearRect(0, 0, fieldW, fieldH);
			field = new Rectangle2D(fieldX, fieldY, fieldW, fieldH);
			gc.setFill(bg);
			gc.fillRect(fieldX, fieldY, fieldW, fieldH);

			// menu
			if(part == 0){
				gc.setFill(Color.BLACK);
				gc.setFont(xl);
				gc.fillText("MiniGolf", 175, 64);
				gc.setFont(m);
				gc.fillText("Press any key to start.", 125, fieldH - 10);
			}

			// game
			else{

				// text
				gc.setFill(Color.BLACK);
				gc.setFont(m);
				gc.fillText("Level " + part, 0, 36);

				// obstacles
				gc.setFill(dark);
				for(Obstacle obstacle : level.getObstacles())
					gc.fillPolygon(obstacle.getXPoints(), obstacle.getYPoints(), obstacle.getNPoints());

				// hole
				gc.setFill(Color.BLACK);
				gc.fillOval(hole.getPosX() - hole.getSize(), hole.getPosY() - hole.getSize(), hole.getSize() * 2, hole.getSize() * 2);

				// ball
				gc.setFill(Color.WHITE);
				gc.fillOval(ball.getPosX() - ball.getSize(), ball.getPosY() - ball.getSize(), ball.getSize() * 2, ball.getSize() * 2);
				ball.move();
				ball.bounce(level, launch, fieldX, fieldY, fieldW, fieldH);
				if(Math.abs(ball.getVelX()) < 2.0 && Math.abs(ball.getVelY()) < 2.0){
					if(hole.countPoints(ball.getPoints()) == ball.getPoints().size()){
						ball.setVelX(0.0);
						ball.setVelY(0.0);
						ball.setAccX(0.0);
						ball.setAccY(0.0);
						status = true;
					}
				}
				else{
					status = false;
					wait = 0;
				}

				// changing part
				if(status)
					wait++;
				if(wait == 60){
					part++;
					wait = 0;
					status = false;
					if(part != 1){
						//level = new Level(2, 2, 3, 6, fieldX, fieldY, fieldW, fieldH);
						level = new Level(part);
						hole = new Hole(300.0, 50.0);
						ball = new Ball(300.0, 540.0);
					}
				}

			}

		}

	}

	public void handle(final InputEvent event){

		// menu
		if(part == 0){
			if(event instanceof KeyEvent)
				part++;
		}

		// game
		else{

			// drag mouse to move ball
			if(wait <= 1 && event instanceof MouseEvent && Math.round(Math.abs(ball.getVelX()) * 100) / 100 == 0.0 && Math.round(Math.abs(ball.getVelY()) * 100) / 100 == 0.0){
				launch = new Point2D(((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				double disX = ball.getPosX() - ((MouseEvent)event).getX();
				double disY = ball.getPosY() - ((MouseEvent)event).getY();
				ball.setVelX(ball.getVelX() + disX / 20);
				ball.setVelY(ball.getVelY() + disY / 20);
			}

		}

	}

}
