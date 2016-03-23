package models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Deze klasse representeert het hele model uit het MVC-pattern. Een
 * AsteroidsModel object heeft een ruimteschip, een lijst met asteroiden en een
 * lijst me kogels.
 * 
 * @author Wilco Wijbrandi
 */
public abstract class AsteroidsModel extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * Geeft aan of het spel afgelopen is. Het spel is afgelopen als het
     * ruimteschip een asteroide aanraakt.
     */
    private boolean gameOver = false;

    /** De lijst met kogels. Protected so multiplayer can create a setter */
    protected Collection<Bullet> bullets;

    /** De lijst met asteroiden.  Protected so multiplayer can create a setter  */
    protected Collection<Asteroid> asteroids;

    /**
     * Een lijst die GameObjecten bevat die aan het model toegvoegd moeten
     * worden. Objecten worden namelijk pas aan het eind van iedere stap aan het
     * model toegevoegd. Dit is omdat het niet mogelijk is objecten aan een
     * lijst toe te voegen als je over de lijst itereert.
     */
    protected Collection<GameObject> queue;

    /** Een randon number generator */
    private static Random rand;
    
    /**
	 * For concurrency problems, use the ReentrantReadWriteLocks.
	 */
	protected ReentrantReadWriteLock rwl;
    protected Lock r;
    protected Lock w;
    
    
    public AsteroidsModel() {
    	this.rwl = new ReentrantReadWriteLock();
    	this.r = rwl.readLock();
    	this.w = rwl.writeLock();
    	initNewGame();
    }
    
    
    protected void initNewGame() {
    	w.lock();
    	try {
	    	// Maak een random number generator
	    	rand = new Random();
	    	// Het spel is nog niet gameover
	    	gameOver = false;
	    	// Maak de lijst met kogels
	    	bullets = Collections.synchronizedList(new LinkedList<Bullet>());
	    	// Maak de lijst met asteroiden
	    	asteroids = Collections.synchronizedList(new LinkedList<Asteroid>());
	    	// Initialiseer de queue
	    	queue = Collections.synchronizedList(new LinkedList<GameObject>());
	    	for (int i = 0; i < 4; i++) {
	    	    asteroids.add(randAsteroid());
	    	}
    	} finally {
    		w.unlock();
    	}
    }
    

    /**
     * Geeft aan of het spel gameover is.
     * 
     * @return true als de speler gameover is
     */
    public boolean getGameOver() {
    	r.lock();
    	try {
    		return gameOver;
    	} finally {
    		r.unlock();
    	}
    }

    /**
     * Maakt het spel gameover
     */
   public void setGameOver() {
    	w.lock();
    	try {
    		this.gameOver = true;
    	} finally {
    		w.unlock();
    	}
    }
   
   /**
    * Maakt het spel gameover
    */
  public void setGameOver(boolean val) {
   	w.lock();
   	try {
   		this.gameOver = val;
   	} finally {
   		w.unlock();
   	}
   }


   
   

    /**
     * Retourneert de lijst met kogels
     * 
     * @return Collection met kogels
     */
    public Collection<Bullet> getBullets() {
    	r.lock();
    	try {
    		return bullets;
    	} finally {
    		r.unlock();
    	}
    }

    /**
     * Retourneert de lijst met asteroiden
     * 
     * @return Collection met asteroiden
     */
    public Collection<Asteroid> getAsteroids() {
    	r.lock();
    	try {
    		return asteroids;
    	} finally {
    		r.unlock();
    	}
    }
    
    public Bullet[] getBulletArray() {
    	r.lock();
    	Bullet[] ret = new Bullet[bullets.size()];
    	try {
    		return getBullets().toArray(ret);
    	} finally {
    		r.unlock();
    	}
    }
    
    public Asteroid[] getAsteroidArray() {
    	r.lock();
    	Asteroid[] ret = new Asteroid[bullets.size()];
    	try {
    		return getAsteroids().toArray(ret);
    	} finally {
    		r.unlock();
    	}
    }

    
    
    /**
     * Update het spel voor het volgende frame. De locaties van alle
     * GameObjecten worden aangepast, asteroiden en kogels worden toegevoegd of
     * verwijderd indien nodig. Het is ook mogelijk dat de toestand van het spel
     * op gameover wordt gezet. Indien het spel gameover is doet deze methode
     * niets.
     */
    public abstract void nextStep();

    /**
     * Zet een nieuw GameObject-object in de wachtrij om toegevoegd te worden.
     * De methode emptyQueue() leegt deze wachtrij en stopt de objecten in de
     * goede lijst.
     * 
     * @param o
     *            het nieuwe object
     */
    public void addGameObject(GameObject o) {
    	w.lock();
    	try {
    		queue.add(o);
    	} finally {
    		w.unlock();
    	}
    }

    /**
     * Deze methode haalt alle GameObject-objecten uit de wachtrij en plaatst ze
     * in de goede lijst.
     */
    protected void emptyQueue() {
    	w.lock();
    	try {
	    	Iterator<GameObject> it = queue.iterator();
			while (it.hasNext()) {
			    GameObject next = it.next();
			    if (next instanceof Asteroid) {
			    	asteroids.add((Asteroid) next);
			    } else if (next instanceof Bullet) {
			    	bullets.add((Bullet) next);
			    }
			    it.remove();
			}
    	} finally {
    		w.unlock();
    	}
    }

    /**
     * Genereert een willekeurige grote asteroide. De locatie is willekeurig,
     * maar bevindt zich niet minstens 100 pixels van het midden van het spel.
     * Dit omdat bij het begin van het spel het ruimteschip hier spawnt, en we
     * niet willen dat de speler bij het begin van het spel al direct gameover
     * is.
     * 
     * @return Een nieuwe LargeAsteroid
     */
    protected Asteroid randAsteroid() {
    	// Genereer een willekeurige locatie, niet in het midden
		int x = rand.nextInt(600);
		x = (x > 300) ? x + 200 : x;
		int y = rand.nextInt(600);
		y = (y > 300) ? y + 200 : y;
		// Genereer een willekeurige snelheid en richting
		double dx = rand.nextDouble() * 10 - 4;
		double dy = rand.nextDouble() * 10 - 4;
		// Retourneer het nieuwe object
		return new LargeAsteroid(x, y, dx, dy, this);
    }

}
