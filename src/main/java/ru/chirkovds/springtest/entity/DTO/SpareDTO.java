package ru.chirkovds.springtest.entity.DTO;

import ru.chirkovds.springtest.entity.Spare;

import java.io.Serializable;

public class SpareDTO implements Serializable{
    private String item;
    private Long id;
    private int qty;
    private float sellCost;

    public SpareDTO() {}

    public SpareDTO(Spare spare){
        this.item = spare.getItem().getItem();
        this.id = spare.getId();
        this.qty = spare.getQty();
        this.sellCost = spare.getPrice();
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long spareId) {
        this.id = spareId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getSellCost() {
        return sellCost;
    }

    public void setSellCost(float sellcost) {
        this.sellCost = sellcost;
    }
    public String toString() {
        return item + ", " + sellCost + ", " + qty + ".";
    }
}
