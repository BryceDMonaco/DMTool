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

    int str;
    int strMod;
    int dex;
    int dexMod;
    int con;
    int conMod;
    int intl;
    int intlMod;
    int wis;
    int wisMod;
    int cha;
    int chaMod;

    @Override
    public String toString() {
        return String.format(name + " - " + entityClass);
    }

}
