package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dto.Change;
import dto.Item;

@Component
public class VendingMachineDAOFileImpl implements VendingMachineDAO {
	
    private Map <String, Item> items = new HashMap<>();
    public static final String DELIMITER = "::";
    private final String VENDING_MACHINE_FILE;

    public VendingMachineDAOFileImpl() {
        VENDING_MACHINE_FILE = "VendingMachine.txt";
    }
    public VendingMachineDAOFileImpl(String testFile) {
        VENDING_MACHINE_FILE = testFile;
    }

	@Override
	public int getItemInventory(String name) throws IOException {
        fromFile();
        return items.get(name).getInventory();
	}

	@Override
	public void itemPurchased(String name) throws IOException {
		fromFile();
		Item item = items.get(name);
        int prevInventory = item.getInventory();
        item.setInventory(prevInventory-1);
        toFile();
	}

	@Override
	public Item getItem(String name) throws IOException {
		fromFile();
        return items.get(name);
	}

	@Override
	public Map<String, BigDecimal> getMapOfInStockItemNamesWithPrices() throws IOException {
		fromFile();
        Map<String, BigDecimal> itemsInStock = items.entrySet()//out of all entries in the map
                .stream()
                .filter(map -> map.getValue().getInventory() > 0)//keep only the ones that have a stock > 0
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue().getCost()));//then put these items into their own map
        return itemsInStock;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Item> getAllItems() throws IOException {
		fromFile();
        return new ArrayList(items.values());
	}

	@Override
	public Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money) {
        BigDecimal itemCost = item.getCost();
        Map<BigDecimal, BigDecimal> changeDuePerCoin = Change.changeDuePerCoin(itemCost, money);
        return changeDuePerCoin;
	}

    private void toFile() throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        String strItem;
        List <Item> itemList = this.getAllItems();
        for (Item item : itemList) {
        	strItem = marshalItem(item);
            out.println(strItem);
            out.flush();
        }
        out.close();
    }
    
    private String marshalItem (Item anItem) {
        String strItem = anItem.getName() + DELIMITER;
        strItem += anItem.getCost() + DELIMITER;
        strItem += anItem.getInventory();
        return strItem;
    }

    private void fromFile() throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(VENDING_MACHINE_FILE)));
        String line;
        Item item;

        while (scanner.hasNextLine()) {
        	line = scanner.nextLine();
        	item = unmarshalItem(line);
            items.put(item.getName(), item);
        }
        scanner.close();
    }
    
    private Item unmarshalItem (String itemAsText){
        String [] itemFields = itemAsText.split("::");
        Item item = new Item();
        item.setName(itemFields[0]);
        item.setCost(new BigDecimal(itemFields[1]));
        item.setInventory(Integer.parseInt(itemFields[2]));
        return item;
    }
}
