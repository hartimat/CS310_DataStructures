package cs310hartigan;

/**
 * The GoKartStackImpl class defines a standard stack for the purpose of 
 * keeping track of which go karts are currently available for use.
 * 
 * @author Matthew Hartigan
 * @version Week 4
 */
public class GoKartStackImpl {
    
    // Data Fields
    private int [] goKartStack = null;
    private int numGoKartsInStack = 0;
    
    
    // Constructors
    /**
     * Default
     */
    public GoKartStackImpl () {   
    }    
    
    /**
     *
     * @param goKartArray
     */
    public GoKartStackImpl (int [] goKartArray) {
        this.goKartStack = goKartArray;
        this.numGoKartsInStack = goKartArray.length;
    }
    
    /**
     *
     * @param arrayLength
     * @param arrayStart
     */
    public GoKartStackImpl (int arrayLength, int arrayStart) {
        this.numGoKartsInStack = arrayLength;
        this.goKartStack = new int [arrayLength];
        
        // Fill array in descending order
        for (int i = 0; i < arrayLength; ++i) {
            this.goKartStack[i] = arrayStart + arrayLength - (i+1);
        }
    }
    
    
    // Getters and Setters
    /**
     *
     * @return
     */
    public int[] getGoKartStack() {
        return goKartStack;
    }

    /**
     *
     * @param goKartStack
     */
    public void setGoKartStack(int[] goKartStack) {
        this.goKartStack = goKartStack;
    }

    /**
     *
     * @return
     */
    public int getNumGoKartsInStack() {
        return numGoKartsInStack;
    }

    /**
     *
     * @param numGoKartsInStack
     */
    public void setNumGoKartsInStack(int numGoKartsInStack) {
        this.numGoKartsInStack = numGoKartsInStack;
    }
    
        
    // User-Defined Methods
    /**
     * printStack
     * Prints the current contents of the stack to screen.
     */
    public void printStack () {
        int i = 0;    // loop counter
        
        if (! isEmpty()) {
            System.out.println("Go kart stack contents: ");
            for (i = 0; i < this.numGoKartsInStack; ++i) {
                System.out.println(this.goKartStack[i]);
            }
        }
        else {
            System.out.println("The stack is empty.  There is nothing to print.");
        }
    }
    
    /**
     * isEmpty
     * Checks to see if stack is currently empty.
     * 
     * @return stackIsEmpty
     */
    public boolean isEmpty() {
        boolean stackIsEmpty = false;
        
        if (this.numGoKartsInStack == 0) {
            stackIsEmpty = true;
        }
        
        return stackIsEmpty;
    }
    
    /**
     * isFull
     * Checks to see if stack is currently full.
     * @return stackIsFull
     */
    public boolean isFull() {
        boolean stackIsFull = true;
       
        if (this.numGoKartsInStack != this.goKartStack.length) {
            stackIsFull = false;
        }
        
        // If stack is full, display error message
        else {
            System.out.println("The stack is already full, therefore no go kart was pushed.");
        }
        
        return stackIsFull;
    }
    
    /**
     * push
     * Pushes input goKartNum onto stack.
     * 
     * @param goKartNum
     */
    public void push (int goKartNum) {
        
        // Check that stack is not already full, then add
        if (! isFull()) {
            this.goKartStack[this.numGoKartsInStack] = goKartNum;    // Add to top
            this.numGoKartsInStack++;
        }
    }
    
    /**
     * pop
     * Remove the goKartNum that is on top of the stack and return it.
     * 
     * @return goKartNum (int)
     */
    public int pop () {
        int goKartNum = -1; 
            
        // Check stack not empty
        if (! isEmpty()) {
            goKartNum = this.goKartStack[this.numGoKartsInStack - 1];
            this.numGoKartsInStack--;
        }
        
        // If stack is empty, display error message
        else {
            System.out.println("The stack is empty, therefore no go kart was popped.");
        }
        
        return goKartNum;
    }
    
    /**
     * isKartType
     * Checks to see if input gokart number is a member of the input kart array.
     * Returns boolean according to result.
     * 
     * @param kartArray
     * @param kartNum
     * @return isKartType
     */
    public boolean isKartType(int [] kartArray, int kartNum) {
        boolean isKartType = false;
        int i = 0;    // loop counter
        
        for (i = 0; i < kartArray.length; ++i) {
            
            if (kartNum == kartArray[i]) {
                isKartType = true;
            }
        }
        
        return isKartType;
    }
}
