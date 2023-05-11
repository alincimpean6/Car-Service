# Car-Service App
App that is done in Java using Models/Service/Repository/UI

## App description

The app can store cars,cards and transactions and it helps the management of a service center.

Car(int carID, String carModel, Date carDate, int carKm, boolean carGuarantee) 

ClientCard(int clientID, String clientFirstName, String clientLastName, String clientCNP, Date birthDate, Date registrationDate)

Transaction(int tranzactionID, int carID, int clientCardID, int sumOfParts, int sumOfWork, Date dateTime) if there exists a ClientCard there will be 10%discount and if it has guarantee there will be 100% discount.

Functionality:
1. Full text search.
2. Show all transactions between a interval.
3. Show all cars sortered by work price.
4. Show all client cards sortered by sum of discounts.
5. Delete all transactions between two dates.
6. Car guarantee update if the car is older than 3 years and maximum 60.000 KM

