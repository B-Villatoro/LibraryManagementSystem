package com.smoothstack.lms.dao;

import com.smoothstack.lms.model.Publisher;

import java.io.*;
import java.util.*;

public class PublisherDao {

    public static void add(Publisher publisher) {
        try {
            FileWriter fr = new FileWriter("./resources/publisher.csv", true);
            BufferedWriter writer = new BufferedWriter(fr);
            writer.newLine();
            writer.append(publisher.getAddress() + ";");
            writer.append(publisher.getName() + ";");
            writer.append(publisher.getId() + ";");
            writer.close();
            System.out.println(publisher.getName() + " has been added!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show() {
        File fileName = new File("./resources/publisher.csv");
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Publisher> createMap() {

        Map<String, Publisher> publisherBookMap = new HashMap<>();
        //initiated buffer reader
        try {
            FileInputStream fin = new FileInputStream("./resources/publisher.csv");
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
            String authorLine;
            while ((authorLine = buffReader.readLine()) != null) {
                String[] splitArray = authorLine.split(";");
                Publisher p = new Publisher(splitArray[0], splitArray[1], splitArray[2]);
                publisherBookMap.put(splitArray[2], p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //initiate map
        return publisherBookMap;
    }

    public static void delete(String key, Map<String, Publisher> map) {
        if (map.containsKey(key)) {
            try {
                FileWriter fr = new FileWriter("./resources/publisher.csv");
                BufferedWriter writer = new BufferedWriter(fr);
                map.remove(key);
                map.forEach((mapKey, value) -> {
                    try {
                        writer.append(mapKey + ";");
                        writer.append(value.getName() + ";");
                        writer.append(value.getAddress() + ";");
                        writer.append(value.getId() + ";");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        } else {
            System.out.println("Publisher does not exist");
        }
    }

//updates the csv by writing to it
    public static void update(Map<String, Publisher> map) {
        try {
            FileWriter fr = new FileWriter("./resources/publisher.csv");
            BufferedWriter writer = new BufferedWriter(fr);
            map.forEach((mapKey, value) -> {
                try {
                    writer.append(value.getName() + ";");
                    writer.append(value.getAddress() + ";");
                    writer.append(value.getId() + ";");
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}


