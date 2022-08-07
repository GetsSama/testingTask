package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BuildControllerTest {

    @Test
    void isRulesEqualsMethodTest(){
        List<String> firstList = Arrays.asList("One", "Two", "Three");
        List<String> secondList = Arrays.asList("One", "Two", "Three");
        List<String> notEqualsList = Arrays.asList("One", "One", "One");

        assertAll(
                () -> assertTrue(BuilderController.isRulesEquals(firstList, secondList)),
                () -> assertTrue(BuilderController.isRulesEquals(firstList, firstList)),
                () -> assertFalse(BuilderController.isRulesEquals(firstList, notEqualsList))
        );
    }

    @Test
    void doNeedBuildNewSetMethodTest(){
        List<String> previousList = Arrays.asList("1", "2", "3", "4", "5");
        List<String> newListTrueCase1 = Arrays.asList("1", "2", "3", "10", "11");
        List<String> newListTrueCase2 = Arrays.asList("1", "2", "3", "4", "5", "10", "11", "12", "13", "14");
        List<String> newListTrueCase3 = Arrays.asList("1", "2", "3");
        List<String> newListFalseCase1 = Arrays.asList("1", "2", "3", "4", "10");
        List<String> newListFalseCase2 = Arrays.asList("1", "2", "3", "4", "5", "10");
        List<String> newListFalseCase3 = Arrays.asList("1", "2", "3", "4");

        assertAll(
                () -> assertTrue(BuilderController.doNeedBuildNewSet(previousList, newListTrueCase1)),
                () -> assertTrue(BuilderController.doNeedBuildNewSet(previousList, newListTrueCase2)),
                () -> assertTrue(BuilderController.doNeedBuildNewSet(previousList, newListTrueCase3)),
                () -> assertFalse(BuilderController.doNeedBuildNewSet(previousList, newListFalseCase1)),
                () -> assertFalse(BuilderController.doNeedBuildNewSet(previousList, newListFalseCase2)),
                () -> assertFalse(BuilderController.doNeedBuildNewSet(previousList, newListFalseCase3))
        );
    }

    @Test
    void getChangeMapMethodTest(){
        List<String> previousList = Arrays.asList("1", "2", "3", "4", "5");
        List<String> newList = Arrays.asList("1", "2", "3", "10", "11");
        Map<String, Boolean> expectedMap = new HashMap<>(Map.of("4", false,
                                                                "5", false,
                                                                "10", true,
                                                                "11", true));

        assertEquals(expectedMap, BuilderController.getChangeMap(previousList, newList));
    }
}
