package models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Een kleine asteroide.
 * 
 * @author Wilco Wijbranid
 */
public class SmallAsteroid extends Asteroid {
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
    public SmallAsteroid(double x, double y, double dx, double dy,
	    AsteroidsModel model) {
	super(x, y, dx, dy, model);
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
		    if (collides(b)) {
			// Als de asteroide geraakt wordt door een kogel kunnen de kogel
			// en de asteroide verwijderd worden
			this.deleteMe = true;
			b.setDeleteMe();
		    }
		}
    }

    /*
     * (non-Javadoc)
     * 
     * @see game.model.GameObject#getRadius()
     */
    @Override
    public int getRadius() {
    	return 10;
    }

}
