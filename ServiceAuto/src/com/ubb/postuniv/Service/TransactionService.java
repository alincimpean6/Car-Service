package com.ubb.postuniv.Service;

import com.ubb.postuniv.Domain.Car;
import com.ubb.postuniv.Domain.ClientCard;
import com.ubb.postuniv.Domain.Transaction;
import com.ubb.postuniv.Domain.TransactionValidator;
import com.ubb.postuniv.Repository.IRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionService {

    private IRepository<Transaction> transactionRepository;
    private IRepository<Car> carRepository;
    private IRepository<ClientCard> clientCardRepository;

    private TransactionValidator transactionValidator;

    public TransactionService(IRepository<Transaction> transactionRepository,
                              IRepository<Car> carRepository,
                              IRepository<ClientCard> clientCardRepository,
                              TransactionValidator transactionValidator) {
        this.transactionRepository = transactionRepository;
        this.carRepository = carRepository;
        this.clientCardRepository = clientCardRepository;
        this.transactionValidator = transactionValidator;
    }

    public void add(int tranzactionID, int carID, int clientCardID, int sumOfParts, int sumOfWork, Date dateTime) throws Exception {
        Transaction transaction = new Transaction(tranzactionID, carID, clientCardID, sumOfParts, sumOfWork, dateTime);
        this.transactionValidator.validate(transaction, this.carRepository, this.clientCardRepository);
        this.transactionRepository.create(transaction);
        calculateTransaction(transaction, carID, clientCardID, sumOfParts, sumOfWork);
    }

    public List<Transaction> getAll() {
        return this.transactionRepository.readAll();
    }

    public void calculateTransaction(Transaction transaction, int carID, int clientCardID, int sumOfParts, int sumOfWork) {
        float sum = 0;
        if (clientCardID != 0) {
            boolean isCarGuarantee = carRepository.read(carID).isCarGuarantee();

            if (isCarGuarantee == true) {
                sum = 0;
                System.out.println("The price paid is " + sum + " and the car has guarantee.");
                transaction.setTotalSum(sum);
                transaction.setTotalDiscount(100);
                float totalSum = sumOfParts + sumOfWork;
                clientCardRepository.read(clientCardID).setSumDiscount(totalSum);
            } else {
                float totalSum = sumOfParts + sumOfWork;
                float sumDiscount = totalSum/10;
                sum = totalSum - totalSum/10;
                System.out.println("The price paid is " + sum + " and the discount is 10%");
                transaction.setTotalSum(sum);
                transaction.setTotalDiscount(10);
                clientCardRepository.read(clientCardID).setSumDiscount(sumDiscount);
            }
        }
    }

    public List<Transaction> showTransactions(float rangeOne, float rangeTwo) {
        List<Transaction> matchingTransactions = new ArrayList<>();
        for (Transaction transaction : this.transactionRepository.readAll()) {
            if (transaction.getTotalSum() > rangeOne && transaction.getTotalSum() < rangeTwo) {
                matchingTransactions.add(transaction);
            }
        }
        return matchingTransactions;
    }

    public List<Transaction> deleteTransactionByDateRange(Date dateOne, Date dateTwo) throws Exception {
        List<Transaction> transactionsToDelete = new ArrayList<>();
        for (Transaction transaction : this.transactionRepository.readAll()) {
            if (transaction.getDateTime().compareTo(dateOne) >= 0 && transaction.getDateTime().compareTo(dateTwo) <= 0) {
                transactionsToDelete.add(transaction);
            }
        }
        List<Transaction> deletedTransactions = new ArrayList<>();
        for (Transaction transaction : transactionsToDelete) {
            this.transactionRepository.delete(transaction.getId());
            deletedTransactions.add(transaction);
        }
        return deletedTransactions;
    }
}
