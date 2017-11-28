package ru.chirkovds.springtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "item")
public class Item implements Serializable {

    private Long id;
    private String item;
    private float buyCost;
    private float sellCost;
    private int version;
    private Client client;
    private Spare spare;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "item")
    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "buy_cost")
    public float getBuyCost() {
        return this.buyCost;
    }

    public void setBuyCost(float buyCost) {
        this.buyCost = buyCost;
    }

    @Column(name = "sell_cost")
    public float getSellCost() {
        return this.sellCost;
    }

    public void setSellCost(float sellCost) {
        this.sellCost = sellCost;
    }

    @Column(name = "version")
    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return this.version;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_item_client"))
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "item", cascade = CascadeType.ALL)
    public Spare getSpare(){
        return this.spare;
    }
    public void setSpare (Spare spare){
        this.spare = spare;
    }
}