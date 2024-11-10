package org.example.interfaces;

import org.example.enums.DeliveryMethod;
import org.example.models.Delivery;
import org.example.models.Seller;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface IDeliveryCalculator {
    Optional<Delivery> findBestDeliveryOption(ZonedDateTime orderDateTime, DeliveryMethod deliveryMethod);

    ZonedDateTime calculateNextWorkingDay(
            Seller seller,
            ZonedDateTime orderDateTime,
            DeliveryMethod deliveryMethod);
}
