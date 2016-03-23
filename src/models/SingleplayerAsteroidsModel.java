package models;

import java.util.Iterator;

import controllers.MainController;
import controllers.SpaceshipController;

/**
 * Deze klasse representeert het hele model uit het MVC-pattern. Een
 * AsteroidsModel object heeft een ruimteschip, een lijst met asteroiden en een
 * lijst me kogels.
 * 
 * @author Wilco Wijbrandi
 */
public class SingleplayerAsteroidsModel extends AsteroidsModel {

    /** Het ruimteschip */
    private Spaceship ship;
    private SpaceshipController sc;
    
    /**
     * Initialiseert een nieuw AsteroidsModel. Er worden standaard 4 grote
     * asteroiden met een willekeurige plaats en snelheid in het model gestopt.
     * 
     * @param sc Een SpaceshipController
     */
    public SingleplayerAsteroidsModel(SpaceshipController sc, Player p) {
		super();
		this.sc = sc;
		this.ship = new Spaceship(sc, this, p);
    }

    /**
     * Retourneerd het Spaceship-object van dit model
     * 
     * @return Het spaceship-object
     */
    public Spaceship getSpaceship() {
    	return this.ship;
    }
    
    public void createSpaceship() {
    	this.sc.restart();
    	this.ship = new Spaceship(this.sc, this, MainController.getPlayer());
    }
    
    /**
     * Restart the single player game.
     */
    public void restartGame() {
    	super.initNewGame();
    	this.createSpaceship();
    	this.nextStep();
    }

    /**
     * Update het spel voor het volgende frame. De locaties van alle
     * GameObjecten worden aangepast, asteroiden en kogels worden toegevoegd of
     * verwijderd indien nodig. Het is ook mogelijk dat de toestand van het spel
     * op gameover wordt gezet. Indien het spel gameover is doet deze methode
     * niets.
     */
    public void nextStep() {
		if (!getGameOver()) {
		    // Update het ruimteschip
		    ship.nextStep();
		    if(this.ship.isAlive() == false) {
		    	setGameOver();
		    }
		    // Update alle kogels, verwijder ze indien nodig
		    Iterator<Bullet> ib = getBullets().iterator();
		    while (ib.hasNext()) {
		    	Bullet b = ib.next();
				b.nextStep();
				if (b.deleteMe()) {
			    	ib.remove();
				}
		    }
		    // Update alle asteroiden, verwijder ze indien nodig
		    Iterator<Asteroid> ia = getAsteroids().iterator();
		    while (ia.hasNext()) {
		    	Asteroid a = ia.next();
		    	a.nextStep();
		    	if (a.deleteMe()) {
		    		ia.remove();
		    	}
		    }
		    
		    if(getAsteroids().size() == 0) {
		    	setGameOver();
		    }
		    // Kopier alle nieuwe GameObject-objecten naar de lijst met kogels
		    // of de lijst met asteroiden
		    emptyQueue();
		    // Notify obeservers
		    setChanged();
		    notifyObservers();
		}
    }

}