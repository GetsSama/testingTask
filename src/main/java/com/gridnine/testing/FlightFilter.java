package com.gridnine.testing;

import java.util.*;
import java.util.stream.Collectors;

public class FlightFilter {
    private final RuleSetBuilder setBuilder = new RuleSetBuilder();
    private Set<Rule> rulesSet;
    private List<Flight> flightsList;

    public FlightFilter(List<Flight> flights){
        Objects.requireNonNull(flights);
        this.flightsList = flights;
    };

    private boolean validateOne(Flight flight){
        /*for (Rule rule : rulesSet) {
             if (!rule.isAcceptRule(flight))
                 return false;
        }*/

        if (rulesSet.parallelStream().anyMatch(rule -> !rule.isAcceptRule(flight)))
            return false;

        return true;
    }

    public List<Flight> getFilteredList(){
        return flightsList;
    }

    public FlightFilter filter(String... rules){
        Objects.requireNonNull(rules);
        List<String> rulesList = Arrays.asList(rules);
        rulesSet = setBuilder.getSetOfRule(rulesList);
        ArrayList<Flight> multipleList = new ArrayList<>(flightsList);
        /*for (Flight flight : flights){
            if (validateOne(flight))
                filteredList.add(flight);
        }*/
        flightsList = multipleList.parallelStream().filter(flight -> validateOne(flight)).sequential().collect(Collectors.toCollection(LinkedList::new));

        return this;
    }

    public FlightFilter filter(List<String> rules){
        rulesSet = setBuilder.getSetOfRule(rules);
        ArrayList<Flight> multipleList = new ArrayList<>(flightsList);
        flightsList = multipleList.parallelStream().filter(flight -> validateOne(flight)).sequential().collect(Collectors.toCollection(LinkedList::new));

        return this;
    }
}
