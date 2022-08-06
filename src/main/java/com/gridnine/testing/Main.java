package com.gridnine.testing;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        List<String> rules = Arrays.asList("DepartureBeforeNow");
        System.out.println(flights);

        FlightFilter filter1 = new FlightFilter();
        //filter1.filter(flights, rules);
        System.out.println(filter1.filter(flights, rules));
        System.out.println(flights);
    }
}
