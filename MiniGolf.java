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

public class MiniGolf extends Application{

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

		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image trooper = new Image("trooper.jpg");
		gc.drawImage(trooper, 180, 100);

		stage.show();

	}

	public class AnimateObjects extends AnimationTimer{

		public void handle(long now){

			//

		}

	}

}