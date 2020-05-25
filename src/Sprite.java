import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Vector;

// Most of the components from this class borrowed from CS349 Sample Code: 2-8-Transformation/scene_graph  

/**
 * A building block for creating your own shapes that can be
 * transformed and that can respond to input. This class is
 * provided as an example; you will likely need to modify it
 * to meet the assignment requirements.
 * 
 * Michael Terry & Jeff Avery
 */
public abstract class Sprite {
    
    /**
     * Tracks our current interaction mode after a mouse-down
     */
    protected enum InteractionMode {
        IDLE,
        DRAGGING,
        SCALING,
        ROTATING
    }
    
    private Sprite parent = null;                               // Pointer to our parent
    private Vector<Sprite> children = new Vector<Sprite>();     // Holds all of our children
    private AffineTransform transform = new AffineTransform();  // Our transformation matrix
    protected AffineTransform rotation = new AffineTransform();
    protected Point2D lastPoint = null;                         // Last mouse point
    protected InteractionMode interactionMode = InteractionMode.IDLE;    // current state
    protected double cWidth, maxW, minW;
    protected double oldR = 0; 
    protected double radian = 0;
    protected Point2D first = new Point2D.Double();
    protected Point2D second = new Point2D.Double();
    protected AffineTransform changed = new AffineTransform();
    protected AffineTransform scaled = new AffineTransform();

    public Sprite() {
    }
    
    public Sprite(Sprite parent) {
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public void addChild(Sprite s) {
        children.add(s);
        s.setParent(this);
    }
    public Sprite getParent() {
        return parent;
    }
    private void setParent(Sprite s) {
        this.parent = s;
    }

    /**
     * Test whether a point, in world coordinates, is within our sprite.
     * @throws NoninvertibleTransformException 
     */
    public abstract boolean pointInside(Point2D p) throws NoninvertibleTransformException;

    /**
     * Handles a mouse down event, assuming that the event has already
     * been tested to ensure the mouse point is within our sprite.
     */
    protected void handleMouseDownEvent(MouseEvent e) {
        lastPoint = e.getPoint();
        if (e.getButton() == MouseEvent.BUTTON1) {
            interactionMode = InteractionMode.DRAGGING;
        }
        // Handle rotation, scaling mode depending on input
    }

    /**
     * Handle mouse drag event, with the assumption that we have already
     * been "selected" as the sprite to interact with.
     * This is a very simple method that only works because we
     * assume that the coordinate system has not been modified
     * by scales or rotations. You will need to modify this method
     * appropriately so it can handle arbitrary transformations.
     * @throws NoninvertibleTransformException 
     */
    protected void handleMouseDragEvent(MouseEvent e) throws NoninvertibleTransformException {
        Point2D p2 = lastPoint;
        Point2D p1 = e.getPoint();
        switch (interactionMode) {
            case IDLE:
                ; // no-op (shouldn't get here)
                break;
            case DRAGGING:
                double x_diff = p1.getX() - p2.getX();
                double y_diff = p1.getY() - p2.getY();
                AffineTransform translation = AffineTransform.getTranslateInstance(x_diff, y_diff);
                transform(translation);
                break;
            case ROTATING:
                ; // Provide rotation code here
                break;
            case SCALING:
                ; // Provide scaling code here
                break;
                
        }
        // Save our last point, if it's needed next time around
        lastPoint = e.getPoint();
    }
    
    protected void handleMouseUp(MouseEvent e) {
        interactionMode = InteractionMode.IDLE;
        // Do any other interaction handling necessary here
    }
    
    /**
     * Locates the sprite that was hit by the given event.
     * You *may* need to modify this method, depending on
     * how you modify other parts of the class.
     * 
     * @return The sprite that was hit, or null if no sprite was hit
     * @throws NoninvertibleTransformException 
     */
    public Sprite getSpriteHit(MouseEvent e) throws NoninvertibleTransformException {
        for (Sprite sprite : children) {
            Sprite s = sprite.getSpriteHit(e);
            if (s != null) {
                return s;
            }
        }
        if (this.pointInside(e.getPoint())) {
            return this;
        }
        return null;
    }
    
    /*
     * Important note: How transforms are handled here are only an example. You will
     * likely need to modify this code for it to work for your assignment.
     */
    
    /**
     * Returns the full transform to this object from the root
     */
    public AffineTransform getFullTransform() {
        AffineTransform returnTransform = new AffineTransform();
        Sprite curSprite = this;
        while (curSprite != null) {
            returnTransform.preConcatenate(curSprite.getLocalTransform());
            curSprite = curSprite.getParent();
        }
        return returnTransform;
    }

    /**
     * Returns our local transform
     */
    public AffineTransform getLocalTransform() {
        return (AffineTransform)transform.clone();
    }
    
    /**
     * Performs an arbitrary transform on this sprite
     */
    public void transform(AffineTransform t) {
        transform.concatenate(t);
    }

    protected void scale(Point2D p1, Point2D p2){
        double f = ((second.distance(p1)-second.distance(p2))/cWidth) + 1;
        if (cWidth * f > maxW || cWidth * f < minW){
            return;
        }
        cWidth = cWidth * f;
        scaled.concatenate(AffineTransform.getScaleInstance(1,f));
    }
    
    protected double angle(Point2D p1, Point2D p2){
        if (first.distance(p1) != 0){
        	double newY = p1.getY() - second.getY();
            double newX = p1.getX() - second.getX();
            double oldY = p2.getY() - second.getY();
            double oldX = p2.getX() - second.getX();
            return Math.atan(newY/newX) -  Math.atan(oldY/oldX);
        } else {
        	return 0;
        }
    }
    
    public void setRotation(AffineTransform t){
        rotation = t; 
        for (Sprite child : children){
            child.setRotation(rotation);
        }  
    }
    
    public void rotate(AffineTransform t) throws NoninvertibleTransformException {
        transform.concatenate(rotation.createInverse());
        rotation.concatenate(t);
        transform.concatenate(rotation);
        for (Sprite sprite : children) {
            sprite.setRotation(rotation);
        }
    }

    public void rotate1(AffineTransform t) {
        rotation.concatenate(t);
        for (Sprite sprite : children) {
            sprite.setRotation(rotation);
        }
    }

    public void needChange(int i) throws NoninvertibleTransformException{   
    	if (i == 1) {
    		transform.concatenate(changed);
    	} else {
    		transform.concatenate(changed.createInverse());	
    	}
    }

    
    /**
     * Draws the sprite. This method will call drawSprite after
     * the transform has been set up for this sprite.
     */
    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();
        // Set to our transform
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform currentAT = g2.getTransform();
        currentAT.concatenate(this.getFullTransform());
        g2.setTransform(currentAT);
        // Draw the sprite (delegated to sub-classes)
        this.drawSprite(g);
        // Restore original transform
        g.setTransform(oldTransform);
        // Draw children
        for (Sprite sprite : children) {
            sprite.draw(g);
        }
    }
    
    /**
     * The method that actually does the sprite drawing. This method
     * is called after the transform has been set up in the draw() method.
     * Sub-classes should override this method to perform the drawing.
     */
    protected abstract void drawSprite(Graphics2D g);
}
