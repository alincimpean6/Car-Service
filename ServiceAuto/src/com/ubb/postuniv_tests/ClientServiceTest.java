package com.ubb.postuniv_tests;

import com.sun.security.ntlm.Client;
import com.ubb.postuniv.Domain.*;
import com.ubb.postuniv.Repository.IRepository;
import com.ubb.postuniv.Repository.InMemoryRepository;
import com.ubb.postuniv.Service.CarService;
import com.ubb.postuniv.Service.ClientService;
import com.ubb.postuniv.Service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

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
    public void testAddClient() throws Exception {
        int clientID = 1;
        String firstName = "John";
        String lastName = "Doe";
        String clientCNP = "1234567890123";
        Date birthDate = new Date();
        Date registrationDate = new Date();
        clientService.add(clientID, firstName, lastName, clientCNP, birthDate, registrationDate);
        List<ClientCard> clients = clientService.getAll();
        assertEquals(1, clients.size());
        assertEquals(clientID, clients.get(0).getId());
        assertEquals(firstName, clients.get(0).getClientFirstName());
        assertEquals(lastName, clients.get(0).getClientLastName());
        assertEquals(clientCNP, clients.get(0).getClientCNP());
        assertEquals(birthDate, clients.get(0).getBirthDate());
        assertEquals(registrationDate, clients.get(0).getRegistrationDate());
    }

    @Test
    void addInvalidCNPClient() {
        assertThrows(ClientValidatorException.class, () -> clientService.add(1, "Vasile", "Alexandru", "123", new Date(), new Date()));
    }

    @Test
    void addDuplicateID() throws Exception {
        int clientID = 1;
        clientService.add(clientID, "Alex", "Pop", "1234567890123", new Date(), new Date());
        assertThrows(Exception.class, () -> {clientService.add(clientID, "Jennifer", "Vasile", "1234567890124", new Date(), new Date());});
        List<ClientCard> clients = clientService.getAll();
        assertEquals(1, clients.size());
    }

    @Test
    void addDuplicateCNP() throws Exception {
        clientService.add(1, "Alex", "Pop", "1234567890123", new Date(), new Date());
        assertThrows(ClientValidatorException.class, () -> {clientService.add(2, "Jennifer", "Vasile", "1234567890123", new Date(), new Date());});
        List<ClientCard> clients = clientService.getAll();
        assertEquals(1, clients.size());
    }

    @Test
    public void testShowClient() throws Exception {
        int clientID = 1;
        String firstName = "John";
        String lastName = "Doe";
        String clientCNP = "1234567890123";
        Date birthDate = new Date();
        Date registrationDate = new Date();
        clientService.add(clientID, firstName, lastName, clientCNP, birthDate, registrationDate);
        List<ClientCard> matchingClients = clientService.showClient(firstName + " " + lastName);
        assertEquals(1, matchingClients.size());
    }

    @Test
    void testGetAllClients() throws Exception {
        int id1 = 1;
        String firstName1 = "John";
        String lastName1 = "Doe";
        String cnp1 = "1234567890123";
        Date birthDate1 = new Date();
        Date registrationDate1 = new Date();
        int id2 = 2;
        String firstName2 = "Jane";
        String lastName2 = "Doe";
        String cnp2 = "2345678901234";
        Date birthDate2 = new Date();
        Date registrationDate2 = new Date();
        clientService.add(id1, firstName1, lastName1, cnp1, birthDate1, registrationDate1);
        clientService.add(id2, firstName2, lastName2, cnp2, birthDate2, registrationDate2);
        List<ClientCard> clients = clientService.getAll();
        assertEquals(2, clients.size());
    }

    @Test
    void getClientsSortedByDiscount() throws Exception {
        clientService.add(1, "John", "Doe", "1234567890123", new Date(), new Date());
        clientService.add(2, "Jane", "Doe", "1234567890124", new Date(), new Date());
        clientService.add(3, "Jani", "Poe", "1234567890154", new Date(), new Date());
        clientService.getAll().get(0).setSumDiscount(45.0f);
        clientService.getAll().get(1).setSumDiscount(2.0f);
        clientService.getAll().get(2).setSumDiscount(100.0f);
        List<ClientCard> clients = clientService.getAll();
        List<ClientDiscountDTO> sortedClients = clientService.getClientsDiscountSorted();
        assertEquals(sortedClients.get(0).getClient().getSumDiscount(),clients.get(2).getSumDiscount());
        assertEquals(sortedClients.get(1).getClient().getSumDiscount(),clients.get(0).getSumDiscount());
        assertEquals(sortedClients.get(2).getClient().getSumDiscount(),clients.get(1).getSumDiscount());
    }
}