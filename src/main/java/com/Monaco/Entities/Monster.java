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
        stats = sentStats;

        name = sentStats[0];
        entityClass = sentStats[0];
        type = sentStats[1];
        armorClass = Integer.parseInt(sentStats[5]);
        currentHP = Integer.parseInt(sentStats[6]);
        maxHP = Integer.parseInt(sentStats[6]);
        attackOneDamage = sentStats[8];
        attackTwoDamage = sentStats[9];
        xp = (sentStats[10].equals("")) ? -999 : Integer.parseInt(sentStats[10]);
        str = (sentStats[11].equals("")) ? -999 : Integer.parseInt(sentStats[11]);
        strMod = (sentStats[12].equals("")) ? -999 : Integer.parseInt(sentStats[12]);
        dex = (sentStats[13].equals("")) ? -999 : Integer.parseInt(sentStats[13]);
        dexMod = (sentStats[14].equals("")) ? -999 : Integer.parseInt(sentStats[14]);
        con = (sentStats[15].equals("")) ? -999 : Integer.parseInt(sentStats[15]);
        conMod = (sentStats[16].equals("")) ? -999 : Integer.parseInt(sentStats[16]);
        intl = (sentStats[17].equals("")) ? -999 : Integer.parseInt(sentStats[17]);
        intlMod = (sentStats[18].equals("")) ? -999 : Integer.parseInt(sentStats[18]);
        wis = (sentStats[19].equals("")) ? -999 : Integer.parseInt(sentStats[19]);
        wisMod = (sentStats[20].equals("")) ? -999 : Integer.parseInt(sentStats[20]);
        cha = (sentStats[21].equals("")) ? -999 : Integer.parseInt(sentStats[21]);
        chaMod = (sentStats[22].equals("")) ? -999 : Integer.parseInt(sentStats[22]);

    }

    @Override
    public String toString() {
        return String.format(entityClass);
    }
}
