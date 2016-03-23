package models;

import java.io.Serializable;

/**
 * GameObject is een abstracte klasse waar iedere klasse die in ruimte zweeft
 * van erft. Een GameObject heeft een locatie en een snelheid.
 * 
 * @author Wilco Wijbrandi
 */
public abstract class GameObject implements Serializable  {

	private static final long serialVersionUID = 3L;

	/**
     * De locatie op de x-as. Deze moet tussen de 0 en de 800 inliggen vanwege
     * de afmetingen van het venster.
     */
    protected double x;

    /**
     * De locatie op de y-as. Deze moet tussen de 0 en de 800 inliggen vanwege
     * de afmetingen van het venster.
     */
    protected double y;

    /** De huidige snelheid in de richting van de x-as */
    protected double dx;

    /** De huidige snelheid in de richting van de y-as */
    protected double dy;

    /**
     * Deze methode wordt aangeroepen om de nieuwe locatie en snelheid voor het
     * voldende frame te berekenen.
     */
    abstract public void nextStep();

    /**
     * Deze methode moet de radius van het object retourneren. De afstand van
     * het middelpunt van het object tot de buitenkant dus. Dit wordt gebruikt
     * voor de collision-detection en in het geval van de asteroiden ook voor
     * het tekenen van de asteroiden.
     * 
     * @return De radius in pixels
     */
    abstract public int getRadius();

    /**
     * De locatie op de x-as.
     * 
     * @return x
     */
    public int getX() {
	return (int) this.x;
    }

    /**
     * De locatie op de y-as.
     * 
     * @return y
     */
    public int getY() {
	return (int) this.y;
    }

    /**
     * Controleert of het GameObject-object tegen een ander GameObject aanzit.
     * 
     * @param o
     *            Het andere object
     * 
     * @return true als de objecten tegen elkaar aanzitten
     */
    public boolean collides(GameObject o) {
	double x = Math.abs(this.x - o.getX());
	double y = Math.abs(this.y - o.getY());
	double distance = Math.sqrt(x * x + y * y);
	return distance < this.getRadius() + o.getRadius();
    }

}
