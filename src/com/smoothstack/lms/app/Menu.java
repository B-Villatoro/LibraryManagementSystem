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

    public Menu() {
        System.out.println("Hello welcome to BookBook!\nHere you will be able to organize your books!");
        mainMenu();
    }

    private static void mainMenu() {
        Scanner scan = new Scanner(System.in);
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

            case "0":
                System.out.println("GoodBye!");
                System.exit(0);
                break;

            default:
                System.out.println("Please enter valid option");
                mainMenu();
        }
    }

    //method creates book and returns it to the list. Needed for adding and author with a list of books
    private static List<Book> createBook(List<Book> bookL, String authorId) {
        String isbn;
        String title;
        String publisherId;
        String yn;
        Scanner scan = new Scanner(System.in);
        Map<String, Publisher> publisherMap = PublisherDao.createMap();
        Map<String, Book> bookMap = BookDao.createMap();

        System.out.println("Please enter the ISBN");
        isbn = "isbn-" + scan.nextLine();
        if (bookMap.containsKey(isbn)) {
            System.out.println("That book already exists, would you like to try again?");
            yn = scan.nextLine();
            if (yn.equalsIgnoreCase("y") || yn.equalsIgnoreCase("yes")) {
                return createBook(bookL, authorId);
            } else mainMenu();
        }

        System.out.println("Please enter the publisher Id");
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
            System.out.println("Publisher does not exist, please add");
            handleAddPublisher();

        }

        return bookL;
    }

    //prompts back to main menu
    private static void backToMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to go back to main menu? y/n");
        String yn = scan.nextLine();
        yn = yn.toLowerCase();
        if (yn.equals("y") || yn.equals("yes")) {
            mainMenu();
        } else System.exit(0);
    }

    private static void handleAdd() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What item would you like to add?\n" +
                "(1)Author\n" +
                "(2)Book\n" +
                "(3)Publisher");
        String opt = scan.nextLine();
        opt = opt.toLowerCase();
        switch (opt) {
            case "1":
            case "author":
                handleAddAuthor();
                backToMenu();
                break;
            case "2":
            case "book":
                handleAddBook();
                backToMenu();
                break;

            case "3":
            case "publisher":
                handleAddPublisher();
                backToMenu();
                break;

            default:
                System.out.println("Wrong Input");
                handleAdd();
                break;
        }

    }

    private static void handleShow() {
        Scanner scan = new Scanner(System.in);
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

    private static void handleUpdate() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What item would you like to update?\n" +
                "(1)Author\n" +
                "(2)Book\n" +
                "(3)Publisher");
        String opt = scan.nextLine();
        opt = opt.toLowerCase();
        switch (opt) {
            case "1":
            case "author":
                handleUpdateAuthor();
                backToMenu();
                break;

            case "2":
            case "book":
                handleUpdateBook();
                backToMenu();
                break;

            case "3":
            case "publisher":
                handleUpdatePublisher();
                backToMenu();
                break;

            default:
                System.out.println("Wrong Input");
                handleUpdate();
                break;

        }
    }

    private static void handleDelete() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What item would you like to delete?\n" +
                "(1)Author\n" +
                "(2)Book\n" +
                "(3)Publisher");
        String opt = scan.nextLine();
        opt = opt.toLowerCase();
        switch (opt) {
            case "1":
            case "author":
                handleDeleteAuthor();
                backToMenu();
                break;
            case "2":
            case "book":
                handleDeleteBook();
                backToMenu();
                break;

            case "3":
            case "publisher":
                Map<String, Publisher> publisherMap = PublisherDao.createMap();
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

    private static void handleAddAuthor() {
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
        }
    }

    private static void handleAddBook() {
        Map<String, Book> bookMap = BookDao.createMap();
        Scanner scan = new Scanner(System.in);
        String title;
        String isbn;
        String authorId;
        String publisherId;

        System.out.println("Please enter Isbn ");
        isbn = scan.nextLine();
        isbn = "isbn-" + isbn;

        //if isbn already exists, book exists
        if (bookMap.containsKey(isbn)) {
            System.out.println("ISBN already exists, returning to main menu");
        } else {
            //prompt for pid and check if exists
            Map<String, Publisher> publisherMap = PublisherDao.createMap();

            System.out.println("Please enter the title name you would like to add");
            title = scan.nextLine();

            System.out.println("Please enter the publisher id");
            publisherId = scan.nextLine();
            publisherId = "pid" + publisherId;

            if (publisherMap.containsKey(publisherId)) {
                //prompt for aid and check if exists
                Map<String, Author> authorMap = AuthorDao.createMap();
                System.out.println("Please enter the author id");
                authorId = scan.nextLine();
                authorId = "aid-" + authorId;
                if (authorMap.containsKey(publisherId)) {
                    //all passes create book
                    BookDao.add(new Book(title, (isbn), authorId, publisherId));
                    authorMap.get(authorId).getBooks().add(new Book(title, (isbn), authorId, publisherId));
                    backToMenu();
                } else {
                    //if author does not exist add the book through author handler
                    System.out.println("Author does not exist, creating author");
                    handleAddAuthor();
                }

            } else {
                //if pid doesn't exist make it exist
                System.out.println("publisher does not exist...\n" +
                        "Creating publisher");
                handleAddPublisher();
                handleAddBook();
            }
        }//end big else
    }

    private static void handleAddPublisher() {
        Map<String, Publisher> publisherMap = PublisherDao.createMap();
        Scanner scan = new Scanner(System.in);
        String address;
        String publisherName;
        String publisherId;

        System.out.println("Please create the publisher Id");
        publisherId = scan.nextLine();
        publisherId = "pid-" + publisherId;
        if (publisherMap.containsKey(publisherId)) {
            System.out.println("Publisher id already exists");
        } else {
            System.out.println("Please enter the publisher name you would like to add");
            publisherName = scan.nextLine();

            System.out.println("Please enter the publisher address");
            address = scan.nextLine();

            PublisherDao.add(new Publisher(publisherName, address, publisherId));
        }
    }

    private static void handleUpdateAuthor() {
        Map<String, Author> authorMap = AuthorDao.createMap();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the Author Id you would like to update");
        String authorKey = scan.nextLine();

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
                    while (authorMap.containsKey(changeAid)) {
                        System.out.println("ISBN already exists, please try again");
                        changeAid = scan.nextLine();
                    }

                    Map<String,Book> bookMap = BookDao.createMap();

                    String finalChangeAid = changeAid;
                    bookMap.forEach((key,book)-> {
                        if(book.getAuthorId().equalsIgnoreCase(authorMap.get(authorKey).getId())){
                            book.setAuthorId("aid-"+finalChangeAid);
                        }
                    });
                    a.setId("aid-" + changeAid);
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

    private static void handleUpdateBook() {
        Scanner scan = new Scanner(System.in);
        Map<String, Book> bookMap = BookDao.createMap();
        String bookKey;
        String userChoice;

        System.out.println("Enter the ISBN that you would like to update");
        bookKey = scan.nextLine();

        if (bookMap.containsKey(bookKey)) {
            Book b = bookMap.get(bookKey);
            System.out.println("What would you like to change?\n" +
                    "(1)Book title\n" +
                    "(2)Book ISBN\n" +
                    "(3)Author id\n" +
                    "(4)Publisher id");
            userChoice = scan.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("What would you like to change it to?");
                    String changeTitle = scan.nextLine();
                    b.setTitle(changeTitle);
                    bookMap.put(bookKey, b);
                    BookDao.update(bookMap);
                    break;

                case "2":
                    System.out.println("What would you like to change it to?");
                    String changeIsbn = scan.nextLine();
                    while (bookMap.containsKey(changeIsbn)) {
                        System.out.println("ISBN already exists, please try again");
                        changeIsbn = scan.nextLine();
                    }
                    b.setIsbn("pid-" + changeIsbn);
                    bookMap.remove(bookKey);
                    bookMap.put(changeIsbn, b);
                    BookDao.update(bookMap);
                    break;

                case "3":
                    System.out.println("What would you like to change it to?");
                    String changeAid = scan.nextLine();
                    b.setAuthorId(changeAid);
                    bookMap.put(bookKey, b);
                    BookDao.update(bookMap);
                    break;

            }
        } else {
            System.out.println("ISBN does not exist");
        }

    }


    private static void handleUpdatePublisher() {
        Scanner scan = new Scanner(System.in);
        Map<String, Publisher> publisherMap = PublisherDao.createMap();
        String publisherKey;
        String userChoice;

        System.out.println("Enter the publisher id that you would like to update");
        publisherKey = scan.nextLine();

        if (publisherMap.containsKey(publisherKey)) {
            Publisher p = publisherMap.get(publisherKey);
            System.out.println("What would you like to change?\n" +
                    "(1)Publisher name\n" +
                    "(2)Publisher address\n" +
                    "(3)Publisher id");
            userChoice = scan.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("What would you like to change it to?");
                    String changeName = scan.nextLine();
                    p.setName(changeName);
                    publisherMap.put(publisherKey, p);
                    PublisherDao.update(publisherMap);
                    break;

                case "2":
                    System.out.println("What would you like to change it to?");
                    String changeAddress = scan.nextLine();
                    p.setAddress(changeAddress);
                    publisherMap.put(publisherKey, p);
                    PublisherDao.update(publisherMap);
                    break;

                case "3":
                    System.out.println("What would you like to change it to?");
                    String changeId = scan.nextLine();
                    while (publisherMap.containsKey(changeId)) {
                        System.out.println("Id already exists, please try again.");
                        changeId = scan.nextLine();
                    }
                    Map<String,Book>bookMap = BookDao.createMap();
                    String finalChangePid = changeId;
                    bookMap.entrySet().forEach(e-> {
                        if(e.getValue().getAuthorId().equalsIgnoreCase(publisherMap.get(publisherKey).getId())){
                            e.getValue().setAuthorId("pid-"+finalChangePid);
                        }
                    });
                    p.setId("pid-" + changeId);
                    publisherMap.remove(publisherKey);
                    publisherMap.put(changeId, p);
                    PublisherDao.update(publisherMap);
                    break;
            }
        } else {
            System.out.println("Publisher does not exist");
        }

    }
    private static void handleDeleteAuthor(){
        Map<String, Author> authorMap = AuthorDao.createMap();
        Map<String,Book> bookMap = BookDao.createMap();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author id you would like to delete");
        String deleteKey = scan.nextLine();
        String finalDeleteKey = "aid-"+deleteKey;
        bookMap.forEach((key,book)-> {
                    if (book.getAuthorId().equalsIgnoreCase(authorMap.get(finalDeleteKey).getId())) {
                        BookDao.delete(key, bookMap);
                    }
                });
        AuthorDao.delete(deleteKey, authorMap);
    }
    private static void handleDeleteBook(){
        Map<String, Author> authorMap = AuthorDao.createMap();
        Map<String,Book> bookMap = BookDao.createMap();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the isbn you would like to delete");
        String deleteKey = scan.nextLine();
        String finalDeleteKey = "isbn-"+deleteKey;

        authorMap.forEach((key,bookList)-> {
            if (bookList.getBooks().contains(bookMap.get(finalDeleteKey))) {
                bookList.getBooks().remove(bookMap.get(finalDeleteKey));
                AuthorDao.update(authorMap);
                if(authorMap.get(key).getBooks().isEmpty()){
                    AuthorDao.delete(key,authorMap);
                }
            }
        });
        BookDao.delete(deleteKey, bookMap);
    }
    private static void handleDeletePublisher(){
        Map<String, Publisher> publisherMap = PublisherDao.createMap();
        Map<String,Book> bookMap = BookDao.createMap();
        Map<String,Author> authorMap = AuthorDao.createMap();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the publisher id you would like to delete");
        String deleteKey = scan.nextLine();
        String finalDeleteKey = "pid-"+deleteKey;

        authorMap.forEach((key,bookList)-> {
            if (bookList.getBooks().contains(bookMap.get(finalDeleteKey))) {
                bookList.getBooks().remove(bookMap.get(finalDeleteKey));
                AuthorDao.update(authorMap);
                if(authorMap.get(key).getBooks().isEmpty()){
                    AuthorDao.delete(key,authorMap);
                }
            }
        });

        bookMap.forEach((key,book)-> {
            if (book.getPublisherId().equalsIgnoreCase(publisherMap.get(finalDeleteKey).getId())) {
                BookDao.delete(key, bookMap);
            }
        });
        PublisherDao.delete(deleteKey, publisherMap);
    }
}
