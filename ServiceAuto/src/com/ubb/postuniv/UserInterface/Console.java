package com.ubb.postuniv.UserInterface;

import com.ubb.postuniv.Domain.*;
import com.ubb.postuniv.Service.CarService;
import com.ubb.postuniv.Service.ClientService;
import com.ubb.postuniv.Service.TransactionService;

import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;

public class Console {

    private CarService carService;
    private ClientService clientService;
    private TransactionService transactionService;
    private Scanner scanner;

    public Console(CarService carService, ClientService clientService, TransactionService transactionService) {
        this.carService = carService;
        this.clientService = clientService;
        this.transactionService = transactionService;
        this.scanner = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("1. Add a car");
        System.out.println("2. Show all cars");
        System.out.println("3. Add a client");
        System.out.println("4. Show all clients");
        System.out.println("5. Add a transaction");
        System.out.println("6. Show all transactions");
        System.out.println("7. Find car by name");
        System.out.println("8. Find client by name");
        System.out.println("9. Show all transactions in a range");
        System.out.println("10. Show all cars sorted by work price");
        System.out.println("11. Show all clients sorted by sum of discounts");
        System.out.println("12. Delete transactions between two dates");
        System.out.println("13. Update all cars guarantee");
        System.out.println("Enter your option: ");
    }

    private void handleAddCar() {
        try {
            System.out.print("Enter the car ID: ");
            int carID = scanner.nextInt();

            System.out.print("Enter the car Model: ");
            String carModel = scanner.next();

            System.out.print("Enter the car date by the format dd.MM.yyyy: ");
            String carDateString = scanner.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date carDate = dateFormat.parse(carDateString);

            System.out.print("Enter the car Km: ");
            int carKm = scanner.nextInt();

            System.out.print("Enter the guarantee status: ");
            boolean carGuarantee = scanner.nextBoolean();

            this.carService.add(carID, carModel, carDate, carKm, carGuarantee);
        } catch (InputMismatchException ime) {
            System.out.println("Wrong data type entered.");
            scanner.next();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleAddClient() {
        try {
            System.out.print("Enter the client ID: ");
            int clientID = scanner.nextInt();

            System.out.print("Enter the client first name: ");
            String clientFirstName = scanner.next();

            System.out.print("Enter the client last name: ");
            String clientLastName = scanner.next();

            System.out.print("Enter the client CNP: ");
            String clientCNP = scanner.next();

            System.out.print("Enter the client birth date by the format dd.MM.yyyy: ");
            String birthDateString = scanner.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date birthDate = dateFormat.parse(birthDateString);

            System.out.print("Enter the client registration date by the format dd.MM.yyyy: ");
            String registrationDateString = scanner.next();
            Date registrationDate = dateFormat.parse(registrationDateString);

            this.clientService.add(clientID,clientFirstName, clientLastName, clientCNP, birthDate, registrationDate);
        } catch (InputMismatchException ime) {
            System.out.println("Wrong data type entered.");
            scanner.next();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleAddTransaction() {
        try {
            System.out.print("Enter the transaction ID: ");
            int tranzactionID = scanner.nextInt();

            System.out.print("Enter the client car ID: ");
            int carID = scanner.nextInt();

            System.out.print("Enter the client card ID: ");
            int clientCardID = scanner.nextInt();

            System.out.print("Enter the sum of parts used: ");
            int sumOfParts = scanner.nextInt();

            System.out.print("Enter the sum of work done: ");
            int sumOfWork = scanner.nextInt();

            System.out.print("Enter the transaction date by the format dd.MM.yyyy/HH:hh : ");
            String workDateTime = scanner.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy/HH:hh");
            Date dateTime = dateFormat.parse(workDateTime);

            this.transactionService.add(tranzactionID, carID, clientCardID, sumOfParts, sumOfWork, dateTime);
        } catch (InputMismatchException ime) {
            System.out.println("Wrong data type entered.");
            scanner.next();
        } catch (TransactionValidatorException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void runConsole() {
        while (true) {
            this.showMenu();
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
                    this.handleAddCar();
                    break;
                case 2:
                    this.handleShowAll();
                    break;
                case 3:
                    this.handleAddClient();
                    break;
                case 4:
                    this.handleShowAllClients();
                    break;
                case 5:
                    this.handleAddTransaction();
                    break;
                case 6:
                    this.handleShowAllTransactions();
                    break;
                case 7:
                    this.handleShowCar();
                    break;
                case 8:
                    this.handleShowClient();
                    break;
                case 9:
                    this.handleShowTransactionRange();
                    break;
                case 10:
                    this.handleShowCarsSorted();
                    break;
                case 11:
                    this.handleShowClientsDiscount();
                    break;
                case 12:
                    this.handleDeleteTransactionsByDateRange();
                    break;
                case 13:
                    this.handleUpdateGuarantee();
                    break;
                    case 0:
                    return;
                default:
                    System.out.println("Unsupported command.");
            }
        }
    }

    private void handleUpdateGuarantee() {
        System.out.println("Updated guarantee for: ");
        for (Car c :
                this.carService.updateGuarantee()) {
            System.out.println(c);
        }
    }

    private void handleShowAll() {
        for (Car c :
                this.carService.getAll()) {
            System.out.println(c);
        }
    }

    private void handleShowAllClients() {
        for (ClientCard c :
                this.clientService.getAll()) {
            System.out.println(c);
        }
    }

    private void handleShowCarsSorted() {
        for (CarWorkPriceDTO c :
                this.carService.getCarWorkPriceSorted()) {
            System.out.println(c);
        }
    }

    private void handleShowClientsDiscount() {
        for (ClientDiscountDTO c :
                this.clientService.getClientsDiscountSorted()) {
            System.out.println(c);
        }
    }

    private void handleDeleteTransactionsByDateRange() {
        try {
            System.out.print("Enter the transaction first date by the format dd.MM.yyyy/HH:hh : ");
            String rangeOne = scanner.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy/HH:hh");
            Date dateTimeFirst = dateFormat.parse(rangeOne);

            System.out.print("Enter the transaction second date by the format dd.MM.yyyy/HH:hh : ");
            String rangeTwo = scanner.next();
            Date dateTimeSecond = dateFormat.parse(rangeTwo);

            for (Transaction t :
                    this.transactionService.deleteTransactionByDateRange(dateTimeFirst,dateTimeSecond)) {
                System.out.println(t);
            }
        } catch (InputMismatchException ime) {
            System.out.println("Wrong data type entered.");
            scanner.next();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleShowTransactionRange() {
        try {
            System.out.print("Enter the first number in range: ");
            int rangeOne = scanner.nextInt();

            System.out.print("Enter the second number in range: ");
            int rangeTwo = scanner.nextInt();

            for (Transaction t :
                    this.transactionService.showTransactions(rangeOne,rangeTwo)) {
                System.out.println(t);
            }
        } catch (InputMismatchException ime) {
            System.out.println("Wrong data type entered.");
            scanner.next();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleShowCar() {
        try {
            System.out.print("Enter the car name that you want to find: ");
            String findCarName = scanner.next();

            for (Car c :
                    this.carService.showCar(findCarName)) {
                    System.out.println(c);
            }
        } catch (InputMismatchException ime) {
            System.out.println("Wrong data type entered.");
            scanner.next();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleShowClient() {
        try {
            scanner.nextLine();
            System.out.print("Enter the client name that you want to find: ");
            String findClientName = scanner.nextLine();

            for (ClientCard c :
                    this.clientService.showClient(findClientName)) {
                System.out.println(c);
            }
        } catch (InputMismatchException ime) {
            System.out.println("Wrong data type entered.");
            scanner.next();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    private void handleShowAllTransactions() {
        for (Transaction t :
                this.transactionService.getAll()) {
            System.out.println(t);
        }
    }
}
