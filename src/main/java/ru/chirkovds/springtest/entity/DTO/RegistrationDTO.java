package ru.chirkovds.springtest.entity.DTO;

import org.hibernate.validator.constraints.NotEmpty;
import ru.chirkovds.springtest.entity.enums.ClientSpecific;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RegistrationDTO implements Serializable{
    private String inn;
    private String client;
    private ClientSpecific clientSpecific;
    private String address;
    private String phone;
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private String password;
    public RegistrationDTO (){}

    public RegistrationDTO getRegistrationDTO(){
        return new RegistrationDTO();
    }

    @NotEmpty(message = "{validation.inn.NotEmpty.message}")
    @Size(min = 10, max = 12, message = "{validation.inn.Size.message}")
    public String getInn() {
        return this.inn;
    }
    public void setInn(String inn) {
        this.inn = inn;
    }

    @NotEmpty(message = "{validation.client.NotEmpty.message}")
    @Size(min = 5, max = 60, message = "{validation.client.Size.message}")
    public String getClient() {
        return this.client;
    }
    public void setClient(String client) {
        this.client = client;
    }

    @NotNull(message = "(validation.client_specific.NotEmpty.message")
    public ClientSpecific getClientSpecific() {
        return this.clientSpecific;
    }
    public void setClientSpecific(ClientSpecific clientSpecific) {
        this.clientSpecific = clientSpecific;
    }

    @NotEmpty(message = "{validation.address.NotEmpty.message}")
    @Size(min = 15, message = "{validation.address.Size.message}")
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @NotEmpty(message = "{validation.phone.NotEmpty.message}")
    @Size(min = 10, max = 10, message = "{validation.phone.Size.message}")
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotEmpty(message = "{validation.first_name.NotEmpty.message}")
    @Size(min = 2, max = 25, message = "{validation.first_name.Size.message}")
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotEmpty(message = "{validation.last_name.NotEmpty.message}")
    @Size(min = 2, max = 25, message = "{validation.last_name.Size.message}")
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotEmpty(message = "{validation.middle_name.NotEmpty.message}")
    @Size(min = 2, max = 25, message = "{validation.middle_name.Size.message}")
    public String getMiddleName() {
        return this.middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @NotEmpty(message = "{validation.username.NotEmpty.message}")
    @Size(min = 5, max = 30, message = "{validation.username.Size.message}")
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "{validation.password.NotEmpty.message}")
    @Size(min = 8, message = "{validation.password.Size.message}")
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}