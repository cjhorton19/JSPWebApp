/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 *
 * @author curti
 */
public class User implements Serializable {
    
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String st;
    private String zip;
    private String country;
    private String creditCardType;
    private String creditCardNumber;
    private String creditCardExpiration;

  
    public User() {
        firstName = "";
        lastName = "";
        email = "";
        address = "";
        city = "";
        st = "";
        zip = "";
        country = "";
        creditCardType = "";
        creditCardNumber = "";
        creditCardExpiration = "";
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    // State is a reserved SQL word, so the field is called "st" instead.
   

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCreditCardType(String creditCartType) {
        this.creditCardType = creditCartType;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardExpiration(String creditCardExpiration) {
        this.creditCardExpiration = creditCardExpiration;
    }

    public String getCreditCardExpiration() {
        return creditCardExpiration;
    }
}
