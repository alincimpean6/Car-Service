package com.ubb.postuniv.Domain;

public class CarWorkPriceDTO {
    public Car car;
    public float workProce;

    public CarWorkPriceDTO(Car car, float workProce) {
        this.car = car;
        this.workProce = workProce;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "CarWorkPriceDTO{" +
                "car=" + car +
                ", workPrice=" + workProce +
                '}';
    }
}
