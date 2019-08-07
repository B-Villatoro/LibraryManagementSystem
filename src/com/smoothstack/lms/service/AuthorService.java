package com.smoothstack.lms.service;

import com.smoothstack.lms.app.Menu;
import com.smoothstack.lms.dao.AuthorDao;
import com.smoothstack.lms.dao.BookDao;
import com.smoothstack.lms.dao.PublisherDao;
import com.smoothstack.lms.model.Author;
import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AuthorService {

    //method creates book and returns it to the list. Needed for adding and author with a list of books
    public static List<Book> createBook(List<Book> bookL, String authorId) {
        String isbn;
        String title;
        String publisherId;
        String yn;
        Scanner scan = new Scanner(System.in);
        Map<String, Publisher> publisherMap = PublisherDao.createMap();
        Map<String, Book> bookMap = BookDao.createMap();

        System.out.println("Please enter the ISBN of the book written by the author");
        isbn = "isbn-" + scan.nextLine();
        if (bookMap.containsKey(isbn)) {
            System.out.println("That book already exists, would you like to try again?");
            yn = scan.nextLine();
            if (yn.equalsIgnoreCase("y") || yn.equalsIgnoreCase("yes")) {
                return createBook(bookL, authorId);
            } else Menu.mainMenu();
        }

        System.out.println("Please enter the publisher Id of the book");
        publisherId = "pid-" + scan.nextLine();
        if (publisherMap.containsKey(publisherId)) {

            System.out.println("Please enter the title written by said author");
            title = scan.nextLine();

            bookL.add(new Book(title, isbn, authorId, publisherId));
            BookDao.add(new Book(title, isbn, authorId, publisherId));

            System.out.println("Would you like to add another? y/n");
            yn = scan.nextLine();
            yn = yn.toLowerCase();

            if (yn.equalsIgnoreCase("y") || yn.equalsIgnoreCase("yes")) {
                return createBook(bookL, authorId);
            }
        } else {
            System.out.println("Publisher does not exist, please add publisher first");
            PublisherService.addPublisher(publisherId);
            System.out.println("lets try this again");
            return createBook(bookL,authorId);
        }

        return bookL;
    }
//Prompt users to fulfill requirements for the dao
    public static void addAuthor() {
        Map<String, Author> authorMap = AuthorDao.createMap();
        Scanner scan = new Scanner(System.in);
        String name;
        String authorId;

        System.out.println("Please enter author Id");
        authorId = scan.nextLine();
        authorId = "aid-" + authorId;

        if (authorMap.containsKey(authorId)) {
            System.out.println("Author id already exists");
        } else {
            System.out.println("Please enter the author name you would like to add");
            name = scan.nextLine();

            List<Book> bookList = new ArrayList<>();
            bookList = createBook(bookList, authorId);

            AuthorDao.add(new Author(name, bookList, authorId));
            System.out.println("Author added!");
        }
    }

    public static void addAuthor(String authorId,String title,String isbn,String publisherId){
        System.out.println("Please enter the author name you would like to add");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(title,isbn,authorId,publisherId));
        System.out.println("Would you like to add more under this author? y/n");
        String userAnswer = scan.nextLine();
        if(userAnswer.equalsIgnoreCase("y")||userAnswer.equalsIgnoreCase("yes")){
            bookList = createBook(bookList, authorId);
        }else{
            AuthorDao.add(new Author(name, bookList, authorId));
        }
    }

    public static void updateAuthor() {
        Map<String, Author> authorMap = AuthorDao.createMap();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the Author Id you would like to update");
        String authorKey = scan.nextLine();
        authorKey = "aid-" + authorKey;

        if (authorMap.containsKey(authorKey)) {
            Author a = authorMap.get(authorKey);
            System.out.println("What would you like to change?\n" +
                    "(1)Author name\n" +
                    "(2)Author Id\n");
            String userChoice = scan.nextLine();
            switch (userChoice) {
                case "1":
                    System.out.println("What would you like to change it to?");
                    String changeName = scan.nextLine();
                    a.setName(changeName);
                    authorMap.put(authorKey, a);
                    AuthorDao.update(authorMap);
                    break;

                case "2":
                    System.out.println("What would you like to change it to?");
                    String changeAid = scan.nextLine();
                    changeAid = "aid-" + changeAid;
                    while (authorMap.containsKey(changeAid)) {
                        System.out.println("Author id already exists, please try again");
                        changeAid = scan.nextLine();
                        changeAid = "aid-" + changeAid;
                    }

                    Map<String, Book> bookMap = BookDao.createMap();

                    String finalChangeAid = changeAid;
                    String finalAuthorKey = authorKey;
                    bookMap.forEach((key, book) -> {
                        if (book.getAuthorId().equalsIgnoreCase(authorMap.get(finalAuthorKey).getId())) {
                            book.setAuthorId(finalChangeAid);
                        }
                    });
                    a.setId(changeAid);
                    authorMap.remove(authorKey);
                    authorMap.put(changeAid, a);
                    AuthorDao.update(authorMap);
                    BookDao.update(bookMap);
                    break;
            }

        } else {
            System.out.println("Author Id does not exist");
        }
    }
    public static void deleteAuthor() {
        Map<String, Author> authorMap = AuthorDao.createMap();
        Map<String, Book> bookMap = BookDao.createMap();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the author id you would like to delete");
        String deleteKey = scan.nextLine();
        deleteKey = "aid" + deleteKey;
        String finalDeleteKey = deleteKey;

        if (authorMap.containsKey(deleteKey)) {
            bookMap.forEach((key, book) -> {
                if (book.getAuthorId().equalsIgnoreCase(authorMap.get(finalDeleteKey).getId())) {
                    BookDao.delete(key, bookMap);
                }
            });
            AuthorDao.delete(deleteKey, authorMap);
        } else {
            System.out.println("Author does not exist");
        }

    }
}
