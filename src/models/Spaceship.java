package models;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;

import controllers.SpaceshipController;

/**
 * A class representing a spaceship. This class is controlled by using a 
 * KeyManager that check which keys are pressed.
 */
public class Spaceship extends GameObject {

    /**
     * The direction of the spaceship in radians. A direction of 0 means
     * that the spaceship is pointing upwards.
     */
    protected double direction;

    /** The SpaceshipController that is used to control the spaceship. */
    private SpaceshipController sc;

    /** Boolean that checks wheter the spaceship accelerates. */
    private boolean goForward;
    
    /**
     * Is the spaceship still in the game?
     */
    private boolean isAlive;
    
    /**
     * The player which owns this ship!
     */
    private Player player;

    /**
     * The number of frames that the spaceship needs to fire another bullet.
     * This is to make sure that the spaceship does not fire a new bullet in 
     * every frame.
     */
    private int stepsTillFire;

    /** The AsteroidsModel to which this Spaceship belongs. */
    private AsteroidsModel model;


	/**
     * Initializes a new Spaceship-object. A new spaceship that is created
     * by default in the center of the screen (location 400, 400).
     * 
     * @param sc
     *            The KeyManager of the spaceship
     * @param model
     *            The AsteroidsModel to which this Spaceship belongs.
     */
    public Spaceship(SpaceshipController sc, AsteroidsModel model, Player p) {
    	this.sc = sc;
    	this.model = model;
    	this.x = 400;
    	this.y = 400;
    	this.dx = 0;
    	this.dy = 0;
    	this.direction = 0;
    	this.goForward = false;
    	this.stepsTillFire = 0;
    	this.setAlive(true);
    	this.setPlayer(p);
    }

    /**
     * Returns the direction of the spaceship.
     * 
     * @return the direction in radians.
     */
    public double getDirection() {
	   return this.direction;
    }

    /**
     * Checks whether the spaceship accelerates.
     * 
     * @return true, ift the spaceship accelerates
     */
    public boolean goForward() {
	   return this.goForward;
    }

    /*
     * (non-Javadoc)
     * 
     * @see game.model.GameObject#getRadius()
     */
    @Override
    public int getRadius() {
	   return 15;
    }
    
    /**
     * Get the color of the spaceship
     */
    public Color getColor() {
    	return this.getPlayer().getColor();
    }
    
    /**
     * Set the Controller
     */
    public void setController(SpaceshipController sc) {
    	this.sc = sc;
    }
    
    public void setPlayer(Player p) {
    	this.player = p;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public SpaceshipController getController() {
		return this.sc;
	}
	
	public void reset() {
		this.x = 400;
    	this.y = 400;
    	this.dx = 0;
    	this.dy = 0;
    	this.direction = 0;
    	this.goForward = false;
    	this.stepsTillFire = 0;
    	setAlive(true);
		this.sc.restart();
	}
	
    /*
     * (non-Javadoc)
     * 
     * @see game.model.GameObject#nextStep()
     */
    @Override
    public void nextStep() {
    	if(!isAlive()){
    		return;
    	}
    	// Turn the spaceship if necessary
    	if (sc.goLeft()) {
    	    this.direction -= 0.04 * Math.PI;
    	}
    	if (sc.goRight()) {
    	    this.direction += 0.04 * Math.PI;
    	}

    	// Go forward if necessary
    	if (sc.goForward()) {
    	    this.dx += Math.sin(direction) * 0.4;
    	    this.dy -= Math.cos(direction) * 0.4;
    	    this.goForward = true;
    	} else {
    	    this.goForward = false;
    	}

    	// Update the current location
    	this.x = (800 + this.x + this.dx) % 800;
    	this.y = (800 + this.y + this.dy) % 800;

        // Update the velocity. To make the game better playable, we apply a 
        // bit of resistance.
    	this.dx *= 0.99;
    	this.dy *= 0.99;

    	// Decrease stepsTillFire while larger than 0
    	this.stepsTillFire = Math.max(0, this.stepsTillFire - 1);

    	// Checks whether a bullet should be fired and create the bullet if necessary
    	if (sc.fireBullets() && stepsTillFire == 0) {
    	    Bullet b = new Bullet(this.x, this.y, this.dx, this.dy, this.direction);
    	    model.addGameObject(b);
    	    stepsTillFire = 10;
    	}

    	// Check whether the spaceship collides with an asteroid. 
        // If this is the case, then the game is over.
    	Collection<Asteroid> asteroids = model.getAsteroids();
    	Iterator<Asteroid> it = asteroids.iterator();
    	while (it.hasNext()) {
    	    if (this.collides(it.next())) {
    	    	this.setAlive(false);
    	    }
    	}
    }
}