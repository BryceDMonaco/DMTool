package com.Monaco.Entities.Tools;

import com.Monaco.Entities.Monster;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MonsterParser {
    public  MonsterParser() {
    }

    public static List<Monster> GetMonstersFromCSV (String filename) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

            String[] line;
            List<Monster> monsterList = new ArrayList<>();

            while ((line = csvReader.readNext()) != null) {
                monsterList.add(new Monster(line));
            }

            return monsterList;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
