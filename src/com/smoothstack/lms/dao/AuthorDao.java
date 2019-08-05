package com.smoothstack.lms.dao;


import java.io.*;
import java.util.*;
import com.smoothstack.lms.model.Author;
import com.smoothstack.lms.model.Book;


public class AuthorDao {

    Scanner scan = new Scanner(System.in);

    public static void add(Author authorO) {
        try {
            FileWriter fr = new FileWriter("./resources/booksAndAuthors.csv", true);
            BufferedWriter writer = new BufferedWriter(fr);
            writer.newLine();
            writer.append(authorO.getName() + ";");
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
        File fileName = new File("./resources/booksAndAuthors.csv");
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            br.lines().forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<Book>> createMap() {

        Map<String, List<Book>> authorBookMap = new HashMap<String, List<Book>>();
        //initiated buffer reader
        try {
            FileInputStream fin = new FileInputStream("./resources/booksAndAuthors.csv");
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
            String authorLine;

            while ((authorLine = buffReader.readLine()) != null) {
                String[] splitArray = authorLine.split(";");

                Book b = new Book();
                b.setTitle(splitArray[1]);
                b.setIsbn(splitArray[2]);

                if (authorBookMap.containsKey(splitArray[0])) {
                    authorBookMap.get(splitArray[0]).add(b);
                } else {
                    List<Book> books = new ArrayList<>();
                    books.add(b);
                    authorBookMap.put(splitArray[0], books);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //initiate map

        return authorBookMap;
    }

    public static void delete(String key, Map<String, List<Book>> map) {

        if (map.containsKey(key)) {
            try {
                FileWriter fr = new FileWriter("./resources/booksAndAuthors.csv");
                BufferedWriter writer = new BufferedWriter(fr);
                map.remove(key);
                map.forEach((mapKey, bookList) -> {
                    map.get(mapKey).forEach(e -> {
                        try {
                            String stringBuild = mapKey + ";" + e.getTitle() + ";" + e.getIsbn() + ";";
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

    public void update(String key, Map<String, List<Book>> map) {
        if (map.containsKey(key)) {

            List<Book> newBooks = map.get(key);

            System.out.println("What would you like to change?");
            map.get(key).forEach(e -> {
                System.out.println(e.getTitle() + " " + e.getIsbn());
            });
            String userChoice = scan.nextLine();
            int listIndex = 99999;
            for (int i = 0; i < newBooks.size(); i++) {
                System.out.println(newBooks.get(i).getTitle());
                if (newBooks.get(i).getTitle().equals(userChoice)) {
                    listIndex = i;
                    System.out.println("What Would you like to change it to?");
                    String changeTo = scan.nextLine();
                    newBooks.get(listIndex).setTitle(changeTo);
                    map.get(key).set(listIndex, newBooks.get(listIndex));
                    break;
                }
            }
            if (listIndex == 99999){
                System.out.println("\nNo match try again\n");
                update(key,map);
            }
            else{
                try {
                    FileWriter fr = new FileWriter("./resources/booksAndAuthors.csv");
                    BufferedWriter writer = new BufferedWriter(fr);
                    map.forEach((mapKey, bookList) -> {
                        map.get(mapKey).forEach(e -> {
                            try {
                                String stringBuild = mapKey + ";" + e.getTitle() + ";" + e.getIsbn() + ";";
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
        } else {
            System.out.println("Author does not exist");
        }
    }
}
