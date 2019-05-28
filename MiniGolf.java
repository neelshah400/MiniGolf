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
	int posX, posY, size, velX, velY, accX, accY;
	Circle golfBall;

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
		posX = 100;
		posY = 100;
		size = 30;

		// animations setup
		animate = new AnimateObjects();
		animate.start();

		// event handlers
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
		scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

		// audio setup
		URL resource = getClass().getResource("test.wav");
		AudioClip clip = new AudioClip(resource.toString());
		clip.play();

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
			if(level == 0){
				gc.setFill(dark);
				gc.setFont(xl);
				gc.fillText("MiniGolf", 175, 64);
				gc.setFont(m);
				gc.fillText("Press any key to start.", 125, fieldH - 10);
			}

			// game
			else{

				golfBall = new Circle(posX, posY, size);
				gc.setFill(Color.WHITE);
				gc.fillOval(posX, posY, size, size);
				velX += accX;
				posX += velX;
				velY += accY;
				posY += velY;

				// level 1
				if(level == 1){
					gc.setFill(dark);
					gc.setFont(xl);
					gc.fillText("Level 1", 200, 64);
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


			// level 1
			if(level == 1){
				//
			}

		}

	}

}
