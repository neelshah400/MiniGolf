// JavaFX imports
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

// other imports
import java.util.ArrayList;

public class MiniGolf extends Application implements EventHandler<InputEvent>{

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

	double holeX, holeY, holeSize;
	Circle hole;
	Rectangle2D holeRect;

	double posX, posY, size, velX, velY, accX, accY;
	Circle golfBall;
	Rectangle2D ballRect;

	// other variables
	int level;

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
		level = 0;

		// graphics setup
		stage.setTitle("MiniGolf");
		Group root = new Group();
		Canvas canvas = new Canvas(fieldW, fieldH);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);

		// graphics

		gc = canvas.getGraphicsContext2D();
		field = new Rectangle2D(fieldX, fieldY, fieldW, fieldH);
		gc.setFill(bg);
		gc.fillRect(fieldX, fieldY, fieldW, fieldH);

		holeX = 50.0;
		holeY = 50.0;
		holeSize = 50.0;

		posX = 300.0;
		posY = 300.0;
		size = 30.0;
		velX = 0.0;
		velY = 0.0;
		accX = 0.0;
		accY = 0.0;

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

		// print
		System.out.println("posX\tposY\tsize\tvelX\tvelY\taccX\taccY");
		for(int i = 0; i < 56; i++)
			System.out.print("-");
		System.out.println();

	}

	public class AnimateObjects extends AnimationTimer{

		public void handle(long now){

			// print
			System.out.println(
				(double)Math.round(posX * 100) / 100 + "\t" +
				(double)Math.round(posY * 100) / 100 + "\t" +
				(double)Math.round(size * 100) / 100 + "\t" +
				(double)Math.round(velX * 100) / 100 + "\t" +
				(double)Math.round(velY * 100) / 100 + "\t" +
				(double)Math.round(accX * 100) / 100 + "\t" +
				(double)Math.round(accY * 100) / 100
			);

			// background
			gc.clearRect(0, 0, fieldW, fieldH);
			field = new Rectangle2D(fieldX, fieldY, fieldW, fieldH);
			gc.setFill(bg);
			gc.fillRect(fieldX, fieldY, fieldW, fieldH);

			// menu
			if(level == 0){
				gc.setFill(dark);
				gc.setFont(xl);
				gc.fillText("MiniGolf", 175, 64);
				gc.setFont(m);
				gc.fillText("Press any key to start.", 125, fieldH - 10);
			}

			// game
			else{

				gc.setFill(dark);
				gc.setFont(xl);
				gc.fillText("Level " + level, 200, 64);

				hole = new Circle(holeX, holeY, holeSize);
				gc.setFill(Color.ORANGE);
				gc.fillOval(holeX, holeY, holeSize, holeSize);
				holeRect = new Rectangle2D(holeX - holeSize, holeY - holeSize, 2 * holeSize, 2 * holeSize);

				golfBall = new Circle(posX, posY, size);
				gc.setFill(Color.WHITE);
				gc.fillOval(posX, posY, size, size);
				ballRect = new Rectangle2D(posX - (0.5 * size), posY - (0.5 * size), size, size);
				velX += accX;
				posX += velX;
				velY += accY;
				posY += velY;

				if(velX < 0)
					accX = 0.02;
				else if(velX > 0)
					accX = -0.02;
				else
					accX = 0.0;
				if(velY < 0)
					accY = 0.02;
				else if(velY > 0)
					accY = -0.02;
				else
					accY = 0.0;

				if(posX < fieldX || posX > fieldW - size){
					velX *= -1.0;
					accX *= -1.0;
				}
				if(posY < fieldY || posY > fieldH - size){
					velY *= -1.0;
					accY *= -1.0;
				}

				if(holeRect.contains(ballRect)){
					level++;
				}

				// level 1
				if(level == 1){
					//
				}

			}

		}

	}

	public void handle(final InputEvent event){

		// menu
		if(level == 0){
			if(event instanceof KeyEvent)
				level++;
		}

		// game
		else{

			if(event instanceof MouseEvent){
				double disX = posX - ((MouseEvent)event).getX();
				double disY = posY - ((MouseEvent)event).getY();
				velX += disX / 60;
				velY += disY / 60;
			}


			// level 1
			if(level == 1){
				//
			}

		}

	}

}
