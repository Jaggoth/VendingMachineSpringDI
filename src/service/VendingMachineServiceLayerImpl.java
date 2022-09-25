package service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Component;

import dao.VendingMachineAuditDAO;
import dao.VendingMachineDAO;
import dto.Item;

@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
	
    private VendingMachineAuditDAO auditDao;
    private VendingMachineDAO dao;
    
    public VendingMachineServiceLayerImpl(VendingMachineAuditDAO auditDao, VendingMachineDAO dao) {
        this.auditDao = auditDao;
        this.dao = dao;
    }

	@Override
	public void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws InsufficientFundsException {
        if ((item.getCost().compareTo(inputMoney)) == 1) {//if item cost is greater than money in throw the exception
            throw new InsufficientFundsException ("Insufficient funds, you only have " + inputMoney);
        }
	}

	@Override
	public Map<String, BigDecimal> getItemsInStockWithPrices() throws IOException {
        //Map <name, cost>
        Map<String, BigDecimal> itemsInStockWithCosts = dao.getMapOfInStockItemNamesWithPrices();
        return itemsInStockWithCosts;
	}

	@Override
	public Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money) {
        //Map <coinType, ammountOfCoin>
        Map<BigDecimal, BigDecimal> changeDuePerCoin = dao.getChangePerCoin(item, money);
        return changeDuePerCoin;
	}

	@Override
	public Item getItem(String name, BigDecimal inputMoney) throws InsufficientFundsException, NoItemInventoryException, IOException {
        Item wantedItem = dao.getItem(name);   //watch out the inputs are case-sensitive.
        
        if (wantedItem == null) {//if item is null, the item does not exist
            throw new NoItemInventoryException ("There are no such items in the vending machine.");
        }

        checkIfEnoughMoney(wantedItem,inputMoney);
        removeOneItemFromInventory(name);
        return wantedItem;
	}

	@Override
	public void removeOneItemFromInventory(String name) throws NoItemInventoryException, IOException {
        if (dao.getItemInventory(name)>0) {
            dao.itemPurchased(name);
            auditDao.writeAuditEntry(" One " + name + " removed");
        } else {
            throw new NoItemInventoryException (
                    "ERROR: " + name + " is out of stock.");
        }
	}

}
