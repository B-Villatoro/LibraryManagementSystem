package com.smoothstack.lms.dao;


import java.io.*;
import java.util.*;

import com.smoothstack.lms.model.Author;
import com.smoothstack.lms.model.Book;

public class AuthorDao {

    public static void add(Author authorO) {
        try {
            FileWriter fr = new FileWriter("./resources/authors.csv", true);
            BufferedWriter writer = new BufferedWriter(fr);
            writer.newLine();
            writer.append(authorO.getName() + ";" + authorO.getId());
            authorO.getBooks().forEach(e -> {
                try {
                    writer.append(e.getTitle() + ";");
                    writer.append(e.getIsbn() + ";");
                } catch (IOException a) {
                    a.printStackTrace();
                }
            });
            writer.close();
            System.out.println(authorO.getName() + " has been added!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show() {
        File fileName = new File("./resources/authors.csv");
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.lines().forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Author> createMap() {

        Map<String, Author> authorBookMap = new HashMap<String, Author>();
        //initiated buffer reader
        try {
            FileInputStream fin = new FileInputStream("./resources/authors.csv");
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
            String authorLine;

            while ((authorLine = buffReader.readLine()) != null) {
                String[] splitArray = authorLine.split(";");
                //creating the author object to pass into the map
                Author author = new Author();
                author.setName(splitArray[0]);
                author.setId(splitArray[3]);

                Book b = new Book();
                b.setTitle(splitArray[1]);
                b.setIsbn(splitArray[2]);

                if (authorBookMap.containsKey(splitArray[3])) {
                    //if the author id is already made just add the book to the book list
                    authorBookMap.get(splitArray[0]).getBooks().add(b);
                } else {
                    //else make a new value with a new key
                    List<Book> books = new ArrayList<>();
                    books.add(b);
                    author.setBooks(books);
                    authorBookMap.put(splitArray[0], author);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //initiate map
        return authorBookMap;
    }

    public static void delete(String key, Map<String, Author> map) {
        if (map.containsKey(key)) {
            try {
                FileWriter fr = new FileWriter("./resources/authors.csv");
                BufferedWriter writer = new BufferedWriter(fr);
                map.remove(key);
                map.forEach((mapKey, bookList) -> {
                    map.get(mapKey).getBooks().forEach(e -> {
                        try {
                            String stringBuild = mapKey + ";" + e.getTitle() + ";" + e.getIsbn() + ";" + e.getAuthorId() + ";";
                            writer.append(stringBuild);
                            writer.newLine();
                        } catch (IOException exc0) {
                            exc0.printStackTrace();
                        }
                    });
                });
                writer.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        } else {
            System.out.println("Author does not exist");
        }
    }

    public static void update(Map<String, Author> map) {
        try {
            FileWriter fr = new FileWriter("./resources/authors.csv");
            BufferedWriter writer = new BufferedWriter(fr);
            map.forEach((mapKey, bookList) -> {
                map.get(mapKey).getBooks().forEach(e -> {
                    try {
                        String stringBuild = mapKey + ";" + e.getTitle() + ";" + e.getIsbn() + ";" + e.getAuthorId() + ";";
                        writer.append(stringBuild);
                        writer.newLine();
                    } catch (IOException exc0) {
                        exc0.printStackTrace();
                    }
                });
            });
            writer.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}