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

public class MiniGolf extends Application implements EventHandler<InputEvent>{

	GraphicsContext gc;
	Image trooper;
	int x;
	AnimateObjects animate;

	public static void main(String[]args){

		launch();

	}

	public void start(Stage stage){

		stage.setTitle("MiniGolf");
		Group root = new Group();
		Canvas canvas = new Canvas(800, 400); // change canvas size
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);

		gc = canvas.getGraphicsContext2D();
		trooper = new Image("trooper.jpg");
		gc.drawImage(trooper, 180, 100);

		animate = new AnimateObjects();
		animate.start();

		scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
		scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

		URL resource = getClass().getResource("test.wav");
		AudioClip clip = new AudioClip(resource.toString());
		clip.play();

		stage.show();

	}

	public class AnimateObjects extends AnimationTimer{

		public void handle(long now){

			if (x > -50) {
				//x++;
				gc.drawImage(trooper, 180 + x, 100);

				Rectangle2D rect1 = new Rectangle2D(400, 100, 100, 100);
				gc.fillRect(400, 100, 100, 100);

				Rectangle2D rect2 = new Rectangle2D(180 + x, 100, trooper.getWidth(), trooper.getHeight());
				if(rect1.intersects(rect2))
					System.out.println("HIT");
			}
			else{
				gc.setFill(Color.YELLOW);
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(1);
				Font font = Font.font("Arial", FontWeight.NORMAL, 48);
				gc.setFont(font);
				gc.fillText("Game Over", 100, 50);
				gc.strokeText("Game Over", 100, 50);
			}

		}

	}

	public void handle(final InputEvent event){

		if(event instanceof KeyEvent){
			if(((KeyEvent)event).getCode() == KeyCode.LEFT)
				x--;
		}
		if(event instanceof MouseEvent){
			System.out.println(((MouseEvent)event).getX());
			System.out.println(((MouseEvent)event).getY());
		}

	}

}