package com.cdl.shopping.cart.util;

import com.cdl.shopping.cart.cart.ShoppingCart;
import com.cdl.shopping.cart.domain.Item;
import com.cdl.shopping.cart.domain.ItemDiscount;
import com.cdl.shopping.cart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

@Component
public class ShoppingCartUtil {

    public static List<Item> items = new ArrayList<>();
    public static List<ItemDiscount> discounts = new ArrayList<>();

    @Autowired
    public ShoppingCartService service;

    /* The initializeItems() method set up the Database before the application begins transactions. */
    public void initializeItems(){
        Item itemA = new Item("A", 50.00);
        items.add(itemA);
        Item itemB = new Item("B", 30.00);
        items.add(itemB);
        Item itemC = new Item("C", 20.00);
        items.add(itemC);
        Item itemD = new Item("D", 15.00);
        items.add(itemD);

        discounts.add(new ItemDiscount(3, 130.00, itemA));
        discounts.add(new ItemDiscount(2, 45.00, itemB));
        service.saveItemDiscounts(discounts);

        service.saveItems(items);
    }

    /* The printItems() method prints the Item and Price list along with the MENU options */
    public void printItems(){
        System.out.print("Item Id\t|Item Name\t|Price");
        System.out.print("\n");
        System.out.print("--------------------------");
        System.out.print("\n");
        for(Item item: items) {
            System.out.print(item.getItemId() + "\t|");
            System.out.print(item.getItemName() + "       \t|");
            System.out.print(item.getUnitPrice() + "\t");
            System.out.print("\n");
        }
        System.out.print("--------------------------\n");
        System.out.print("MENU: PLEASE ENTER | B - BUY | R - REMOVE | C - CHECKOUT\n");
        System.out.print("--------------------------------------------------------\n");
    }

    /* getOption() method is the main entry point to the application */
    public void getOption(Scanner scanner, ShoppingCart cart){

        System.out.print("Enter a value from menu:\n");
        String option = scanner.nextLine();

        if(option.equalsIgnoreCase(ShoppingCartConstants.BUY)
            || option.equalsIgnoreCase(ShoppingCartConstants.REMOVE)
            || option.equalsIgnoreCase(ShoppingCartConstants.CHECKOUT)){
            getItemDetails(scanner, cart, option);
        }else{
            System.out.print("Please select the correct menu option!\n");
            System.out.print("--------------------------------------------------------\n");
            System.out.print("MENU: PLEASE ENTER | B - BUY | R - REMOVE | C - CHECKOUT\n");
            System.out.print("--------------------------------------------------------\n");
            getOption(scanner, cart);
        }
    }

    /* This is the main method where we scan the items and store or remove from the cart.*/
    public void getItemDetails(Scanner scanner, ShoppingCart cart, String option){

        if(option.equalsIgnoreCase(ShoppingCartConstants.CHECKOUT)){
            cart.setTotalAmount(calculateTotalAmount(cart.getItem()));
            cart.setPayableAmount(calculateTotalPayableAmount(cart.getItem()));
            displayCheque(scanner, cart);
        }

        System.out.print("Enter the item number:\n");
        String itemNum = scanner.nextLine();
        String quantity = "0";
        if(option.equalsIgnoreCase(ShoppingCartConstants.BUY)){ //Ignoring quantity input for REMOVE
            System.out.print("Enter quantity:\n");
            quantity = scanner.nextLine();
        }
        try {
            if (Integer.parseInt(itemNum) <= 0) {
                System.out.print("Input valid Item number.\n");
                getItemDetails(scanner, cart, option);
            }
            if (option.equalsIgnoreCase(ShoppingCartConstants.BUY) && Integer.parseInt(quantity) <= 0) {
                System.out.print("Input valid quantity in numeric.\n");
                getItemDetails(scanner, cart, option);
            }
        }catch (NumberFormatException e){
            System.out.print("Item number or quantity should be a numeric.\n");
            getItemDetails(scanner, cart, option);
        }
        List<Item> items = service.getItemList();
        boolean isItemFound = false;
        for(Item item: items){
            if(Integer.parseInt(itemNum) == item.getItemId()){
                isItemFound = true;
                if(option.equalsIgnoreCase(ShoppingCartConstants.BUY)){
                    item.setQuantity(Integer.parseInt(quantity));
                    cart.add(item);
                    break;
                }else if(option.equalsIgnoreCase(ShoppingCartConstants.REMOVE)){
                    cart.remove(item);
                    break;
                }
            }
        }
        if(!isItemFound){
            System.out.print("Item not found!\n");
            printItems();
        }
        displayInvoice(scanner, cart);
    }

    /* displayInvoice() prints the Invoice for the purchase. */
    public void displayInvoice(Scanner scanner, ShoppingCart cart) {
        List<Item> items = cart.getItem();
        ListIterator<Item> iterator = items.listIterator();
        System.out.println("----------------------- INVOICE --------------------------");
        System.out.println("It_no\tName\tQuant\tUnit\ttTotal\tAmt_Payable");
        while (iterator.hasNext()) {
            Item item = iterator.next();
            System.out.print(item.getItemId() + "\t");
            System.out.print(item.getItemName() + "\t\t");
            System.out.print(item.getQuantity() + "\t\t");
            System.out.print(item.getUnitPrice() + "\t");
            System.out.print(item.getUnitPrice() * item.getQuantity()+ "\t");
            System.out.print(calculatePayableAmount(item) + "\n");
        }
        System.out.println("---------------------------------------------------------");
        getOption(scanner, cart);
    }

    /* The displayCheque() method displays the final bill for the purchase with
    * Total Amount and Total payable amount after discount reduction. */
    public void displayCheque(Scanner scanner, ShoppingCart cart){
        System.out.println("----------------------------------------------------------");
        System.out.println("************************ CHEQUE **************************");
        System.out.println("\t\t\t" + "Total Amount   : " + cart.getTotalAmount());
        System.out.println("\t\t\t" + "Payable Amount : " + cart.getPayableAmount());
        System.out.println("----------------------------------------------------------");
        cart.setItem(new ArrayList<>());
        cart.setPayableAmount(0.00);
        cart.setTotalAmount(0.00);
        printItems();
        getOption(scanner, cart);
    }

    /* The calculateTotalAmount() method calculates the Total Amount for the entire purchase. */
    public Double calculateTotalAmount(List<Item> items){
        ListIterator<Item> itr = items.listIterator();
        Double totalAmount = 0.00;
        while(itr.hasNext()) {
            Item item = itr.next();
            totalAmount = totalAmount + (item.getUnitPrice() * item.getQuantity());
        }
        return totalAmount;
    }

    /* The calculatePayableAmount() method calculates the payable amount for the single item after a scan. */
    public Double calculatePayableAmount(Item item){
        ItemDiscount discount = service.getItemDiscount(item);
        Double payableAmt = 0.00;
        if(discount != null){
            if(item.getQuantity() % discount.getNoOfUnits() >= 0){
                int remaining = item.getQuantity() % discount.getNoOfUnits();
                int multiple = item.getQuantity() / discount.getNoOfUnits();
                payableAmt = discount.getDiscountedPrice() * multiple;
                payableAmt = payableAmt + (item.getUnitPrice() * remaining);
            }else{
                payableAmt = item.getUnitPrice() * item.getQuantity();
            }
        }else{
            payableAmt = item.getUnitPrice() * item.getQuantity();
        }
        return payableAmt;
    }

    /* The calculateTotalPayableAmount() method calculates the Total payable amount for the entire purchase. */
    public Double calculateTotalPayableAmount(List<Item> items){
        ListIterator<Item> itr = items.listIterator();
        Double totalPayAmount = 0.00;
        while(itr.hasNext()) {
            Item item = itr.next();
            totalPayAmount = totalPayAmount + calculatePayableAmount(item);
        }
        return totalPayAmount;
    }
}
