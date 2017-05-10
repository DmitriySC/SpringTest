package ru.chirkovds.springtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Entity
@Table(name = "customer")

public class Customer implements Serializable{
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String phone;
    private String address;
    private int version;
    private Set<Trouble> TroubleList;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    @NotEmpty(message = "{validation.first_name.NotEmpty.message}")
    @Size(min = 2, max = 25, message = "{validation.first_name.Size.message}")
    @Column(name = "first_name")
    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    @NotEmpty(message = "{validation.last_name.NotEmpty.message}")
    @Size(min = 2, max = 25, message = "{validation.last_name.Size.message}")
    @Column(name = "last_name")
    public String getLastName(){
            return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @NotEmpty(message = "{validation.middle_name.NotEmpty.message}")
    @Size(min = 2, max = 25, message = "{validation.middle_name.Size.message}")
    @Column(name = "middle_name")
    public String getMiddleName() {
        return this.middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    @NotNull(message = "{validation.date.NotEmpty.message}")
    //@Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DATE)
    @Column(name ="birth_date")
    public Date getBirthDate() {
        return this.birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
    @NotEmpty(message = "{validation.address.NotEmpty.message}")
    @Size(min = 30, message = "{validation.address.Size.message}")
    @Column(name = "address")
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Version
    @Column(name = "version")
    public int getVersion() {
        return this.version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    public Set<Trouble> getTroubleList(){
        return this.TroubleList;
    }
    public void setTroubleList(Set<Trouble> troubleList){
        this.TroubleList = troubleList;
    }
    public void addTrouble(Trouble trouble){
        trouble.setCustomer(this);
        getTroubleList().add(trouble);
    }
    @Override
    public String toString() {
        return lastName + " " + firstName.toUpperCase().charAt(0) + ". " + middleName.toUpperCase().charAt(0) + ".";
    }
}