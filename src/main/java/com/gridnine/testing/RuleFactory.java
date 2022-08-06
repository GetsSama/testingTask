package com.gridnine.testing;

import java.util.HashMap;
import java.util.Map;

class RuleFactory {
    private RuleFactory(){};

    private static Map<String, Integer> ruleCases = new HashMap<>(Map.of("DepartureBeforeNow", 1));

    public static Rule getRule(String ruleName) {
        switch (ruleCases.get(ruleName)){
            case 1:
                return DepartureBeforeNow.getInstance();
            default:
                throw new IllegalArgumentException("There is no such rule!");
        }
    }
}
