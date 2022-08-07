package com.gridnine.testing;

import java.util.HashMap;
import java.util.Map;

class RuleFactory {
    private RuleFactory(){};

    private static final Map<String, Integer> ruleCases = new HashMap<>(Map.of("DepartureBeforeNow", 1,
                                                                                "ArrivedBeforeDeparture", 2,
                                                                                "EarthTimeLess", 3));

    public static Rule getRule(String ruleName) {
        switch (ruleCases.get(ruleName)){
            case 1:
                return DepartureBeforeNow.getInstance();
            case 2:
                return ArrivedBeforeDeparture.getInstance();
            case 3:
                return EarthTimeLess.getInstance("2");
            default:
                throw new IllegalArgumentException("There is no such rule!");
        }
    }
}
