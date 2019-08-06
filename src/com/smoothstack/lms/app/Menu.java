package com.smoothstack.lms.app;

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

public class Menu {
    private String name;
    private String isbn;
    private String title;
    private String address;
    private String authorId;
    private String publisherId;
    Scanner scan = new Scanner(System.in);
    private Map<String, List<Book>> authorMap;
    private Map<String, Publisher> publisherMap;
    private Map<String, Book> bookMap;

    public Menu() {
        authorMap = AuthorDao.createMap();//create my map on startup
        publisherMap = PublisherDao.createMap();
        bookMap = BookDao.createMap();

        System.out.println("Hello welcome to BookBook!\nHere you will be able to organize your books!");
        mainMenu();
    }

    private void mainMenu() {
        System.out.println("What would you like to do today?\n" +
                "(1)View the library\n" +
                "(2)Add to the library\n" +
                "(3)Update an existing item\n" +
                "(4)Remove from the library\n" +
                "(0)Close Program");

        String optionSelect = scan.nextLine();
        switch (optionSelect) {
            case "1":
                handleShow();
                break;

            case "2":
                handleAdd();
                break;

            case "3":
                handleUpdate();
                break;

            case "4":
                handleDelete();
                break;

            case"0":
                System.out.println("GoodBye!");
                System.exit(0);
                break;

            default:
                System.out.println("Please enter valid option");
                mainMenu();
        }
    }

    //create a book and add isbn- to number then adds to book list
    private List<Book> createBook(List<Book> bookL,String authorId) {
        System.out.println("Please enter the title written by said author");
        title = scan.nextLine();
        System.out.println("Please enter the ISBN");
        isbn = "isbn-" + scan.nextLine();
        System.out.println("Please enter the publisher Id");
        publisherId = "isbn-" + scan.nextLine();
        bookL.add(new Book(title, isbn,publisherId,authorId));
        System.out.println("Would you like to add another? y/n");
        String yn = scan.nextLine();
        yn = yn.toLowerCase();
        if (yn.equals("y") || yn.equals("yes")) {
            return createBook(bookL,authorId);
        }
        return bookL;
    }

    //prompts back to main menu
    private void backToMenu() {
        System.out.println("Would you like to go back to main menu? y/n");
        String yn = scan.nextLine();
        yn = yn.toLowerCase();
        if (yn.equals("y") || yn.equals("yes")) {
            mainMenu();
        } else System.exit(0);
    }

    private void handleAdd() {
        System.out.println("What item would you like to add?\n" +
                "(1)Author\n" +
                "(2)Book\n" +
                "(3)Publisher");
        String opt = scan.nextLine();
        opt = opt.toLowerCase();
        switch (opt) {
            case "1":
            case "author":
                System.out.println("Please enter the author name you would like to add");
                name = scan.nextLine();
                System.out.println("Please enter author Id");
                authorId = scan.nextLine();
                authorId = "aid-"+authorId;
                if (authorMap.containsKey(authorId)) {
                    System.out.println("Author id already exists");
                    backToMenu();
                } else {
                    List<Book> bookList = new ArrayList<>();
                    bookList = createBook(bookList,authorId);
                    AuthorDao.add(new Author(name, bookList,authorId));
                    authorMap = AuthorDao.createMap();
                    backToMenu();
                }
                break;
            case "2":
            case "book":
                System.out.println("Please enter the title name you would like to add");
                name = scan.nextLine();
                System.out.println("Please enter Isbn ");
                isbn = scan.nextLine();
                isbn ="isbn-"+isbn;
                if (bookMap.containsKey(isbn)) {
                    System.out.println("ISBN already exists");
                    backToMenu();
                } else {
                    System.out.println("Please enter the author id");
                    authorId = scan.nextLine();
                    System.out.println("Please enter the publisher id");
                    publisherId = scan.nextLine();
                    BookDao.add(new Book(name,(isbn),authorId,publisherId));
                    bookMap = BookDao.createMap();
                    backToMenu();
                }
                break;

            case "3":
            case "publisher":
                System.out.println("Please enter the publisher name you would like to add");
                name = scan.nextLine();
                System.out.println("Please enter the publisher Id");
                publisherId = scan.nextLine();
                publisherId = "pid-"+publisherId;
                if (publisherMap.containsKey(publisherId)) {
                    System.out.println("Publisher id already exists");
                    backToMenu();
                } else {
                    System.out.println("Please enter Publisher address");
                    address = scan.nextLine();
                    PublisherDao.add(new Publisher(name,address,publisherId));
                    publisherMap = PublisherDao.createMap();
                    backToMenu();
                }

                break;

            default:
                System.out.println("Wrong Input");
                handleAdd();
                break;
        }

    }

    private void handleShow() {
        System.out.println("What item would you like to see?\n" +
                "(1)Author\n" +
                "(2)Book\n" +
                "(3)Publisher");
        String opt = scan.nextLine();
        opt = opt.toLowerCase();
        switch (opt) {
            case "1":
            case "author":
                AuthorDao.show();
                backToMenu();
                break;
            case "2":
            case "book":
                BookDao.show();
                backToMenu();
                break;

            case "3":
            case "publisher":
                PublisherDao.show();
                backToMenu();
                break;

            default:
                System.out.println("Wrong Input");
                handleShow();
                break;

        }

    }
    private void handleUpdate(){
        System.out.println("What item would you like to update?\n" +
                "(1)Author\n" +
                "(2)Book\n" +
                "(3)Publisher");
        String opt = scan.nextLine();
        opt = opt.toLowerCase();
        switch (opt) {
            case "1":
            case "author":
                System.out.println("Enter the author id that you would like to update");
                String updateKey = scan.nextLine();
                AuthorDao.update(updateKey, authorMap);
                authorMap = AuthorDao.createMap();
                backToMenu();
                break;
            case "2":
            case "book":
                System.out.println("Enter the book isbn that you would like to update");
                String updateKey0 = scan.nextLine();
                BookDao.update(updateKey0, bookMap);
                bookMap = BookDao.createMap();
                backToMenu();
                break;

            case "3":
            case "publisher":
                System.out.println("Enter the publisher id thay you would like to update");
                String updateKey1 = scan.nextLine();
                PublisherDao.update(updateKey1, publisherMap);
                publisherMap = PublisherDao.createMap();
                backToMenu();
                break;

            default:
                System.out.println("Wrong Input");
                handleShow();
                break;

        }
    }

    private void handleDelete(){
        System.out.println("What item would you like to delete?\n" +
                "(1)Author\n" +
                "(2)Book\n" +
                "(3)Publisher");
        String opt = scan.nextLine();
        opt = opt.toLowerCase();
        switch (opt) {
            case "1":
            case "author":
                System.out.println("Write the author you would like to delete?");
                String deleteKey = scan.nextLine();
                AuthorDao.delete(deleteKey, authorMap);
                backToMenu();
                break;
            case "2":
            case "book":
                System.out.println("Write the publisher you would like to delete?");
                String deleteKey0 = scan.nextLine();
                BookDao.delete(deleteKey0, bookMap);
                backToMenu();
                break;

            case "3":
            case "publisher":
                System.out.println("Write the publisher you would like to update?");
                String deleteKey1 = scan.nextLine();
                PublisherDao.delete(deleteKey1, publisherMap);
                backToMenu();
                break;

            default:
                System.out.println("Wrong Input");
                handleShow();
                break;
        }
    }

}
