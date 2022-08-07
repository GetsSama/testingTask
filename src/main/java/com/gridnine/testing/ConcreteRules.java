package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;

//Класс-шаблон для простых правил
abstract class SimpleRuleImpl implements Rule {
    private String name;
    SimpleRuleImpl(){
        this.name = this.getClass().getSimpleName();
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
abstract class AttributedRule implements Rule {
    protected Object attribute;
    private String name;

    public AttributedRule(Object attr){
        this.attribute = attr;
        this.name = this.getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        System.out.println(name + " attr: " + attribute);
        return attribute.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttributedRule)
            this.attribute.equals(((AttributedRule) obj).attribute);
        return false;
    }
    @Override
    public abstract boolean isAcceptRule(Flight flight);
}


/*
    Реализации правил:
    Если правило исключающее, метод isAcceptRule должен возвращать семнатически обратное значение,
    если правило совпадает семантически с методом isAcceptRule, то он должен возвращать прямое значение.
*/

//Вылет до текущего момента времени
class DepartureBeforeNow extends SimpleRuleImpl{
    private static DepartureBeforeNow thisInstance;
    private DepartureBeforeNow() {
        super();
    };
    public static Rule getInstance(){
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

//Имеются сегменты с датой прилета раньше даты вылета
class ArrivedBeforeDeparture extends SimpleRuleImpl{
    private static ArrivedBeforeDeparture thisInstance;
    private ArrivedBeforeDeparture() { super(); }
    public static Rule getInstance() {
        if (thisInstance == null)
            thisInstance = new ArrivedBeforeDeparture();
        return thisInstance;
    }
    @Override
    public boolean isAcceptRule(Flight flight) {
        Objects.requireNonNull(flight);
        for (Segment segm : flight.getSegments()) {
            if (segm.getArrivalDate().isBefore(segm.getDepartureDate()))
                return false;
        }
        return true;
    }
}

//Общее время на земле превышает два часа
class EarthTimeLess extends AttributedRule{
    private EarthTimeLess(String hours){super(hours);}
    public static Rule getInstance(String attribute){
        return new EarthTimeLess(Objects.requireNonNull(attribute));
    }
    @Override
    public boolean isAcceptRule(Flight flight) {
        if (flight.getSegments().size() <= 1)
            return true;

        int hoursLess = Integer.parseInt((String) this.attribute);
        List<Segment> segments = flight.getSegments();
        int numberSegments = segments.size();
        double countTime = 0;

        for (int i=0; i<numberSegments-1; i++) {
            LocalDateTime segBefore = segments.get(i).getArrivalDate();
            LocalDateTime segAfter = segments.get(i+1).getDepartureDate();
            if (segBefore.isAfter(segAfter))
                return false;

            LocalDateTime delta;
            if (segBefore.getDayOfYear() == segAfter.getDayOfYear()) {
                delta = segAfter.minusHours(segBefore.getHour()).minusMinutes(segBefore.getMinute());
            } else {
                delta = segAfter.minusDays(1).minusHours(segBefore.getHour()).minusMinutes(segBefore.getMinute());
            }
            countTime += delta.getHour() + delta.getMinute()/60;
        }

        return Double.compare(hoursLess, countTime)>=0;
    }
}