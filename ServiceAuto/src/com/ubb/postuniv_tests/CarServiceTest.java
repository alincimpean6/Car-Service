package com.ubb.postuniv_tests;

import com.ubb.postuniv.Domain.*;
import com.ubb.postuniv.Repository.IRepository;
import com.ubb.postuniv.Repository.InMemoryRepository;
import com.ubb.postuniv.Service.CarService;
import com.ubb.postuniv.Service.ClientService;
import com.ubb.postuniv.Service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarServiceTest {

    private CarValidator validator;
    private TransactionValidator transactionValidator;
    private ClientValidator clientCardValidator;
    private IRepository<Car> carRepository;
    private IRepository<Transaction> transactionRepository;
    private IRepository<ClientCard> clientCardRepository;
    private CarService carService;
    private TransactionService transactionService;
    private ClientService clientCardService;

    @BeforeEach
    void setUp() {
        IRepository<Car> carRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        CarValidator carValidator = new CarValidator();
        TransactionValidator transactionValidator = new TransactionValidator();
        ClientValidator clientCardValidator = new ClientValidator();
        this.carService = new CarService(carRepository, transactionRepository, carValidator);
        this.transactionService = new TransactionService(transactionRepository, carRepository, clientCardRepository, transactionValidator);
        this.clientCardService = new ClientService(clientCardRepository, clientCardValidator);
    }

    @Test
    void addCar() throws Exception {
        int carID = 1;
        String carModel = "Honda";
        Date date = new Date();
        int carKm = 1000;
        boolean carGuarantee = true;
        carService.add(carID, carModel, date, carKm, carGuarantee);
        List<Car> cars = carService.getAll();
        assertEquals(1, cars.size());
        assertEquals("Honda", cars.get(0).getCarModel());
        assertEquals(carID, cars.get(0).getId());
        assertEquals(carModel, cars.get(0).getCarModel());
        assertEquals(date, cars.get(0).getCarDate());
        assertEquals(carKm, cars.get(0).getCarKm());
        assertEquals(carGuarantee, cars.get(0).isCarGuarantee());
    }

    @Test
    void updateCar() throws Exception {
        int carID = 1;
        String carModel = "Honda";
        Date date = new Date();
        int carKm = 1000;
        boolean carGuarantee = true;
        carService.add(carID, carModel, date, carKm, carGuarantee);
        carService.update(carID,"Honda",4000,true);
        assertEquals("Honda", carService.getAll().get(0).getCarModel());
        assertEquals(date, carService.getAll().get(0).getCarDate());
        assertEquals(4000, carService.getAll().get(0).getCarKm());
        assertEquals(carGuarantee, carService.getAll().get(0).isCarGuarantee());
    }

    @Test
    void updateCarWithNoID() throws Exception {
        assertThrows(Exception.class, () -> {
            carService.update(9,"Honda",4000,true);
        });
    }

    @Test
    void deleteCar() throws Exception {
        int carID = 1;
        String carModel = "Honda";
        Date date = new Date();
        int carKm = 1000;
        boolean carGuarantee = true;
        carService.add(carID, carModel, date, carKm, carGuarantee);
        carService.delete(carID);
        assertEquals(0, carService.getAll().size());
    }

    @Test
    void deleteWithNoID() throws Exception {
        assertThrows(Exception.class, () -> {
            carService.delete(9);
        });
    }

    @Test
    void addCarsWithSameID() throws Exception {
        int carId = 1;
        carService.add(carId, "Toyota", new Date(), 10000, true);
        assertThrows(Exception.class, () -> {
            carService.add(carId, "Honda", new Date(), 20000, true);
        });
        List<Car> cars = carService.getAll();
        assertEquals(1, cars.size());
    }

    @Test
    void addInvalidCar() {
        assertThrows(CarValidatorException.class, () -> carService.add(1, "", new Date(), -100, true));
    }

    @Test
    void getAllCars() throws Exception {
        carService.add(1, "Honda", new Date(), 10000, true);
        carService.add(2, "Toyota", new Date(), 20000, false);
        List<Car> cars = carService.getAll();
        assertEquals(2, cars.size());
    }

    @Test
    void showCarByString() throws Exception {
        carService.add(1, "Honda", new Date(), 10000, true);
        carService.add(2, "Toyota", new Date(), 20000, false);
        List<Car> matchingCars = carService.showCar("Honda");
        assertEquals(1, matchingCars.size());
        assertEquals("Honda", matchingCars.get(0).getCarModel());
    }

    @Test
    void updateGuarante() throws Exception {
        carService.add(1, "Honda", new Date(), 75000, true);
        carService.add(2, "Toyota", new Date(), 20000, true);
        carService.updateGuarantee();
        List<Car> cars = carService.getAll();
        assertEquals(false, cars.get(0).isCarGuarantee());
        assertEquals(true, cars.get(1).isCarGuarantee());
    }

    @Test
    void getCarWorkPriceSorted_returnsCarsSortedByWorkPrice() throws Exception {
        carService.add(1, "Honda", new Date(), 75000, true);
        carService.add(2, "Toyota", new Date(), 20000, true);
        clientCardService.add(1, "Alin", "Cimpean", "1960515684532", new Date(), new Date());
        clientCardService.add(2, "Adrian", "Pop", "8960515684532", new Date(), new Date());
        transactionService.add(1, 1, 1, 100, 100, new Date());
        transactionService.add(2, 2, 2, 500, 200, new Date());
        List<Car> cars = carService.getAll();
        List<CarWorkPriceDTO> sortedCars = carService.getCarWorkPriceSorted();
        assertEquals(sortedCars.get(0).getCar().getId(),cars.get(1).getId());
        assertEquals(sortedCars.get(1).getCar().getId(),cars.get(0).getId());
    }
}
