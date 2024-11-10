package org.example.services;

import org.example.models.Holiday;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HolidayServiceTests {
    private HolidayService holidayService;
    private Set<Holiday> holidays;

    @BeforeEach
    void setUp() {
        holidays = new HashSet<>();
        holidays.add(new Holiday(LocalDate.of(2024, 1, 1)));
        holidays.add(new Holiday(LocalDate.of(2024, 12, 25)));

        holidayService = new HolidayService(holidays);
    }

    @Test
    void testGetHolidays() {
        Set<Holiday> retrievedHolidays = holidayService.getHolidays();
        assertEquals(holidays, retrievedHolidays, "Holidays set should match the expected holidays.");
    }

    @Test
    void testIsHolidayForHolidayDate() {
        assertTrue(holidayService.isHoliday(LocalDate.of(2024, 1, 1)), "January 1st should be a holiday.");
        assertTrue(holidayService.isHoliday(LocalDate.of(2024, 12, 25)), "December 25th should be a holiday.");
    }

    @Test
    void testIsHolidayForNonHolidayDate() {
        assertFalse(holidayService.isHoliday(LocalDate.of(2024, 7, 4)), "July 4th should not be a holiday.");
    }

    @Test
    void testIsHolidayForEmptyHolidaySet() {
        holidayService = new HolidayService(new HashSet<>());
        assertFalse(holidayService.isHoliday(LocalDate.of(2024, 1, 1)), "No holidays should be set, so January 1st should not be a holiday.");
    }
}
