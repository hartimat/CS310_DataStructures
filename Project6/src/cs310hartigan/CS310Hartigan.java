package cs310hartigan;


import java.util.Scanner;
import java.io.*;


/**
 * The CS310Hartigan class is the main class of the cs310hartigan package.
 * For Week 6 of CS310, it features five methods (main, 
 * readAndProcessingInputFile, addFundManager, buyStocktrade, and createReport).
 * Together, these methods will process an input file of FundManagers and 
 * StockTrades that are assumed to be valid.  They will then generate a report
 * summarizing the taxable status of StockTrades as requested by FundManagers
 * in a separate input file.  Meanwhile an audit trail is output to screen so the user
 * can follow the program steps.
 * 
 * @author Matthew Hartigan
 * @version Week 6
 */
public class CS310Hartigan {
    
    // Instantiations
    static FundManagerLogImpl fundManagerLogImpl = new FundManagerLogImpl ();
    static StockTradeLogImpl stockTradeLogImpl = new StockTradeLogImpl();
    static PrintImpl printImpl = new PrintImpl();
    
    
   /**
    * main
    * Sets filename constants and manages the method calls required to 
    * read input, display an audit trail and generate a report.
    * 
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       
       // Constant filename definitions 
       final String INPUT_FILENAME = "input/assn6input3.txt";  
       final String TAX_INPUT_FILENAME = "input/FundManagerRequests7.txt";
       final String OUTPUT_FILENAME = "output/assn6salesReport.txt";
       
       // Initial processing of input file
       readAndProcessInputFile(INPUT_FILENAME);
       
       // Traverse and display contents of log trees
       System.out.println();
       System.out.println("Fund Manager List:");
       fundManagerLogImpl.traverseDisplay(fundManagerLogImpl.getRoot());
       System.out.println();
       System.out.println("Stock Trade List:");
       stockTradeLogImpl.traverseDisplay(stockTradeLogImpl.getStockTradeTreeMap().values().iterator());
       System.out.println();
       
       
       // Read taxable input file, create report
       createReport(TAX_INPUT_FILENAME, OUTPUT_FILENAME);
   }   
     
    /**
     * readAndProcessInputFile
     * Takes an input file name String parameter and attempts to open the 
     * corresponding file so that data can be read in.  A try / catch block are
     * included to handle any IOExceptions thrown during this process.  Once the
     * input file is successfully opened, the function processes the input and calls
     * the remaining functions in the main class accordingly (addFundManager, 
     * buyStocktrade)
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
           if (inputFileScanner.hasNextLine()) {
               while (inputFileScanner.hasNextLine()) {

                   inputLine = inputFileScanner.nextLine();    // Read next input line from file
                   inputLineParsed = inputLine.split(",");    // Parse (csv file expected)

                   // Handle input if line contains BROKER details
                   // Assume no "DEL" requests will be made, per Week 5 project spec
                   if (inputLineParsed[0].equals("BROKER")) {
                       lineCounter++;
                       if (inputLineParsed[1].equals("ADD")) {    
                           addFundManager(inputLineParsed);    // Add a fund manager
                       }
                   }

                   // Handle input if line contains TRADE details
                   // Assume no "SELL" requests will be made, per Week 5 project spec
                   else if (inputLineParsed[0].equals("TRADE")) {
                       lineCounter++;
                       if (inputLineParsed[1].equals("BUY")) {   
                           buyStockTrade(inputLineParsed);    // Buy a stock trade
                       }
                   }

                   // Handle unexpected input file contents
                   else if (! inputLine.isEmpty()) {
                       System.out.println("Error: " + inputFilename + " provided unexpected input.");
                       System.out.println("Exiting program.");
                       System.exit(1);
                   }
               }    // Close while loop
           }
           
              else if (! inputFileScanner.hasNextLine()) {
                   System.out.println("Error: " + inputFilename + " is empty.");
                   System.out.println("Exiting program.");
                   System.exit(1);
              }
           
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
     * line from the input file wishes to add a fund manager to the log.  
     * 
     * @param inputLineParsed
     */
   public static void addFundManager(String [] inputLineParsed) {
       boolean operationWasSuccessful = false;
       // Instantiate new fund manager based on file input
       FundManager inputFundManager = new FundManager(inputLineParsed);    
       
       // Validate that broker license number does not already exist in the fund manager log
       // If broker license number is unique, add it to log and display summary to screen
       if (! fundManagerLogImpl.contains(fundManagerLogImpl.getRoot(), inputFundManager.getBrokerLicense())) {
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
     * buyStockTrade
     * This method is called by class method readAndProcessInputFile when a 
     * line from the input file wishes to add a stock trade to the log. 
     * 
     * @param inputLineParsed
     */
    public static void buyStockTrade(String [] inputLineParsed) {
        boolean operationWasSuccessful = false;
        StockTrade inputStockTrade = new StockTrade (inputLineParsed);
        
        // Add StockTrade to log (no validations required)
        if (! stockTradeLogImpl.getStockTradeTreeMap().containsKey(inputStockTrade.getStockSymbol())) {
               operationWasSuccessful = stockTradeLogImpl.addToLog(inputStockTrade);

               if (operationWasSuccessful) {    // Display message to screen if addition was successful

                   System.out.println("ADDED: StockTrade with stock symbol " 
                       + inputStockTrade.getStockSymbol() + " listed by FundManager " 
                       + inputStockTrade.getBrokerLicense());
           }
        }
        
       // If stock trade symbol is not unique, display message to screeen
       else {
           System.out.println("ERROR: StockTrade with stock symbol " 
               + inputStockTrade.getStockSymbol() + " already exists in the log.  "
                   + "Therefore it will not be re-added.");
       }  
   }
   
    /**
     * createReport
     * This method is called from main after the input file has been fully processed.
     * It then calls the printReport method from the print implementation to generate
     * an output file.
     * 
     * @param inputFilename
     * @param outputFilename
     */
    public static void createReport(String inputFilename, String outputFilename) {
        System.out.println("Creating  initial report...");
       
       // Create output report
       printImpl.printReport(fundManagerLogImpl, stockTradeLogImpl, inputFilename, outputFilename);           
   }
}
