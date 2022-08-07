package com.gridnine.testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleRuleImplTest {

    private LocalDateTime ldNow;

    @BeforeAll
    void prepare(){
        ldNow = LocalDateTime.now();
    }

    @Test
    void isDepartureBeforeNowCorrect(){
        Flight unCorrectFlight = new Flight(Arrays.asList(new Segment(ldNow.minusDays(1), ldNow.now().plusHours(5))));
        Flight correctFlight = new Flight(Arrays.asList(new Segment(ldNow.plusHours(1), ldNow.plusHours(5))));
        Rule testingRule = DepartureBeforeNow.getInstance();

        assertAll(
                () -> assertTrue(testingRule.isAcceptRule(correctFlight)),
                () -> assertFalse(testingRule.isAcceptRule(unCorrectFlight))
        );
    }

    @Test
    void isDepartureBeforeNowSingleton(){
        Rule firstInstance = DepartureBeforeNow.getInstance();
        Rule secondInstance = DepartureBeforeNow.getInstance();

        assertAll(
                () -> assertNotNull(firstInstance),
                () -> assertNotNull(secondInstance),
                () -> assertSame(firstInstance, secondInstance)
        );
    }

    @Test
    void isArrivedBeforeDepartureCorrect(){
        Flight unCorrectFlight = new Flight(Arrays.asList(new Segment(ldNow, ldNow.minusHours(2))));
        Flight correctFlight = new Flight(Arrays.asList(new Segment(ldNow, ldNow.plusHours(2))));
        Rule testingRule = ArrivedBeforeDeparture.getInstance();

        assertAll(
                () -> assertTrue(testingRule.isAcceptRule(correctFlight)),
                () -> assertFalse(testingRule.isAcceptRule(unCorrectFlight))
        );
    }

    @Test()
    void isArrivedBeforeDepartureSingleton(){
        Rule firstInstance = ArrivedBeforeDeparture.getInstance();
        Rule secondInstance = ArrivedBeforeDeparture.getInstance();

        assertAll(
                () -> assertNotNull(firstInstance),
                () -> assertNotNull(secondInstance),
                () -> assertSame(firstInstance, secondInstance)
        );
    }


}
