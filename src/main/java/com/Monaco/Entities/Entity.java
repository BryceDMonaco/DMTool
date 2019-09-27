package com.Monaco.Entities;

public class Entity {
    public String name;
    public String entityClass;
    public String type;
    public int maxHP;
    public int currentHP;
    public int armorClass;
    public int status;
    public int initiative;

    public int str;
    public int strMod;
    public int dex;
    public int dexMod;
    public int con;
    public int conMod;
    public int intl;
    public int intlMod;
    public int wis;
    public int wisMod;
    public int cha;
    public int chaMod;

    @Override
    public String toString() {
        return String.format(name + " - " + entityClass);
    }

}
