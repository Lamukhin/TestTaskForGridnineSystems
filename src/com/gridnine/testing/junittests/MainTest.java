package com.gridnine.testing.junittests;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Main;
import com.gridnine.testing.Segment;

class MainTest {

	static List<Flight> testFlights;
	static Flight normalFlight;
	static Flight pastFlight;
	static Flight incorrectFlight;
	static Flight flightWithTreeHoursWait;
	
	@BeforeAll
	public static void init() {
		LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
		Segment firstNormalSegment = new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(3));
		
		Segment segmentOfPastFlight = new Segment(threeDaysFromNow.minusDays(10), threeDaysFromNow);
		
		Segment incorrectSegment = new Segment(threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(1));
		
		Segment firstSegtmentWithThreeHoursWait = new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(1));
		Segment secondSegtmentWithThreeHoursWait = new Segment(threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(3));
		Segment thirdSegtmentWithThreeHoursWait = new Segment(threeDaysFromNow.plusHours(4), threeDaysFromNow.plusHours(5));
		Segment fourthSegtmentWithThreeHoursWait = new Segment(threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7));
		
		normalFlight = new Flight(
				new ArrayList<Segment>(
						Arrays.asList(firstNormalSegment)));
		
		pastFlight = new Flight(
				new ArrayList<Segment>(
						Arrays.asList(segmentOfPastFlight)));
		
		incorrectFlight = new Flight(
				new ArrayList<Segment>(
						Arrays.asList(incorrectSegment)));
								
		flightWithTreeHoursWait = new Flight(
				new ArrayList<Segment>(
						Arrays.asList(firstSegtmentWithThreeHoursWait,
								secondSegtmentWithThreeHoursWait,
								thirdSegtmentWithThreeHoursWait,
								fourthSegtmentWithThreeHoursWait)));
		
		testFlights = Arrays.asList(
				normalFlight,
				pastFlight, 
				incorrectFlight,
				flightWithTreeHoursWait);

	}
		
	
	@Test
	public void pastFlightsFilterTest() {
		List<Flight> filteredFlights = Main.shallowCopy(testFlights);
		Iterable<Flight> actual = Main.pastFlightsFilter(filteredFlights);
		Iterable<Flight> expected = Arrays.asList(
				normalFlight, 
				incorrectFlight,
				flightWithTreeHoursWait);
		Assertions.assertIterableEquals(expected, actual);
	}
	
	@Test
	public void incorrectSegmentsFilterTest() {
		List<Flight> filteredFlights = Main.shallowCopy(testFlights);
		Iterable<Flight> actual = Main.incorrectSegmentsFilter(filteredFlights);
		Iterable<Flight> expected = Arrays.asList(
				normalFlight,
				pastFlight, 
				flightWithTreeHoursWait);
		Assertions.assertIterableEquals(expected, actual);
	}
	
	@Test
	public void moreThenTwoHoursWaitingFilterTest() {
		List<Flight> filteredFlights = Main.shallowCopy(testFlights);
		Iterable<Flight> actual = Main.moreThenTwoHoursWaitingFilter(filteredFlights);
		Iterable<Flight> expected = Arrays.asList(
				normalFlight,
				pastFlight, 
				incorrectFlight);
		Assertions.assertIterableEquals(expected, actual);
	}
	
	@Test
	public void shallowCopyTest() {
		Iterable<Flight> actual = Main.shallowCopy(testFlights);
		Iterable<Flight> expected = testFlights;
		Assertions.assertEquals(expected, actual);
	}

}
