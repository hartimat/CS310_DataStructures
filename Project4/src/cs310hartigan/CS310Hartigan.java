package cs310hartigan;

import java.util.Scanner;
import java.io.*;

/**
 * The CS310Hartigan class is the main class of the cs310hartigan package.
 * For Week 4 of CS310, it features ten methods (main, 
 * readAndProcessingInputFile, addFundManager, deleteFund Manager, 
 * buyStocktrade, sellStockTrade, createReport, processGoKartInfo, 
 * processGoKartRequest and processGoKartReturn). Together, these methods
 * will process an input file of FundManagers and StockTrades, clean out
 * any invalid data, and generate a report.  They will then process an input file of 
 * go kart information, displaying an audit trail to screen before generating a
 * report.
 * 
 * @author Matthew Hartigan
 * @version Week 4
 */
public class CS310Hartigan {

    // Constants  (user may adjust as needed)
    static final double TOP_BROKER_THRESHOLD = 5000000;    // USD.  Sets amount of sales needed to reach top broker status
    static final int [] BASIC_GO_KART_ARRAY = {4, 3, 2, 1};    // Numerical list of regular go kart numbers in descending order
    static final int BASIC_GO_KART_ARRAY_LENGTH = 4;
    static final int BASIC_GO_KART_ARRAY_START = 1;
    static final int [] RACING_GO_KART_ARRAY = {7, 6, 5};    // Numerical list of top go kart numbers in descending order
    static final int RACING_GO_KART_ARRAY_LENGTH = 3;
    static final int RACING_GO_KART_ARRAY_START = 5;
    
    
    // Instantiations
    static FundManagerLogImpl fundManagerLogImpl = new FundManagerLogImpl ();
    static StockTradeLogImpl stockTradeLogImpl = new StockTradeLogImpl();
    static PrintImpl printImpl = new PrintImpl();
    static GoKartStackImpl basicGoKartStackImpl = new GoKartStackImpl(BASIC_GO_KART_ARRAY_LENGTH, BASIC_GO_KART_ARRAY_START);
    static GoKartStackImpl racingGoKartStackImpl = new GoKartStackImpl(RACING_GO_KART_ARRAY_LENGTH, RACING_GO_KART_ARRAY_START);
    static GoKartUsageImpl goKartUsageImpl = new GoKartUsageImpl(BASIC_GO_KART_ARRAY.length + RACING_GO_KART_ARRAY.length);    
    static FundManagerQueueImpl topFundManagerQueueImpl = new FundManagerQueueImpl();
    static FundManagerQueueImpl standardFundManagerQueueImpl = new FundManagerQueueImpl();
    
    
   /**
    * main
    * Sets filename constants and manages the method calls required to 
    * read both input files, provide audit trails, and generate both output reports.
    * 
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       
       // Constant filename definitions 
       final String INPUT_FILENAME = "input/assn4input21.txt"; 
       final String GO_KART_INPUT_FILENAME = "input/gokartInfo21s.txt";
       final String CLEAN_OUTPUT_FILENAME = "output/assn4cleanReport.txt";
       final String GO_KART_OUTPUT_FILENAME = "output/gokartUsageReport.txt";
       
       // Initial processing of input file
       readAndProcessInputFile(INPUT_FILENAME);
       
       // Once complete, display contents of FundManager and StockTrade logs to screen
       System.out.println();
       fundManagerLogImpl.traverseDisplay();
       stockTradeLogImpl.traverseDisplay();
       System.out.println();
       
       // Validate FundManager and StockTrade logs
       System.out.println("Cleaning up fundManager and stockTrade logs...");
       System.out.println();
       fundManagerLogImpl.cleanUp(stockTradeLogImpl);
       stockTradeLogImpl.cleanUp();
       
       // Create final report based on revisions made during validation
       System.out.println("Creating clean report...");
       System.out.println();
       createReport(CLEAN_OUTPUT_FILENAME);
       
       // Process go kart input file
       processGoKartInfo(GO_KART_INPUT_FILENAME);
       System.out.println();
       
       // Create go kart usage report
       System.out.println("Creating go kart usage report...");
       System.out.println();
       createReport(GO_KART_OUTPUT_FILENAME); 
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
       String inputLine = "";    // holds each line read from input file
       String [] inputLineParsed = null;    // holds parsed form of each line read from input file
       FileInputStream inputFileObject = null;
       Scanner inputFileScanner  = null;
       int lineCounter = 0;    // tallies how many lines have been read for input
       
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
                   lineCounter++;
                   if (inputLineParsed[1].equals("ADD")) {    
                       addFundManager(inputLineParsed);    // Add a fund manager
                   }
                   else if (inputLineParsed[1].equals("DEL")) {     
                       deleteFundManager(inputLineParsed);    // Delete a fund manager
                   }
               }
              
               // Handle input if line contains TRADE details
               else if (inputLineParsed[0].equals("TRADE")) {
                   lineCounter++;
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
       
       System.out.println("Done reading file. " + lineCounter + " lines read.");
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
       
       // If broker license number did not already exist in the log, display message to screen
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
       
//       // Validate whether stock trade symbol is unique
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
//               
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
     * @param outputFilename
     */
    public static void createReport(String outputFilename) {
        System.out.println("Creating report...");
       
       // Create Go-Kart Usage Report
       if (outputFilename.contains("kart")) {
           printImpl.printReport(goKartUsageImpl, racingGoKartStackImpl, basicGoKartStackImpl, topFundManagerQueueImpl, standardFundManagerQueueImpl, fundManagerLogImpl, stockTradeLogImpl, outputFilename);
       }
       
       // Create FundManager / StockTrade report
       else {
           printImpl.printReport(fundManagerLogImpl, stockTradeLogImpl, outputFilename);
       }
           
   }
    
    /**
     * processGoKartInfo
     * Opens a go kart input file specified by the input parameter.  Processes the
     * input file by calling processGoKartRequest() and processGoKartReturn()
     * accordingly.
     * 
     * @param inputFilename
     */
    public static void processGoKartInfo(String inputFilename) {
        String inputLine = "";    // holds each line read from input file
        String [] inputLineParsed = null;    // holds parsed form of each line read from input file
        FileInputStream inputFileObject = null;
        Scanner inputFileScanner = null;
        int lineCounter = 0;
        
        // Attempt to open designated input file.  If successful, process its contents
        try {
           // Open input file
           inputFileObject = new FileInputStream(inputFilename);
           inputFileScanner = new Scanner(inputFileObject);
           System.out.println("Reading data from file " + inputFilename);        
           
           // Process each line of input file 
           // Execute method calls to processGoKartRequest() and 
           // processGoKartReturn() accordingly
           while (inputFileScanner.hasNextLine()) {
               inputLine = inputFileScanner.nextLine();    // Read next input line from file
               inputLineParsed = inputLine.split(" ");    // Parse (space expected)
               
               // Handle input if line contains REQUEST details
               if (inputLineParsed[0].equals("REQUEST")) {
                   lineCounter++;

                   // Process request if broker exists in FundManager log
                   if (! fundManagerLogImpl.isLicenseUnique(inputLineParsed[1]) ) {
                       processGoKartRequest(inputLineParsed[1]);           
                   }
                   
                   // Ignore request if broker not found in FundManager log
                   else {
                       System.out.println("Unknown broker " + inputLineParsed[1] 
                           + " is not allowed access to go-karts.  Request ignored.");
                   }
               }
              
               // Handle input if line contains RETURN details
               else if (inputLineParsed[0].equals("RETURN")) {
                   lineCounter++;

                   // Process return if broker exists in FundManager log
                   if (! fundManagerLogImpl.isLicenseUnique(inputLineParsed[1]) ) {
                       processGoKartReturn(inputLineParsed[1]);           
                   }
                   
                   // Ignore request if broker not found in FundManager log
                   else {
                       System.out.println("Unknown broker " + inputLineParsed[1] 
                           + " is not allowed to access go-karts.  Return ignored");
                           }
               }
                              
               // Handle unexpected input file contents
               else if (! inputLine.isEmpty()) {
                   System.out.println("Error: " + inputFilename + " provided an unexpected input line.");
                   System.out.println("Exiting program.");
                   System.exit(1);
               }
           }    // Close while loop
           
           inputFileObject.close();    // Close the input file object
        }
        
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
        
        System.out.println("Done reading file. " + lineCounter + " lines read.");
    }
    
    /**
     * processGoKartRequest
     * Handles  a broker request for a go kart based on the contents of the 
     * go kart input file.  Brokers are identified as top or standard based on
     * the constant TOP_BROKER_THRESHOLD value defined above.
     * An audit trail is output to screen according to how the function handles
     * each request bast on broker characteristics specified in program requirements.
     * 
     * @param license
     */
    public static void processGoKartRequest(String license) {
       
        // Process go kart request from top broker
        if ( (stockTradeLogImpl.totalStockTradeValue(license) - TOP_BROKER_THRESHOLD) > 0.0) {
            
            if (! racingGoKartStackImpl.isEmpty()) {    // Check for available racing go karts
                goKartUsageImpl.requestKart(racingGoKartStackImpl.pop(), license);    // Take first racing go kart from stack
                System.out.printf("Top broker %s %s has been assigned racing go-kart number %s", 
                    fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                    fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                    goKartUsageImpl.hasKart(license));
                System.out.println();
            }
            
            else if ( ! basicGoKartStackImpl.isEmpty()) {    // Otherwise check for availalbe regular go karts
                 goKartUsageImpl.requestKart(basicGoKartStackImpl.pop(), license);
                 System.out.printf("Top broker %s %s has been assigned basic go-kart number %s", 
                     fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                     fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                     goKartUsageImpl.hasKart(license));
                System.out.println();
             }
            
            else {    // If no go karts are available, add the top broker to the top broker queue
                topFundManagerQueueImpl.addToQueue(fundManagerLogImpl.findFundManagerNode(license).getFundManager());
                System.out.printf("%s %s waiting in top broker queue", fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                    fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName());
                System.out.println();
            }
        }
        
        // Process go kart request from regular broker
        else {
            if ( ! basicGoKartStackImpl.isEmpty()) {    // Check for availalbe regular go karts
                 goKartUsageImpl.requestKart(basicGoKartStackImpl.pop(), license);
                 System.out.printf("Standard broker %s %s has been assigned basic go-kart number %s", 
                     fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                     fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                     goKartUsageImpl.hasKart(license));
                System.out.println();
             }
                
            else {    // If no regular go karts are availalbe, add the standard broker to the standard broker queue
                standardFundManagerQueueImpl.addToQueue(fundManagerLogImpl.findFundManagerNode(license).getFundManager());   
                System.out.printf("%s %s waiting in standard broker queue", fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                    fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName());
                System.out.println();
            }
        }
    }
    
    /**
     * processGoKartReturn
     * Handles  a broker return of a go kart based on the contents of the 
     * go kart input file.  An audit trail is output to screen according to how the function handles
     * each return based on broker characteristics specified in program requirements.
     * 
     * @param license
     */
    public static void processGoKartReturn(String license) {
        int i = 0;    // loop counter
        
        // Check broker returning kart is in usage log, if not, output error message
        if (goKartUsageImpl.hasKart(license) == -1)  {
            
            System.out.println("Broker " + license + " attempting to return a go-kart does not currently have one according to the usage log.  Return ignored.");
        }

        // After confirming broker is in usage log, process request accordingly
        else {
            
            // CASE 1: Go-kart is being returned and there are top brokers waiting in the queue
            if (! topFundManagerQueueImpl.isEmpty()) {

                // CASE 1A; Go-kart being returned is a top go-kart
                if (racingGoKartStackImpl.isKartType(RACING_GO_KART_ARRAY, goKartUsageImpl.hasKart(license))) {
                    
                    // Display return message
                    System.out.printf("%s %s has returned racing go-kart number %s", 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                
                    // Display swap message, then assign returned top gokart to top broker
                    System.out.printf("Top broker %s %s has been assigned racing go-kart number %s", 
                        topFundManagerQueueImpl.peekFirst().getFirstName(), topFundManagerQueueImpl.peekFirst().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                    
                    // Swap to first broker in queue
                    goKartUsageImpl.swapKart(goKartUsageImpl.hasKart(license), topFundManagerQueueImpl.removeFromQueue().getBrokerLicense());
                }

                // CASE 1B: Go-kart being returned is a standard go-kart
                else {
                    
                    // Display return message
                    System.out.printf("%s %s has returned standard go-kart number %s", 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                
                    // Display swap message, then assign returned standard gokart to top broker
                    System.out.printf("Top broker %s %s has been assigned standard go-kart number %s", 
                        topFundManagerQueueImpl.peekFirst().getFirstName(), 
                        topFundManagerQueueImpl.peekFirst().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                    
                    // Swap to first broker in queue
                    goKartUsageImpl.swapKart(goKartUsageImpl.hasKart(license), topFundManagerQueueImpl.removeFromQueue().getBrokerLicense());
                }
            }

            // CASE 2: Go-kart is being returned and there are regular brokers waiting in the queue (but no top brokers)
            else if (! standardFundManagerQueueImpl.isEmpty()) {
                
                // CASE 2A; Go-kart being returned is a regular go-kart
                if (basicGoKartStackImpl.isKartType(BASIC_GO_KART_ARRAY, goKartUsageImpl.hasKart(license))) {
                    
                    // Display return message
                    System.out.printf("%s %s has returned basic go-kart number %s", 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                
                    // Display swap message, then assign returned basic gokart to standard broker
                    System.out.printf("Standard broker %s %s has been assigned basic go-kart number %s", 
                        standardFundManagerQueueImpl.peekFirst().getFirstName(), 
                        standardFundManagerQueueImpl.peekFirst().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                    
                    // Swap to first broker in queue
                    goKartUsageImpl.swapKart(goKartUsageImpl.hasKart(license), standardFundManagerQueueImpl.removeFromQueue().getBrokerLicense());
                    
                }

                // CASE 2B; Go-kart being returned is a top go-kart
                else {

                    // Display return message
                    System.out.printf("%s %s has returned racing go-kart number %s", 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                    
                    // Push racing go kart back onto stack, update usage log
                    racingGoKartStackImpl.push(goKartUsageImpl.hasKart(license));    
                    goKartUsageImpl.returnKart(goKartUsageImpl.hasKart(license), license);
                }   
            }
            
            // CASE 3: Both queues are empty
            else {
                
                // Push returned kart back to top stack
                if (racingGoKartStackImpl.isKartType(RACING_GO_KART_ARRAY, goKartUsageImpl.hasKart(license))) {
                    
                    // Display return message
                    System.out.printf("%s %s has returned racing go-kart number %s", 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                    
                    // Push racing go kart back onto stack and update usage log
                    racingGoKartStackImpl.push(goKartUsageImpl.hasKart(license));    
                    goKartUsageImpl.returnKart(goKartUsageImpl.hasKart(license), license);    
                    
                }

                // Push returned kart back to regular stack
                else {
                    
                    // Display return message
                    System.out.printf("%s %s has returned standard go-kart number %s", 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getFirstName(), 
                        fundManagerLogImpl.findFundManagerNode(license).getFundManager().getLastName(), 
                        goKartUsageImpl.hasKart(license));
                    System.out.println();
                    
                    // Push basic go kart back onto stack and update usage log
                    basicGoKartStackImpl.push(goKartUsageImpl.hasKart(license));   
                    goKartUsageImpl.returnKart(goKartUsageImpl.hasKart(license), license);   
                }
            }
        }  
    }
}
