package cs310hartigan;

/**
 * The FundManagerQueueImpl class defines a standard queue
 * of FundManagers.  It is intended to be used to keep track of which FundManagers
 * are waiting for a go kart to be returned.  Only one type of Fund Manager should
 * be used per instantiation of this class.
 * 
 * @author Matthew Hartigan
 * @version Week 4
 */
public class FundManagerQueueImpl {
    
    // Data Fields
    private FundManagerNode frontNode = null;
    private FundManagerNode rearNode = null;
    private int numFundManagersInQueue = 0;
    
    
    // Constructors
    /**
     * Default
     */
    public FundManagerQueueImpl() {
    }

    
    // Getters and Setters
    /**
     *
     * @return
     */
    public FundManagerNode getFrontNode () {
        return frontNode;
    }

    /**
     *
     * @param frontNode
     */
    public void setFrontNode(FundManagerNode frontNode) {
        this.frontNode = frontNode;
    }

    /**
     *
     * @return
     */
    public FundManagerNode getRearNode() {
        return rearNode;
    }

    /**
     *
     * @param rearNode
     */
    public void setRearNode(FundManagerNode rearNode) {
        this.rearNode = rearNode;
    }

    /**
     *
     * @return
     */
    public int getNumFundManagersInQueue() {
        return numFundManagersInQueue;
    }

    /**
     *
     * @param numFundManagersInQueue
     */
    public void setNumFundManagersInQueue(int numFundManagersInQueue) {
        this.numFundManagersInQueue = numFundManagersInQueue;
    }

    
    // User-Defined Methods
    /**
     * addToQueue
     * Adds a new FundManager node to the back of the queue based on 
     * the input FundManager parameter.
     * 
     * @param inputFundManager
     */
    public void addToQueue(FundManager inputFundManager) {
        boolean queueIsFull = false;
        
        queueIsFull = isFull();
        
        if (! queueIsFull) {
            FundManagerNode newNode = new FundManagerNode(inputFundManager);  

            // Check that queue is not empty
            if (this.rearNode != null) {
                rearNode.setNextNode(newNode);
            }

            // If queue is empty, add first node
            else {
                frontNode = newNode;
            }
            rearNode = newNode;
            this.numFundManagersInQueue++;
        }
    }
    
    /**
     * Returns the first FundManager in the queue without removing it from the 
     * queue.
     * 
     * @return
     */
    public FundManager peekFirst () {
        FundManager fundManager = null;
        
        // Check queue is not empty
        if (this.frontNode != null) {
            fundManager = this.frontNode.getFundManager();
        }
        
        // Otherwise, output message to screen
        else {
            System.out.println("Queue is empty, cannot return first fund manager.");
        }
        
        return fundManager;        
    }
    
    /**
     * removeFromQueue
     * Removes first FundManager element from queue and returns it.
     * 
     * @return fundManager
     */
    public FundManager removeFromQueue () {
        FundManager fundManager = null;
        
        if (this.frontNode != null) {
            fundManager = this.frontNode.getFundManager();
            this.frontNode = this.frontNode.getNextNode();
        }
        
        else {
            System.out.println("Queue is empty, cannot return broker license.");
        }
        
        this.numFundManagersInQueue--;
        
        return fundManager;     
    }
    
    /**
     * isEmpty
     * Checks for empty queue.
     * 
     * @return queueIsEmpty
     */
    public boolean isEmpty () {
        boolean queueIsEmpty = false;
        
        // Check if front node in linked list exists
        if (this.frontNode == null) {
            queueIsEmpty = true;
        }
        
        return queueIsEmpty;
    }
    
    /**
     * isFull
     * Creates a test FundManager node to determine whether or not there is
     * memory space aviailable for a new node.  Returns a boolean accordingly.
     * 
     */
    public boolean isFull () {
        boolean queueIsFull = false;
        
        try {
            FundManagerNode testNode = new FundManagerNode();   // Test Node
        }
        catch (OutOfMemoryError excpt) {
            queueIsFull = true;
            System.out.println("The queue is full.  Therefore no node will be added.");
        }

        return queueIsFull;
    }
    
    /**
     * printQueue
     * Displays contents of queue to screen.
     */
    public void printQueue () {
        int i = 0;    // loop counter
        
        // Check queue is not empty, then print contents to screen
        if (! isEmpty()) {
            FundManagerNode currentNode = this.frontNode;
            System.out.println("The queue contains " + this.numFundManagersInQueue + " person(s).");
            
            while (currentNode != null) {
                System.out.println(currentNode.getFundManager().getBrokerLicense());
                currentNode = currentNode.getNextNode();
            }
        }
        
        else {
            System.out.println("The queue is empty.");
        }
    }
    
    /**
     * isInQueue
     * Returns true or false based on whether or not the input broker's license
     * is currently in the queue.
     * 
     * @param license
     * @return isInQueue
     */
    public boolean isInQueue (String license) {
        boolean isInQueue = false;
        int i = 0;    // loop counter
        
        if (!isEmpty()) {
            FundManagerNode currentNode = this.frontNode;
            
            while (currentNode != null) {
                if (currentNode.getFundManager().getBrokerLicense().equals(license)) {
                    isInQueue = true;
                }
                
                currentNode = currentNode.getNextNode();
            }
        }
        
        return isInQueue;
    }
}
