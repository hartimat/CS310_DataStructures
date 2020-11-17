package cs310hartigansort;


import java.util.Arrays;


/**
 * The Sort class contains all required sorting algorithms for the Week 7 assignment
 * that can be called from the program's main class as needed.
 * 
 * @author Matthew Hartigan
 * @version Week 7
 */
public class SortImpl {

    // Data Fields
    private int[] mergeSortTempArray = null;
    private int[] mergeSortInputArray = null;
    
    
    // Constructors
    SortImpl() {
    }

    
    // Methods
    /**
     * selectionSort
     * Implementation of selection array sort method.
     * 
     * @param inputArray
     * @return  
     */
    public static int [] selectionSort(int[] inputArray) {
        int [] sortedArray = inputArray;
        int remainingListTop = 0;
        int lastItemIndex = sortedArray.length - 1;
        int smallestIndex = 0;
        int compareIndex = 1;
        int valueToSwap= 0;    // holds value being swapped
        
        // Loop 1
        while (remainingListTop < sortedArray.length) {
            smallestIndex = remainingListTop;    // Start loop with smallestIndex set to first element of remaining stretch of list
            compareIndex = remainingListTop + 1;
            
            // Loop 2
            while (compareIndex <= lastItemIndex) {
                if (sortedArray[compareIndex] < sortedArray[smallestIndex]) {
                    smallestIndex = compareIndex;
                }
                ++compareIndex;
            }    // Close Loop 2
            
            valueToSwap = sortedArray[smallestIndex];    // Temporarily hold smallest value that was found for swapping
            sortedArray[smallestIndex] = sortedArray[remainingListTop];    // Replace index of smallest value found with current list top value
            sortedArray[remainingListTop] = valueToSwap;    // Replace current list top value with smallest value that was found
            ++remainingListTop;
        }    // Close Loop 1
        
        return sortedArray;
    }
    
    
    /**
     * insertionSort
     * Implementation of the insertion array sort method.
     * 
     * @param inputArray
     * @return 
     */
    public static int [] insertionSort(int[] inputArray) {
        int [] sortedArray = inputArray;
        int lastIndex = sortedArray.length - 1;
        int remainingTopIndex = 1;
        int insertValue = 0;
        int currentIndex = 0;
        
        // Loop 1
        while (remainingTopIndex <= lastIndex) {
            insertValue = sortedArray[remainingTopIndex];
            currentIndex = remainingTopIndex - 1;
            
            // Loop 2
            while ( (currentIndex >= 0) && (insertValue < sortedArray[currentIndex]) ) {
                sortedArray[currentIndex + 1] = sortedArray[currentIndex];
                --currentIndex;
            }
            
            sortedArray[currentIndex + 1] = insertValue;
            ++remainingTopIndex;
        }
        
        return sortedArray;
    }
    
    
    /**
     * mergeSort
     * Implementation of the mergeSort array sort method.  Calls two sub-methods
     * (doSort and doMerge) to perform the actual sort.
     * 
     * @param inputArray
     * @return  
     */
    public int[] mergeSort(int[] inputArray) {
        this.mergeSortInputArray= inputArray;
        this.mergeSortTempArray = new int[inputArray.length];

        doSort(0, inputArray.length - 1);
        
        return this.mergeSortInputArray; 
    }
    
    
    /**
     * doSort
     * Helper method for the mergeSort method.
     * 
     * @param lowIndex
     * @param highIndex
     * @return  
     */
    private void doSort(int lowIndex, int highIndex) {
        int middleIndex = 0;
        
        // Sort portion of mergeSort
        if (lowIndex < highIndex) {
            middleIndex = lowIndex + ( (highIndex - lowIndex) / 2 );
            doSort(lowIndex, middleIndex);
            doSort(middleIndex + 1, highIndex);
            doMerge(lowIndex, middleIndex, highIndex);    // Call merge portion of mergeSort
        }
    }
    
    /**
     * doMerge
     * Helper method for the mergeSort method.
     * 
     * @param lowIndex
     * @param middleIndex
     * @param highIndex
     * @return  
     */
    private void doMerge(int lowIndex, int middleIndex, int highIndex) {
        int i = 0;    // loop counter
        int j = 0;    // loop counter
        int k = 0;    // loop counter
        
        for (i = lowIndex; i <= highIndex; ++i) {
            this.mergeSortTempArray[i] = this.mergeSortInputArray[i];
        }
        
        // Combine subArrays in sorted order
        i = lowIndex;
        j = middleIndex + 1;
        k = lowIndex;

        while ( (i <= middleIndex) && (j <= highIndex) ) {
            if (this.mergeSortTempArray[i] <= this.mergeSortTempArray[j]) {
                this.mergeSortInputArray[k] = this.mergeSortTempArray[i];
                ++i;
            }
            else {
                this.mergeSortInputArray[k] = this.mergeSortTempArray[j];
                ++j;
            }
            
            ++k;
        }
        
        // Clear out any remaining elements in subArrays
        while (i <= middleIndex) {
            this.mergeSortInputArray[k] = this.mergeSortTempArray[i];
            ++i;
            ++k;
        }
    }
    
}
