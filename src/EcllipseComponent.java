
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;

//Most of the components from this class borrowed from CS349 Sample Code: 2-8-Transformation/scene_graph  

/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry & Jeff Avery
 */
public class EcllipseComponent extends Sprite {

	private Ellipse2D circle = null;
	
	/**
	 * Creates a rectangle based at the origin with the specified width and
	 * height
	 */
	public EcllipseComponent(int diameter) {
		super();
		this.initialize(diameter, diameter);
		double i = diameter / 2;
		second.setLocation(i, diameter);
	}

	/**
	 * Creates a rectangle based at the origin with the specified width, height,
	 * and parent
	 */
	public EcllipseComponent(int width, int height, Sprite parentSprite) {
		super(parentSprite);
		this.initialize(width, height);
	}

	private void initialize(int width, int height) {
		circle = new Ellipse2D.Double(0, 0, width, height);
	}

	protected void handleMouseDragEvent(MouseEvent e) throws NoninvertibleTransformException {
		Point2D oldP = lastPoint;
		Point2D newP = e.getPoint();
		switch (interactionMode) {
        case IDLE:
            ; // no-op (shouldn't get here)
            break;
        case DRAGGING:
            AffineTransform fullTransform = this.getFullTransform();
            AffineTransform inverseTransform = null;
            inverseTransform = fullTransform.createInverse();
            inverseTransform.transform(newP, newP);
            double degree = angle(newP, oldP);
            if (radian < 0.61 && radian > -1 * 0.41){                    
                radian += degree;
                rotate(AffineTransform.getRotateInstance(degree, second.getX(), second.getY()));
                oldR = degree;
            } 
            if (Math.abs(radian + degree) < 0.41){
                radian += degree;
                rotate(AffineTransform.getRotateInstance(degree, second.getX(), second.getY()));
            }
            break;
    }
    // Save our last point, if it's needed next time around
    lastPoint = e.getPoint();
	}

	/**
	 * Test if our rectangle contains the point specified.
	 * 
	 * @throws NoninvertibleTransformException
	 */
	public boolean pointInside(Point2D p) throws NoninvertibleTransformException {
		AffineTransform fullTransform = this.getFullTransform();
		AffineTransform inverseTransform = null;
		inverseTransform = fullTransform.createInverse();
		Point2D newP = (Point2D) p.clone();
		inverseTransform.transform(newP, newP);
		return circle.contains(newP);
	}

	protected void drawSprite(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.draw(circle);
	}

	public String toString() {
		return "circle: " + circle;
	}
}
