package models;

import java.util.Collection;
import java.util.Iterator;

/**
 * Een grote asteroide.
 * 
 * @author Wilco Wijbrandi
 */
public class LargeAsteroid extends Asteroid {

	/**
     * Initialiseert een grote asteroide
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
    public LargeAsteroid(double x, double y, double dx, double dy,
	    AsteroidsModel model) {
	super(x, y, dx, dy, model);
    }

    public LargeAsteroid() { super(); }
    
    /*
     * (non-Javadoc)
     * 
     * @see game.model.GameObject#getRadius()
     */
    @Override
    public int getRadius() {
	return 40;
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
				// Maak twee medium asteroiden, die 1.5 keer zo snel gaan en in
				// tegenovergestelde richtingen van elkaar gaan
				MediumAsteroid a = new MediumAsteroid(x, y, -dx * 1.5,
					dy * 1.5, model);
				model.addGameObject(a);
				a = new MediumAsteroid(x, y, dx * 1.5, -dy * 1.5, model);
				model.addGameObject(a);
				// Verwijder deze asteroide
				this.deleteMe = true;
				// Verwijder de kogel
				b.setDeleteMe();
		    }
		}
    }
}
