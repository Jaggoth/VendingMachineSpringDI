package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Component;

import dto.Item;
import service.InsufficientFundsException;
import service.NoItemInventoryException;
import service.VendingMachineServiceLayer;
import view.UserIO;
import view.UserIOConsoleImpl;
import view.VendingMachineView;

@Component
public class VendingMachineController {
	
    private VendingMachineView view;
    private VendingMachineServiceLayer service;
    
    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        String selectedItem = "";
        BigDecimal paymentIn;
        view.printMenuHeader();
        try {
            view.printMenu(service.getItemsInStockWithPrices());
        } catch (IOException e) {
            view.displayErrorMessage(e.getMessage());
        }
        paymentIn = view.paymentIn();
        while (keepGoing) {
            try {
            	selectedItem = view.getItemInput();
                if (selectedItem.equalsIgnoreCase("exit")) {
                    break;
                }
                getItem(selectedItem, paymentIn);
                keepGoing = false;

            } catch (InsufficientFundsException | NoItemInventoryException | IOException e) {
                view.displayErrorMessage(e.getMessage());
                view.printNextSelectionPrompt();
            }
        }
        view.printExitMessage();

    }

	private void getItem(String itemName, BigDecimal itemCost) throws InsufficientFundsException, NoItemInventoryException, IOException {
        Item wantedItem = service.getItem(itemName, itemCost);
        Map<BigDecimal, BigDecimal> changeDuePerCoin = service.getChangePerCoin(wantedItem, itemCost);
        view.printChangePerCoin(changeDuePerCoin);
        view.printTransactionFooter(itemName);
		
	}
}
