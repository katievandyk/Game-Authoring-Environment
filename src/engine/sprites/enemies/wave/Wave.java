package engine.sprites.enemies.wave;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import engine.path.Path;
import engine.sprites.enemies.Enemy;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Class used for defining a wave of enemies to appear in a level
 */
public class Wave {
    
    private final Map<Enemy, Integer> myEnemies;
    private final Path myPath;
    
    public Wave(Path path) {
	myEnemies = new TreeMap<Enemy, Integer>();
	myPath = path;
    }
    
    /**
     * Adds a number of an enemy type to a wave for the level
     * 
     * @param enemy: The type of enemy to be added to the wave
     * @param number: the number of the enemy to be added to the wave
     */
    public void addEnemy(Enemy enemy, int number) {
	myEnemies.put(enemy, number);
    }
    
    /**
     * Returns a map that an Enemy type key to an Integer value representing the number
     * of that type in the wave. 
     * 
     * @return Map<EnemyI, Integer>: an unmodifiable map of the Enemies in the wave
     */
    public Map<Enemy, Integer> getUnmodifiableEnemies(){
	return Collections.unmodifiableMap(myEnemies);
    }
    
    /**
     * Returns a boolean indicating whether the wave is finished or not. A wave is considered
     * finished if there are no enemies left to be spawned. 
     * 
     * @return boolean: true if the wave is finished, false otherwise.
     */
    public boolean isFinished() {
	for (Entry<Enemy, Integer> entry : myEnemies.entrySet()) {
	    if (entry.getValue() > 0) {
		return false;
	    }
	}
	return true;
    }

}