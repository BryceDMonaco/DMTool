package com.Monaco.Entities.Tools;

import com.Monaco.Entities.Monster;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MonsterParser {
    public  MonsterParser() {
    }

    public static List<Monster> GetMonstersFromCSV (String filename) {
        try {
            InputStream in = MonsterParser.class.getResourceAsStream(filename);
            Reader reader = new BufferedReader(new InputStreamReader(in));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

            String[] line;
            List<Monster> monsterList = new ArrayList<>();

            while ((line = csvReader.readNext()) != null) {
                monsterList.add(new Monster(line));
            }

            return monsterList;

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();

            System.out.println("here");
        }

        return null;
    }

    public static List<Monster> ReadSavedFileMonsters (File file) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(file.getPath()));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

            String[] line;
            List<Monster> monsterList = new ArrayList<>();

            while ((line = csvReader.readNext()) != null) {
                if (line[39].equals("MONSTER")) {
                    Monster monster = new Monster(saveFileLineToStandardLine(line));

                    monster.name = line[0];
                    monster.currentHP = Integer.parseInt(line[8]);

                    monsterList.add(monster);
                }
            }

            return  monsterList;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String[] saveFileLineToStandardLine (String[] line) {
        return new String[]{
                line[1], line[2], line[4], line[5], line[6], line[7], line[9], line[10], line[11], line[12], line[13], line[14], line[15], line[16], line[17], line[18], line[19], line[20], line[21], line[22], line[23], line[24], line[25], line[26], line[27], line[28], line[29], line[30], line[31], line[32], line[33], line[34], line[35], line[36], line[37], line[38]
        };
    }
}
