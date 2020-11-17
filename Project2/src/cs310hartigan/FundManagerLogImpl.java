package cs310hartigan;

import java.util.*;

/**
 * The FundManagerLogImpl class implements the Log interface and defines
 * an ArrayList data structure to hold Fund Manager objects.  It contains methods
 * for adding and removing fund manager elements from the ArrayList.  It also 
 * contains methods for sorting the list in ascending order by broker license
 * number, as well as determining whether or not a broker license already exists
 * in the log.
 * 
 * @author Matthew Hartigan
 * @version Week 2
 */
public class FundManagerLogImpl implements LogInterfaceImpl {
    // Data fields
    private ArrayList<FundManager> fundManagerLog = new ArrayList<>();    
    
    
    // Constructors
    /**
     * Default constructor
     */
    public FundManagerLogImpl () {
    }
  
    
    // Getters and Setters
    /**
     * Get fund manager log.
     * @return
     */
    public ArrayList<FundManager> getFundManagerLog () {
        return fundManagerLog; 
    }
    
    
    // User-defined methods
    /**
     * addToLog
     * This method implements the corresponding Log interface method.  It first 
     * sorts the existing fund manager ArrayList to identify the proper insertion 
     * position for the new fund manager based on broker license number.  It then
     * makes the addition and returns a boolean indicating the success of the 
     * operation.
     * 
     * @param obj
     * @return boolean
     */
    public boolean addToLog (Object obj) {
        FundManager inputFundManager = (FundManager) obj;   // Downcast object to be added into type FundManager
        boolean addToLogSuccessful = false;
        int initialLogSize = fundManagerLog.size();
        int insertionPosition = 0;    // Holds index where new object should be added
        
        // Perform linear sort, return index of desired position (ascending order)
        insertionPosition = sortLogByAscending(inputFundManager.getBrokerLicense());
        
        // Insert the new element
        fundManagerLog.add(insertionPosition, inputFundManager);
        
        // Validate that insertion was successful
        if (fundManagerLog.size() == (initialLogSize + 1)) {
            addToLogSuccessful = true;
        }

        return addToLogSuccessful;
    }

    /**
     * removeFromLog
     * This method implements the corresponding Log interface method.  It uses an
     * existing ArrayList instance method (ArrayList.remove()) to remove a fund
     * manager from the log.  It then return a boolean indicating the success of the
     * operation.
     * 
     * @param obj
     * @return
     */
    public boolean removeFromLog (Object obj) { 
        FundManager inputFundManager = (FundManager) obj;   // Downcast object to be removed into type FundManager
        
        // Remove specified object from the log (method .remove() returns true if element was found and removed)
        return fundManagerLog.remove(inputFundManager);
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
        int i = 0;     // loop counter
        
        // Iterate through entire fund manager log and identify first license match
        for (i = 0; i < fundManagerLog.size(); ++i) {
            if (fundManagerLog.get(i).getBrokerLicense().equals(license)) {
                licenseIsUnique = false;
            }
        }
       
        return licenseIsUnique;
    }

    /**
     * sortLogByAscending
     * This method takes in a String for a new entry (in this instance corresponding
     * to the new fund manager's broker license and identifies the correct position
     * that it should be inserted into the ArrayList at in order to maintain ascending
     * order.
     * 
     * @param newEntry
     * @return int
     */
    public int sortLogByAscending(String newEntry) {
        int insertionPosition = 0;
        
        // Increment through fund manager log until correct insertion position is reached
        while ((insertionPosition < fundManagerLog.size()) 
            && ((fundManagerLog.get(insertionPosition).getBrokerLicense().compareToIgnoreCase(newEntry)) < 0)) {
            ++insertionPosition;
        }

        return insertionPosition;
    }
}
