package com.gridnine.testing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FlightFilter {
    private final RuleSetBuilder setBuilder = new RuleSetBuilder();
    private Set<Rule> rulesSet;

    public FlightFilter(){};

    private boolean validateOne(Flight flight){
        /*for (Rule rule : rulesSet) {
             if (!rule.isAcceptRule(flight))
                 return false;
        }*/

        if (rulesSet.parallelStream().anyMatch(rule -> !rule.isAcceptRule(flight)))
            return false;

        return true;
    }

    public List<Flight> filter(List<Flight> flights, List<String> rules){
        ArrayList<Flight> multiFlights = new ArrayList<>(flights);
        List<Flight> filteredList;
        rulesSet = setBuilder.getSetOfRule(rules);

        /*for (Flight flight : flights){
            if (validateOne(flight))
                filteredList.add(flight);
        }*/
        filteredList = multiFlights.parallelStream().filter(flight -> validateOne(flight)).sequential().collect(Collectors.toCollection(LinkedList::new));

        return filteredList;
    }
}
