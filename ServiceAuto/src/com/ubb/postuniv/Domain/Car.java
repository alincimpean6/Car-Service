package com.ubb.postuniv.Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Car extends Entity {
    private String carModel;
    private Date carDate = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private int carKm;
    private boolean carGuarantee;

    public Car(int carID, String carModel, Date carDate, int carKm, boolean carGuarantee) {
        super(carID);
        this.carModel = carModel;
        this.carDate = carDate;
        this.carKm = carKm;
        this.carGuarantee = carGuarantee;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Date getCarDate() {
        return carDate;
    }

    public void setCarDate(Date carDate) {
        this.carDate = carDate;
    }

    public int getCarKm() {
        return carKm;
    }

    public void setCarKm(int carKm) {
        this.carKm = carKm;
    }

    public boolean isCarGuarantee() {
        return carGuarantee;
    }

    public void setCarGuarantee(boolean carGuarantee) {
        this.carGuarantee = carGuarantee;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carID=" + this.getId() +
                ", carModel='" + carModel + '\'' +
                ", carDate=" + dateFormat.format(carDate) +
                ", carKm=" + carKm +
                ", carGuarantee=" + carGuarantee +
                '}';
    }
}
