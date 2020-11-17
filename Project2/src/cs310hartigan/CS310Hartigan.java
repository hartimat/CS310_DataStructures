package cs310hartigan;

import java.util.Scanner;
import java.io.*;

/**
 * The CS310Hartigan class is the main class of the cs310hartigan package.  For
 * Week 2 of CS310, it features seven methods (main, 
 * readAndProcessingInputFile, addFundManager, deleteFund Manager, 
 * buyStocktrade, sellStockTrade, and createReport).  Together, these methods
 * will instantiate an ArrayList data structure of the FundManager class that we 
 * created during Week 1, as well as an array data structure of StockTrade class
 * objects.  A print implementation class will also be instantiated and used to
 * generate an output report (assn2report.txt) based on the contents that are read
 * and processed from various input files.
 * 
 * @author Matthew Hartigan
 * @version Week 2
 */
public class CS310Hartigan {

    // Implement instantiations
    static FundManagerLogImpl fundManagerLogImpl = new FundManagerLogImpl ();
    static StockTradeLogImpl stockTradeLogImpl = new StockTradeLogImpl();
    static PrintImpl printImpl = new PrintImpl();
        
   /**
    * main
    * Sets the input file name constant and calls readAndProcessInputFile to
    * collect the input data that will dictate what operations are executed by the
    * program.  Once input processing is complete, main calls createReport to 
    * generate an output file of results.
    * 
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       final String INPUT_FILENAME = "input/assn2input19.txt";   
       
       readAndProcessInputFile(INPUT_FILENAME);
       createReport();
   }   
     
    /**
     * readAndProcessInputFile
     * Takes an input file name String parameter and attempts to open the 
     * corresponding file so that data can be read in.  A try / catch block are
     * included to handle any IOExceptions thrown during this process.  Once the
     * input file is successfully opened, the function processes the input and calls
     * the remaining functions in the main class accordingly (addFundManager, 
     * deleteFund Manager, buyStocktrade, and sellStockTrade)
     * 
     * @param inputFilename
     */
    private static void readAndProcessInputFile(String inputFilename) {
       String inputLine = "";    // string to hold each line read from input file
       String [] inputLineParsed = null;    // string array to hold parsed form of each line read from input file
       FileInputStream inputFileObject = null;
       Scanner inputFileScanner  = null;
       
       // Attempt to open designated input file.  If successful, process its contents
       try {
           // Open input file
           inputFileObject = new FileInputStream(inputFilename);
           inputFileScanner = new Scanner(inputFileObject);
           System.out.println("Reading data from file " + inputFilename);
           
           // Process each line of input file 
           // Execute method calls to addFundManager, deleteFundManager,
           // buyStockTrade and sellStockTrade accordingly
           while (inputFileScanner.hasNextLine()) {
               inputLine = inputFileScanner.nextLine();    // Read next input line from file
               inputLineParsed = inputLine.split(",");    // Parse (csv file expected)
               
               // Handle input if line contains BROKER details
               if (inputLineParsed[0].equals("BROKER")) {
                   if (inputLineParsed[1].equals("ADD")) {    
                       addFundManager(inputLineParsed);    // Add a fund manager
                   }
                   else if (inputLineParsed[1].equals("DEL")) {     
                       deleteFundManager(inputLineParsed);    // Delete a fund manager
                   }
               }
              
               // Handle input if line contains TRADE details
               else if (inputLineParsed[0].equals("TRADE")) {
                   if (inputLineParsed[1].equals("BUY")) {   
                       buyStockTrade(inputLineParsed);    // Buy a stock trade
                   }
                   else if (inputLineParsed[1].equals("SELL")) {    
                       sellStockTrade(inputLineParsed);    // Sell a stock trade
                   } 
               }
               
               // Handle unexpected input file contents
               else if (! inputLine.isEmpty()) {
                   System.out.println("Error: " + inputFilename + " provided unexpected input.");
                   System.out.println("Exiting program.");
                   System.exit(1);
               }
           }    // Close while loop
           
           inputFileObject.close();    // Close the input file object
       }    // Close try block
       
       // Catch block for exceptions 
       catch (FileNotFoundException excpt) {
           System.out.println("Error: " + inputFilename + " could not be found.");
           System.out.println("Exiting program.");
           System.exit(1);
       }
       catch (Exception excpt) {
           System.out.println("Error: Unexpected exception thrown while attempting to access " + inputFilename);
           System.out.println(excpt.getMessage());
           System.out.println("Exiting program.");
           System.exit(1);
       } 
   }
    
    
    /**
     * addFundManager
     * This method is called by class method readAndProcessInputFile when a 
     * line from the input file wishes to add a fund manager to the log.  First, 
     * addFundManager will validate the broker license and department number of
     * the input. Then it will validate that the fund manager does not already 
     * exist in the log.
     * 
     * @param inputLineParsed
     */
   public static void addFundManager(String [] inputLineParsed) {
       boolean operationWasSuccessful = false;
       // Instantiate new fund manager based on file input
       FundManager inputFundManager = new FundManager(inputLineParsed);    
       
       // Validate the input fund manager details
       // NOTE: Invalid data will not prevent the fund manager from being added to the log
       // Validate broker license number
       if (! inputFundManager.checkBrokerLicenseIsValid()) {    
           System.out.println("ERROR: FundManager with broker license " 
               + inputFundManager.getBrokerLicense() + " has an invalid broker license.");
       }
       
       // Validate department number    
       if (! inputFundManager.checkDeptIsValid()) {    
       System.out.println("ERROR: FundManager with broker license " 
           + inputFundManager.getBrokerLicense() + " has invalid department number "
           + inputFundManager.getDept());
       }

       // Validate that broker license number does not already exist in the fund manager log
       // If broker license number is unique, add it to log and display summary to screen
       if (fundManagerLogImpl.isLicenseUnique(inputFundManager.getBrokerLicense())) {
           operationWasSuccessful = fundManagerLogImpl.addToLog(inputFundManager);    // Add the fund manager to the log
           
           // Display message if all data was valid and addition to log was successful
           if (operationWasSuccessful && inputFundManager.checkBrokerLicenseIsValid() 
               && inputFundManager.checkDeptIsValid()) {
               
               System.out.println("ADDED: FundManager with license " 
                   + inputFundManager.getBrokerLicense());
           }
           
           // Display message if NOT all data was valid but addition to log was successful
           else if (operationWasSuccessful && (! inputFundManager.checkBrokerLicenseIsValid() 
               || ! inputFundManager.checkDeptIsValid())) {
               
               System.out.println("ADDED: FundManager with license " 
                   + inputFundManager.getBrokerLicense() + ", regardless of data errors.");
           }
           
           // Display message if addition to the log was NOT successful
           else if (! operationWasSuccessful) {
               
               System.out.println("ERROR: The operation to add a new FundManager with broker license " 
                   + inputFundManager.getBrokerLicense() + " to the log failed");
           }
       }
       
       // If broker license number is not unique, display message to screeen
       else {
           System.out.println("ERROR: FundManager with license " 
               + inputFundManager.getBrokerLicense() + " already exists in the log.  "
                   + "Therefore they will not be re-added.");
       }       
   }
   
   
    /**
     * deleteFundManager
     * This method is called by class method readAndProcessInputFile when a 
     * line from the input file wishes to remove a fund manager from the log.  First, 
     * deleteFundManager will validate that the broker already exists in the fund
     * manager log.  It then displays a message corresponding to the result of
     * the operation. If a fund manager is successfully removed from the log, their
     * stock trades will also be removed from the stock trade log.
     * 
     * @param inputLineParsed
     */
    public static void deleteFundManager(String [] inputLineParsed) {
       boolean operationWasSuccessful = false;
       // Instantiate new fund manager based on file iput
       FundManager inputFundManager = new FundManager(inputLineParsed);    
       
       // Validate FundManager already exists in fund manager log.  If so, they can be deleted
       if (! fundManagerLogImpl.isLicenseUnique(inputFundManager.getBrokerLicense())) {    
           // Delete from fund manager log
           operationWasSuccessful = fundManagerLogImpl.removeFromLog(inputFundManager);
           
           // Display message for successful operation
           if (operationWasSuccessful) {   
               
               System.out.println("DELETED: Fund Manager with license " 
                   + inputFundManager.getBrokerLicense() + " has been removed from the FundManager log.  "
                       + "All FundManager's stocks will also be removed from the StockTrade log.");
               
               // Remove all stock trades corresponding to the fund manager that was removed
               operationWasSuccessful = stockTradeLogImpl.removeStockTradeByFundManager(inputFundManager.getBrokerLicense());    
           }
           
           // Display message if removal operation was NOT successful
           else if (! operationWasSuccessful) {    
               System.out.println("ERROR: There was an error removing FundManager with license number " 
                   + inputFundManager.getBrokerLicense() + " from the log.");
           }
       }
       
       // If broker license number did not already exist in the log, display message to screeen
       else {
           System.out.println("ERROR: Broker license number " 
               + inputFundManager.getBrokerLicense() + " was not found in the log.  "
                   + "Therefore it can NOT be deleted.");
       } 
   }
   
    
    /**
     * buyStockTrade
     * This method is called by class method readAndProcessInputFile when a 
     * line from the input file wishes to add a stock trade to the log.  First, 
     * buyStockTrade will validate the stock symbol, stock price per share, and
     * number of shares being purchased.  Next, buyStockTrade will validate the
     * whether the fund manager license numbers and stock trade symbols
     * are unique.  Results of these validations will then be processed accordingly
     * with messages displayed to screen.
     * 
     * @param inputLineParsed
     */
    public static void buyStockTrade(String [] inputLineParsed) {
        boolean operationWasSuccessful = false;
        boolean fundManagerLicenseNumberIsUnique = false;
        boolean stockTradeStockSymbolIsUnique = false;
        StockTrade inputStockTrade = new StockTrade (inputLineParsed);
        
        // Validate input data.  
        // NOTE: Invalid data will not prevent the stock trade from being added to the log
       // Validate stock symbol
       if (! inputStockTrade.checkStockSymbolIsValid()) {    // Display error 
           System.out.println("ERROR: StockTrade with stock symbol " + inputStockTrade.getStockSymbol() + " has an invalid stock symbol of " + inputStockTrade.getStockSymbol());
       }

       // Validate stock price
       if (! inputStockTrade.checkPricePerShareIsValid()) {    // Display error 
           System.out.println("ERROR: StockTrade with stock symbol " + inputStockTrade.getStockSymbol() + " has an invalid price per share of " + inputStockTrade.getPricePerShare());
       }
       
       // Validate number of shares
       if (! inputStockTrade.checkWholeSharesIsValid()) {    // Display error 
           System.out.println("ERROR: StockTrade with stock symbol " + inputStockTrade.getStockSymbol() + " has an invalid number of whole shares of " + inputStockTrade.getWholeShares());
       }
       
       
       // Validate existing log data
       // Validate whether fund manager number is unique
       fundManagerLicenseNumberIsUnique = fundManagerLogImpl.isLicenseUnique(inputStockTrade.getBrokerLicense());
       
       // Validate whether stock trade symbol is unique
       stockTradeStockSymbolIsUnique = stockTradeLogImpl.isStockSymbolUnique(inputStockTrade.getStockSymbol());
       
       
       // Handle input stock trade according to validation results
       // Case 1: Fund manager already exists, stock trade does not already exist, no StockTrade data validation errors
       if (! fundManagerLicenseNumberIsUnique 
           && stockTradeStockSymbolIsUnique 
           && inputStockTrade.checkStockSymbolIsValid() 
           && inputStockTrade.checkPricePerShareIsValid() 
           && inputStockTrade.checkWholeSharesIsValid()) {
           
           // Add StockTrade to log
           operationWasSuccessful = stockTradeLogImpl.addToLog(inputStockTrade);
           if (operationWasSuccessful) {    // Display message to screen if addition was successful
               
               System.out.println("ADDED: StockTrade with stock symbol " 
                   + inputStockTrade.getStockSymbol() + " listed by FundManager " 
                   + inputStockTrade.getBrokerLicense());
           }
           
           else {    // Display message to screen if addition was unsuccessful
               
               System.out.println("ERROR: There was an error adding StockTrade with stock symbol " 
                   + inputStockTrade.getStockSymbol() + " listed by FundManager " 
                   + inputStockTrade.getBrokerLicense() + " to the log.");
           }
       }
       
       // Case 2: Fund manager already exists, stock trade does not already exist, but there are StockTrade data validation errors
       else if (! fundManagerLicenseNumberIsUnique 
           && stockTradeStockSymbolIsUnique 
           && (! inputStockTrade.checkStockSymbolIsValid() 
           || ! inputStockTrade.checkPricePerShareIsValid() 
           || ! inputStockTrade.checkWholeSharesIsValid())) {
           
           // Add StockTrade to log
           operationWasSuccessful = stockTradeLogImpl.addToLog(inputStockTrade);
           if (operationWasSuccessful) {    // Display message to screen if addition was successful
               System.out.println("ADDED: StockTrade with stock symbol " 
                   + inputStockTrade.getStockSymbol() + " listed by FundManager " 
                   + inputStockTrade.getBrokerLicense() + ", regardless of data errors.");
           }
           
           else {    // Display message to screen if addition was unsuccessful
               System.out.println("ERROR: There was an error adding StockTrade with stock symbol " 
                   + inputStockTrade.getStockSymbol() + " listed by FundManager " 
                   + inputStockTrade.getBrokerLicense() + " to the log.");
           }
       }
       
       // Case 3: Broker license does not already exist 
       else if (fundManagerLicenseNumberIsUnique) {
           
           System.out.println("ADD ERROR: StockTrade with stock symbol " 
               + inputStockTrade.getStockSymbol() + " has FundManager with license number " 
               + inputStockTrade.getBrokerLicense() 
               + ", but there is no such FundManager license in the FundManager log.  StockTrade " 
               + inputStockTrade.getStockSymbol() + " will NOT be added to the StockTrade log.");
       }
       
       // Case 4: StockTrade stock symbol already exists
       else if (! stockTradeStockSymbolIsUnique) {
           
           System.out.println("ADD ERROR: StockTrade with stock symbol " 
               + inputStockTrade.getStockSymbol() + " has FundManager with license number " 
               + inputStockTrade.getBrokerLicense() 
               + ", but there is already a StockTrade with that stock symbol in the StockTrade log.  Therefore " 
               + inputStockTrade.getStockSymbol() 
               + " will NOT be added to the StockTrade log again.");
       }
   }
   
    
    /**
     * sellStockTrade
     * This method is called by class method readAndProcessInputFile when a 
     * line from the input file wishes to remove a stock trade from the log.  First, 
     * sellStockTrade will validate that the stock trade already exists in the log.
     * It will then display a message to screen corresponding to the results of the
     * operation.
     * 
     * @param inputLineParsed
     */
    public static void sellStockTrade(String [] inputLineParsed) {
       boolean operationWasSuccessful = false;
       boolean stockTradeStockSymbolIsUnique = false;
       // Instantiate new stock trade based on file input
       StockTrade  inputStockTrade = new StockTrade (inputLineParsed);    
       
       // Validate input stock trade exists in stock trade log already.  If so, remove it
       if (! stockTradeLogImpl.isStockSymbolUnique(inputStockTrade.getStockSymbol())) {
         operationWasSuccessful = stockTradeLogImpl.removeFromLog(inputStockTrade);
         
         // Display message to screen depending on outcome of stock trade removal operation
          if (operationWasSuccessful) {    // Display message for successful operation
              
              System.out.println("DELETED: StockTrade with stock symbol " 
                  + inputStockTrade.getStockSymbol() 
                  + " has been removed from the StockTrade log.");
          }
          
          else if (! operationWasSuccessful) {    // Display message for unsuccessful operation
              
              System.out.println("ERROR: There was an error removing StockTrade with stock symbol " 
                  + inputStockTrade.getStockSymbol() + " listed by FundManager " 
                  + inputStockTrade.getBrokerLicense() + " from the log.");
          }
      } 
       
       // If stock trade does not exist in the stock trade log already, display message to screen accordingly 
       else {
           
          System.out.println("DEL ERROR: StockTrade with stock symbol " 
              + inputStockTrade.getStockSymbol() 
              + " is not in the StockTrade log, so it cannot be deleted.");
      }  
   }
   
    
    /**
     * createReport
     * This method is called from main after the input file has been fully processed.
     * It then calls the printReport method from the print implementation to generate
     * an output file.
     * 
     */
    public static void createReport() {
       printImpl.printReport(fundManagerLogImpl, stockTradeLogImpl);
   }
}



