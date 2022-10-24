package com.cdl.shopping.cart.service;

import com.cdl.shopping.cart.domain.Item;
import com.cdl.shopping.cart.domain.ItemDiscount;
import com.cdl.shopping.cart.repository.ItemDiscountRepository;
import com.cdl.shopping.cart.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemDiscountRepository itemDiscountRepository;

    @Transactional
    @Modifying
    public void saveItems(List<Item> items){
        itemRepository.saveAll(items);
    }

    @Transactional
    @Modifying
    public void saveItemDiscounts(List<ItemDiscount> discounts){
        itemDiscountRepository.saveAll(discounts);
    }

    public Item findItemByItemId(Integer itemId){
        return itemRepository.findById(itemId).get();
    }

    public List<Item> getItemList(){
        return itemRepository.findAll();
    }

    public ItemDiscount getItemDiscount(Item item){
        return itemDiscountRepository.findItemDiscountByItem(item);
    }
}
