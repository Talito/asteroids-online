package models;


/**
 * Deze klasse representeert een kogel. Een kogel heeft altijd een aantal frames
 * dat hij blijft leven. Als hij langer dan dit aantal frames in het spel zit
 * wordt hij gewoon verwijderd, omdat kogels anders door zouden blijven vliegen
 * totdat ze tegen een asteroide aankomen.
 * 
 * @author Wilco Wijbrandi
 */
public class Bullet extends GameObject {

	/** Het aantal frames dat deze kogel nog blijft bestaan. */
    private int stepsToLive;

    /** Boolean die aangeeft of dit object verwijderd mag worden */
    private boolean deleteMe;
    
    //private Player player;

    /**
     * Initialiseert een nieuw Bullet-object
     * 
     * @param x
     *            De locatie op de x-as
     * @param y
     *            De locatie op de y-as
     * @param dx
     *            De snelheid in de richting van de x-as
     * @param dy
     *            De snelheid in de richting van de y-as
     * @param direction
     *            De richting van de kogel (in radialen)
     */
    public Bullet(double x, double y, double dx, double dy, double direction /*, Player player*/) {
		this.x = x;
		this.y = y;
		stepsToLive = 60;
		this.dx = dx + Math.sin(direction) * 8;
		this.dy = dy - Math.cos(direction) * 8;
		this.deleteMe = false;
		//this.player = new Player();
		//this.player = player;
    }

    /*
     * (non-Javadoc)
     * 
     * @see game.model.GameObject#getRadius()
     */
    @Override
    public int getRadius() {
	return 0;
    }

    /**
     * Geeft aan of dit object verwijderd mag worden
     * 
     * @return true, als dit object verwijderd mag worden
     */
    public boolean deleteMe() {
	return deleteMe;
    }

    /**
     * Als deze methode wordt aangeroepen zal deze kogel verwijderd worden
     */
    void setDeleteMe() {
	deleteMe = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see game.model.GameObject#nextStep()
     */
    @Override
    public void nextStep() {
	this.x = (800 + this.x + this.dx) % 800;
	this.y = (800 + this.y + this.dy) % 800;
	stepsToLive--;
	if (stepsToLive < 0) {
	    this.deleteMe = true;
	}
    }

}
