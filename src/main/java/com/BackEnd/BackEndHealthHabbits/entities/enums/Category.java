package com.BackEnd.BackEndHealthHabbits.entities.enums;

public enum Category {
    HIDRATACAO,
    EXERCICIO,
    MEDITAR,
    CAMINHADA,
    SUPLEMENTOS;

    public static Category fromString(String type) {
        for (Category categoryType : Category.values()) {
            if (categoryType.name().equalsIgnoreCase(type)) {
                return categoryType;
            }
        }
        throw new IllegalArgumentException("Invalid document type: " + type);
    }
}
