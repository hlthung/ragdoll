import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.event.MouseEvent;

//Most of the components from this class borrowed from CS349 Sample Code: 2-8-Transformation/scene_graph  

/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry & Jeff Avery
 */
public class ArmComponent extends Sprite {

	private Ellipse2D rect = null;
	
    /**
     * Creates a rectangle based at the origin with the specified
     * width and height
     */
    public ArmComponent(int width, int height) {
        super();
        this.initialize(width, height);
        cWidth = width;
        maxW = 1.5 * width;
        minW = 1 * width;
        double i = height/2;
        second.setLocation(0,i);
    }
    /**
     * Creates a rectangle based at the origin with the specified
     * width, height, and parent
     */
    public ArmComponent(int width, int height, Sprite parentSprite) {
        super(parentSprite);
        this.initialize(width, height);
    }
    
    private void initialize(int width, int height) {
    	rect = new Ellipse2D.Double(0, 0, width, height);
    }

    @Override
    protected void handleMouseDragEvent(MouseEvent e) throws NoninvertibleTransformException {
        Point2D old = lastPoint;
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
                double degree = angle(newP, old);
                rotate(AffineTransform.getRotateInstance(degree, second.getX(), second.getY()));
                oldR = degree;
                break;
        }
        // Save our last point, if it's needed next time around
        lastPoint = e.getPoint();
    }
 
    /**
     * Test if our rectangle contains the point specified.
     * @throws NoninvertibleTransformException 
     */
    public boolean pointInside(Point2D p) throws NoninvertibleTransformException {
        AffineTransform fullTransform = this.getFullTransform();
        AffineTransform inverseTransform = null;
        inverseTransform = fullTransform.createInverse();
        Point2D newP = (Point2D)p.clone();
        inverseTransform.transform(newP, newP);
        return rect.contains(newP);
    }

    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(rect);
    }
    
    public String toString() {
        return "RectangleSprite: " + rect;
    }
}
