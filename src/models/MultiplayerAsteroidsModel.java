package models;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;

import controllers.SpaceshipController;


public class MultiplayerAsteroidsModel extends AsteroidsModel {

	/** Ref.NO for identifying packets. */
	private int refNumber;
	
    /**
     * The spaceships and the players in the game.
     */
    private Collection<Spaceship> spaceships;
    private Collection<Player> players;
    private Collection<Spectator> spectators;

    /**
     * Initialiseert een nieuw AsteroidsModel. Er worden standaard 4 grote
     * asteroiden met een willekeurige plaats en snelheid in het model gestopt.
     * 
     * @param sc Een SpaceshipController
     */
    public MultiplayerAsteroidsModel(Collection<Player> players) {
    	super();
    	this.players = players;
		this.refNumber = 0;
    }

    
    public void incrementRefNumber() {
    	w.lock();
    	try {
        	this.refNumber++;
    	} finally {
        	w.unlock();
    	}
    }
    
    public Collection<Player> getPlayers() {
    	r.lock();
    	try {
    		return this.players;
    	} finally {
    		r.unlock();
    	}
    }
    
    public Spaceship getSpaceship(Player p) {
    	r.lock();
    	try {
    		for(Spaceship s : getSpaceships()) {
    			if(s.getPlayer().getName().equals(p.getName())) {
    				return s;
    			}
    		}
    	} finally {
    		r.unlock();
    	}
    	return null;
    }
    
    public int getRefNumber() {
    	r.lock();
    	try {
        	return this.refNumber;
    	} finally {
    		r.unlock();
    	}
    }
    
    public Collection<Spaceship> getSpaceships() {
    	r.lock();
    	try {
    		return spaceships;
    	} finally {
    		r.unlock();
    	}
    }
    
    /* Setters */
    public void setAsteroids(Collection<Asteroid> asteroids) {
    	w.lock();
    	try {
    		this.asteroids = asteroids;
    	} finally {
    		w.unlock();
    	}
    }
    
    public void setBullets(Collection<Bullet> bullets) {
    	w.lock();
    	try {
    		this.bullets = bullets;
    	} finally {
    		w.unlock();
    	}
    }
    
    public void setSpaceships(Collection<Spaceship> spaceships) {
    	w.lock();
    	try {
    		this.spaceships = spaceships;
    	} finally {
    		w.unlock();
    	}
    }
    
    public void setSpaceshipController(SpaceshipController sc, InetAddress address, int port) {
    	w.lock();
    	try {
    		for(Player p : getPlayers()) {
    			if(p.getAddress().equals(address) && p.getPort() == port) {
    				updateSpaceship(p, sc);
    			}
    		}
    	} finally {
    		w.unlock();
    	}
    }
    
    public void updatedModel() {
    	this.setChanged();
    	this.notifyObservers();
    }
    
    public void addSpaceship(Player p) {
    	w.lock();
    	try {
    		spaceships.add(new Spaceship(null, this, p));
    	} finally {
    		w.unlock();
    	}
    }
    
    public Spaceship findSpaceship(Player p) {
    	r.lock();
    	try {
	    	for(Spaceship sp: spaceships) {
				if (sp.getPlayer().getName().equals(p.getName())){
					return sp;
				}
			}
			return null;
    	} finally {
    		r.unlock();
    	}
	}
    
	public void updateSpaceship(Player p, SpaceshipController sc) {
		w.lock();
    	try {
			if (sc != null) {
				findSpaceship(p).setController(sc);  
			}
    	} finally {
    		w.unlock();
    	}
	}
    
    
   public boolean oneAlive() {
	   r.lock();
	   try {
		   int num = getNumSpaceshipsAlive();
		   if (num == 1 || num == 0) {
			   rewardWinner();
			   return true;
	    	}
	    	return false;
	   } finally {
	   		r.unlock();
	   	}
    }
    
   private int getNumSpaceshipsAlive() {
	   int num = 0;
	   for(Spaceship s : getSpaceships()) {
		   if(s.isAlive()) {
			   num++;
		   }
	   }
	   return num;
   }
    
   private void rewardWinner() {
	   for(Spaceship s : getSpaceships()) {
		   if(s.isAlive()) {
			  	for(Player p : getPlayers()) {
			  		if(p.getName().equals(s.getPlayer().getName())) {
			  			p.incrementScore();
			  		}
			  	}
		   }
	   }
   }
   
    private void resetSpaceships() {
    	w.lock();
    	try {
	    	for(Spaceship sp : getSpaceships()){
	    		sp.reset();
	    	}
    	} finally {
    		w.unlock();
    	}
    }
    
    private void respawnAsteroids() {
	    for(int i = 0; i < 4; i++) {
	    	w.lock();
	    	try {
				addGameObject(randAsteroid());
	    	} finally {
	        	w.unlock();
	        }
	    }
    }
    
    /**
     * @post winner = null
     * @post gameOver = false
     * @post Collections rebuilt
     */
    public void restartGame() {
    	w.lock();
    	try {
	    	initNewGame();
			resetSpaceships();
    	} finally {
    		w.unlock();
    	}
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
	    	w.lock();
	    	try {
				if (spaceships != null) {
					if (getSpaceships().size() > 0 && oneAlive()) { 
						setGameOver();
						return;
					}

					for(Spaceship s : getSpaceships()) {
						if (s.isAlive()) {
							s.nextStep();
						}
					}
					
				}
			} finally {
				w.unlock();
			}
			
			w.lock();
			try {
				Iterator<Bullet> ib = getBullets().iterator();
			    while (ib.hasNext()) {
			    	Bullet b = ib.next();
					b.nextStep();
					if (b.deleteMe()) {
				    	ib.remove();
					}
			    }
			} finally {
				w.unlock();
			}
		    
		    // Update alle asteroiden, verwijder ze indien nodig
			w.lock();
			try {
				Iterator<Asteroid> ia = getAsteroids().iterator();
				while (ia.hasNext()) {
				    Asteroid a = ia.next();
				    a.nextStep();
				    if (a.deleteMe()) {
				    	ia.remove();
				    }
				}
			} finally {
				w.unlock();
			}
			
			r.lock();
			try {
				if(getAsteroids().size() == 0) {
				    respawnAsteroids();
				}
			} finally {
				r.unlock();
			}
			    
			incrementRefNumber();
	    }
			    
	    // Kopier alle nieuwe GameObject-objecten naar de lijst met kogels
	    // of de lijst met asteroiden
	    emptyQueue();
	    // Notify observers
	    setChanged();
		notifyObservers();

    }
    
    public Collection<Spectator> getSpectators() {
		return spectators;
	}


	public void setSpectators(Collection<Spectator> spectators) {
		this.spectators = spectators;
	}



}