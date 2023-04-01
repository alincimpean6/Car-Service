package com.ubb.postuniv.Domain;

public class ClientDiscountDTO {
    public ClientCard client;
    public float discount;

    public ClientDiscountDTO(ClientCard client, float discount) {
        this.client = client;
        this.discount = discount;
    }

    public ClientCard getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "ClientDiscountDTO{" +
                "client=" + client +
                ", discount=" + discount +
                '}';
    }
}
