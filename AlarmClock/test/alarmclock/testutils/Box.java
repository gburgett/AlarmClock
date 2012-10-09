package alarmclock.testutils;

/**
 * This is a simple Box for a value.  It remembers whether the value has
 * been set, and throws an assertion failure if an attempt to read the value
 * is made before the value was ever set.
 *
 * We use this so we can set and save values from within anonymous classes.
 * The box is final, but we can set and get its value.
 * @author Gordon
 */
public class Box<T> {

    private T value;
    private boolean valueIsSet = false;

    /**
     * Sets the value.
     * <p/>
     * This method is synchronized, that means if multiple threads attempt
     * to access it (or any other synchronized method on the same instance of
     * this class) at the same time, then it will 'lock', which means it will
     * enforce a one-at-a-time policy of access for the different threads.
     * <br/>
     * synchronized methods are the same as wrapping the body of the method
     * in {@code synchronized(this)}
     * @param value
     */
    public synchronized void setValue(T value){
        this.valueIsSet = true;
        this.value = value;
    }

    /**
     * Gets the value, if it has been set before now.
     * @return The value that was set
     * @throws junit.framework.AssertionFailedError if the value was never set
     */
    public synchronized T getValue()
            throws junit.framework.AssertionFailedError
    {
        return this.value;
    }

    /**
     * Returns whether the value has been set before now.
     * Because this property is a boolean, it has a slightly different naming
     * convention.  Instead of getProperty we use isProperty because that reads
     * better.
     * @return True if setValue has been called
     */
    public synchronized boolean isValueSet(){
        return this.valueIsSet;
    }

    public Box()
    {
        this.value = null;
        this.valueIsSet = false;
    }

    public Box(T initialValue)
    {
        this.value = initialValue;
        this.valueIsSet = false;
    }

    /**
     * Verifies that the value was set
     * @param message the message to display if the assertion fails.
     * @throws junit.framework.AssertionFailedError if the value was never set
     */
    public synchronized void VerifySet(String message)
            throws junit.framework.AssertionFailedError
    {
        org.junit.Assert.assertTrue(message,
                this.valueIsSet);
    }

    /**
     * Verifies that the value was set
     * @throws junit.framework.AssertionFailedError if the value was never set
     */
    public synchronized void verifySet()
            throws junit.framework.AssertionFailedError
    {
        this.VerifySet("The box's value was never set");
    }


}
