package com.ria.process.parameter;

public class ParameterDefinition {
    private String name;
    private ParameterSourceType sourceType;
    private ValueExtractor valueExtractor;

    public ParameterDefinition(String name, ParameterSourceType sourceType, ValueExtractor valueExtractor) {
        this.name = name;
        this.sourceType = sourceType;
        this.valueExtractor = valueExtractor;
    }

    public String getName() {
        return name;
    }

    public ParameterSourceType getSourceType() {
        return sourceType;
    }

    public ValueExtractor getValueExtractor() {
        return valueExtractor;
    }
}
