package com.gridnine.testing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * FlightFilter реализует непосредственно функцонал фильтрации.
 * Основные методы - filter и validateOne.
 */
public class FlightFilter {
    private final RuleSetBuilder setBuilder = new RuleSetBuilder();
    private Set<Rule> rulesSet;
    private List<Flight> flightsList;

    public FlightFilter(List<Flight> flights){
        Objects.requireNonNull(flights);
        this.flightsList = flights;
    };
/**
 * Метод приводит проверку одного полета по заданному сету правил.
 * Проверка происходит параллельно для каждого правила до тех пор, пока хотя бы одно не выдаст false, тогда
 * метод сам возвращает false и данный полет будет исключен.
 * Если полет прошел провеку всеми правилами, метод вернет true и данный полет будет сохранен.
 */
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

    /**
     * Метод проводит фильтрацию заданного листа полетов по заданному набору правил.
     * Лист с полетами преобразуется к конкретной реализации в виде массива для простого распараллеливания.
     * После фильтрации, метод обновит внутренний лист на отфильтрованный.
    * */
    public FlightFilter filter(String... rules){
        Objects.requireNonNull(rules);
        List<String> rulesList = Arrays.asList(rules);
        rulesSet = setBuilder.getSetOfRule(rulesList);
        ArrayList<Flight> multipleList = new ArrayList<>(flightsList);
        /*for (Flight flight : flights){
            if (validateOne(flight))
                filteredList.add(flight);
        }*/
        flightsList = multipleList.parallelStream().filter(this::validateOne).sequential().collect(Collectors.toCollection(LinkedList::new));

        return this;
    }

    public FlightFilter filter(List<String> rules){
        rulesSet = setBuilder.getSetOfRule(rules);
        ArrayList<Flight> multipleList = new ArrayList<>(flightsList);
        flightsList = multipleList.parallelStream().filter(this::validateOne).sequential().collect(Collectors.toCollection(LinkedList::new));

        return this;
    }
}
