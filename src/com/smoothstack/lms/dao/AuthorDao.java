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
            writer.append(authorO.getName() + ";" + authorO.getId() + ";");
            authorO.getBooks().forEach(e -> {
                try {
                    writer.append(e.getTitle() + ";");
                    writer.append(e.getIsbn() + ";");
                    writer.append(e.getPublisherId() + ";");
                    writer.append(e.getAuthorId() + ";");
                } catch (IOException a) {
                    a.printStackTrace();
                }
            });
            writer.close();
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
                author.setId(splitArray[1]);

                for (int i = 2; i < splitArray.length; i += 4) {
                    Book b = new Book();
                    List<Book> books = new ArrayList<>();
                    b.setTitle(splitArray[i]);
                    b.setIsbn(splitArray[i + 1]);
                    b.setAuthorId(splitArray[i + 2]);
                    b.setPublisherId(splitArray[i + 3]);
                    books.add(b);
                    author.setBooks(books);
                    authorBookMap.put(splitArray[1], author);
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
            FileWriter fr = new FileWriter("./resources/authors.csv", true);
            BufferedWriter writer = new BufferedWriter(fr);
            writer.newLine();
            map.forEach((key,author)->{
                try {
                    writer.append(author.getName() + ";");
                    writer.append(author.getId() + ";");
                    author.getBooks().forEach((book)->{
                        try{
                            writer.append(book.getTitle() + ";");
                            writer.append(book.getIsbn() + ";");
                            writer.append(book.getPublisherId() + ";");
                            writer.append(book.getAuthorId() + ";");
                        }catch (IOException ex2){
                            ex2.printStackTrace();
                        }
                    });
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            });
            writer.newLine();
        } catch (IOException exc1) {
            exc1.printStackTrace();
        }
    }
}