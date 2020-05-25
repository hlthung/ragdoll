import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

//Most of the components from this class borrowed from CS349 Sample Code: 2-8-Transformation/scene_graph  

/**
 * A simple demo of how to create a elpangular sprite.
 * 
 * Michael Terry & Jeff Avery
 */
public class ForearmComponent extends Sprite {

	private Ellipse2D elp = null;
	
    /**
     * Creates a rectangle based at the origin with the specified
     * width and height
     */
    public ForearmComponent(int width, int height) {
        super();
        this.initialize(width, height);
        cWidth = width;
        maxW = 1.5 * width;
        minW = 1 * width;
        double i = height/2;
        second.setLocation(0,i);
    }
    /**
     * Creates a elpangle based at the origin with the specified
     * width, height, and parent
     */
    public ForearmComponent(int width, int height, Sprite parentSprite) {
        super(parentSprite);
        this.initialize(width, height);
    }
    
    private void initialize(int width, int height) {
    	elp = new Ellipse2D.Double(0, 0, width, height);
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
                if (Math.abs(radian + degree) < 2.36){
                    radian += degree;
                    rotate(AffineTransform.getRotateInstance(degree, second.getX(), second.getY()));
                }
                if (radian < 2.36 && radian > -1 * 2.36){                    
                    radian += degree;
                    rotate(AffineTransform.getRotateInstance(degree, second.getX(), second.getY()));
                    oldR = degree;
                }            
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
        return elp.contains(newP);
    }

    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(elp);
    }
    
    public String toString() {
        return "elpangleSprite: " + elp;
    }
}
