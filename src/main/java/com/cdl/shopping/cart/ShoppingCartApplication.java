package com.cdl.shopping.cart;

import com.cdl.shopping.cart.cart.ShoppingCart;
import com.cdl.shopping.cart.util.ShoppingCartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class ShoppingCartApplication implements CommandLineRunner {

    @Autowired
    ShoppingCartUtil util;
    @Autowired
    ShoppingCart cart;

    public static void main(String[] args) throws Exception{
        System.out.println("Application starts...");
        SpringApplication.run(ShoppingCartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Runtime.getRuntime().exec("clear");
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        // creates an object of Scanner
        Scanner input = new Scanner(System.in);
        util.initializeItems();
        util.printItems();
        cart.setItem(new ArrayList<>());
        cart.setPayableAmount(0.00);
        cart.setTotalAmount(0.00);
        util.getOption(input, cart);
    }
}
