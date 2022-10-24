package com.cdl.shopping.cart.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "Item"
)
public class Item {

    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_item_id")
    @SequenceGenerator(name = "seq_item_id", sequenceName = "seq_item_id",initialValue = 1001, allocationSize = 1)
    private Integer itemId;
    @Column(name = "item_name", nullable = false)
    private String itemName;
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;
    @Column(name = "quantity")
    private Integer quantity;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "item", fetch = FetchType.LAZY)
    private ItemDiscount itemDiscount;

    public Item() {}

    public Item(String itemName, Double unitPrice) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId.equals(item.itemId) && itemName.equals(item.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName);
    }
}
