package org.example.models;

import org.example.enums.DeliveryMethod;

import java.time.ZonedDateTime;

public class Delivery {
    private ZonedDateTime orderDateTime;
    private Seller seller;
    private DeliveryMethod deliveryMethod;
    private ZonedDateTime deliveryDateTime;

    public Delivery(
            ZonedDateTime orderDateTime,
            Seller seller,
            DeliveryMethod deliveryMethod,
            ZonedDateTime deliveryDateTime) {
        this.orderDateTime = orderDateTime;
        this.seller = seller;
        this.deliveryMethod = deliveryMethod;
        this.deliveryDateTime = deliveryDateTime;
    }

    public ZonedDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(ZonedDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public ZonedDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(ZonedDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }
}
