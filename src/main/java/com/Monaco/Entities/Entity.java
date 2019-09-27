package com.Monaco.Entities;

public class Entity {
    String name = "";
    String entityClass = "";
    String type = "";
    int maxHP = -1;
    int currentHP = -1;
    int armorClass = -1;
    int status = -1;
    int initiative = -1;

    @Override
    public String toString() {
        return String.format(name + " - " + entityClass);
    }

}
