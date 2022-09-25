package dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Change {

    public static BigDecimal changeDueInPennies (BigDecimal itemCost, BigDecimal money) {
        BigDecimal changeDueInPennies = (money.subtract(itemCost)).multiply(new BigDecimal("100"));
        System.out.println("Change due: $" + (changeDueInPennies.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP).toString()));
        return changeDueInPennies;
    }
    
    //Method in-case the Coin enum needs to be extended
    public static ArrayList<BigDecimal> numberValueOfAllCoins() {
        //Get all coin types into a String array
    	Coin[] coinEnumArray = Coin.values();
        ArrayList <String> coinStringList = new ArrayList<>();
        for (Coin coin : coinEnumArray) {
            coinStringList.add(coin.toString());
        }
        //Convert the String array into a number array
        ArrayList<BigDecimal> coins = new ArrayList<BigDecimal>();
        for (String coin : coinStringList) {
            coins.add(new BigDecimal(coin));
        }
        return coins;
    }

    public static Map<BigDecimal, BigDecimal> changeDuePerCoin (BigDecimal itemCost, BigDecimal money) {
    	//Get the number value of all coin types present in the enum
    	ArrayList<BigDecimal> coins = numberValueOfAllCoins();
        BigDecimal changeDueInPennies = changeDueInPennies(itemCost, money);
        BigDecimal ammountOfCoin;
        BigDecimal zero = new BigDecimal("0");
        //Map <coinType, ammountOfCoin>
        Map <BigDecimal, BigDecimal> amountPerCoin = new HashMap<>();

        for (BigDecimal coin : coins) {
            if (changeDueInPennies.compareTo(coin) >= 0) {//if the changeDueInPennies is equal or greater than the coin amount                
                if (!changeDueInPennies.remainder(coin).equals(zero)) {//If the coin amount is not an integer
                	
                	ammountOfCoin = changeDueInPennies.divide(coin,0,RoundingMode.DOWN);
                    amountPerCoin.put(coin, ammountOfCoin);
                    
                    changeDueInPennies = changeDueInPennies.remainder(coin);
                    
                    if ((changeDueInPennies.compareTo(zero))<0) {//if the change amount is less than 0 stop
                        break;
                    }
                } else if (changeDueInPennies.remainder(coin).equals(zero)) {//check again if the change divided by the coin is an integer
                	ammountOfCoin = changeDueInPennies.divide(coin,0,RoundingMode.DOWN);
                    amountPerCoin.put(coin, ammountOfCoin);
                    
                    if ((changeDueInPennies.compareTo(zero))<0) {//if the change amount is less than 0 stop
                        break;
                    }
                }
            } else {/*if the changeDueInPennies is less than the current coin do nothing*/}
        }
        return amountPerCoin;
    }
}
    
