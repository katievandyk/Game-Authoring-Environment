package engine.sprites.towers;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.*;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.Image;

/**
 * Class for tower object in game. Implements Sprite methods.
 * 
 * @author Katherine Van Dyk
 * @author Miles Todzo
 */
public class Tower extends ShootingSprites implements FrontEndTower {
    
    private final String ENEMIES_KILLED = "Enemies Killed";
    
    private HealthProperty myHealth;
    private String myImage; 
    private double mySize;
    private Launcher myLauncher; 
    private ValueProperty myValue;
    private double myTowerValue; 
    private Map<String, Integer> propertyStats;

    /**
     * Constructor for a Tower object that accepts parameter properties.
     * 
     * @param image: Tower's image
     * @param launcher: Type of launcher that the Tower inherits 
     * @param health: Initial health of the tower
     * @param value: Value of the tower for selling
     */
    public Tower(String name, String image, double size, Launcher launcher, HealthProperty health, ValueProperty value) {
	super(name, image, size, launcher);
	myHealth = health;
	propertyStats = new HashMap<String, Integer>();


	propertyStats.put(health.getName(), (int) health.getProperty());
	propertyStats.put(value.getName(), (int) value.getProperty());
	propertyStats.put(this.getDamageName(), (int) this.getDamage());
	myLauncher = launcher;
	myValue = value;
	myTowerValue = value.getProperty();
    }

    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower) {
	super(copiedTower.getName(), copiedTower.getImageString(), 
		copiedTower.mySize, copiedTower.getLauncher()); 
	myHealth = copiedTower.getHealthProperty();
	myValue = copiedTower.getValueProperty(); 
    }

    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower, Point point) {
	super(copiedTower.getName(), copiedTower.getImageString(), 
	copiedTower.mySize, new Launcher(copiedTower.getLauncher())); 
	myHealth = copiedTower.getHealthProperty();
	myValue = copiedTower.getValueProperty();
	propertyStats = new HashMap<String, Integer>();
	propertyStats.put(myHealth.getName(),(int) myHealth.getProperty());
	propertyStats.put(myValue.getName(), (int) myValue.getProperty());
	propertyStats.put(this.getDamageName(), (int) this.getDamage());
	this.place(point.getX(), point.getY());
    }

    /**
     * Handles decrementing tower's damage when it gets hit by an enemy
     * 
     * @return boolean: True if tower is alive, false otherwise
     */
    @Override
    public boolean handleCollision(Sprite collider) {
    	return true;
    }

    /**
     * Handles selling a tower
     */
    @Override
    public int sell() {
	removeAllProjectiles();
	return (int) myValue.getProperty();
    }
    
    private void removeAllProjectiles() {
	for(Projectile projectile : this.getProjectiles()) {
	    projectile.place(-100000, -100000);
	}
    }

    /**
     * Handles upgrading the health of a tower
     */
    public double upgradeHealth(double balance) {
	updateStatsMap(myHealth.getName(), myHealth.getProperty());
	return myHealth.upgrade(balance);
    }

    /**
     * Upgrades the rate of fire
     */
    public double upgradeRateOfFire(double balance) {
	return this.getLauncher().upgradeFireRate(balance);
    }

    /**
     * Upgrades the amount of damage a tower's projectiles exhibit
     */
    public double upgradeDamage(double balance) {
	return this.getLauncher().upgradeDamage(balance);
    }

    /**
     * Upgrades all aspects of a tower
     */
    public double upgrade(double balance) {
	balance -= upgradeHealth(balance);
	balance -= upgradeRateOfFire(balance);
	balance = upgradeDamage(balance);
	updateStatsMap(myHealth.getName(), myHealth.getProperty());
	updateStatsMap(this.getLauncher().getFireRateName(), this.getLauncher().getFireRate());
	updateStatsMap(this.getLauncher().getDamageName(), this.getLauncher().getDamage());
	return balance;
    }

    public String getDamageName() {
	return this.getLauncher().getDamageName();
    }

    /**
     * Returns the image
     * @return
     */
    public String getImage() {
	return myImage;
    }

    /**
     * Returns ValueProperty
     * @return
     */
    public ValueProperty getValueProperty() {
	return myValue;
    }

    /**
     * Returns the health property
     */
    public HealthProperty getHealthProperty() {
	return myHealth;
    }

    @Override
    public Map<String, Integer> getTowerStats(){
	System.out.println("in map thing " + this.getDeadCount());
	updateStatsMap(ENEMIES_KILLED, (int) this.getDeadCount());
	return propertyStats;
    }

    private void updateStatsMap(String name, double value) {
	propertyStats.put(name, (int) value);
    }

    @Override
    public int purchase(int myResources) throws CannotAffordException {
	if (myResources < myValue.getProperty()) {
	    throw new CannotAffordException();
	}
	return (int) (myResources - myValue.getProperty());
    }

	@Override
	public int getPointValue() {
		// TODO Auto-generated method stub
		return 0;
	}

    @Override
    public double getTowerRange() {
        return this.getLauncher().getRange();
    }

    public void updateProperties() {
	// TODO Auto-generated method stub
	
    }
}