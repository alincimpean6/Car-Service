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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private CarValidator validator;
    private TransactionValidator transactionValidator;
    private ClientValidator clientCardValidator;
    private IRepository<Car> carRepository;
    private IRepository<Transaction> transactionRepository;
    private IRepository<ClientCard> clientCardRepository;
    private CarService carService;
    private TransactionService transactionService;
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        IRepository<Car> carRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        CarValidator carValidator = new CarValidator();
        TransactionValidator transactionValidator = new TransactionValidator();
        ClientValidator clientCardValidator = new ClientValidator();
        this.carService = new CarService(carRepository, transactionRepository, carValidator);
        this.transactionService = new TransactionService(transactionRepository, carRepository, clientCardRepository, transactionValidator);
        this.clientService = new ClientService(clientCardRepository, clientCardValidator);
    }

    @Test
    void testAddValidTransaction() throws Exception {
        int carID = 1;
        String carModel = "Honda";
        Date date = new Date();
        int carKm = 1000;
        boolean carGuarantee = true;
        carService.add(carID, carModel, date, carKm, carGuarantee);
        int clientID = 1;
        String firstName = "John";
        String lastName = "Doe";
        String clientCNP = "1234567890123";
        Date birthDate = new Date();
        Date registrationDate = new Date();
        clientService.add(clientID, firstName, lastName, clientCNP, birthDate, registrationDate);
        int transactionID = 1;
        Date dateTime = new Date();
        transactionService.add(transactionID, carService.getAll().get(0).getId(), clientService.getAll().get(0).getId(), 100, 50, dateTime);
        List<Transaction> transactions = transactionService.getAll();
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.get(0);
        assertEquals(1, transaction.getId());
        assertEquals(carService.getAll().get(0).getId(), transaction.getCarID());
        assertEquals(clientService.getAll().get(0).getId(), transaction.getClientCardID());
        assertEquals(100, transaction.getSumOfParts());
        assertEquals(50, transaction.getSumOfWork());
        assertEquals(dateTime, transaction.getDateTime());
        assertEquals(150, clientService.getAll().get(0).getSumDiscount());
    }

    @Test
    void addInvalidTransactionWithInvalidCarID() {
        assertThrows(TransactionValidatorException.class, () -> {
            transactionService.add(1, -50, 0, 100, 50, new Date());
        });
    }

    @Test
    void addInvalidTransactionWithInvalidClientID() {
        assertThrows(TransactionValidatorException.class, () -> {
            transactionService.add(1, 0, -50, 100, 50, new Date());
        });
    }

    @Test
    void getAllTransactions() throws Exception {
        clientService.add(1, "Alex", "Pop", "1234567890123", new Date(), new Date());
        clientService.add(2, "Andrei", "Vasilache", "2345678901234", new Date(), new Date());
        carService.add(1, "Honda", new Date(), 10000, true);
        carService.add(2, "Toyota", new Date(), 20000, false);
        transactionService.add(1, 1, 1, 100, 50, new Date());
        transactionService.add(2, 2, 2, 150, 100, new Date());
        List<Transaction> transactions = transactionService.getAll();
        assertEquals(2, transactions.size());
    }

    @Test
    public void calculateWithClientCardAndCarGuarantee() throws Exception {
        clientService.add(1, "Alex", "Pop", "1234567890123", new Date(), new Date());
        carService.add(1, "Honda", new Date(), 10000, true);
        transactionService.add(1, 1, 1, 100, 50, new Date());
        transactionService.calculateTransaction(transactionService.getAll().get(0), carService.getAll().get(0).getId(), clientService.getAll().get(0).getId(), transactionService.getAll().get(0).getSumOfParts(), transactionService.getAll().get(0).getSumOfWork());
        assertEquals(0, transactionService.getAll().get(0).getTotalSum());
        assertEquals(100, transactionService.getAll().get(0).getTotalDiscount());
        assertEquals(150, clientService.getAll().get(0).getSumDiscount());
    }

    @Test
    public void calculateWithClientCardAndWithoutCarGuarantee() throws Exception {
        clientService.add(1, "Alex", "Pop", "1234567890123", new Date(), new Date());
        carService.add(1, "Honda", new Date(), 200000, false);
        transactionService.add(1, 1, 1, 100, 100, new Date());
        transactionService.calculateTransaction(transactionService.getAll().get(0), carService.getAll().get(0).getId(), clientService.getAll().get(0).getId(), transactionService.getAll().get(0).getSumOfParts(), transactionService.getAll().get(0).getSumOfWork());
        assertEquals(180, transactionService.getAll().get(0).getTotalSum());
        assertEquals(10, transactionService.getAll().get(0).getTotalDiscount());
        assertEquals(20, clientService.getAll().get(0).getSumDiscount());
    }

    @Test
    public void showTransactionsInRange() throws Exception {
        clientService.add(1, "Alex", "Pop", "1234566890123", new Date(), new Date());
        carService.add(1, "BMW", new Date(), 10, false);
        transactionService.add(1, 1, 1, 150, 400, new Date());
        clientService.add(2, "Vasile", "Gheorghe", "1234867890123", new Date(), new Date());
        carService.add(2, "Honda", new Date(), 50000, false);
        transactionService.add(2, 2, 2, 100, 100, new Date());
        clientService.add(3, "Andrei", "Moldovan", "1734567890123", new Date(), new Date());
        carService.add(3, "Ferrari", new Date(), 10000, false);
        transactionService.add(3, 3, 3, 160, 400, new Date());
        List<Transaction> transactions = transactionService.showTransactions(100, 600);
        assertEquals(3, transactions.size());
        assertEquals(1, transactions.get(0).getId());
        assertEquals(2, transactions.get(1).getId());
        assertEquals(3, transactions.get(2).getId());
    }

    @Test
    public void deleteTransactionsInRange() throws Exception {
        clientService.add(1, "Alex", "Pop", "1234566890123", new Date(), new Date());
        carService.add(1, "BMW", new Date(), 10, false);
        transactionService.add(1, 1, 1, 150, 400, new Date());
        clientService.add(2, "Vasile", "Gheorghe", "1234867890123", new Date(), new Date());
        carService.add(2, "Honda", new Date(), 50000, false);
        transactionService.add(2, 2, 2, 100, 100, new Date());
        clientService.add(3, "Andrei", "Moldovan", "1734567890123", new Date(), new Date());
        carService.add(3, "Ferrari", new Date(), 10000, false);
        transactionService.add(3, 3, 3, 160, 400, new Date());
        List<Transaction> transactions = transactionService.showTransactions(100, 600);
        assertEquals(3, transactions.size());
        assertEquals(1, transactions.get(0).getId());
        assertEquals(2, transactions.get(1).getId());
        assertEquals(3, transactions.get(2).getId());
    }

    @Test
    void deleteTransactionByDateRange() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        clientService.add(1, "Alex", "Pop", "1234566890123", new Date(), new Date());
        carService.add(1, "BMW", new Date(), 10, false);
        String dateString = "18.03.2005";
        Date date1 = dateFormat.parse(dateString);
        transactionService.add(1, 1, 1, 150, 400, date1);
        clientService.add(2, "Vasile", "Gheorghe", "1234867890123", new Date(), new Date());
        carService.add(2, "Honda", new Date(), 50000, false);
        String dateString2 = "10.02.2005";
        Date date2 = dateFormat.parse(dateString2);
        transactionService.add(2, 2, 2, 100, 100, date2);
        clientService.add(3, "Andrei", "Moldovan", "1734567890123", new Date(), new Date());
        carService.add(3, "Ferrari", new Date(), 10000, false);
        String dateString3 = "18.03.2010";
        Date date3 = dateFormat.parse(dateString3);
        transactionService.add(3, 3, 3, 160, 400, date3);
        String dateString4 = "01.01.2005";
        Date dateRange1 = dateFormat.parse(dateString4);
        String dateString5 = "20.12.2005";
        Date dateRange2 = dateFormat.parse(dateString5);
        List<Transaction> deletedTransactions = transactionService.deleteTransactionByDateRange(dateRange1, dateRange2);
        assertEquals(2, deletedTransactions.size());
        List<Transaction> transactions = transactionService.getAll();
        assertEquals(1, transactions.size());
    }
}