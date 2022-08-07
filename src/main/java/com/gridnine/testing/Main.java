package com.gridnine.testing;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        String rule1 = "DepartureBeforeNow";
        String rule2 = "ArrivedBeforeDeparture";
        String rule3 = "EarthTimeLess";

        System.out.println(flights);

        FlightFilter filter1 = new FlightFilter();

        System.out.println(filter1.filter(flights, Arrays.asList(rule1, rule2, rule3)));
        //System.out.println(filter1.filter(flights, Arrays.asList(rule2)));
        //System.out.println(filter1.filter(flights, Arrays.asList(rule3)));
    }
}
