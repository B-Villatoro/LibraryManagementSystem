package com.smoothstack.lms.dao;

import com.smoothstack.lms.model.Book;

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
                writer.append(book.getAuthorId() + ";");
                writer.append(book.getPublisherId() + ";");
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

        Map<String, Book> bookMap = new HashMap<>();
        //initiated buffer reader
        try {
            FileInputStream fin = new FileInputStream("./resources/book.csv");
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
            String authorLine;
            while ((authorLine = buffReader.readLine()) != null) {
                String[] splitArray = authorLine.split(";");
                Book b = new Book();

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
                        writer.append(value.getTitle() + ";");
                        writer.append(value.getIsbn() + ";");
                        writer.append(value.getAuthorId() + ";");
                        writer.append(value.getPublisherId() + ";");
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


    public static void update(Map<String, Book> map) {
        try {
            FileWriter fr = new FileWriter("./resources/book.csv");
            BufferedWriter writer = new BufferedWriter(fr);
            map.forEach((mapKey, value) -> {

                try {
                    writer.append(map.get(mapKey).getTitle() + ";");
                    writer.append(map.get(mapKey).getIsbn() + ";");
                    writer.append(map.get(mapKey).getAuthorId() + ";");
                    writer.append(map.get(mapKey).getPublisherId() + ";");
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

