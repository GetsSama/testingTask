package com.gridnine.testing;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
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

    private static final Map<String, Function<Void, Rule>> allSimpleRules = getSimpleRulesRealisation();

    private static final Map<String, Function<String, Rule>> allAttributedRules = getAttributedRulesRealisation();
    private RuleFactoryManager(){};
    public static RuleFactory getInstance(){
        return thisInstance;
    }

    public static Map<String, Function<Void, Rule>> getSimpleRulesRealisation() {
        List<Class<?>> classList = ClassFinder.find("com.gridnine.testing");
        Map<String, Function<Void, Rule>> result = new HashMap<>();

        for (Class clazz : classList){
            String superClassName = "";

            try {
                superClassName = clazz.getSuperclass().getSimpleName();
            } catch (NullPointerException e) {

            }

            if (superClassName.equals("SimpleRuleImpl")) {
                result.put(clazz.getSimpleName(), (x)-> {
                    try {
                        return (Rule) clazz.getMethod("getInstance").invoke(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        return result;
    }

    public static Map<String, Function<String, Rule>> getAttributedRulesRealisation() {
        List<Class<?>> classList = ClassFinder.find("com.gridnine.testing");
        Map<String, Function<String, Rule>> result = new HashMap<>();

        for (Class clazz : classList){
            String superClassName = "";

            try {
                superClassName = clazz.getSuperclass().getSimpleName();
            } catch (NullPointerException e) {

            }
            if (superClassName.equals("AttributedRule")) {
                result.put(clazz.getSimpleName(), (x)-> {
                    try {
                        return (Rule) clazz.getMethod("getInstance", String.class).invoke(null, x);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        return result;
    }

    public static Map<String, Function<Void, Rule>> getSimpleRulesMap(){
        return allSimpleRules;
    }

    public static Map<String, Function<String, Rule>> getAttributedRulesMap() {
        return allAttributedRules;
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
    //private final Map<String, Integer> rulesCases = new HashMap<>(Map.of("DepartureBeforeNow", 1, "ArrivedBeforeDeparture", 2));
    private static final Map<String, Function<Void, Rule>> rulesCases = RuleFactoryManager.getSimpleRulesRealisation();


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
            return rulesCases.get(ruleName).apply(null);
        throw new IllegalArgumentException();
    }
}

class AttributedRuleFactory implements RuleFactory{

    //private final Map<String, Integer> rulesCases = new HashMap<>(Map.of("EarthTimeLess", 1));

    private final Map<String, Function<String, Rule>> rulesCases = RuleFactoryManager.getAttributedRulesRealisation();
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
           return rulesCases.get(nameAndAttribute[0]).apply(nameAndAttribute[1]);
        } else {
            throw new IllegalArgumentException("There is no such rule!");
        }
    }
}
