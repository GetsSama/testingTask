package com.gridnine.testing;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FlightFilter {
    private final RuleSetBuilder setBuilder = new RuleSetBuilder();
    private Set<Rule> rulesSet;

    public FlightFilter(){};

    private boolean validateOne(Flight flight){
        for (Rule rule : rulesSet) {
             if (!rule.isAcceptRule(flight))
                 return false;
        }
        return true;
    }

    public List<Flight> filter(List<Flight> flights, List<String> rules){
        List<Flight> filteredList = new LinkedList<>();
        rulesSet = setBuilder.getSetOfRule(rules);

        for (Flight flight : flights){
            if (validateOne(flight))
                filteredList.add(flight);
        }

        flights = filteredList;
        return filteredList;
    }
}
