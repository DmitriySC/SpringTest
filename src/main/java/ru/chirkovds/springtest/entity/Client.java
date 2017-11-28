package ru.chirkovds.springtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import ru.chirkovds.springtest.entity.enums.ClientSpecific;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

 @Entity
 @Table (name = "Client")
 public class Client implements Serializable {
     private Long id;
     private String inn;
     private int version;
     private String client;
     private ClientSpecific clientSpecific;
     private String address;
     private String phone;
     private List<User> UserList;
     private List<Trouble> TroubleList;
     private List<Item> itemList;

     public Client() {
     }

     @Id
     @GeneratedValue(strategy = IDENTITY)
     @Column(name = "id")
     public Long getId() {
         return this.id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     @NotEmpty(message = "{validation.inn.NotEmpty.message}")
     @Size(min = 10, max = 12, message = "{validation.inn.Size.message}")
     @Column(name = "inn")
     public String getInn() {
         return this.inn;
     }

     public void setInn(String inn) {
         this.inn = inn;
     }

     @Version
     @Column(name = "version")
     public int getVersion() {
         return this.version;
     }

     public void setVersion(int version) {
         this.version = version;
     }

     @NotEmpty(message = "{validation.client.NotEmpty.message}")
     @Size(min = 5, max = 60, message = "{validation.client.Size.message}")
     @Column(name = "client")
     public String getClient() {
         return this.client;
     }

     public void setClient(String client) {
         this.client = client;
     }

     @NotNull(message = "{validation.client_specific.NotEmpty.message}")
     @Column(name = "client_specific")
     @Enumerated(EnumType.STRING)
     public ClientSpecific getClientSpecific() {
         return this.clientSpecific;
     }

     public void setClientSpecific(ClientSpecific clientSpecific) {
         this.clientSpecific = clientSpecific;
     }

     @NotEmpty(message = "{validation.address.NotEmpty.message}")
     @Size(min = 15, message = "{validation.address.Size.message}")
     @Column(name = ("address"))
     public String getAddress() {
         return this.address;
     }

     public void setAddress(String address) {
         this.address = address;
     }

     @NotEmpty(message = "{validation.phone.NotEmpty.message}")
     @Size(min = 10, max = 10, message = "{validation.phone.Size.message}")
     @Column(name = "phone")
     public String getPhone() {
         return this.phone;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }

     @JsonIgnore
     @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = {CascadeType.ALL})
     public List<User> getUserList() {
         return this.UserList;
     }

     public void setUserList(List<User> userList) {
         this.UserList = userList;
     }

     public void addUser(User user) {
         user.setClient(this);
         getUserList().add(user);
     }

     public void removeUser(User user) {
         getUserList().remove(user);
     }

     @JsonIgnore
     @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
     public List<Trouble> getTroubleList() {
         return this.TroubleList;
     }

     public void setTroubleList(List<Trouble> troubleList) {
         this.TroubleList = troubleList;
     }

     public void addTrouble(Trouble trouble) {
         trouble.setClient(this);
         getTroubleList().add(trouble);
     }

     @JsonIgnore
     @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = {CascadeType.ALL})
     public List<Item> getItemList() {
         return this.itemList;
     }

     public void setItemList(List<Item> itemList) {
         this.itemList = itemList;
     }

     public void addItem(Item item) {
         item.setClient(this);
         getItemList().add(item);
     }

     public String toString() {
         return "Client ID - " + id + ", Client - " + client + ", INN - " + inn + ", Role - " + clientSpecific + ", Phone " + phone;
     }
 }