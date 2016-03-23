package models;

import java.io.Serializable;

/**
 * Dit is een abstracte klasse Asteroid. Er zijn drie soorten asteroiden: large,
 * medium en small.
 * 
 * @author Wilco Wijbrandi
 */
public abstract class Asteroid extends GameObject {

	/**
     * Initialiseert een nieuwe asteroide
     * 
     * @param x
     *            De locatie op de x-as
     * @param y
     *            De locatie op de y-as
     * @param dx
     *            De snelheid in de richting van de x-as
     * @param dy
     *            De snelheid in de richting van de y-as
     * @param model
     *            Het model waar deze asteroide toe behoort
     */
    public Asteroid(double x, double y, double dx, double dy,
	    AsteroidsModel model) {
	this.x = x;
	this.y = y;
	this.dx = dx;
	this.dy = dy;
	this.model = model;
	this.deleteMe = false;
    }
    
    /* Delete later on */
    public Asteroid() {}

    /** Boolean die aangeeft of de astroide verwijderd kan worden. */
    protected boolean deleteMe;

    /** Het model waartoe deze asteroide behoort. */
    protected AsteroidsModel model;

    /**
     * Deze methode controleert of de asteroide tegen een kogel aankomt en
     * onderneemt actie.
     */
    protected abstract void checkCollisions();

    /**
     * Geeft aan of deze asteroide verwijderd moet worden.
     * 
     * @return true als deze asteroide verwijderd moet worden
     */
    public boolean deleteMe() {
	return deleteMe;
    }

    /*
     * (non-Javadoc)
     * 
     * @see game.model.GameObject#nextStep()
     */
    @Override
    public void nextStep() {
    	x = (800 + x + dx) % 800;
    	y = (800 + y + dy) % 800;
    	checkCollisions();
    }
}
