package com.smoothstack.lms.dao;

import com.smoothstack.lms.model.Publisher;
import com.sun.javafx.logging.PulseLogger;

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

        Map<String, Publisher> publisherBookMap = new HashMap<String, Publisher>();
        //initiated buffer reader
        try {
            FileInputStream fin = new FileInputStream("./resources/publisher.csv");
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
            String authorLine;
            while ((authorLine = buffReader.readLine()) != null) {
                String[] splitArray = authorLine.split(";");
                Publisher p = new Publisher(splitArray[0],splitArray[1],splitArray[2]);
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
                        writer.append(mapKey + ";" + value.getName()+";"+value.getAddress()+";"+value.getId()+";");
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

    public static void update(String key, Map<String, Publisher> map) {
        Scanner scan = new Scanner(System.in);
        if (map.containsKey(key)) {
            Publisher p = map.get(key);
            System.out.println("What would you like to change?\n" +
                    "(1)Publisher name\n" +
                    "(2)Publisher address\n" +
                    "(3)Publisher id");
            String userChoice = scan.nextLine();
            switch (userChoice){
                case "1":
                    System.out.println("What would you like to change it to?");
                    String changeName = scan.nextLine();
                    p.setName(changeName);
                    map.put(key,p);
                    doUpdate(map);
                    break;
                case"2":
                    System.out.println("What would you like to change it to?");
                    String changeAddress = scan.nextLine();
                    p.setAddress(changeAddress);
                    map.put(key,p);
                    doUpdate(map);
                case"3":
                    System.out.println("What would you like to change it to?");
                    String changeId = scan.nextLine();
                    while(map.containsKey(changeId)){
                        System.out.println("Id already exists");
                        changeId = scan.nextLine();
                    }
                    p.setId("pid-"+changeId);
                    map.remove(key);
                    map.put(changeId,p);
                    doUpdate(map);
            }
        } else {
            System.out.println("Publisher does not exist");
        }
    }


    //will write the new map to the csv
    private static void doUpdate(Map<String,Publisher>map){
        try {
            FileWriter fr = new FileWriter("./resources/publisher.csv");
            BufferedWriter writer = new BufferedWriter(fr);
            map.forEach((mapKey, value0) -> {
                try {
                    writer.append(value0.getName() + ";" + value0.getAddress()+";"+value0.getId()+";");
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

