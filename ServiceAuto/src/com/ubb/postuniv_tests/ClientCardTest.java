package com.ubb.postuniv_tests;

import com.ubb.postuniv.Domain.Car;
import com.ubb.postuniv.Domain.ClientCard;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClientCardTest {
    private ClientCard clientCard;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    void testConstructorAndGetters() {
        int clientID = 1;
        String clientFirstName = "Alex";
        String clientSecondName = "Pop";
        String clientCNP = "1234567890123";
        Date birthDate = new Date();
        Date registrationDate = new Date();

        clientCard = new ClientCard(clientID, clientFirstName, clientSecondName, clientCNP, birthDate, registrationDate);

        assertEquals(clientID, clientCard.getId());
        assertEquals(clientFirstName, clientCard.getClientFirstName());
        assertEquals(clientSecondName, clientCard.getClientLastName());
        assertEquals(clientCNP, clientCard.getClientCNP());
        assertEquals(birthDate, clientCard.getBirthDate());
        assertEquals(registrationDate, clientCard.getRegistrationDate());
    }

    @Test
    void testSetters() {
        int clientID = 1;
        String clientFirstName = "Alex";
        String clientSecondName = "Pop";
        String clientCNP = "1234567890123";
        Date birthDate = new Date();
        Date registrationDate = new Date();

        clientCard = new ClientCard(clientID, clientFirstName, clientSecondName, clientCNP, birthDate, registrationDate);

        String newFirstName = "Andreas";
        clientCard.setClientFirstName(newFirstName);
        assertEquals(newFirstName, clientCard.getClientFirstName());

        String newSecondName = "Yop";
        clientCard.setClientLastName(newSecondName);
        assertEquals(newSecondName, clientCard.getClientLastName());

        String newCNP = "1234667890123";
        clientCard.setClientCNP(newCNP);
        assertEquals(newCNP, clientCard.getClientCNP());

        Date newBirthDate = new Date();
        clientCard.setBirthDate(newBirthDate);
        assertEquals(newBirthDate, clientCard.getBirthDate());

        Date newregistrationDate = new Date();
        clientCard.setRegistrationDate(newregistrationDate);
        assertEquals(newregistrationDate, clientCard.getRegistrationDate());
    }
}