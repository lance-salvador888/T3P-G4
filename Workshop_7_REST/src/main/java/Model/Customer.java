package Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    @Size(max = 25)
    @NotNull
    @Column(name = "CustFirstName", nullable = false, length = 25)
    private String custFirstName;

    @Size(max = 25)
    @NotNull
    @Column(name = "CustLastName", nullable = false, length = 25)
    private String custLastName;

    @Size(max = 75)
    @NotNull
    @Column(name = "CustAddress", nullable = false, length = 75)
    private String custAddress;

    @Size(max = 50)
    @NotNull
    @Column(name = "CustCity", nullable = false, length = 50)
    private String custCity;

    @Size(max = 2)
    @NotNull
    @Column(name = "CustProv", nullable = false, length = 2)
    private String custProv;

    @Size(max = 7)
    @NotNull
    @Column(name = "CustPostal", nullable = false, length = 7)
    private String custPostal;

    @Size(max = 25)
    @Column(name = "CustCountry", length = 25)
    private String custCountry;

    @Size(max = 20)
    @Column(name = "CustHomePhone", length = 20)
    private String custHomePhone;

    @Size(max = 20)
    @NotNull
    @Column(name = "CustBusPhone", nullable = false, length = 20)
    private String custBusPhone;

    @Size(max = 50)
    @NotNull
    @Column(name = "CustEmail", nullable = false, length = 50)
    private String custEmail;

    @Size(max = 25)
    @Column(name = "CustPassword", length = 25)
    private String custPassword;

    @Column(name = "AgentId")
    private Integer agentId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProv() {
        return custProv;
    }

    public void setCustProv(String custProv) {
        this.custProv = custProv;
    }

    public String getCustPostal() {
        return custPostal;
    }

    public void setCustPostal(String custPostal) {
        this.custPostal = custPostal;
    }

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustHomePhone() {
        return custHomePhone;
    }

    public void setCustHomePhone(String custHomePhone) {
        this.custHomePhone = custHomePhone;
    }

    public String getCustBusPhone() {
        return custBusPhone;
    }

    public void setCustBusPhone(String custBusPhone) {
        this.custBusPhone = custBusPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustPassword() {
        return custPassword;
    }

    public void setCustPassword(String custPassword) {
        this.custPassword = custPassword;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

}