package com.cdl.shopping.cart.cart;

import com.cdl.shopping.cart.domain.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ListIterator;

/*
*  ShoppingCart class has members and methods that are related to shopping cart
*  such as add, remove items to the cart and get items from the cart
*/

@Component
public class ShoppingCart {

    List<Item> items;
    double totalAmount;
    double payableAmount;

    public ShoppingCart() {}

    public ShoppingCart(List<Item> items, double totalAmount, double payableAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
        this.payableAmount = payableAmount;
    }

    public List<Item> getItem() {
        return items;
    }

    public void setItem(List<Item> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public void add(Item item) {
        if(!items.contains(item)) {
            this.items.add(item);
            return;
        }
        ListIterator<Item> itr = this.items.listIterator();
        while(itr.hasNext()) {
            Item item2 = itr.next();
            if (item2.equals(item)) {
                item2.setQuantity(item2.getQuantity()+item.getQuantity());
                break;
            }
        }
    }

    public void remove(Item item) {
        ListIterator<Item> iterator1 = this.items.listIterator();
        while(iterator1.hasNext()) {
            Item item2 = iterator1.next();
            if (item2.equals(item)) {
                this.items.remove(item);
                break;
            }
        }
    }
}
