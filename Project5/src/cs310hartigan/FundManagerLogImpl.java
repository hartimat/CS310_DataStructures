package cs310hartigan;

/**
 * The FundManagerLogImpl class implements the Log interface and defines
 * a hash map with an underlying array to keep track of which FundManager 
 * objects are in the log.  It contains methods for adding to the log, displaying
 * log contents, finding a particular FM in the log, and resolving log addition 
 * collisions.
 * 
 * @author Matthew Hartigan
 * @version Week 5
 */
public class FundManagerLogImpl implements LogInterfaceImpl {

    // Data fields
    private final int MAX_HASHMAP_SIZE = 19;
    private FundManager [] fundManagerHash = null;
    
   
    // Constructors
    /**
     * Default constructor
     */
    public FundManagerLogImpl () {    
        this.fundManagerHash = new FundManager [this.MAX_HASHMAP_SIZE];
    }
    
    
    // Getters and Setters
    /**
     * Get array that underlies FundManager hash map. 
     * @return
     */
    public FundManager[] getFundManagerHash() {
        return fundManagerHash;
    }

    /**
     * Set array that underlies hash map.
     * 
     * @param fundManagerHash
     */
    public void setFundManagerHash(FundManager[] fundManagerHash) {
        this.fundManagerHash = fundManagerHash;
    }
    
    
    // User-defined Methods
    /**
     * addToLog
     * This method adds an element to the FundManager hash map
     * data structure.  
     * 
     * @param obj
     * @return addToLogSuccessful
     */    
    public boolean addToLog (Object obj) {
        FundManager inputFundManager = (FundManager) obj;   // Downcast object to be added into type FundManager
        boolean addToLogSuccessful = false;
        
        // Get hash code for input FM and determine if corresponding index is available.  If so, add to log
        if (this.fundManagerHash[inputFundManager.hashCode() % this.MAX_HASHMAP_SIZE] == null) {
            this.fundManagerHash[inputFundManager.hashCode() % this.MAX_HASHMAP_SIZE] = inputFundManager;
            addToLogSuccessful = true;
        }

        // If there is a collision at the initially requested index, call resolveCollision to find next availalble index
        else {
            if (resolveCollision(inputFundManager.hashCode() % this.MAX_HASHMAP_SIZE) == -1) {
                System.out.println("ERROR: FIXME: There was no available slot found in the FM hash map. Therefore no FM was added.");
            }
            else {
                this.fundManagerHash[resolveCollision(inputFundManager.hashCode() % this.MAX_HASHMAP_SIZE)] = inputFundManager;
                addToLogSuccessful = true;
            }
        }

        return addToLogSuccessful;
    }
    
    /**
     * removeFromLog
     * This method removes an element from the FundManager linked list
     * data structure.  
     * 
     * Note: This method is not utilized during the Week 5 assignment.  
     * However, it was left in to ensure that the FundManagerLogImpl class
     * still correctly implements the LogInterfaceImpl class.
     * 
     * @param obj
     * @return operationWasSuccessful
     */    
    public boolean removeFromLog (Object obj) {
        boolean operationWasSuccessful = true;
        
        // Method not implemented per Week 5 spec
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
        boolean licenseIsUnique = true;
        
        // Method not implemented per Week 5 spec
        // Left in order to avoid modifying validation method calls in CS310 main
        
        return licenseIsUnique;
    }
        
    /**
     * displayHash
     * Method to output contents of hash map to screen.
     * 
     */
    public void displayHash () {
        int i = 0;    // loop counter
        
        System.out.println("FundManager Hash Table:");
                
        // Loop through underlying array, displaying contents of each bucket accordingly
        for (i = 0; i < this.MAX_HASHMAP_SIZE; ++i) {
            
            // Bucket is empty
            if (this.fundManagerHash[i] == null) {
                System.out.println("Index " + i + " is empty");
            }
            
            // Bucket is full
            else {
                System.out.println("Index " + i + " contains FundManager " + this.fundManagerHash[i].getBrokerLicense() + ", " + this.fundManagerHash[i].getFirstName() + " " + this.fundManagerHash[i].getLastName());
            }
        }
    }
    
    /**
     * resolveCollision
     * Resolves hash map collisions via linear probe method.  Returns next available
     * index.
     * 
     * @param collisionIndex
     * @return currentIndex
     */
    public int resolveCollision (int collisionIndex) {
        int currentIndex = collisionIndex;
        boolean openIndexFound = false;
        
        // Linear probe through remaining elements of hash map array
        while (currentIndex < this.MAX_HASHMAP_SIZE 
            && ! openIndexFound) {
            if (this.fundManagerHash[currentIndex] == null) {
                openIndexFound = true;
                break;
            }
            else {
                currentIndex++;
            }
        }
            
        // Linear probe through preceding elements of hash map array
        if (! openIndexFound) {
            currentIndex = 0;
            
            while (currentIndex < collisionIndex 
                && ! openIndexFound) {
                if (this.fundManagerHash[currentIndex] == null) {
                    openIndexFound = true;
                    break;
               }
                else {
                    currentIndex++;
                }
            }
        }
            
        return currentIndex;
    }
    
    /**
     * find
     * Uses hash code of input broker license to search for and return matching
     * FundManager.  If they are not in the log, returns null reference variable value.
     * 
     * @param license
     * @return
     */
    public FundManager find (String license) {
        FundManager logEntry = null;
        int currentIndex = 0;
        
        // Case 1: No collisions, licenses match
        if (this.fundManagerHash[Integer.parseInt(license.substring(3, license.length())) % this.MAX_HASHMAP_SIZE].getBrokerLicense().equals(license)) {
            logEntry = this.fundManagerHash[Integer.parseInt(license.substring(3, license.length())) % this.MAX_HASHMAP_SIZE];
        }
        
        // Case 2: Collision, licenses may match
        else {
            // Set current index to start at original index of collision
            currentIndex = Integer.parseInt(license.substring(3, license.length())) % this.MAX_HASHMAP_SIZE;
            
            // Linear probe through remaining elements of hash map array
            while (currentIndex < this.MAX_HASHMAP_SIZE) {
                
                // Verify that index position is not empty (null reference value)
                if (this.fundManagerHash[currentIndex] != null) {
                    if (this.fundManagerHash[currentIndex].getBrokerLicense().equals(license)) {
                        logEntry = this.fundManagerHash[currentIndex];
                        break;
                    }
                    currentIndex++;
                }
                
                // Continue past empty position
                else {
                    currentIndex++;
                }
            }
            
            // Linear probe through remaining elements of hash map array
            if (logEntry == null) {
                currentIndex = 0;
                
                while (currentIndex < Integer.parseInt(license.substring(3, license.length())) % this.MAX_HASHMAP_SIZE) {
                    if (this.fundManagerHash[currentIndex] != null) {
                        if (this.fundManagerHash[currentIndex].getBrokerLicense().equals(license)) {
                            logEntry = this.fundManagerHash[currentIndex];
                            break;
                        }
                        currentIndex++;
                    }
                    
                    // Continue past empty position
                    else {
                        currentIndex++;
                    }
                }
            }
        }

        return logEntry;
    }
}
