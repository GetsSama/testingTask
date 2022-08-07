package com.gridnine.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RuleSetBuilderTest {

    @Test
    void methodGetSetOfRuleReturnCorrect(){
        List<String> rules = Arrays.asList("DepartureBeforeNow", "ArrivedBeforeDeparture", "EarthTimeLess 2");
        Set<Rule> expectedSet = new HashSet<>(Arrays.asList(DepartureBeforeNow.getInstance(),
                                                         ArrivedBeforeDeparture.getInstance(),
                                                         EarthTimeLess.getInstance("2")));
        RuleSetBuilder testSB = new RuleSetBuilder();
        Set<Rule> actualSet = testSB.getSetOfRule(rules);

        assertAll(
                () -> assertEquals(expectedSet, actualSet, "The sets must be equals!"),
                () -> assertSame(actualSet, testSB.getSetOfRule(rules), "The ref must be same!")
        );
    }
}
