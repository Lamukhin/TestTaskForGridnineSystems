package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightBuilder {
	static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
            //обычный полёт длительностью 2 часа
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
            //обычный полёт с 1 пересадкой
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
            //полёт с прошедшим вылетом
            createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
            //полёт с приземлением до отправления
            createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
            //полёт с более чем 2 часами ожидания между рейсами
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
            //полёт с суммарным ожиданием во всех пересадках более 2 часов
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
