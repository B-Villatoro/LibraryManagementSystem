package com.smoothstack.lms.dao;

import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.Publisher;


import java.io.*;
import java.util.*;

public class BookDao {

    public static void add(Book book) {
        try {
            FileWriter fr = new FileWriter("./resources/book.csv", true);
            BufferedWriter writer = new BufferedWriter(fr);
            writer.newLine();
            try {
                writer.append(book.getTitle() + ";");
                writer.append(book.getIsbn() + ";");
                writer.append(book.getPublisherId() + ";");
                writer.append(book.getAuthorId() + ";");
            } catch (IOException a) {
                a.printStackTrace();
            }
            writer.close();
            System.out.println(book.getTitle() + " has been added!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show() {
        System.out.println("Start 0");
        File fileName = new File("./resources/book.csv");
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Book> createMap() {

        Map<String, Book> bookMap = new HashMap<String, Book>();
        //initiated buffer reader
        try {
            Book b = new Book();
            FileInputStream fin = new FileInputStream("./resources/book.csv");
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
            String authorLine;
            while ((authorLine = buffReader.readLine()) != null) {
                String[] splitArray = authorLine.split(";");
                b.setTitle(splitArray[0]);
                b.setIsbn(splitArray[1]);
                b.setAuthorId(splitArray[2]);
                b.setPublisherId(splitArray[3]);
                bookMap.put(b.getIsbn(), b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //initiate map
        return bookMap;
    }

    public static void delete(String key, Map<String, Book> map) {
        if (map.containsKey(key)) {
            try {
                FileWriter fr = new FileWriter("./resources/book.csv");
                BufferedWriter writer = new BufferedWriter(fr);
                map.remove(key);
                map.forEach((mapKey, value) -> {
                    try {
                        writer.append(mapKey + ";" + value.getTitle() + ";" +
                                value.getIsbn() + ";" + value.getAuthorId() + ";" + value.getPublisherId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        } else {
            System.out.println("book does not exist");
        }
    }


    public static void update(String key, Map<String, Book> map) {
        Scanner scan = new Scanner(System.in);
        if (map.containsKey(key)) {
            Book b = map.get(key);
            System.out.println("What would you like to change?\n" +
                    "(1)Title\n" +
                    "(2)Isbn\n" +
                    "(3)Author id\n" +
                    "(4)Publisher id");
            String userChoice = scan.nextLine();
            switch (userChoice) {
                case "1":
                    System.out.println("What would you like to change it to?");
                    String changeTitle = scan.nextLine();
                    b.setTitle(changeTitle);
                    map.put(key, b);
                    doUpdate(map);
                    break;
                case "2":
                    System.out.println("What would you like to change it to?");
                    String changeIsbn = scan.nextLine();
                    while (map.containsKey(changeIsbn)) {
                        System.out.println("ISBN already exists");
                        changeIsbn = scan.nextLine();
                    }
                    b.setIsbn("isbn-" + changeIsbn);
                    map.remove(key);
                    map.put(changeIsbn, b);
                    doUpdate(map);
                    break;

                case "3":
                    System.out.println("What would you like to change it to?");
                    String changeAid = scan.nextLine();
                    b.setAuthorId("aid-" + changeAid);
                    map.put(changeAid, b);
                    doUpdate(map);
                    break;

                case "4":
                    System.out.println("What would you like to change it to?");
                    String changePid = scan.nextLine();
                    b.setPublisherId("pid-" + changePid);
                    map.put(changePid, b);
                    doUpdate(map);
                    break;

                default:
                    break;

            }
        } else {
            System.out.println("Publisher does not exist");
        }
    }

    private static void doUpdate(Map<String, Book> map) {
        try {
            FileWriter fr = new FileWriter("./resources/publisher.csv");
            BufferedWriter writer = new BufferedWriter(fr);
            map.forEach((mapKey, value0) -> {
                try {
                    writer.append(value0.getTitle() + ";" + value0.getIsbn() + ";" +
                            value0.getAuthorId() + ";" +
                            value0.getPublisherId() + ";");
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

