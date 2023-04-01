package com.ubb.postuniv.Service;

import com.ubb.postuniv.Domain.*;
import com.ubb.postuniv.Repository.IRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientService {

    private IRepository<ClientCard> clientCardRepository;
    private ClientValidator clientValidator;

    public ClientService(IRepository<ClientCard> clientCardRepository, ClientValidator clientValidator) {
        this.clientCardRepository = clientCardRepository;
        this.clientValidator = clientValidator;
    }

    public void add(int clientID, String clientFirstName, String clientLastName, String clientCNP, Date birthDate, Date registrationDate) throws Exception {
        ClientCard card = new ClientCard(clientID, clientFirstName, clientLastName, clientCNP, birthDate, registrationDate);
        this.clientValidator.validate(card,clientCardRepository);
        this.clientCardRepository.create(card);
    }

    public List<ClientCard> getAll() {
        return this.clientCardRepository.readAll();
    }

    public List<ClientCard> showClient(String fullName) {
        String[] nameArray = fullName.split(" ");
        String firstName = nameArray[0];
        String lastName = nameArray[1];
        List<ClientCard> matchingNames = new ArrayList<>();
        for (ClientCard c : this.clientCardRepository.readAll()) {
            if (c.getClientFirstName().equals(firstName) && c.getClientLastName().equals(lastName)) {
                matchingNames.add(c);
            } }
        return matchingNames;
    }

    public List<ClientDiscountDTO> getClientsDiscountSorted() {
        List<ClientDiscountDTO> matchingClients = new ArrayList<>();
        for (ClientCard c : this.clientCardRepository.readAll()) {
            int clientId = c.getId();
            ClientCard client = clientCardRepository.read(clientId);
            if (client != null) {
                float sumDiscount = client.getSumDiscount();
                if(sumDiscount != 0) {
                    matchingClients.add(new ClientDiscountDTO(c, sumDiscount));
                }
            }
        }
        matchingClients.sort((a, b) -> {
            float difference = a.discount - b.discount;
            float eps = 0.00001f;
            if (Math.abs(difference) < eps) {
                return 0; }
            return difference < -eps ? 1 : -1;
        });
        return matchingClients;
    }
}
