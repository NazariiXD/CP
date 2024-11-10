package org.example.models;

import org.example.enums.DeliveryMethod;

import java.time.*;
import java.util.Map;
import java.util.Set;

public class Seller {
    private String name;
    private Set<DayOfWeek> workingDays;
    private Map<DeliveryMethod, Duration> deliveryMethods;
    private ZoneId timeZone;

    public Seller(
            String name,
            Set<DayOfWeek> workingDays,
            Map<DeliveryMethod, Duration> deliveryMethods,
            ZoneId timeZone) {
        this.name = name;
        this.workingDays = workingDays;
        this.deliveryMethods = deliveryMethods;
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DayOfWeek> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Set<DayOfWeek> workingDays) {
        this.workingDays = workingDays;
    }

    public boolean isWorkingDay(LocalDate day) {
        return workingDays.contains(day.getDayOfWeek());
    }

    public Map<DeliveryMethod, Duration> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(Map<DeliveryMethod, Duration> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }

    public Duration getDeliveryMethodDuration(DeliveryMethod deliveryMethod) {
        if (!deliveryMethods.containsKey(deliveryMethod)) {
            return null;
        }

        return deliveryMethods.get(deliveryMethod);
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    public double getDistance(ZoneId zoneId) {
        var currentOffset = timeZone.getRules().getOffset(Instant.now());
        var otherOffset = zoneId.getRules().getOffset(Instant.now());

        return Math.abs(currentOffset.getTotalSeconds() - otherOffset.getTotalSeconds()) / 3600.0;
    }
}
