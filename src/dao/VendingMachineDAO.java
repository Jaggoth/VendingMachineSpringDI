package dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import dto.Item;

public interface VendingMachineDAO {

    int getItemInventory(String name) throws IOException;
    void itemPurchased(String name) throws IOException;
    Item getItem(String name) throws IOException;
    Map<String,BigDecimal> getMapOfInStockItemNamesWithPrices() throws IOException;
    List<Item> getAllItems() throws IOException;
    Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money);
}
