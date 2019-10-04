package com.Monaco.Entities;

public class Monster extends Entity {
    public String attackOneDamage;
    public String attackTwoDamage;

    public int xp;

    String[] stats;

    public Monster (Monster monster) {
        this(monster.stats);

    }

    public Monster (String[] sentStats) {
        super();

        status = 0;

        stats = sentStats.clone();

        name = stats[0];
        entityClass = stats[0];
        type = stats[1];
        armorClass = Integer.parseInt(stats[5]);
        currentHP = Integer.parseInt(stats[6]);
        maxHP = Integer.parseInt(stats[6]);
        attackOneDamage = stats[8];
        attackTwoDamage = stats[9];
        xp = (stats[10].equals("")) ? -999 : Integer.parseInt(stats[10]);
        str = (stats[11].equals("")) ? -999 : Integer.parseInt(stats[11]);
        strMod = (stats[12].equals("")) ? -999 : Integer.parseInt(stats[12]);
        dex = (stats[13].equals("")) ? -999 : Integer.parseInt(stats[13]);
        dexMod = (stats[14].equals("")) ? -999 : Integer.parseInt(stats[14]);
        con = (stats[15].equals("")) ? -999 : Integer.parseInt(stats[15]);
        conMod = (stats[16].equals("")) ? -999 : Integer.parseInt(stats[16]);
        intl = (stats[17].equals("")) ? -999 : Integer.parseInt(stats[17]);
        intlMod = (stats[18].equals("")) ? -999 : Integer.parseInt(stats[18]);
        wis = (stats[19].equals("")) ? -999 : Integer.parseInt(stats[19]);
        wisMod = (stats[20].equals("")) ? -999 : Integer.parseInt(stats[20]);
        cha = (stats[21].equals("")) ? -999 : Integer.parseInt(stats[21]);
        chaMod = (stats[22].equals("")) ? -999 : Integer.parseInt(stats[22]);

    }

    @Override
    public String toString() {
        return (entityClass.equals(name)) ? entityClass : name + " the " + entityClass;
    }
}
