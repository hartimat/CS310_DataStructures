package cs310hartigan;

/**
 * The FundManagerLogImpl class implements the Log interface and defines
 * a single Linked List data structure to hold FundManager objects.  It contains 
 * methods for adding and removing fund manager elements from the log  It also 
 * contains methods for displaying the log contents to screen, checking if 
 * a broker license number is unique, and removing invalid broker license numbers
 * from the log.  The FundManagerNode class is the node class for this linked list
 * implementation.
 * 
 * @author Matthew Hartigan
 * @version Week 4
 */
public class FundManagerLogImpl implements LogInterfaceImpl {

    // Data fields
    private FundManagerNode listHead = null;    // top of list
    private int listSize = 0;
    
    
    // Constructors
    /**
     * Default constructor
     */
    public FundManagerLogImpl () {    
    }
    
    
    // Getters and Setters
    /**
     * Get list head
     * 
     * @return listHead
     */
    public FundManagerNode getListHead() {
        return listHead;
    }

    /**
     * Set list head
     * 
     * @param listHead
     */
    public void setListHead(FundManagerNode listHead) {
        this.listHead = listHead;
    }

    /**
     * Get list size
     * 
     * @return
     */
    public int getListSize() {
        return listSize;
    }

    /**
     * Set list size
     * 
     * @param listSize
     */
    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
    

    
    // User-defined Methods
    /**
     * addToLog
     * This method adds an element to the FundManager linked list
     * data structure.  The list is automatically kept in lexicographical order.
     * 
     * @param obj
     * @return addToLogSuccessful
     */    
    public boolean addToLog (Object obj) {
        FundManager inputFundManager = (FundManager) obj;   // Downcast object to be added into type FundManager
        boolean addToLogSuccessful = false;
        FundManagerNode newNode = new FundManagerNode(inputFundManager);
        
        // Case 1: First FundManager in the log
        if (this.listHead == null) {
            this.listHead = newNode;
            addToLogSuccessful = true;
        }
        
        // Case 2: New node should be inserted before list head
        else if ((newNode.getFundManager().getBrokerLicense().compareToIgnoreCase(listHead.getFundManager().getBrokerLicense())) < 0) {
            newNode.setNextNode(this.listHead);
            this.listHead = newNode;    // Move list head to second position    
            addToLogSuccessful = true;
        }

        // Case 3:New node should be inserted between other nodes or at end
        else {
            FundManagerNode previousNode = this.listHead;
            FundManagerNode currentNode = this.listHead.getNextNode();
            
            // Loop through linked list until next lexicographical index or end of list is reached
            while ( (currentNode != null)  
                && ((newNode.getFundManager().getBrokerLicense().compareToIgnoreCase(currentNode.getFundManager().getBrokerLicense())) > 0) ) {
                
                previousNode = currentNode;
                currentNode = currentNode.getNextNode();                
            }            
            
            // Insert new node into position
            newNode.setNextNode(currentNode);
            previousNode.setNextNode(newNode);
            addToLogSuccessful = true;
        }
        
        this.listSize++;
        
        return addToLogSuccessful;
    }
    
    /**
     * removeFromLog
     * This method removes an element from the FundManager linked list
     * data structure.  
     * 
     * @param obj
     * @return operationWasSuccessful
     */    
    public boolean removeFromLog (Object obj) {
        FundManager inputFundManager = (FundManager) obj;
        FundManagerNode currentNode = this.listHead.getNextNode();
        FundManagerNode previousNode = this.listHead;
        boolean operationWasSuccessful = false;
        
        // Case 1: List with a single element
        if (this.listHead.getNextNode() == null) {
            if (previousNode.getFundManager().getBrokerLicense().equals(inputFundManager.getBrokerLicense())) {
                this.listHead = this.listHead.getNextNode();
                operationWasSuccessful = true;
            }
        }
        
        // Case 2: List with multiple elements
        else {
            // Check first element
            if (previousNode.getFundManager().getBrokerLicense().equals(inputFundManager.getBrokerLicense())) {
                this.listHead = this.listHead.getNextNode();
                operationWasSuccessful = true;
            }
                
            else {
                // If no match found, check remaining elements
                    while (currentNode != null) {
                        if (currentNode.getFundManager().getBrokerLicense().equals(inputFundManager.getBrokerLicense())) {
                            previousNode.setNextNode(currentNode.getNextNode());     
                            operationWasSuccessful = true;
                        }
                        previousNode = currentNode;
                        currentNode = currentNode.getNextNode();
                    }
            }          
        }
        
        return operationWasSuccessful;
    }

    /**
     * traverseDisplay
     * Loops through entire FundManager log and displays contents to screen
     * using toString().
     */    
    public void traverseDisplay () {
        boolean isSingleNodeLinkedList = false;
        FundManagerNode currentNode = this.listHead;
        
        System.out.println("FundManager Log:");
        
        if (currentNode != null) {    // Empty list case
            if (currentNode.getNextNode() == null) {
                System.out.println(currentNode.getFundManager());    // Single element list case
            }
            else {
                while (currentNode != null) {    // Display remainder of log
                    System.out.println(currentNode.getFundManager());
                    currentNode = currentNode.getNextNode();
                }
            }
        }
    }

    /**
     * isLicenseUnique
     * This method takes an input broker license string and returns a boolean
     * indicating whether or not that license number already exists in the fund 
     * manager log.
     * 
     * @param license
     * @return
     */
    public boolean isLicenseUnique (String license) {
        boolean licenseIsUnique = false;
        FundManagerNode currentNode = this.listHead;
        
        // Case 1: Empty list
        if (currentNode == null) {
            licenseIsUnique = true;
        }
        
        // Case 2: Single element list
        else if (currentNode.getNextNode() == null) {
            if (! currentNode.getFundManager().getBrokerLicense().equals(license)) {
                licenseIsUnique = true;
            }
        }
        
        // Case 3: List greater than one element
        else {
            licenseIsUnique = true;
            while (currentNode.getNextNode() != null) {
                if (currentNode.getFundManager().getBrokerLicense().equals(license)) {
                    licenseIsUnique = false;
                    break;
                }
                currentNode = currentNode.getNextNode();
            }
            
            // Check final element in linked list
            if (currentNode.getFundManager().getBrokerLicense().equals(license)) {
                licenseIsUnique = false;
            }
            
        }
        
        return licenseIsUnique;
    }
    
    /**
     * cleanUp
     * Searches FundManager log for invalid broker licenses.  Removed those
     * that it finds, along with the associated StockTrades in the StockTrade log.
     * 
     * @param stockTradeLogImpl
     */
    public void cleanUp (StockTradeLogImpl stockTradeLogImpl) {
        FundManagerNode currentNode = this.listHead;
        
        // Loop through entire FundManager log
        while (currentNode != null) {
            if (! currentNode.getFundManager().checkBrokerLicenseIsValid()) {    // Detect invalid license
                
                System.out.println("Invalid broker license found for FundManager " 
                    + currentNode.getFundManager().getBrokerLicense() 
                    + " -- deleting FundManager and associated StockTrades from log");
                
                removeFromLog(currentNode.getFundManager());    // Remove fund manager
                stockTradeLogImpl.removeStockTradeByFundManager(currentNode.getFundManager().getBrokerLicense());    // Remove associated stock trades
            }
            currentNode = currentNode.getNextNode();
        }
        
    }
    
    /**
     * findFundManagerNode
     * Given an input broker license, search the FundManager log for a matching
     * license number.  Return if found.
     * 
     * @param license
     * @return matchingNode
     */
    public FundManagerNode findFundManagerNode (String license) {
        FundManagerNode matchingNode = null;
        FundManagerNode currentNode = this.listHead;
        
        // Loop through entire linked list searching for node with matching license number
        while (currentNode != null) {
            if (currentNode.getFundManager().getBrokerLicense().equals(license)) {    // Check for matching license
                matchingNode = currentNode;
            }
            
            currentNode = currentNode.getNextNode();    // Increment to next node in list
        }
        
        return matchingNode;
    }
}
