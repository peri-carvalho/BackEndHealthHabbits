package com.BackEnd.BackEndHealthHabbits.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum Rule {
    VIEW_PROFILE(1, "Visualizar Perfil"),
    UPDATE_PROFILE(2, "Criar Perfil",Arrays.asList(VIEW_PROFILE));

    private final String label;
    private final int value;
    private final List<Rule> impliedRules;

    // Construtor sem dependências
    Rule(int value, String label) {
        this(value, label, Collections.emptyList());
    }

    // Construtor com dependências
    Rule(int value, String label, List<Rule> impliedRules) {
        this.label = label;
        this.value = value;
        this.impliedRules = impliedRules;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public List<Rule> getImpliedRules() {
        return impliedRules;
    }

    @JsonValue
    public int getId() {
        return value;
    }

    public static Rule fromValue(int value) {
        for (Rule type : Rule.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Rule value: " + value);
    }

    public static Rule fromLabel(String label) {
        for (Rule type : Rule.values()) {
            if (type.getLabel().equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Rule label: " + label);
    }

    public static Rule getByValue(int value) {
        for (Rule rule : Rule.values()) {
            if (rule.getValue() == value) {
                return rule;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    public static List<String> getAllLabels() {
        return Arrays.stream(Rule.values())
                .map(Rule::getLabel)
                .collect(Collectors.toList());
    }
}
