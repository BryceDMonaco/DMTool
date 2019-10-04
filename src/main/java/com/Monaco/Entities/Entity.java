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

    public Entity () {
        name = "ENTITY";
        entityClass = "ENTITYCLASS";
        type = "ENTITYTYPE";
        maxHP = 0;
        currentHP= 0;
        armorClass= 0;
        status= 0;
        initiative= 0;

        str= 0;
        strMod= 0;
        dex= 0;
        dexMod= 0;
        con= 0;
        conMod= 0;
        intl= 0;
        intlMod= 0;
        wis= 0;
        wisMod= 0;
        cha= 0;
        chaMod= 0;
    }
    
    @Override
    public String toString() {
        return String.format(name + " - " + entityClass);
    }

}
