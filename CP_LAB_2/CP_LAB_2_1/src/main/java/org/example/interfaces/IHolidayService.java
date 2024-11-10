package org.example.interfaces;

import org.example.models.Holiday;

import java.time.LocalDate;
import java.util.Set;

public interface IHolidayService {
    Set<Holiday> getHolidays();
    boolean isHoliday(LocalDate date);
}
