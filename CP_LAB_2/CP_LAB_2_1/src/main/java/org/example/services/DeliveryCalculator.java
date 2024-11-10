package org.example.services;

import org.example.enums.DeliveryMethod;
import org.example.interfaces.IDeliveryCalculator;
import org.example.interfaces.IHolidayService;
import org.example.models.Delivery;
import org.example.models.Seller;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class DeliveryCalculator implements IDeliveryCalculator {
    private final IHolidayService holidayService;
    private final List<Seller> sellers;

    public DeliveryCalculator(IHolidayService holidayService, List<Seller> sellers) {
        this.holidayService = holidayService;
        this.sellers = sellers;
    }

    @Override
    public Optional<Delivery> findBestDeliveryOption(ZonedDateTime orderDateTime, DeliveryMethod deliveryMethod) {
        Seller closestSeller = null;
        ZonedDateTime closestDeliveryDateTime = null;
        double closestDistance = Double.MAX_VALUE;

        for (Seller seller: sellers) {
            if (seller.getDeliveryMethodDuration(deliveryMethod) == null) {
                continue;
            }

            var deliveryDateTime = calculateNextWorkingDay(seller, orderDateTime, deliveryMethod);
            var distanceToSeller = closestSeller != null ? seller.getDistance(closestSeller.getTimeZone()) : 0.0;

            if (isCloserDelivery(deliveryDateTime, closestDeliveryDateTime, distanceToSeller, closestDistance)) {
                closestDeliveryDateTime = deliveryDateTime;
                closestSeller = seller;
                closestDistance = distanceToSeller;
            }
        }

        if (closestSeller == null) {
            return Optional.empty();
        }

        return Optional.of(new Delivery(orderDateTime, closestSeller, deliveryMethod, closestDeliveryDateTime));
    }

    @Override
    public ZonedDateTime calculateNextWorkingDay(
            Seller seller,
            ZonedDateTime orderDateTime,
            DeliveryMethod deliveryMethod) {
        var deliveryDuration = seller.getDeliveryMethodDuration(deliveryMethod);

        if (deliveryDuration == null) {
            throw new IllegalArgumentException("Метод доставки не знайдено для продавця: " + seller.getName());
        }

        var estimatedDeliveryDateTime = orderDateTime.plusHours(deliveryDuration.toHours());

        if (estimatedDeliveryDateTime.getHour() < 9) {
            estimatedDeliveryDateTime = estimatedDeliveryDateTime.withHour(9);
        } else if (estimatedDeliveryDateTime.getHour() >= 20) {
            estimatedDeliveryDateTime = estimatedDeliveryDateTime.plusDays(1).withHour(9);
        }

        while (!isWorkingDay(estimatedDeliveryDateTime.toLocalDate(), seller)) {
            estimatedDeliveryDateTime = estimatedDeliveryDateTime.plusDays(1);
        }

        return estimatedDeliveryDateTime;
    }

    private boolean isCloserDelivery(
            ZonedDateTime deliveryDateTime,
            ZonedDateTime closestDeliveryDateTime,
            double distanceToSeller,
            double closestDistance) {
        return closestDeliveryDateTime == null ||
                deliveryDateTime.isBefore(closestDeliveryDateTime) ||
                (deliveryDateTime.isEqual(closestDeliveryDateTime) && distanceToSeller < closestDistance);
    }

    private boolean isWorkingDay(LocalDate day, Seller seller) {
        return seller.isWorkingDay(day) && !holidayService.isHoliday(day);
    }
}

