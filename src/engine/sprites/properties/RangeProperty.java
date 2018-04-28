package engine.sprites.properties;

/**
 * Range property implemented by launcher. Determines how far projectiles move after
 * being launched by Launcher object.
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public class RangeProperty extends Property {
   
    /**
     * Constructor that takes in cost of range property, upgrade value, and 
     * initial range value.
     * 
     * @param cost
     * @param value
     * @param range
     */
    public RangeProperty(double cost) {
	super(cost);
    }

    public RangeProperty(Property p) {
	super(p);
    }

    @Override
    public Object execute(Object... args) {
	// TODO Auto-generated method stub
	return null;
    }
}
