import com.sun.security.ntlm.Client;
import com.ubb.postuniv.Domain.*;
//import com.ubb.postuniv.Repository.ClientCardRepository;
//import com.ubb.postuniv.Repository.TranzactionRepository;
import com.ubb.postuniv.Repository.IRepository;
import com.ubb.postuniv.Repository.InMemoryRepository;
import com.ubb.postuniv.Service.CarService;
import com.ubb.postuniv.Service.ClientService;
import com.ubb.postuniv.Service.TransactionService;
import com.ubb.postuniv.UserInterface.Console;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        IRepository<Car> carRepository = new InMemoryRepository<>();
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionRepository = new InMemoryRepository<>();

        CarValidator carValidator = new CarValidator();
        ClientValidator clientValidator = new ClientValidator();
        TransactionValidator transactionValidator = new TransactionValidator();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String dateString = "18.03.2005";
        Date date = dateFormat.parse(dateString);
        carRepository.create(new Car(1, "BMW", date, 40000, true));
        String dateString2 = "10.03.2005";
        Date date1 = dateFormat.parse(dateString2);
        carRepository.create(new Car(2, "Volvo", date1, 484880, false));
        String dateString3 = "05.05.2008";
        Date date2 = dateFormat.parse(dateString3);
        carRepository.create(new Car(3, "Audi", date2, 200100, false));
        String dateString4 = "01.03.2023";
        Date date3 = dateFormat.parse(dateString4);
        carRepository.create(new Car(4, "BMW", date3, 40000, true));


        String dateString5 = "15.05.1997";
        Date bDate1 = dateFormat.parse(dateString5);
        String dateStringr1 = "20.01.2020";
        Date rDate1 = dateFormat.parse(dateStringr1);
        clientCardRepository.create(new ClientCard(1, "Alin", "Cimpean", "1970515684532",bDate1,rDate1));
        String dateString6 = "10.04.2000";
        Date bDate2 = dateFormat.parse(dateString6);
        String dateStringr2 = "01.01.2022";
        Date rDate2 = dateFormat.parse(dateStringr2);
        clientCardRepository.create(new ClientCard(2, "Vasile", "Gheorghescu", "1200515684498",bDate2,rDate2));
        String dateString7 = "05.05.1996";
        Date bDate3 = dateFormat.parse(dateString7);
        String dateStringr8 = "10.01.2023";
        Date rDate3 = dateFormat.parse(dateStringr8);
        clientCardRepository.create(new ClientCard(3, "Daniel", "Pop", "1960515684532",bDate3,rDate3));
        String dateString9 = "05.09.1968";
        Date bDate4 = dateFormat.parse(dateString9);
        String dateStringr3 = "09.01.2021";
        Date rDate4 = dateFormat.parse(dateStringr3);
        clientCardRepository.create(new ClientCard(4, "Cornel", "Este", "1680515684532",bDate4,rDate4));


        CarService carService = new CarService(carRepository,transactionRepository,carValidator);
        ClientService clientService = new ClientService(clientCardRepository,clientValidator);
        TransactionService transactionService = new TransactionService(
                transactionRepository,
                carRepository,
                clientCardRepository,
                transactionValidator);

        Console console = new Console(carService,clientService, transactionService);

        console.runConsole();
    }
}
