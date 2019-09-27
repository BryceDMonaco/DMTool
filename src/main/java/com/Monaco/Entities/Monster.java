package com.Monaco.Entities;

public class Monster extends Entity {
    String attackOneDamage;
    String attackTwoDamage;

    public Monster (String[] stats) {
        name = stats[0];
        entityClass = stats[0];
        type = stats[1];
        armorClass = Integer.parseInt(stats[5]);
        currentHP = Integer.parseInt(stats[6]);
        maxHP = Integer.parseInt(stats[6]);
        attackOneDamage = stats[8];
        attackTwoDamage = stats[9];

    }

    @Override
    public String toString() {
        return String.format(entityClass);
    }
}
