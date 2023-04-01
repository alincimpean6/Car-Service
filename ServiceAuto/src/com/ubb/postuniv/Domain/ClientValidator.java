package com.ubb.postuniv.Domain;

import com.ubb.postuniv.Repository.IRepository;

public class ClientValidator {
    private IRepository<ClientCard> clientCardRepository;

    public void validate(ClientCard person, IRepository<ClientCard> clientCardRepository) throws ClientValidatorException {
        this.clientCardRepository = clientCardRepository;

        StringBuilder sbClient = new StringBuilder();

        if (person.getClientCNP().length() != 13) {
            sbClient.append("The CNP is not in range!\n");
        }

        boolean found = false;
        String CNP = person.getClientCNP();
        for (ClientCard c : clientCardRepository.readAll()) {
            int value = c.getClientCNP().compareTo(CNP);
            if (value == 0) {
                found = true;
            }
        }

        if (found == true) {
            sbClient.append("There already exists that CNP!\n");
        }

        if (sbClient.length() > 0) {
            throw new ClientValidatorException(sbClient.toString());
        }



    }
}
