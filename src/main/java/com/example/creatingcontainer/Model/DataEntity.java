package com.example.creatingcontainer.Model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "data_entity")
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "provision_status")
    private boolean provisionStatus;
    @Column(name = "site_name")
    private String siteName;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "city_name")
    private String city;
    @Column(name = "pin_code")
    private String pinCode;
    @Column(name = "state_name")
    private String state;
    @Column(name = "country_name")
    private String country;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "contact_number")
    private String contact;
    @Column(name = "e_mail")
    private String email;
    @Column(name = "deployment_Id")
    private String deploymentId;
    public DataEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isProvisionStatus() {
        return provisionStatus;
    }

    public void setProvisionStatus(boolean provisionStatus) {
        this.provisionStatus = provisionStatus;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public DataEntity(Long id, boolean provisionStatus, String siteName, String streetName, String city, String pinCode, String state, String country, String fullName, String contact, String email) {
        this.id = id;
        this.provisionStatus = provisionStatus;
        this.siteName = siteName;
        this.streetName = streetName;
        this.city = city;
        this.pinCode = pinCode;
        this.state = state;
        this.country = country;
        this.fullName = fullName;
        this.contact = contact;
        this.email = email;

    }

    public DataEntity(String deploymentId) {
        this.deploymentId = deploymentId;
    }
}

