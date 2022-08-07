package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightFilterTest {

    @Test
    void equalsInFlightCheck(){
        List<Flight> first = FlightBuilder.createFlights();
        List<Flight> second = FlightBuilder.createFlights();
        List<Flight> third = new ArrayList<>(FlightBuilder.createFlights());
        third.remove(2);

        assertAll(
                () -> assertEquals(first, second),
                () -> assertNotSame(first, second),
                () -> assertNotEquals(first, third)
        );
    }


    @Test
    void filterTest(){
        List<Flight> reportedList = FlightBuilder.createFlights();
        List<Flight> expectedList = new ArrayList<>(FlightBuilder.createFlights());
        String rule1 = "DepartureBeforeNow";
        String rule2 = "ArrivedBeforeDeparture";
        String rule3 = "EarthTimeLess";
        FlightFilter testedFilter = new FlightFilter(reportedList);
        assertEquals(expectedList, testedFilter.getFilteredList());

        expectedList.remove(2);
        assertEquals(expectedList, testedFilter.filter(rule1).getFilteredList());

        expectedList.remove(2);
        assertEquals(expectedList, testedFilter.filter(rule2).getFilteredList());

        expectedList.remove(2);
        expectedList.remove(2);
        assertEquals(expectedList, testedFilter.filter(rule3).getFilteredList());
    }
}
