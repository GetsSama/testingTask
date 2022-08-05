package com.gridnine.testing;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 Билдер собирает сет правил в зависимости от того, какой результат ему выдаст
 контроллер.
*/
class RuleSetBuilder {
    private static Set<Rule> rules;
    private static long numberOfRules;
    private static List<String> previousRules;
    private static List<String> newRules;
    private static RuleSetBuilder thisInstance;
    private RuleSetBuilder(){};

    public static RuleSetBuilder createRuleSetBuilder(List<String> rules){
        if (thisInstance == null) {
            thisInstance = new RuleSetBuilder();
            previousRules = rules;
        }
        newRules = rules;
        return thisInstance;
    }

    private Set<Rule> buildAllRuleSet(List<String> rules) {
        return null;
    }
    private Rule addRule(String ruleName) {
        return RuleFactory.getRule(ruleName);
    }

    //private
}

/*
Контроллер сравнивает новые и старые правила - если листы правил идентичны, вернет true, иначе false;
Метод getDifferenceMap возвращает мапу различий: ключи - различия, значения - действие которое нужно сделать
(заменить на это, добавить, удалить)
 */
class BuilderController {
    //private Map<String, String> ruleDifferences;
    public static boolean isRulesEquals(List<String> previousRules, List<String> newRules) {
        return true;
    }
    public static Map<String, String> getDifferenceMap(List<String> previousRules, List<String> newRules) {
        return null;
    }
}
