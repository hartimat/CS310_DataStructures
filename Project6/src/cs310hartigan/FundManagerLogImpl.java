package cs310hartigan;

/**
 * The FundManagerLogImpl class implements the Log interface and defines
 * a binary search tree to keep track of which FundManager 
 * objects are in the log.  It contains methods for adding to the log, displaying
 * log contents, finding a particular FM in the log, and resolving log addition 
 * collisions.
 * 
 * @author Matthew Hartigan
 * @version Week 5
 */
public class FundManagerLogImpl implements LogInterfaceImpl {

    // Data Fields
    private FundManagerTreeNode root = null;
    
    
    // Constructors
    /**
     * Default
     */
    public FundManagerLogImpl() {
    }
    
    
    // Getters and Setters
    /**
     *
     * @return
     */
    public FundManagerTreeNode getRoot() {
        return root;
    }

    /**
     *
     * @param root
     */
    public void setRoot(FundManagerTreeNode root) {
        this.root = root;
    }
    
    
    // Methods
    /**
     * addToLog
     * This method adds an element to the FundManager data structure.
     * 
     * @param obj
     * @return addToLogSuccessful
     */    
    public boolean addToLog (Object obj) {
        boolean operationWasSuccessful = false;
        int nodeTally = 0;    // Holds number of nodes in tree in order to verify addition was successful
        
        // Cast input parameter to FundManager object
        FundManager inputFundManager = (FundManager) obj;
        
        // Generate FundManagerTreeNode using inputFundManager
        FundManagerTreeNode inputFundManagerTreeNode = new FundManagerTreeNode(inputFundManager);
         
        
        // Get number of nodes in tree prior to addition operation
        nodeTally = countNodes(this.root, 0);
        
        // Insert into tree if root already exists
        if (this.root != null) {
            insertIntoTree(this.root, inputFundManagerTreeNode);
        }
        
        // Otherwise, assign to root
        else {
            this.root = inputFundManagerTreeNode;
        }
        
        // Check number of nodes in tree post addtion operation, return outcome
        if (countNodes(this.root, 0) - nodeTally == 1) {
            operationWasSuccessful = true;
        }
        
        return operationWasSuccessful;
    }
    
    /**
     * insertIntoTree
     * Recursive method initially called by addToLog once it is confirmed that 
     * the binary search tree is not empty.  Searches through tree and places
     * input FundManagerTreeNode in correct sequential position.
     * 
     * @param root
     * @param inputFundManagerTreeNode
     */
    public void insertIntoTree (FundManagerTreeNode root, FundManagerTreeNode inputFundManagerTreeNode) {
        
        // Check left insert
        if (inputFundManagerTreeNode.getData().getBrokerLicense().compareTo(root.getData().getBrokerLicense()) < 0) {

            // Check left node NOT empty
            if (root.getLeftNode() != null) {
                insertIntoTree(root.getLeftNode(), inputFundManagerTreeNode);
            }

            // Otherwise insert into left node
            else {
                root.setLeftNode(inputFundManagerTreeNode);
            }
        }

        // Otherwise check right insert
        else {

            // Check right node NOT empty
            if (root.getRightNode() != null) {
                insertIntoTree(root.getRightNode(), inputFundManagerTreeNode);
            }

            // Otherwise insert into right node
            else {
                root.setRightNode(inputFundManagerTreeNode);
            } 
        }
    }
    

    /**
     * traverseDisplay
     * Traverses entire binary search tree in-order and displays contents to screen.
     * 
     * @param root
     */
    public void traverseDisplay(FundManagerTreeNode root) {
        
        // Ensure root is not empty, otherwise do nothing
        if (root != null) {
            traverseDisplay(root.getLeftNode());

            System.out.println(root.getData().toString());

            traverseDisplay(root.getRightNode());
        }
    }      
    
    
    /**
     * countNodes
     * Traverses the entire binary search tree in-order, counting the number of
     * nodes as it goes.  Returns the tally to calling function.
     * 
     * @param root
     * @param nodeTally
     * @return
     */
    public int countNodes(FundManagerTreeNode root, int nodeTally) {
        
        // Case 1: Current node is NOT empty
        if (root != null) {
            nodeTally = countNodes(root.getLeftNode(), nodeTally);

            // Increment tally
            ++nodeTally;

            nodeTally = countNodes(root.getRightNode(), nodeTally);
        }
        
        // Case 2: Current node is empty
        else {
            return nodeTally;
        }
        
        return nodeTally;
    }
    
    
    /**
     * find
     * Searches binary search tree for input target value, returns value if found.
     * Otherwise returns null.
     * 
     * @param root
     * @param target
     * @return
     */
    public FundManager find (FundManagerTreeNode root, String target) {
        FundManager match = null;
        
        if (root != null) {
            // If target is less than root, move left
            if (target.compareTo(root.getData().getBrokerLicense()) < 0) {
                match = find(root.getLeftNode(), target);
            }

            // Otherwise if target is greater than root, move right
            else if (target.compareTo(root.getData().getBrokerLicense()) > 0) {
                match = find(root.getRightNode(), target);
            }

            // Otherwise, collect root data (if null value, value does not exist in tree)
            else if (target.equals(root.getData().getBrokerLicense())) {
                return root.getData();
            }
        }

        return match;
    }
    
    
    /**
     * contains
     * Searches binary search tree for input target value.  Returns true if found.
     * 
     * @param root
     * @param target
     * @return
     */
    public boolean contains (FundManagerTreeNode root, String target) {
        boolean existsInLog = false;
        
        if (root != null) {
            // If target is less than root, move left
            if (target.compareTo(root.getData().getBrokerLicense()) < 0) {
                existsInLog = contains(root.getLeftNode(), target);
            }

            // Otherwise if target is greater than root, move right
            else if (target.compareTo(root.getData().getBrokerLicense()) > 0) {
                existsInLog = contains(root.getRightNode(), target);
            }

            // Otherwise, collect root data (if null value, value does not exist in tree)
            else if (target.equals(root.getData().getBrokerLicense())) {
                return true;
            }
        }

        return existsInLog;
    }
    
    
    /**
     * removeFromLog
     * This method removes an element from the FundManager linked list
     * data structure.  
     * 
     * Note: This method is not utilized during the Week 6 assignment.  
     * However, it was left in to ensure that the FundManagerLogImpl class
     * still correctly implements the LogInterfaceImpl class.
     * 
     * @param obj
     * @return operationWasSuccessful
     */    
    public boolean removeFromLog (Object obj) {
        boolean operationWasSuccessful = true;
        
        // Method not implemented per Week 6 spec
        // Left in order to ensure LogInterfaceImpl is correctly implemented
        
        return operationWasSuccessful;
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
        
        // Method not implemented per Week 6 spec
        // Left in order to avoid modifying validation method calls in CS310 main
        
        return true;
    }
}
