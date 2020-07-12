package com.Monaco.Entities;

public class Entity {
    public enum Status {
        NORMAL,
        BLINDED,
        CHARMED,
        DEAFENED,
        FRIGHTENED,
        GRAPPLED,
        INCAPACITATED,
        INVISIBLE,
        PARALYZED,
        POISONED,
        PRONE,
        RESTRAINED,
        STUNNED,
        UNCONSCIOUS
    }

    public String name;
    public String entityClass;
    public String type;
    public String alignment;
    public String size;
    
    public int maxHP;
    public int currentHP;
    public int armorClass;
    public Status status;
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
        alignment = "ENTITYALIGN";
        maxHP = 0;
        currentHP= 0;
        armorClass= 0;
        status= Status.NORMAL;
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

    public String getCSVLine() {
        throw new UnsupportedOperationException("No CSV entry for Entity");
    }
    
    @Override
    public String toString() {
        return String.format(name + " - " + entityClass);
    }

}
