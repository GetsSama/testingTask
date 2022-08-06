package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.Objects;

//Класс-шаблон для простых правил
abstract class SimpleRuleImpl implements Rule {
    private String name;

    public SimpleRuleImpl(String name) {
        this.name = name;
    }
    @Override
    public abstract boolean isAcceptRule(Flight flight);

    @Override
    public int hashCode() {
        System.out.println(this.name);
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

}

//Класс-шабон для правил с атрибутами
class AttributedRule implements Rule {
    private int attribute;

    private AttributedRule(){};

    private AttributedRule(int attr){
        this.attribute = attr;
    }

    public static AttributedRule getInstance(int attribute){
        return new AttributedRule(attribute);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.attribute);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this ? ((AttributedRule) obj).attribute == this.attribute : false;       ///!!!!!!!!!!!
    }

    @Override
    public boolean isAcceptRule(Flight flight) {
        return false;
    }
}


/*
    Реализации правил:
    Если правило исключающее, метод isAcceptRule должен возвращать семнатически обратное значение,
    если правило совпадает семантически с методом isAcceptRule, то он должен возвращать прямое заначение.
*/

class DepartureBeforeNow extends SimpleRuleImpl{
    private static DepartureBeforeNow thisInstance;
    private DepartureBeforeNow() {
        super(DepartureBeforeNow.class.getSimpleName());
    };
    public static DepartureBeforeNow getInstance(){
        if (thisInstance == null)
            thisInstance = new DepartureBeforeNow();
        return thisInstance;
    }
    @Override
    public boolean isAcceptRule(Flight flight) {
        Objects.requireNonNull(flight);
        LocalDateTime now = LocalDateTime.now();
        Segment firstSeg = flight.getSegments().get(0);

        return !firstSeg.getDepartureDate().isBefore(now);  //Если вылет до текущей даты, вернет false, чтобы исключить полет из списка.
    }
}