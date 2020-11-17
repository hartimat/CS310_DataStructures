package cs310hartigan;

/**
 * FundManagerNode is the node class for the FundManagerLogImpl single 
 * linked list implementation.  It features data fields for a FundManager object
 * reference, as well as a reference to the next node in the list.
 * 
 * @author Matthew Hartigan
 * @version Week 5
 */
public class FundManagerNode {
    
    // Data fields
    private FundManager fundManager;
    private FundManagerNode nextNode;
    
    
    // Constructors
    /**
     * Default constructor
     */
    public FundManagerNode() {
        this.fundManager = null;
        this.nextNode = null;
    }

    /**
     * List head constructor
     * 
     * @param fundManager
     */
    public FundManagerNode(FundManager fundManager) {
        this.fundManager = fundManager;
        this.nextNode = null;
    }
    
    /**
     * Normal node constructor
     * 
     * @param fundManager
     * @param nextNode
     */
    public FundManagerNode(FundManager fundManager, FundManagerNode nextNode) {
        this.fundManager = fundManager;
        this.nextNode = nextNode;
    }
    
    
    // Getters and Setters
    /**
     * Get FundManager object reference
     * 
     * @return fundManager
     */
    public FundManager getFundManager() {
        return fundManager;
    }

    /**
     * Set FundManager object reference
     * 
     * @param fundManager
     */
    public void setFundManager(FundManager fundManager) {
        this.fundManager = fundManager;
    }

    /**
     * Get next node object reference
     * 
     * @return nextNode
     */
    public FundManagerNode getNextNode() {
        return nextNode;
    }

    /**
     * Set next node object reference
     * 
     * @param nextNode
     */
    public void setNextNode(FundManagerNode nextNode) {
        this.nextNode = nextNode;
    }   
}
