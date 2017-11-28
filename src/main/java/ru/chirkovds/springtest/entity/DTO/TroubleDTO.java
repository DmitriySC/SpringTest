package ru.chirkovds.springtest.entity.DTO;

import org.hibernate.validator.constraints.NotEmpty;
import ru.chirkovds.springtest.entity.Spare;
import ru.chirkovds.springtest.entity.Trouble;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TroubleDTO implements Serializable {
    private Long id;
    private String barcode;
    private Date incomDate;
    private Date outDate;
    private String caseDesc;
    private String result;
    private int paymentStatus;
    private float summCase;
    private int troubleVersion;
    private String customer;
    private String address;
    private String phone;
    private Long customerId;
    private Long clientId;
    private static List<SpareDTO> spareList;

    public TroubleDTO() {
    }

    private List<SpareDTO> convertToDTO (List<Spare> spareList){
        List<SpareDTO> spareDTOList = new LinkedList<SpareDTO>();
        for(Spare spare : spareList){
            spareDTOList.add(new SpareDTO(spare));
        }
        return spareDTOList;
    }
    public TroubleDTO(Trouble trouble) {
        this.id = trouble.getId();
        this.barcode = trouble.getBarcode();
        this.incomDate = trouble.getIncomDate();
        this.outDate = trouble.getOutDate();
        this.caseDesc = trouble.getCaseDesc();
        this.result = trouble.getResult();
        this.paymentStatus = trouble.getPaymentStatus();
        this.summCase = trouble.getSummCase();
        this.troubleVersion = trouble.getVersion();
        this.customer = trouble.getCustomer().toString();//().getLastName() + " " + trouble.getCustomer().getFirstName().toUpperCase().charAt(0) + ". " + trouble.getCustomer().getMiddleName().toUpperCase().charAt(0) + ".";
        this.address = trouble.getCustomer().getAddress();
        this.phone = trouble.getCustomer().getPhone();
        this.clientId = trouble.getClient().getId();
        this.customerId = trouble.getCustomer().getId();
        this.spareList = convertToDTO(trouble.getSpareList());
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @NotNull(message = "{validation.date.NotEmpty.message}")
    public Date getIncomDate() {
        return this.incomDate;
    }

    public void setIncomDate(Date incomDate) {
        this.incomDate = incomDate;
    }

    //@NotNull(message = "{validation.date.NotEmpty.message}")
    public Date getOutDate() {
        return this.outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    @NotEmpty(message = "{validation.case_desc.NotEmpty.message}")
    @Size(min = 20, message = "{validation.case_desc.Size.message}")
    public String getCaseDesc() {
        return this.caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public float getSummCase() {
        return this.summCase;
    }

    public void setSummCase(float summCase) {
        this.summCase = summCase;
    }

    public int getTroubleVersion() {
        return this.troubleVersion;
    }

    public void setTroubleVersion(int troubleVersion) {
        this.caseDesc = caseDesc;
    }

    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customerId = customerId;
    }

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

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<SpareDTO> getSpareList() {
        return this.spareList;
    }

    public void setSpareList(List<SpareDTO> itemList) {
        this.spareList = spareList;
    }
}
