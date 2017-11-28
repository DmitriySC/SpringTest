package ru.chirkovds.springtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Entity
@Table(name = "trouble")
public class Trouble implements Serializable {
    private Long id;
    private String barcode;
    private Date incomDate;
    private Date outDate;
    private String caseDesc;
    private String result;
    private int paymentStatus;
    private float summCase;
    private Client client;
    private Customer customer;
    private int version;
    private Long clientId;
    private List<Spare> spareList;

    public Trouble() {
    }

    public Trouble(Long id, String barcode, Date incomDate, Date out_date, String caseDesc, String result, int paymentStatus, float summCase, int version) {
        this.id = id;
        this.barcode = barcode;
        this.incomDate = incomDate;
        this.outDate = out_date;
        this.caseDesc = caseDesc;
        this.result = result;
        this.paymentStatus = paymentStatus;
        this.summCase = summCase;
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

    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "client_id", insertable = false, updatable = false)
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @NotNull(message = "{validation.date.NotEmpty.message}")
    //@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DATE)
    @Column(name = "incom_date")
    public Date getIncomDate() {
        return this.incomDate;
    }

    public void setIncomDate(Date incomDate) {
        this.incomDate = incomDate;
    }

    //@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DATE)
    @Column(name = "out_date")
    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    @NotEmpty(message = "{validation.case_desc.NotEmpty.message}")
    @Size(min = 20, message = "{validation.case_desc.Size.message}")
    @Column(name = "case_desc")
    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    @Column(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "payment_status")
    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Column(name = "summ_case")
    public float getSummCase() {
        return summCase;
    }

    public void setSummCase(float summCase) {
        this.summCase = summCase;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_trouble_client"))
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_trouble_customer"))
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trouble", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public List<Spare> getSpareList() {
        return this.spareList;
    }

    public void setSpareList(List<Spare> spareList) {
        this.spareList = spareList;
    }

    @Override
    public String toString (){
        return "ID:" + id + ", Barcode: " + barcode + ", incom Date: " + incomDate + ", out Date: " + outDate + ", case Desc: " + caseDesc + ", Result: " +
                result + ", payment status: " + paymentStatus + ", Summ: " + summCase + ", Client: " + client +/* ", Customer: " +
                customer + */", Version: " + version + ", Client ID: " + clientId + ", Spare List: " + spareList;
    }
}
