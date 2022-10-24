package com.cdl.shopping.cart.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ItemDiscount")
public class ItemDiscount {

    @Id
    @Column(name = "discount_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_discount_id")
    @SequenceGenerator(name = "seq_discount_id", sequenceName = "seq_discount_id",initialValue = 501, allocationSize = 1)
    private Integer discountId;
    @Column(name="no_of_units", nullable = false)
    private Integer noOfUnits;
    @Column(name = "discounted_price", nullable = false)
    private Double discountedPrice;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", nullable = false)
    @NotNull
    private Item item;

    public ItemDiscount() {
    }

    public ItemDiscount(Integer noOfUnits, Double discountedPrice, Item item) {
        this.noOfUnits = noOfUnits;
        this.discountedPrice = discountedPrice;
        this.item = item;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public Integer getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(Integer noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDiscount that = (ItemDiscount) o;
        return discountId.equals(that.discountId)
                && noOfUnits.equals(that.noOfUnits)
                && discountedPrice.equals(that.discountedPrice)
                && item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountId, noOfUnits, discountedPrice, item);
    }
}
