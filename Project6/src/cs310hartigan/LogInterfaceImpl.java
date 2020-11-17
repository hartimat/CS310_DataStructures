package cs310hartigan;

/**
 * This interface is intended to outline the base functionality for different "log" 
 * implementations that will be used in CS310.  It includes method signatures
 * for addition and removal of objects from a log.
 * 
 * @author Matthew Hartigan
 * @version Week 6
 */
public abstract interface LogInterfaceImpl {
    
    /**
     * Add an object to a log.
     * 
     * @param obj
     * @return boolean
     */
    public abstract boolean addToLog (Object obj);
        
    /**
     * Remove an object from a log.
     * 
     * @param obj
     * @return boolean
     */
    public abstract boolean removeFromLog (Object obj);
}
