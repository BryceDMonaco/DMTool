package com.Monaco.Entities;

public class Monster extends Entity {
    public String attackOneDamage;
    public String attackTwoDamage;

    public String cr;
    public String spellcasting;
    public String sourcePage;
    public String envArctic;
    public String envCoast;
    public String envDesert;
    public String envForest;
    public String envGrassland;
    public String envHill;
    public String envMountain;
    public String envSwamp;
    public String envUnderdark;
    public String envUnderwater;
    public String envUrban;
    public String sourceBook;

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
        alignment = stats[2];
        size = stats[3];
        cr = stats[4];
        armorClass = Integer.parseInt(stats[5]);
        currentHP = Integer.parseInt(stats[6]);
        maxHP = Integer.parseInt(stats[6]);
        spellcasting = stats[7];
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
        sourcePage = stats[23];
        envArctic = stats[24];
        envCoast = stats[25];
        envDesert = stats[26];
        envForest = stats[27];
        envGrassland = stats[28];
        envHill = stats[29];
        envMountain = stats[30];
        envSwamp = stats[31];
        envUnderdark = stats[32];
        envUnderwater = stats[33];
        envUrban = stats[34];
        sourceBook = stats[35];

    }

    @Override
    public String getCSVLine() {
        return name + "," + entityClass + "," + type + "," + status + "," + alignment + "," + size + "," + cr + "," + armorClass + "," + currentHP + "," + maxHP + "," +
                spellcasting + "," + attackOneDamage + "," + attackTwoDamage + "," + xp + "," + str + "," +
                strMod + "," + dex + "," + dexMod + "," + con + "," + conMod + "," + intl + "," + intlMod + "," +
                wis + "," + wisMod + "," + cha + "," + chaMod + "," + sourcePage + "," + envArctic + "," +
                envCoast + "," + envDesert + "," + envForest + "," + envGrassland + "," + envHill + "," +
                envMountain +"," + envSwamp + "," + envUnderdark + "," + envUnderwater + "," + envUrban +
                "," + sourceBook + ",MONSTER";
    }

    @Override
    public String toString() {
        return (entityClass.equals(name)) ? entityClass : name + " the " + entityClass;
    }
}
