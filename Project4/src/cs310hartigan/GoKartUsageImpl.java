/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310hartigan;

/**
 * The GoKartUsageImpl class defines an array that is used for tracking
 * which go karts are currently in use and by which broker.  The methods of
 * the class serve to update the underlying array as well as return useful 
 * information such as displaying the log contents to screen.
 * 
 * @author Matthew Hartigan
 * @version Week 4
 */
public class GoKartUsageImpl {
    
    // Data Fields
    private String [] goKartUsageArray = null;
    private int numGoKartsInUse = 0;
    
    
    // Constructors
    /**
     * Default
     */
    public GoKartUsageImpl () { 
    }
   
    /**
     *
     * @param numGoKarts
     */
    public GoKartUsageImpl (int numGoKarts) {
        this.goKartUsageArray = new String [numGoKarts];
        for (int i = 0; i < numGoKarts; ++i) {
            this.goKartUsageArray[i] = Integer.toString(i+1);
        }
    }
    
    // Getters and Setters
    /**
     *
     * @return
     */
    public String[] getGoKartUsageArray() {
        return goKartUsageArray;
    }

    /**
     *
     * @param goKartUsageArray
     */
    public void setGoKartUsageArray(String[] goKartUsageArray) {
        this.goKartUsageArray = goKartUsageArray;
    }

    /**
     *
     * @return
     */
    public int getNumGoKartsInUse() {
        return numGoKartsInUse;
    }

    /**
     *
     * @param numGoKartsInUse
     */
    public void setNumGoKartsInUse(int numGoKartsInUse) {
        this.numGoKartsInUse = numGoKartsInUse;
    }
    
    
    // User-Defined Methods
    /**
     * displayUsageStatus
     * Print current contents of usage log to screen.
     */
    public void displayUsageStatus () {
        int i = 0;    // loop counter
        
        System.out.println("Go kart usage status: ");
        
        for (i = 0; i < this.goKartUsageArray.length; ++i) {
            System.out.println(this.goKartUsageArray[i]);
        }
    }
    
    /**
     * requestKart
     * Takes input broker license and inserts it into the corresponding go kart
     * position in the usage array.
     * 
     * @param numKart
     * @param requestorLicense
     * @return operationWasSuccessful
     */
    public boolean requestKart (int numKart, String requestorLicense) {
        boolean operationWasSuccessful = false;
        
        // Check there is an int matching the input numKart in the usage array
        if (this.goKartUsageArray[numKart - 1].equals(Integer.toString(numKart))) {
            this.goKartUsageArray[numKart - 1] = requestorLicense;    // Replace with requestor license
            operationWasSuccessful = true;
            this.numGoKartsInUse++;
        }
        
        return operationWasSuccessful;
    }

    /**
     * swapKart
     * Takes the input go kart number and replaces the existing broker license
     * in its position with the input broker license.  Used for when a go kart is
     * returned and there is already somebody waiting in the queue.
     * 
     * @param numKart
     * @param license
     */
    public void swapKart (int numKart, String license) {
        this.goKartUsageArray[numKart - 1] = license;
    }
    
    /**
     * returnKart
     * Takes input broker license and replaces it in the usage array with the 
     * input go kart number that is being returned.
     * 
     * @param numKart
     * @param returnerLicense
     * @return operationWasSuccessful
     */
    public boolean returnKart (int numKart, String returnerLicense)  {
        boolean operationWasSuccessful = false;
        
        // Check there is a license matching the input returner license in the input numKart position in the usage array
        if (this.goKartUsageArray[numKart - 1].equals(returnerLicense)) {
            this.goKartUsageArray[numKart - 1] = Integer.toString(numKart);
            operationWasSuccessful = true;
            this.numGoKartsInUse--;
        }
              
        return operationWasSuccessful;
    }

    /**
     * hasKart
     * Returns the go kart number corresponding to the input broker license in the
     * usage array.
     * 
     * @param returnerLicense
     * @return
     */
    public int hasKart (String returnerLicense) {
        int kartNum = -1;    // value to be returned if returner license not in usage array
        int i = 0;    // loop counter
        
        // Search usage array for license matching input license.  Return corresponding kartNum
        for (i = 0; i < this.goKartUsageArray.length; ++i) {
            if (this.goKartUsageArray[i].equals(returnerLicense)) {
                kartNum = i + 1;
                break;
            }
        }
        return kartNum;
    }
}
