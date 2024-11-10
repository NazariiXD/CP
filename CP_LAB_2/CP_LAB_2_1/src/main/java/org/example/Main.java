package org.example;

import org.example.enums.DeliveryMethod;
import org.example.interfaces.IDeliveryCalculator;
import org.example.interfaces.IHolidayService;
import org.example.models.Holiday;
import org.example.models.Seller;
import org.example.services.DeliveryCalculator;
import org.example.services.HolidayService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var holidays = getHolidays();
        var deliveryMethods = List.of(DeliveryMethod.values());
        var sellers = getSellers();

        IHolidayService holidayService = new HolidayService(new HashSet<>(holidays));
        IDeliveryCalculator deliveryService = new DeliveryCalculator(holidayService, sellers);

        try {
            System.out.println("Введіть дату оформлення замовлення (формат: YYYY-MM-DD): ");
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            var orderDateTime = LocalDate.parse(scanner.nextLine(), formatter).atStartOfDay();

            System.out.print("Введіть вашу часову зону (наприклад, Europe/Kyiv): ");
            String timeZoneInput = scanner.nextLine();
            ZoneId userTimeZone;

            if (timeZoneInput.isEmpty()) {
                userTimeZone = ZoneId.systemDefault();
            } else {
                userTimeZone = ZoneId.of(timeZoneInput);
            }

            System.out.println("Введіть метод доставки: ");
            System.out.println("Доступні методи доставки:");
            for (int i = 0; i < deliveryMethods.size(); i++) {
                System.out.println((i + 1) + ". " + deliveryMethods.get(i).getDescription());
            }

            int selectedIndex = scanner.nextInt() - 1;
            if (selectedIndex < 0 || selectedIndex >= deliveryMethods.size()) {
                System.out.println("Невірний вибір. Спробуйте ще раз.");
            }

            var deliveryMethod = deliveryMethods.get(selectedIndex);

            var deliveryOption = deliveryService.findBestDeliveryOption(orderDateTime.atZone(userTimeZone), deliveryMethod);

            if (deliveryOption.isPresent()) {
                var delivery = deliveryOption.get();
                System.out.println("Найкращий варіант доставки:");
                System.out.println("Час оформлення замволення: " + delivery.getOrderDateTime().toLocalDateTime() + " (" + delivery.getOrderDateTime().getZone() + ")");
                System.out.println("Продавець: " + delivery.getSeller().getName());
                System.out.println("Часовий пояс продавця: " + delivery.getSeller().getTimeZone());
                System.out.println("Метод доставки: " + delivery.getDeliveryMethod().getDescription());
                System.out.println("Дата доставки: " + delivery.getDeliveryDateTime().toLocalDateTime() + " (" + delivery.getDeliveryDateTime().getZone() + ")");
            }
            else {
                System.out.println("Немає доступних варіантів доставки.");
            }
        }
        catch (Exception ex) {
            System.out.println("Помилка: " + ex.getMessage());
        }
    }

    private static List<Holiday> getHolidays() {
        return List.of(
                new Holiday("Новий рік", LocalDate.of(2024, 1, 1)),
                new Holiday("Свято весни", LocalDate.of(2024, 3, 8)),
                new Holiday("День незалежності", LocalDate.of(2024, 8, 24)),
                new Holiday("Різдво", LocalDate.of(2024, 12, 25)),
                new Holiday("Свято святого Миколая", LocalDate.of(2024, 12, 19))
        );
    }

    private static List<Seller> getSellers() {
        return Arrays.asList(
                createSeller("Tech Gadgets Store",
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                        Map.of(
                                DeliveryMethod.STANDARD_DELIVERY, Duration.ofDays(5),
                                DeliveryMethod.EXPRESS_DELIVERY, Duration.ofDays(2),
                                DeliveryMethod.SAME_DAY_DELIVERY, Duration.ofHours(12)),
                        ZoneId.of("America/New_York")),
                createSeller("Fashion Boutique",
                        Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.MONDAY),
                        Map.of(
                                DeliveryMethod.STANDARD_DELIVERY, Duration.ofDays(3).plus(Duration.ofHours(10)),
                                DeliveryMethod.EXPRESS_DELIVERY, Duration.ofDays(1)),
                        ZoneId.of("Europe/London")),
                createSeller("Home Essentials",
                        Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                        Map.of(
                                DeliveryMethod.STANDARD_DELIVERY, Duration.ofDays(4),
                                DeliveryMethod.EXPRESS_DELIVERY, Duration.ofDays(2)),
                        ZoneId.of("Asia/Tokyo")),
                createSeller("Sports Equipment Co.",
                        Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.TUESDAY),
                        Map.of(
                                DeliveryMethod.STANDARD_DELIVERY, Duration.ofDays(6),
                                DeliveryMethod.EXPRESS_DELIVERY, Duration.ofDays(3)),
                        ZoneId.of("Australia/Sydney")),
                createSeller("Gourmet Food Shop",
                        Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                        Map.of(
                                DeliveryMethod.STANDARD_DELIVERY, Duration.ofDays(2),
                                DeliveryMethod.SAME_DAY_DELIVERY, Duration.ofHours(8)),
                        ZoneId.of("Europe/Berlin"))
        );
    }

    private static Seller createSeller(String name, Set<DayOfWeek> availableDates, Map<DeliveryMethod, Duration> deliveryMethods, ZoneId timeZone) {
        return new Seller(name, availableDates, deliveryMethods, timeZone);
    }
}