package org.example.services;

import org.example.enums.DeliveryMethod;
import org.example.interfaces.IDeliveryCalculator;
import org.example.interfaces.IHolidayService;
import org.example.models.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeliveryCalculatorTests {
    private IDeliveryCalculator calculator;
    private List<Seller> sellers;
    private IHolidayService holidayService;

    @BeforeEach
    void setUp() {
        holidayService = mock(IHolidayService.class);
        sellers = new ArrayList<>();
        calculator = new DeliveryCalculator(holidayService, sellers);
    }

    @Test
    void testFindBestDeliveryOption_ReturnsCorrectDelivery() {
        var seller = mock(Seller.class);
        when(seller.getDeliveryMethodDuration(DeliveryMethod.STANDARD_DELIVERY)).thenReturn(Duration.ofHours(2));
        when(seller.getTimeZone()).thenReturn(ZoneId.of("UTC"));
        when(seller.isWorkingDay(any(LocalDate.class))).thenReturn(true);
        sellers.add(seller);

        when(holidayService.isHoliday(any(LocalDate.class))).thenReturn(false);

        var orderDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        var deliveryMethod = DeliveryMethod.STANDARD_DELIVERY;

        var delivery = calculator.findBestDeliveryOption(orderDateTime, deliveryMethod);

        assertTrue(delivery.isPresent());
        assertEquals(seller, delivery.get().getSeller());
    }

    @Test
    void testFindBestDeliveryOption_ReturnsEmptyWhenNoSellerAvailable() {
        var orderDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        var deliveryMethod = DeliveryMethod.STANDARD_DELIVERY;

        var delivery = calculator.findBestDeliveryOption(orderDateTime, deliveryMethod);

        assertFalse(delivery.isPresent());
    }

    @Test
    void testCalculateNextWorkingDay_ReturnsCorrectDate() {
        var seller = mock(Seller.class);
        when(seller.getDeliveryMethodDuration(DeliveryMethod.STANDARD_DELIVERY)).thenReturn(Duration.ofHours(2));
        when(seller.getTimeZone()).thenReturn(ZoneId.of("UTC"));
        when(seller.isWorkingDay(any(LocalDate.class))).thenReturn(true);
        sellers.add(seller);

        var orderDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        var deliveryMethod = DeliveryMethod.STANDARD_DELIVERY;

        var deliveryDateTime = calculator.calculateNextWorkingDay(seller, orderDateTime, deliveryMethod);

        assertNotNull(deliveryDateTime);
        assertTrue(deliveryDateTime.getHour() >= 9 && deliveryDateTime.getHour() < 20);
    }

    @Test
    void testCalculateNextWorkingDay_ThrowsExceptionForNullDeliveryMethod() {
        var seller = mock(Seller.class);
        when(seller.getDeliveryMethodDuration(null)).thenReturn(null);
        sellers.add(seller);

        var orderDateTime = ZonedDateTime.now(ZoneOffset.UTC);

        var exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateNextWorkingDay(seller, orderDateTime, null);
        });

        assertEquals("Метод доставки не знайдено для продавця: null", exception.getMessage());
    }
}
