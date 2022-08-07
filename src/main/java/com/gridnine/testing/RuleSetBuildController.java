package com.gridnine.testing;

import java.util.*;

/*
 Билдер собирает сет правил в зависимости от того, какой результат ему выдаст BuilderController.
*/
class RuleSetBuilder {
    private Set<Rule> rulesSet = new HashSet<>();
    private List<String> previousRules;

    private RuleFactory ruleFactory = RuleFactoryManager.getInstance();
    public RuleSetBuilder(){};


    //Полное построение сета "с нуля"
    private void buildNewRuleSet(List<String> rules) {
        Objects.requireNonNull(rules);
        if (!rulesSet.isEmpty())
            rulesSet.clear();

        for(String rule : rules)
            addRule(rule);
    }

    //Добавление одного правила в сет
    private void addRule(String ruleName) {
         rulesSet.add(ruleFactory.getRule(ruleName));
    }

    //Удаление одного правила из сета
    private void removeRule(String ruleName) {
        rulesSet.remove(ruleFactory.getRule(ruleName));
    }

    //Метод, меняющий текущий сет по мапе различий
    private void changeRuleSet(Map<String,Boolean> changeMap) {
        for (Map.Entry pair : changeMap.entrySet()){
            if ((boolean) pair.getValue()) {
                addRule((String) pair.getKey());
            } else {
                removeRule((String) pair.getKey());
            }
        }
    }

    //Основной метод, возвращающий корректный сет правил
    public Set<Rule> getSetOfRule(List<String> rules){
        Objects.requireNonNull(rules);
        //Срабатывает при первом вызове метода
        if (previousRules == null) {
            previousRules = rules;
            buildNewRuleSet(rules);
            return rulesSet;
        }
        //Обработка повторных вызовов
        if (BuilderController.isRulesEquals(previousRules, rules))
            return rulesSet;
        else if (BuilderController.doNeedBuildNewSet(previousRules, rules)){
            previousRules = rules;
            buildNewRuleSet(rules);
            return rulesSet;
        }
        else {
            changeRuleSet(BuilderController.getChangeMap(previousRules, rules));
            previousRules = rules;
            return rulesSet;
        }
    }
}

/*
Контроллер сравнивает новые и старые правила методом isRulesEquals - если листы правил идентичны, вернет true, иначе false;
Метод doNeedBuildNewSet решает, нужно ли построить полностью новый сет правил или нет.
Метод getDifferenceMap возвращает мапу различий: ключи - различия, значения - действие которое нужно сделать
(добавить - true, удалить - false)
 */
class BuilderController {
    private static final double criticalValue = 0.3;
    private BuilderController(){};
    public static boolean isRulesEquals(List<String> previousRules, List<String> newRules) {
        Objects.requireNonNull(previousRules);
        Objects.requireNonNull(newRules);
        return previousRules == newRules || previousRules.equals(newRules);
    }

    public static boolean doNeedBuildNewSet(List<String> previousRules, List<String> newRules) {
        Objects.requireNonNull(previousRules);
        Objects.requireNonNull(newRules);
        int prevSize = previousRules.size();
        int newSize = newRules.size();
        int countChanges = 0;
        Set<String> newRulesSet = new HashSet<>(newRules);
        Set<String> previousRulesSet = new HashSet<>(previousRules);

        if (prevSize >= newSize) {
            for (String prevRule : previousRules){
                if (!newRulesSet.contains(prevRule))
                    countChanges++;
            }
            return Double.compare(criticalValue, ((double) countChanges)/prevSize)<=0;
        } else {
            for (String newRule : newRules){
                if (!previousRulesSet.contains(newRule))
                    countChanges++;
            }
            return Double.compare(criticalValue, ((double) countChanges)/newSize)<=0;
        }

    }
    public static Map<String, Boolean> getChangeMap(List<String> previousRules, List<String> newRules) {
        Objects.requireNonNull(previousRules);
        Objects.requireNonNull(newRules);
        int prevSize = previousRules.size();
        int newSize = newRules.size();
        Map<String, Boolean> changeMap = new HashMap<>(Math.max(prevSize, newSize));
        Set<String> newRulesSet = new HashSet<>(newRules);

        for (String prevRule : previousRules) {
            if (newRulesSet.contains(prevRule))
                newRulesSet.remove(prevRule);
            else
                changeMap.put(prevRule, false);
        }

        for (String addRule : newRulesSet)
            changeMap.put(addRule, true);

        return changeMap;
    }
}
