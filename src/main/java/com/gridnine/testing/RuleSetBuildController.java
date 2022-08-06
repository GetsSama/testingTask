package com.gridnine.testing;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/*
 Билдер собирает сет правил в зависимости от того, какой результат ему выдаст
 контроллер.
*/
class RuleSetBuilder {
    private Set<Rule> rulesSet;
    private long numberOfRules;
    private List<String> previousRules;
    public RuleSetBuilder(){};

    //Полное построение сета "с нуля"
    private void buildNewRuleSet(List<String> rules) {

    }

    //Добавление одного правила в сет
    private void addRule(String ruleName) {
         rulesSet.add(RuleFactory.getRule(ruleName));
    }

    //Метод, меняющий текущий сет по мапе различий
    private void changeRuleSet(Map<String,String> changeMap){

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
(заменить на это, добавить, удалить)
 */
class BuilderController {
    private static double criticalValue;
    private BuilderController(){};
    public static boolean isRulesEquals(List<String> previousRules, List<String> newRules) {
        return true;
    }

    public static boolean doNeedBuildNewSet(List<String> previousRules, List<String> newRules) {
        return true;
    }
    public static Map<String, String> getChangeMap(List<String> previousRules, List<String> newRules) {
        return null;
    }
}
