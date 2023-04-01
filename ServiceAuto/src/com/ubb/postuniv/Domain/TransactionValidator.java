package com.ubb.postuniv.Domain;

import com.ubb.postuniv.Repository.IRepository;

public class TransactionValidator {

    public void validate(Transaction transaction, IRepository<Car> carRepository, IRepository<ClientCard> clientCardRepository) throws TransactionValidatorException {
        StringBuilder sb = new StringBuilder();

        if (carRepository.read(transaction.getCarID()) == null) {
            sb.append("The car with the id: " + transaction.getCarID() + " does not exist!\n");
        }

        if (clientCardRepository.read(transaction.getClientCardID()) == null) {
            sb.append("The client with the id: " + transaction.getClientCardID() + " does not exist!");
        }

        if (sb.length() > 0) {
            throw new TransactionValidatorException(sb.toString());
        }
    }
}
