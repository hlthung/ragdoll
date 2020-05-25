import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.*;

//Most of the components from this class borrowed from CS349 Sample Code: 2-8-Transformation/scene_graph  

/**
 * A canvas that draws sprites.
 * 
 * Michael Terry & Jeff Avery
 */
public class Canvas extends JPanel implements KeyListener{

	private Vector<Sprite> sprites = new Vector<Sprite>(); // All sprites we're managing
	private Sprite interactiveSprite = null; // Sprite with which user is interacting
	private JFrame f;
	private Main main;
	boolean ctrl = false;
	private String thisDoll;

	public Canvas(JFrame f, String doll) {
		// Install our event handlers
		thisDoll = doll;
		addKeyListener(this);
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				try {
					handleMousePress(e);
				} catch (NoninvertibleTransformException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public void mouseReleased(MouseEvent e) {
				handleMouseReleased(e);
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				try {
					handleMouseDragged(e);
				} catch (NoninvertibleTransformException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					main.setOriginal(thisDoll);
				} catch (NoninvertibleTransformException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f.dispose();               
			}
		});

		JMenuItem divider = new JMenuItem("-----");
		divider.setEnabled(false);

		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu dolls = new JMenu("Dolls");
		JMenuItem person = new JMenuItem("Person");
		person.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				thisDoll = "person";
				try {
					main.setOriginal(thisDoll);
				} catch (NoninvertibleTransformException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f.dispose();
			}
		});

		JMenuItem animal = new JMenuItem("Animal");
		animal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				thisDoll = "animal";
				try {
					main.setOriginal(thisDoll);
				} catch (NoninvertibleTransformException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f.dispose();
			}
		});

		JMenuItem tree = new JMenuItem("Tree");
		tree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				thisDoll = "tree";
				try {
					main.setOriginal(thisDoll);
				} catch (NoninvertibleTransformException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f.dispose();
			}
		});
		f.setJMenuBar(menuBar);
		menuBar.add(file);
		file.add(reset);
		file.add(divider);
		file.add(quit);
		menuBar.add(dolls);
		dolls.add(person);
		dolls.add(animal);
		dolls.add(tree);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL){
			ctrl = true;
		} 
		if (e.getKeyCode() == KeyEvent.VK_R && ctrl){
			try {
				main.setOriginal(thisDoll);
			} catch (NoninvertibleTransformException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			f.dispose();
		} else if (e.getKeyCode() == KeyEvent.VK_Q && ctrl){
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

	/**
	 * Handle mouse press events
	 * @throws NoninvertibleTransformException 
	 */
	private void handleMousePress(java.awt.event.MouseEvent e) throws NoninvertibleTransformException {
		for (Sprite sprite : sprites) {
			interactiveSprite = sprite.getSpriteHit(e);
			if (interactiveSprite != null) {
				interactiveSprite.handleMouseDownEvent(e);
				break;
			} 
		}
	}

	/**
	 * Handle mouse released events
	 */
	private void handleMouseReleased(MouseEvent e) {
		if (interactiveSprite != null) {
			interactiveSprite.handleMouseUp(e);
			repaint();
		}
		interactiveSprite = null;
	}

	/**
	 * Handle mouse dragged events
	 * @throws NoninvertibleTransformException 
	 */
	private void handleMouseDragged(MouseEvent e) throws NoninvertibleTransformException {
		if (interactiveSprite != null) {
			interactiveSprite.handleMouseDragEvent(e);
			repaint();
		}
	}

	/**
	 * Add a top-level sprite to the canvas
	 */
	public void addSprite(Sprite s) {
		sprites.add(s);
	}
	
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	/**
	 * Paint our canvas
	 */
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		for (Sprite sprite : sprites) {
			sprite.draw((Graphics2D) g);
		}
	}

}
