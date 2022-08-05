package com.gridnine.testing;

class SimpleRule implements Rule {
    private static SimpleRule simpleRule;
    private static String name = "simpleRule";
    private SimpleRule() {}

    @Override
    public boolean isAcceptRule(Flight flight) {
        return false;
    }

    public static Rule getInstance() {
        if (simpleRule == null) {
            simpleRule = new SimpleRule();
        }
        return simpleRule;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SimpleRule;
    }

}

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
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public boolean isAcceptRule(Flight flight) {
        return false;
    }
}