package com.ubb.postuniv.Service;

import com.sun.security.ntlm.Client;
import com.ubb.postuniv.Domain.*;
import com.ubb.postuniv.Repository.IRepository;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CarService {

    private IRepository<Car> carRepository;
    private IRepository<Transaction> transactionRepository;
    private CarValidator carValidator;

    public CarService(IRepository<Car> carRepository,IRepository<Transaction> transactionRepository,CarValidator carValidator) {
        this.carRepository = carRepository;
        this.transactionRepository = transactionRepository;
        this.carValidator = carValidator;
    }

    public void add(int carID, String carModel, Date carDate, int carKm, boolean carGuarantee) throws Exception {
        Car auto = new Car(carID, carModel, carDate, carKm, carGuarantee);
        this.carValidator.validate(auto);
        this.carRepository.create(auto);
    }

    public void update(int carID,String carModel,int carKm,boolean carGuarantee) throws Exception {
        Car auto = new Car(carID, carModel, carRepository.read(carID).getCarDate(), carKm, carGuarantee);
        this.carRepository.update(auto);
    }

    public void delete(int carID) throws Exception {
        this.carRepository.delete(carID);
    }

    public List<Car> getAll() {
        return this.carRepository.readAll();
    }

    public List<Car> showCar(String text) {
        List<Car> matchingCars = new ArrayList<>();
        for (Car c : this.carRepository.readAll()) {
            if (c.getCarModel().equals(text)) {
                matchingCars.add(c);
            }
        }
        return matchingCars;
    }

    public List<Car> updateGuarantee() {
        List<Car> matchingCars = new ArrayList<>();
        Date today = new Date();
        for (Car c : this.carRepository.readAll()) {
            if(c.isCarGuarantee() != false) {
                long diffInMillies = Math.abs(today.getTime() - c.getCarDate().getTime());
                long diffInYears = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) / 365;

                if (diffInYears >= 3 || c.getCarKm() > 60000) {
                    c.setCarGuarantee(false);
                    matchingCars.add(c);
                }
            }
        }
        return matchingCars;
    }

    public List<CarWorkPriceDTO> getCarWorkPriceSorted() {
        List<CarWorkPriceDTO> matchingCars = new ArrayList<>();

        for (Car c : this.carRepository.readAll()) {
            int carId = c.getId();
            Transaction transaction = transactionRepository.read(carId);
            if (transaction != null) {
                float sumOfWork = transaction.getSumOfWork();
                matchingCars.add(new CarWorkPriceDTO(c, sumOfWork));
            }
    }

        matchingCars.sort((a, b) -> {
            float difference = a.workProce - b.workProce;
            float eps = 0.00001f;
            if (Math.abs(difference) < eps) {
                return 0;
            }

            return difference < -eps ? 1 : -1;
        });

        return matchingCars;
    }

}
