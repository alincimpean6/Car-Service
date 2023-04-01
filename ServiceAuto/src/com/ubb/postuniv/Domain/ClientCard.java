package com.ubb.postuniv.Domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientCard extends Entity {
    private String clientFirstName;
    private String clientLastName;
    private String clientCNP;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private Date birthDate = new Date();
    private Date registrationDate = new Date();
    private float sumDiscount;

    public ClientCard(int clientID, String clientFirstName, String clientLastName, String clientCNP, Date birthDate, Date registrationDate) {
        super(clientID);
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.clientCNP = clientCNP;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.sumDiscount = 0;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public float getSumDiscount() { return sumDiscount; }

    public void setSumDiscount(float sumDiscount) { this.sumDiscount = sumDiscount; }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientCNP() {
        return clientCNP;
    }

    public void setClientCNP(String clientCNP) {
        this.clientCNP = clientCNP;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "clientID=" + this.getId() +
                ", clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                ", clientCNP=" + clientCNP +
                ", birthDate=" + dateFormat.format(birthDate) +
                ", registrationDate=" + dateFormat.format(registrationDate) +
                '}';
    }
}
