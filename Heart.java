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

public class Heart{

	// instance variables
	private Point2D position;
	private Image image;
	private Rectangle2D rectangle;

	// nondefault constructor
	public Heart(double x, double y){

		position = new Point2D(x, y);
		image = new Image("Heart.png");
		rectangle = new Rectangle2D(x, y, image.getWidth(), image.getHeight());

	}

	// getter methods
	public Point2D getPosition(){ return position; }
	public Image getImage(){ return image; }
	public Rectangle2D getRectangle(){ return rectangle; }

	// setter methods
	public void setPosition(Point2D position){ this.position = position; }
	public void setImage(Image image){ this.image = image; }
	public void setRectangle(Rectangle2D rectangle){ this.rectangle = rectangle; }

}