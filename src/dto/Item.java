package dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {

    private String name;
    private BigDecimal cost;
    private int inventory;
    
	public Item() {}
	
	public Item(String name) {
		this.name = name;
	}

	public Item(String name, BigDecimal cost, int inventory) {
		this.name = name;
		this.cost = cost;
		this.inventory = inventory;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cost
	 */
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/**
	 * @return the inventory
	 */
	public int getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	@Override
	public String toString() {
		return "Item {name=" + name + ", cost=" + cost + ", inventory=" + inventory + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, inventory, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(cost, other.cost) && inventory == other.inventory && Objects.equals(name, other.name);
	}
    
    
}
