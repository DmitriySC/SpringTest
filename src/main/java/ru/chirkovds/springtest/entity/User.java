package ru.chirkovds.springtest.entity;

import org.hibernate.validator.constraints.NotEmpty;
import ru.chirkovds.springtest.entity.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    //private String role;
    private String username;
    private String password;
    private int version;
    private Client client;
    private UserRole role;
    private Long clientId;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String middleName, UserRole role, String username, String password, Client client, int version) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.role = role;
        this.username = username;
        this.password = password;
        this.client = client;
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "{validation.first_name.NotEmpty.message}")
    @Size(min = 2, max = 25, message = "{validation.first_name.Size.message}")
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotEmpty(message = "{validation.last_name.NotEmpty.message}")
    @Size(min = 2, max = 25, message = "{validation.last_name.Size.message}")
    @Column(name = "last_name")
    public String getLastName() {
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

    @NotNull//Empty(message = "(validation.role.NotEmpty.message)")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @NotEmpty(message = "{validation.username.NotEmpty.message}")
    @Size(min = 5, max = 30, message = "{validation.username.Size.message}")
    @Column(name = "username")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "{validation.password.NotEmpty.message}")
    @Size(min = 8, message = "{validation.password.Size.message}")
    @Column(name = "password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Version
    @Column(name = "version")
    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "client_id", insertable = false, updatable = false)
    public Long getClientId() {
        return this.clientId;
    }
    public void setClientId(Long clientId){
        this.clientId = clientId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_user_client"))
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}