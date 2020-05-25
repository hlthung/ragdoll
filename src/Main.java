import java.awt.GridLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import javax.swing.JFrame;

//Most of the components from this class borrowed from CS349 Sample Code: 2-8-Transformation/scene_graph  

public class Main{
	static Sprite rectangleSprite, circleSprite, arm1, arm2, thigh1, thigh2, forearm2, forearm1, hand1, hand2, leg1, leg2, foot1, foot2;
	private static String thisDoll = "person";
	
	public static void main(String[] args) throws NoninvertibleTransformException {		
		setOriginal(thisDoll);
	}
	
	public static void setOriginal(String pose) throws NoninvertibleTransformException{
		JFrame f = new JFrame();	
		thisDoll = pose;
		Canvas c = new Canvas(f, thisDoll);		
		setupDoll(thisDoll);
		c.addSprite(rectangleSprite);
		f.getContentPane().add(c);
		f.getContentPane().setLayout(new GridLayout(1, 1));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1500, 800);
		f.setVisible(true);	
	}

	private static void setupDoll(final String pose) throws NoninvertibleTransformException{
		if (thisDoll == "person"){
			rectangleSprite = new RectangleSprite(150, 220);
			circleSprite = new EcllipseComponent(100);
			arm1 = new ArmComponent(100, 20);
			arm2 = new ArmComponent(100,20);
			forearm1 = new ForearmComponent(55,25);
			forearm2 = new ForearmComponent(55,25);
			hand1 = new HandComponent(30);
			hand2 = new HandComponent(30);
			thigh1 = new ThighComponent(145,30);
			thigh2 = new ThighComponent(145,30);
			leg1 = new LegComponent(50, 30);
			leg2 = new LegComponent(50,30);
			foot1 = new FootComponent(30);
			foot2 = new FootComponent(30);
			rectangleSprite.addChild(circleSprite);
			rectangleSprite.addChild(arm1);
			rectangleSprite.addChild(arm2);
			rectangleSprite.addChild(thigh1);
			rectangleSprite.addChild(thigh2);
			arm1.addChild(forearm1); 
			arm2.addChild(forearm2);
			forearm1.addChild(hand1);
			forearm2.addChild(hand2);
			thigh1.addChild(leg1);
			thigh2.addChild(leg2);
			leg1.addChild(foot1);
			leg2.addChild(foot2);
			rectangleSprite.transform(AffineTransform.getTranslateInstance(555, 205));
			circleSprite.transform(AffineTransform.getTranslateInstance(25, -100));
			arm1.transform(AffineTransform.getTranslateInstance(0, 30));
			arm1.rotate(AffineTransform.getRotateInstance(3.1));
			arm2.transform(AffineTransform.getTranslateInstance(140, 0));
			forearm1.transform(AffineTransform.getTranslateInstance(90,2));
			forearm2.transform(AffineTransform.getTranslateInstance(90,2));
			hand1.transform(AffineTransform.getTranslateInstance(55,0));
			hand2.transform(AffineTransform.getTranslateInstance(55,0));
			thigh1.transform(AffineTransform.getTranslateInstance(30, 220));
			thigh1.rotate(AffineTransform.getRotateInstance(1.5));
			thigh2.transform(AffineTransform.getTranslateInstance(150, 220));	
			thigh2.rotate(AffineTransform.getRotateInstance(1.5));
			leg1.transform(AffineTransform.getTranslateInstance(145,5));
			leg2.transform(AffineTransform.getTranslateInstance(145,5));
			foot1.transform(AffineTransform.getTranslateInstance(50, 0));
			foot2.transform(AffineTransform.getTranslateInstance(50,-5));
		} else if (thisDoll == "animal"){
			rectangleSprite = new RectangleSprite(215, 115);
			arm2 = new ArmComponent(50,25);
			thigh2 = new ThighComponent(75, 25);
			thigh1 = new ThighComponent(75, 25);
			circleSprite = new EcllipseComponent(75);
			rectangleSprite.addChild(thigh2);
			rectangleSprite.addChild(thigh1);
			rectangleSprite.addChild(arm2);
			rectangleSprite.addChild(circleSprite);
			rectangleSprite.transform(AffineTransform.getTranslateInstance(500, 250));	
			arm2.transform(AffineTransform.getTranslateInstance(0, 20));	
			arm2.transform(AffineTransform.getRotateInstance(3.14));
			circleSprite.transform(AffineTransform.getTranslateInstance(200,-60));
			thigh2.transform(AffineTransform.getTranslateInstance(195, 110));	
			thigh2.transform(AffineTransform.getRotateInstance(1.5));
			thigh1.transform(AffineTransform.getTranslateInstance(20, 110));
			thigh1.transform(AffineTransform.getRotateInstance(1.5));		
		} else {
			rectangleSprite = new RectangleSprite(110, 350);
			Sprite branch1 = new LegComponent(110,30);
			Sprite branch2 = new LegComponent(110,30);
			Sprite branch3 = new LegComponent(110,30);
			Sprite branch4 = new LegComponent(110,30);
			Sprite t1 = new ForearmComponent(65,25);
			Sprite t2 = new ForearmComponent(65,25);
			Sprite t3 = new ForearmComponent(65,25);
			Sprite t4 = new ForearmComponent(65,25);
			Sprite t5 = new ForearmComponent(65,25);
			Sprite t6 = new ForearmComponent(65,25);
			Sprite t7 = new ForearmComponent(65,25);
			Sprite t8 = new ForearmComponent(65,25);
			rectangleSprite.addChild(branch1);
			rectangleSprite.addChild(branch2);
			rectangleSprite.addChild(branch3);
			rectangleSprite.addChild(branch4);
			branch1.addChild(t1);
			branch1.addChild(t2);
			branch2.addChild(t3);
			branch2.addChild(t4);
			branch3.addChild(t5);
			branch3.addChild(t6);
			branch4.addChild(t7);
			branch4.addChild(t8);
			rectangleSprite.transform(AffineTransform.getTranslateInstance(550, 350));	
			branch1.transform(AffineTransform.getTranslateInstance(0, 10));
			branch1.transform(AffineTransform.getRotateInstance(2.5));	
			branch2.transform(AffineTransform.getTranslateInstance(15, 10));
			branch2.transform(AffineTransform.getRotateInstance(-2.5));
			branch3.transform(AffineTransform.getTranslateInstance(60, 5));
			branch3.transform(AffineTransform.getRotateInstance(-1.1));
			branch4.transform(AffineTransform.getTranslateInstance(118, -5));
			branch4.transform(AffineTransform.getRotateInstance(0.61));
			t1.transform(AffineTransform.getTranslateInstance(80,15));
			t1.transform(AffineTransform.getRotateInstance(0.5));
			t2.transform(AffineTransform.getTranslateInstance(75,-10));
			t2.transform(AffineTransform.getRotateInstance(-0.5));
			t3.transform(AffineTransform.getTranslateInstance(80,15));
			t3.transform(AffineTransform.getRotateInstance(0.5));
			t4.transform(AffineTransform.getTranslateInstance(75,-10));
			t4.transform(AffineTransform.getRotateInstance(-0.5));
			t5.transform(AffineTransform.getTranslateInstance(80,15));
			t5.transform(AffineTransform.getRotateInstance(0.5));
			t6.transform(AffineTransform.getTranslateInstance(75,-10));
			t6.transform(AffineTransform.getRotateInstance(-0.5));
			t7.transform(AffineTransform.getTranslateInstance(80,15));
			t7.transform(AffineTransform.getRotateInstance(0.5));
			t8.transform(AffineTransform.getTranslateInstance(75,-10));
			t8.transform(AffineTransform.getRotateInstance(-0.5));
		} 
	}
}
