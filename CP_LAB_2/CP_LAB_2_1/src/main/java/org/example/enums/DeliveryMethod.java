package org.example.enums;

public enum DeliveryMethod {
    STANDARD_DELIVERY("Стандартна доставка"),
    EXPRESS_DELIVERY("Експрес доставка"),
    SAME_DAY_DELIVERY("Доставка в той же день");

    private final String description;

    DeliveryMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
