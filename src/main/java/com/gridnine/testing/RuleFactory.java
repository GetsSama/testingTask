package com.gridnine.testing;

import java.util.HashMap;
import java.util.Map;

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
    private static final RuleFactory thisInstance = new SimpleRuleFactory();
    private SimpleRuleFactory(){};
    public static RuleFactory getInstance(){
        return thisInstance;
    }
    @Override
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
