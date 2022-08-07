package com.gridnine.testing;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        String rule1 = "DepartureBeforeNow";
        String rule2 = "ArrivedBeforeDeparture";
        String rule3 = "EarthTimeLess 2";

        System.out.println(flights);

        FlightFilter filter1 = new FlightFilter(flights);

        System.out.println(filter1.filter(rule1).filter(rule2).filter(rule3).getFilteredList());
        //System.out.println(filter1.filter(flights, Arrays.asList(rule2)));
        //System.out.println(filter1.filter(flights, Arrays.asList(rule3)));
    }
}
