import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

//Most of the components from this class borrowed from CS349 Sample Code: 2-8-Transformation/scene_graph  

/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry & Jeff Avery
 */
public class ThighComponent extends Sprite {

    private Rectangle2D rect = null;
    private boolean j = false;

    /**
     * Creates a rectangle based at the origin with the specified
     * width and height
     */
    public ThighComponent(int width, int height) {
        super();
        this.initialize(width, height);
        cWidth = width;
        maxW = 1.5 * width;
        minW = 1 * width;
        double i = height / 2;
        second.setLocation(0, i);
    }
    /**
     * Creates a rectangle based at the origin with the specified
     * width, height, and parent
     */
    public ThighComponent(int width, int height, Sprite parentSprite) {
        super(parentSprite);
        this.initialize(width, height);
    }
    
    private void initialize(int width, int height) {
        rect = new Rectangle2D.Double(0, 0, width, height);
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
                AffineTransform inverse = null;
                inverseTransform = fullTransform.createInverse();
                inverse = rotation.createInverse();
                inverseTransform.transform(newP, newP);
                double degree = angle(newP, oldP);
                if (Math.abs(radian + degree) < 1.55){
                    radian += degree;
                    rotate1(AffineTransform.getRotateInstance(degree, second.getX(), second.getY()));
                }
                if (radian < 1.55 && radian > -1 * 1.55){                    
                    radian += degree;
                    rotate1(AffineTransform.getRotateInstance(degree, second.getX(), second.getY()));
                    oldR = degree;
                } 
                 
                inverseTransform.transform(oldP, oldP);
                scale(newP,oldP);
                if (scaled != null){
                    if (j == false){
                        transform(inverse);
                    }
                    needChange(0);
                    changed = new AffineTransform();
                    changed.concatenate(rotation);
                    needChange(1);
                    j = true;
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
