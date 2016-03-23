package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Asteroid;
import models.AsteroidsModel;
import models.Bullet;
import models.Spaceship;

/**
 * A JPanel in order to draw the model. The GamePanel-object is an observer
 * of an AsteroidsModel.
 * 
 * @author Wilco Wijbrandi
 */
@SuppressWarnings("serial")
public abstract class GameView extends JPanel implements Observer {

    /** The model that should be drawn. */
    private AsteroidsModel model;

    /**
     * Initializes a new GamePanel-object. The current model is saved
     * and the object notes itself as an observer of the model.
     *
     * @param model
     *            The model that should be drawn
     */
    public GameView(AsteroidsModel model) {
		this.model = model;
		model.addObserver(this);
    }
    
    public AsteroidsModel getModel() {
    	return this.model;
    }

    /**
     * Changes the model that should be drawn. The class becomes observer of the model
     * 
     * @param model
     *            The new model
     */
    public void setModel(AsteroidsModel model) {
		this.model.deleteObserver(this);
		this.model = model;
		this.model.addObserver(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics) 
     * This method is called when the model should be redrawn. This happens for
     * example when the method repaint() is called. 
     */
    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		this.setBackground(Color.black);
		
		paintBullets(g2);
		paintAsteroids(g2);
		paintSpaceships(g2);
	
    }
    
    protected abstract void paintSpaceships(Graphics2D g);
    
    /**
     * Draw the spaceship. The spaceship is drawn as a black triangle with
     * white edges and a yellow triangle that resembles fire that comes from
     * the exhaust of the spaceship.
     * 
     * @param g
     *            Graphics2D-object
     */
    protected void paintSpaceship(Spaceship s, Graphics2D g) {

		// Draw the fire that comes from the exhaust.
		if (s.goForward()) {
		    Polygon p = new Polygon();
		    p.addPoint((int) (s.getX() - Math.sin(s.getDirection()) * 25),
			    (int) (s.getY() + Math.cos(s.getDirection()) * 25));
		    p.addPoint((int) (s.getX() + Math.sin(s.getDirection() + 0.9
			    * Math.PI) * 15), (int) (s.getY() - Math.cos(s
			    .getDirection()
			    + 0.9 * Math.PI) * 15));
		    p.addPoint((int) (s.getX() + Math.sin(s.getDirection() + 1.1
			    * Math.PI) * 15), (int) (s.getY() - Math.cos(s
			    .getDirection()
			    + 1.1 * Math.PI) * 15));
		    g.setColor(Color.yellow);
		    g.fill(p);
		}

		// Draw the spaceship itself.
		Polygon p = new Polygon();
		// The front of the ship:
		p.addPoint((int) (s.getX() + Math.sin(s.getDirection()) * 20), (int) (s
			.getY() - Math.cos(s.getDirection()) * 20));
		// The two other points
		p.addPoint((int) (s.getX() + Math.sin(s.getDirection() + 0.8
				* Math.PI) * 20), (int) (s.getY() - Math.cos(s
				.getDirection()
				+ 0.8 * Math.PI) * 20));
		p.addPoint((int) (s.getX() + Math.sin(s.getDirection() + 1.2
				* Math.PI) * 20), (int) (s.getY() - Math.cos(s
				.getDirection()
				+ 1.2 * Math.PI) * 20));
		g.setColor(Color.black);
		g.fill(p);
		g.setColor(s.getColor());
		g.draw(p);
    }

    /**
     * Draws the bullets of the spaceship. A bullet is drawn as a yellow cirkel.
     * 
     * @param g
     *            The Graphics2D-object
     */
    private void paintBullets(Graphics2D g) {
		g.setColor(Color.yellow);
		for(Bullet b : model.getBulletArray()) {
			if(b != null) {
				 g.drawOval(b.getX() - 2, b.getY() - 2, 5, 5);
			}
		}
    }

    /**
     * Draws the asteroids. Asteroids are drawn as a gray filled circle. 
     * The size of the circle depends on the radius of the asteroid.
     * 
     * @param g
     *            The Graphics2D-object
     */
    private void paintAsteroids(Graphics2D g) {
		g.setColor(Color.gray);
		for(Asteroid a : model.getAsteroidArray()) {
			if(a != null) {
			    Ellipse2D.Double e = new Ellipse2D.Double();
			    e.setFrame(a.getX() - a.getRadius(), a.getY() - a.getRadius(),
				    2 * a.getRadius(), 2 * a.getRadius());
			    g.fill(e);
			}
		}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     * Call repaint() to redraw everything.
     */
    @Override
    public void update(Observable arg0, Object arg1) {
		repaint();
    }
}