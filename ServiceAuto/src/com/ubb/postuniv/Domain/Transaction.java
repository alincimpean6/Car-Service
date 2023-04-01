package com.ubb.postuniv.Domain;

import java.util.Date;

public class Transaction extends Entity{
    private int carID;
    private int clientCardID;
    private int sumOfParts;
    private int sumOfWork;
    private Date dateTime = new Date();
    private float totalSum;
    private int totalDiscount;

    public Transaction(int tranzactionID, int carID, int clientCardID, int sumOfParts, int sumOfWork, Date dateTime) {
        super(tranzactionID);
        this.carID = carID;
        this.clientCardID = clientCardID;
        this.sumOfParts = sumOfParts;
        this.sumOfWork = sumOfWork;
        this.dateTime = dateTime;
        this.totalSum = 0;
        this.totalDiscount = 0;
    }

    public float getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(float totalSum) {
        this.totalSum = totalSum;
    }

    public int getTotalDiscount() { return totalDiscount; }

    public void setTotalDiscount(int totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getClientCardID() {
        return clientCardID;
    }

    public void setClientCardID(int clientCardID) {
        this.clientCardID = clientCardID;
    }

    public int getSumOfParts() {
        return sumOfParts;
    }

    public void setSumOfParts(int sumOfParts) {
        this.sumOfParts = sumOfParts;
    }

    public int getSumOfWork() {
        return sumOfWork;
    }

    public void setSumOfWork(int sumOfWork) {
        this.sumOfWork = sumOfWork;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "carID=" + carID +
                ", clientCardID=" + clientCardID +
                ", sumOfParts=" + sumOfParts +
                ", sumOfWork=" + sumOfWork +
                ", dateTime=" + dateTime +
                ", totalSum=" + totalSum +
                ", totalDiscount=" + totalDiscount +
                '}';
    }
}
