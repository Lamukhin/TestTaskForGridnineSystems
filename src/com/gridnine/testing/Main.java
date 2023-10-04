package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	private final static LocalDateTime today = LocalDateTime.now();

	public static void main(String[] args) {
		List<Flight> flights = FlightBuilder.createFlights();
		System.out.println("Исходные перелёты, полученные из createFlights():");
		flights.forEach(f -> System.out.println(f));

		System.out.println("\nТекущее время: " + today.format(fmt));

		System.out.println("1) Исключены рейсы, вылет которых уже прошёл:");
		pastFlightsFilter(flights).forEach(f -> System.out.println(f));

		System.out.println("\n2) Исключены рейсы, где есть сегменты, в которых дата прилёта раньше даты вылета: ");
		incorrectSegmentsFilter(flights).forEach(f -> System.out.println(f));

		System.out.println("\n3) Исключены рейсы, в которых ожидание на пересадах более 2-х часов: ");
		moreThenTwoHoursWaitingFilter(flights).forEach(f -> System.out.println(f));
	}

	public static List<Flight> pastFlightsFilter(List<Flight> flights) {
		List<Flight> filteredFlights = shallowCopy(flights);
		for (Flight flight : flights) {
			boolean hasFound = false;
			for (Segment segment : flight.getSegments()) {
				if (segment.getDepartureDate().compareTo(today) < 0) {
					hasFound = true;
				}
			}
			if (hasFound) {
				filteredFlights.remove(flight);
			}
		}
		return filteredFlights;
	}

	public static List<Flight> incorrectSegmentsFilter(List<Flight> flights) {
		List<Flight> filteredFlights = shallowCopy(flights);
		for (Flight flight : flights) {
			boolean hasFound = false;		
			for (Segment segment : flight.getSegments()) {
				if (segment.getArrivalDate().compareTo(segment.getDepartureDate()) < 0){
					hasFound = true;
				}
			}
			if (hasFound) {
				filteredFlights.remove(flight);
			}
		}
		return filteredFlights;
	}

	public static List<Flight> moreThenTwoHoursWaitingFilter(List<Flight> flights) {
		List<Flight> filteredFlights = shallowCopy(flights);
		for (Flight flight : flights) {
			int totalWait = 0;
			List<Segment> currentSegments = flight.getSegments();
			for (int i = 1; i < currentSegments.size(); i++) {
				LocalDateTime previousSegmentArrivalDate = currentSegments.get(i - 1).getArrivalDate();
				LocalDateTime currentSegmentdepartureDate = currentSegments.get(i).getDepartureDate();
				totalWait += previousSegmentArrivalDate.until(currentSegmentdepartureDate, ChronoUnit.HOURS);
			}
			if (totalWait > 2) {
				filteredFlights.remove(flight);
			}
		}
		return filteredFlights;
	}

	public static List<Flight> shallowCopy(List<Flight> flights) {
		List<Flight> copiedList = new ArrayList<>();
		for (int i = 0; i < flights.size(); i++) {
			copiedList.add(flights.get(i));
		}
		return copiedList;
	}
}
