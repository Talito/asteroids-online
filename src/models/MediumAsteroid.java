package models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Een medium asteroide.
 * 
 * @author Wilco Wijbrandi
 */
public class MediumAsteroid extends Asteroid {

	/**
     * Initialiseert een medium asteroide
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
    public MediumAsteroid(double x, double y, double dx, double dy,
	    AsteroidsModel model) {
	super(x, y, dx, dy, model);
    }

    /*
     * (non-Javadoc)
     * 
     * @see game.model.GameObject#getRadius()
     */
    @Override
    public int getRadius() {
	return 20;
    }

    /*
     * (non-Javadoc)
     * 
     * @see game.model.Asteroid#checkCollisions()
     */
    @Override
    protected void checkCollisions() {
	Collection<Bullet> bullets = model.getBullets();
	Iterator<Bullet> it = bullets.iterator();
	while (it.hasNext()) {
	    Bullet b = it.next();
	    // Raakt deze asteroide een kogel aan?
	    if (collides(b)) {
		// Maak twee kleine asteroiden, die 1.5 keer zo snel gaan en in
		// tegenovergestelde richtingen van elkaar gaan
		SmallAsteroid a = new SmallAsteroid(x, y, -dx * 1.5, dy * 1.5,
			model);
		model.addGameObject(a);
		a = new SmallAsteroid(x, y, dx * 1.5, -dy * 1.5, model);
		model.addGameObject(a);
		// Verwijder deze asteroide
		this.deleteMe = true;
		// Verwijder de kogel
		b.setDeleteMe();
	    }
	}

    }

}
