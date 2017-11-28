package ru.chirkovds.springtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "spare")
public class Spare implements Serializable {
    private Long id;
    private Long troubleId;
    private int qty;
    private Item item;
    private Trouble trouble;
    private float price;

    public Spare() {
    }

    public Spare(Spare spare) {
        this.id = spare.id;
        this.troubleId = spare.troubleId;
        this.qty = spare.qty;
        this.item = spare.item;
        this.trouble = spare.trouble;
    }

    public Spare(Long id, Long troubleId, int qty, Item item, Trouble trouble) {
        this.id = id;
        this.troubleId = troubleId;
        this.qty = qty;
        this.item = item;
        this.trouble = trouble;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "trouble_id", insertable = false, updatable = false)
    public Long getTroubleId() {
        return this.troubleId;
    }

    public void setTroubleId(Long troubleId) {
        this.troubleId = troubleId;
    }

    @Column(name = "qty")
    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Column(name = "price")
    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id"/*, foreignKey = @ForeignKey(name = "fk_item")*/, nullable = false, updatable = false)
    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trouble_id", foreignKey = @ForeignKey(name = "fk_trouble"))
    public Trouble getTrouble() {
        return this.trouble;
    }

    public void setTrouble(Trouble trouble) {
        this.trouble = trouble;
    }

    public String toString() {
        return "Spare ID - " + id + ", Qty - " + qty;
    }
}