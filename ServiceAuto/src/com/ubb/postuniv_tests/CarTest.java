package com.ubb.postuniv_tests;

import com.ubb.postuniv.Domain.Car;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    private Car car;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    void testConstructorAndGetters() {
        int carID = 1;
        String carModel = "Toyota";
        Date carDate = new Date();
        int carKm = 10000;
        boolean carGuarantee = true;
        car = new Car(carID, carModel, carDate, carKm, carGuarantee);
        assertEquals(carID, car.getId());
        assertEquals(carModel, car.getCarModel());
        assertEquals(carDate, car.getCarDate());
        assertEquals(carKm, car.getCarKm());
        assertTrue(car.isCarGuarantee());
    }

    @Test
    void testSetters() {
        car = new Car(1, "Toyota", new Date(), 10000, true);
        String newModel = "Ford";
        car.setCarModel(newModel);
        assertEquals(newModel, car.getCarModel());

        int newKm = 15000;
        car.setCarKm(newKm);
        assertEquals(newKm, car.getCarKm());
        boolean newGuarantee = false;

        car.setCarGuarantee(newGuarantee);
        assertFalse(car.isCarGuarantee());
    }

    @Test
    void testToString() {
        car = new Car(1, "Toyota", new Date(), 10000, true);
        String expectedString = "Car{carID=1, carModel='Toyota', carDate="
                + dateFormat.format(car.getCarDate()) + ", carKm=10000, carGuarantee=true}";
        assertEquals(expectedString, car.toString());
    }
}