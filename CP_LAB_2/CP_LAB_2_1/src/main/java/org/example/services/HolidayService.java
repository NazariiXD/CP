package org.example.services;

import org.example.interfaces.IHolidayService;
import org.example.models.Holiday;

import java.time.LocalDate;
import java.util.Set;

public class HolidayService implements IHolidayService {
    private final Set<Holiday> holidays;

    public HolidayService(Set<Holiday> holidays) {
        this.holidays = holidays;
    }

    @Override
    public Set<Holiday> getHolidays() {
        return holidays;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidays.contains(new Holiday(date));
    }
}
