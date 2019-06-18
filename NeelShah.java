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
	GolfBall ball;

	// audio variables
	AudioClip background, beep, success;

	// image variables
	Image heart;

	// other variables
	int part;
	boolean status;
	int wait;
	Point2D launch;
	int lives;

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
		part = 5;
		status = false;
		wait = 0;
		lives = 50;

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
		level = new Level(part);
		hole = new Hole(300.0, 50.0);
		ball = new GolfBall(300.0, 540.0);

		// animations setup
		animate = new AnimateObjects();
		animate.start();

		// event handlers
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, this);

		// audio setup
		background = new AudioClip(getClass().getResource("Background.mp3").toString());
		background.play();
		beep = new AudioClip(getClass().getResource("Beep.mp3").toString());
		success = new AudioClip(getClass().getResource("Success.mp3").toString());

		// image setup
		heart = new Image("BigHeart.png");

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
				gc.fillText("- Controls: drag mouse to shoot", 10, 150);
				gc.fillText("- Movement: bouncing is allowed", 10, 200);
				gc.fillText("- Lives: " + lives, 10, 250);
				gc.fillText("- Collect hearts to gain lives", 10, 300);
				gc.fillText("- Ball may get stuck at slow speeds", 10, 350);
				gc.fillText("Press any key to start.", 125, fieldH - 10);
			}

			// game
			else if(part <= 5){

				// text
				gc.setFill(Color.BLACK);
				gc.setFont(m);
				gc.fillText("Level " + part, 10, 46);

				// lives
				gc.drawImage(heart, 500.0, 10.0);
				gc.fillText("" + lives, 550.0, 46.0);

				// obstacles
				gc.setFill(dark);
				for(Obstacle obstacle : level.getObstacles())
					gc.fillPolygon(obstacle.getXPoints(), obstacle.getYPoints(), obstacle.getNPoints());
				for(Heart heart : level.getHearts())
					gc.drawImage(heart.getImage(), heart.getPosition().getX(), heart.getPosition().getY());
				if(level.removeHearts(ball) > 0){
					lives += level.removeHearts(ball);
					beep.play();
				}

				// hole
				gc.setFill(Color.BLACK);
				gc.fillOval(hole.getPosition().getX() - hole.getRadius(), hole.getPosition().getY() - hole.getRadius(), hole.getRadius() * 2, hole.getRadius() * 2);

				// ball
				gc.setFill(Color.WHITE);
				gc.fillOval(ball.getPosition().getX() - ball.getRadius(), ball.getPosition().getY() - ball.getRadius(), ball.getRadius() * 2, ball.getRadius() * 2);
				ball.move(level, launch, fieldX, fieldY, fieldW, fieldH);
				if(Math.abs(ball.getVelocity().getX()) < 2.0 && Math.abs(ball.getVelocity().getY()) < 2.0){
					if(hole.countPoints(ball.getPoints()) == ball.getPoints().size()){
						ball.setVelocity(new Point2D(0.0, 0.0));
						ball.setAcceleration(new Point2D(0.0, 0.0));
						status = true;
					}
				}
				else{
					status = false;
					wait = 0;
				}

				// changing part
				if(lives <= 0)
					part = 7;
				if(status)
					wait++;
				if(wait == 1)
					success.play();
				if(wait == 240){
					part++;
					wait = 0;
					status = false;
					if(part >= 1){
						level = new Level(part);
						hole = new Hole(300.0, 50.0);
						ball = new GolfBall(300.0, 540.0);
					}
				}

			}

			// winning
			else if(part == 6){
				gc.setFill(Color.BLACK);
				gc.setFont(xl);
				gc.fillText("You Won", 175, 64);
				gc.setFont(m);
				gc.fillText("Press any key to play again.", 100, fieldH - 10);
				status = false;
				wait = 0;
				lives = 50;
			}

			// losing
			else{
				gc.setFill(Color.BLACK);
				gc.setFont(xl);
				gc.fillText("You Lost", 175, 64);
				gc.setFont(m);
				gc.fillText("Press any key to play again.", 100, fieldH - 10);
				status = false;
				wait = 0;
				lives = 50;
			}

		}

	}

	public void handle(final InputEvent event){

		// menu
		if(part == 0){
			if(event instanceof KeyEvent)
				part++;
		}

		// winning and losing
		if(part > 5){
			if(event instanceof KeyEvent)
				part = 0;
		}

		// game
		else{

			// drag mouse to move ball
			if(wait <= 1 && event instanceof MouseEvent && Math.round(Math.abs(ball.getVelocity().getX()) * 100) / 100 == 0.0 && Math.round(Math.abs(ball.getVelocity().getY()) * 100) / 100 == 0.0){
				launch = new Point2D(((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				Point2D mouse = new Point2D(((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				ball.setVelocity((ball.getPosition().subtract(mouse).multiply(1 / 20.0)));
				lives--;
			}

		}

	}

}
