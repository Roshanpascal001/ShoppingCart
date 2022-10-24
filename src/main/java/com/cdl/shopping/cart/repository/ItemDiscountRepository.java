package com.cdl.shopping.cart.repository;

import com.cdl.shopping.cart.domain.Item;
import com.cdl.shopping.cart.domain.ItemDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDiscountRepository extends JpaRepository<ItemDiscount, Integer> {
    public ItemDiscount findItemDiscountByItem(Item item);
}
