package controllers;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

/**
 * Dit is de klasse SpaceshipController. Java werkt standaard met event driven
 * programming voor het indrukken van toetsen. Dit is helaas niet zo handig bij 
 * dit programma. Daarom hebben we een aparte klasse SpaceshipController gemaakt, 
 * die ten alle tijde kan vertellen of de knop omhoog, links, rechts of de 
 * spatiebalk ingedrukt is.
 * 
 * @author Wilco Wijbrandi
 */
public class SpaceshipController implements KeyListener, Serializable {

	private static final long serialVersionUID = 2L;

	/** Ga vooruit. */
    private boolean up;

    /** Draai naar links. */
    private boolean left;

    /** Draai naar rechts. */
    private boolean right;

    /** Schiet kogels. */
    private boolean space;

    /**
     * Maakt een nieuwe SpaceshipController. We gaan er van uit dat er op dit moment geen
     * toetsen zijn ingedrukt.
     */
    public SpaceshipController() {
	up = false;
	left = false;
	right = false;
	space = false;
    }

    /**
     * Geeft aan of omhoog ingedrukt is
     * 
     * @return true, als omhoog ingedrukt is
     */
    public boolean goForward() {
	return up;
    }

    /**
     * Geeft aan of links ingedrukt is
     * 
     * @return true, als links ingedrukt is
     */
    public boolean goLeft() {
	return left;
    }

    /**
     * Geeft aan of rechts ingedrukt is
     * 
     * @return true, als rechts ingedrukt is
     */
    public boolean goRight() {
	return right;
    }

    /**
     * Geeft aan of de spatiebalk ingedrukt is
     * 
     * @return true, als de spatiebalk ingedrukt is
     */
    public boolean fireBullets() {
	return space;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {
	switch (e.getKeyCode()) {
	case KeyEvent.VK_UP:
	    this.up = true;
	    break;
	case KeyEvent.VK_LEFT:
	    this.left = true;
	    break;
	case KeyEvent.VK_RIGHT:
	    this.right = true;
	    break;
	case KeyEvent.VK_SPACE:
	    this.space = true;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {
	switch (e.getKeyCode()) {
	case KeyEvent.VK_UP:
	    this.up = false;
	    break;
	case KeyEvent.VK_LEFT:
	    this.left = false;
	    break;
	case KeyEvent.VK_RIGHT:
	    this.right = false;
	    break;
	case KeyEvent.VK_SPACE:
	    this.space = false;
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
    }
    
    /**
     * Restart the spaceship controller.
     */
    public void restart() {
    	up = false;
    	left = false;
     	right = false;
     	space = false;
    }

}
