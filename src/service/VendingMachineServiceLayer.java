package service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import dto.Item;

public interface VendingMachineServiceLayer {

    void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws  InsufficientFundsException;
    Map<String, BigDecimal> getItemsInStockWithPrices() throws IOException;
    Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money);
    Item getItem(String name, BigDecimal inputMoney) throws InsufficientFundsException, NoItemInventoryException, IOException;
    void removeOneItemFromInventory(String name) throws NoItemInventoryException, IOException;
}
