package com.gridnine.testing;

import com.sun.jdi.VoidValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Реализация фабрики, возвращающей объект правила по переданной строке с названием правила.
 * Общение с фабрикой происходит через один общий интерфейс. На данный момент имеется три реализации фабрики: RuleFactoryManager
 * производит первичную обработку переданных объектов и делегирует работу по дальнейшему получению объекта Rule подходящей
 * фабрике - SimpleRuleFactory обрабатывает простые правила, без атрибутов. AttributedRuleFactory - обрабатывает правила с атрибутами.
 */
interface RuleFactory{
    Rule getRule(String ruleName);
}
class RuleFactoryManager implements RuleFactory{
    private static final RuleFactoryManager thisInstance = new RuleFactoryManager();
    private static final RuleFactory simpleRuleFactory = SimpleRuleFactory.getInstance();
    private static final RuleFactory attributedRuleFactory = AttributedRuleFactory.getInstance();
    private RuleFactoryManager(){};
    public static RuleFactory getInstance(){
        return thisInstance;
    }
    @Override
    public Rule getRule(String ruleName) {
        if (ruleName.split(" ").length == 1)
            return simpleRuleFactory.getRule(ruleName);
        else
            return attributedRuleFactory.getRule(ruleName);
    }
}

class SimpleRuleFactory implements RuleFactory{
    private final Map<String, Integer> rulesCases = new HashMap<>(Map.of("DepartureBeforeNow", 1, "ArrivedBeforeDeparture", 2));
    private final Map<String, Function<VoidValue, Rule>> rulesCases2 = new HashMap<>(Map.of("DepartureBeforeNow", (x) -> DepartureBeforeNow.getInstance(),
            "ArrivedBeforeDeparture", (x)->ArrivedBeforeDeparture.getInstance()));


    private static final RuleFactory thisInstance = new SimpleRuleFactory();
    private SimpleRuleFactory(){};
    public static RuleFactory getInstance(){
        return thisInstance;
    }
   /* @Override
    public Rule getRule(String ruleName) {
        if (rulesCases.get(ruleName) != null) {
            switch (rulesCases.get(ruleName)) {
                case 1:
                    return DepartureBeforeNow.getInstance();
                case 2:
                    return ArrivedBeforeDeparture.getInstance();
                default:
                    throw new IllegalArgumentException("There is no such realisation for this rule!");
            }
        } else {
            throw new IllegalArgumentException("There is no such rule!");
        }
    }*/

    @Override
    public Rule getRule(String ruleName) {
        if (rulesCases.get(ruleName) != null)
            return rulesCases2.get(ruleName).apply(null);
        throw new IllegalArgumentException();


    }
}

class AttributedRuleFactory implements RuleFactory{

    private final Map<String, Integer> rulesCases = new HashMap<>(Map.of("EarthTimeLess", 1));
    private static final RuleFactory thisInstance = new AttributedRuleFactory();
    private AttributedRuleFactory(){};
    public static RuleFactory getInstance(){
        return thisInstance;
    }

    private String[] getNameAndAttribute(String ruleName){
        String[] nameAndAttribute = ruleName.split(" ");
        if (nameAndAttribute.length == 2)
            return nameAndAttribute;
        else
            throw new IllegalArgumentException("This rule have non correct format!");
    }
    @Override
    public Rule getRule(String ruleName) {
        String[] nameAndAttribute = getNameAndAttribute(ruleName);

        if (rulesCases.get(nameAndAttribute[0]) != null) {
            switch (rulesCases.get(nameAndAttribute[0])) {
                case 1:
                    return EarthTimeLess.getInstance(nameAndAttribute[1]);
                default:
                    throw new IllegalArgumentException("There is no such realisation for this rule!");
            }
        } else {
            throw new IllegalArgumentException("There is no such rule!");
        }
    }
}
