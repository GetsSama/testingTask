package com.gridnine.testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AttributedRuleImplTest {
    private LocalDateTime ldNow;

    @BeforeAll
    void prepare(){
        ldNow = LocalDateTime.now();
    }

    @Test
    void isEarthTimeLessCorrect(){
        Flight unCorrectFlight = new Flight(Arrays.asList(new Segment(ldNow, ldNow.plusHours(2)),
                                                          new Segment(ldNow.plusHours(5), ldNow.plusHours(6))));
        Flight correctFlight = new Flight(Arrays.asList(new Segment(ldNow, ldNow.plusHours(1)),
                                                        new Segment(ldNow.plusHours(1).plusMinutes(30), ldNow.plusHours(3))));
        String attribute = "2";
        Rule testRule = EarthTimeLess.getInstance(attribute);

        assertAll(
                () -> assertTrue(testRule.isAcceptRule(correctFlight)),
                () -> assertFalse(testRule.isAcceptRule(unCorrectFlight))
        );
    }

    @Test
    void EarthTimeLessCorrectIllegalArgs(){
        String attribute = "-1";
        assertThrows(IllegalArgumentException.class, () -> EarthTimeLess.getInstance(attribute));
    }
}
