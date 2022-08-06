package com.gridnine.testing;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        String rule1 = "DepartureBeforeNow";

        System.out.println(flights);

        FlightFilter filter1 = new FlightFilter();

        System.out.println(filter1.filter(flights, Arrays.asList(rule1)));

    }
}
