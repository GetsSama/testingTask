package com.gridnine.testing;

import java.util.Arrays;
import java.util.List;
/**
   Организована поддержка 3-х фильтров-правил, указанных в задании (rule1, rule2, rule3, соответственно).
   Третье правило содержит атрибут - количество часов на земле, если в полете количетво часов строго больше
   заданного атрибута, то такой полет исключается.
*/
public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        String rule1 = "DepartureBeforeNow";
        String rule2 = "ArrivedBeforeDeparture";
        String rule3 = "EarthTimeLess 2";

        System.out.println(flights);

        FlightFilter filter1 = new FlightFilter(flights);

        //Правила можно передавать различными способами:

        System.out.println(filter1.filter(rule1).filter(rule2).filter(rule3).getFilteredList());
        //System.out.println(filter1.filter(rule1, rule2, rule3).getFilteredList());
        //System.out.println(filter1.filter(Arrays.asList(rule1, rule2, rule3)).getFilteredList());


        List<Class<?>> classes = ClassFinder.find("com.gridnine.testing");
        /*for (Class cls : classes)
            System.out.println(cls.getSimpleName());*/

        for (Class clazz : classes){
            String superClassName = clazz.getSuperclass().getSimpleName();
            if (superClassName.equals("SimpleRuleImpl") || superClassName.equals("AttributedRule"))
                System.out.println(clazz.getSimpleName());
        }
    }
}
