package view;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class VendingMachineView {

	 private UserIO io;
	 
    public VendingMachineView (UserIO io) {
        this.io = io;
    }
    
    public void printMenuHeader() {
        io.print("=== Menu ===");
    }
    
    public BigDecimal paymentIn() {
        return io.readBigDecimal("Enter money before selecting an item");
    }
    
    public void printMenu(Map<String, BigDecimal> inStockItemsAndPrices) {
    	inStockItemsAndPrices.entrySet().forEach(entry -> {
    		System.out.println(entry.getKey() + ": $" + entry.getValue());
        });
    }
    
    public String getItemInput() {
        return io.readString("Type an item from the menu or \"exit\" to quit");
    }
    
    public void printTransactionFooter(String name) {
        io.print("Don't forget your change and " + name + "!");
    }
    
    public void printNextSelectionPrompt() {
        io.print("Try selecting something else.");
    }
    
    public void printChangePerCoin(Map<BigDecimal, BigDecimal> coinAndValueMap) {
    	coinAndValueMap.entrySet().forEach(entry -> {
            System.out.println(entry.getValue() + " x " + entry.getKey() + "c");
        });
    }
    
    public void printExitMessage() {
        io.print("Have a nice day!");
        io.print("Closing app now");
    }
    
    public void displayErrorMessage (String errorMsg) {
        io.print("=== Error ===");
        io.print(errorMsg);
    }
    
    public void printUnknownCommandMessage() {
        io.print("Unknown command!");
    }  
}
