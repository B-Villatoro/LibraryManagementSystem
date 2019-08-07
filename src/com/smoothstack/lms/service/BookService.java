package com.smoothstack.lms.service;

import com.smoothstack.lms.app.Menu;
import com.smoothstack.lms.dao.AuthorDao;
import com.smoothstack.lms.dao.BookDao;
import com.smoothstack.lms.dao.PublisherDao;
import com.smoothstack.lms.model.Author;
import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.Publisher;

import java.util.Map;
import java.util.Scanner;

public class BookService {


    public static void addBook() {
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
            publisherId = "pid-" + publisherId;

            if (publisherMap.containsKey(publisherId)) {
                //prompt for aid and check if exists
                Map<String, Author> authorMap = AuthorDao.createMap();
                System.out.println("Please enter the author id");
                authorId = scan.nextLine();
                authorId = "aid-" + authorId;
                if (authorMap.containsKey(authorId)) {
                    //all passes create book
                    BookDao.add(new Book(title, (isbn), authorId, publisherId));
                    authorMap.get(authorId).getBooks().add(new Book(title, (isbn), authorId, publisherId));
                    System.out.println("Book added! Returning to main menu.");
                    Menu.mainMenu();
                } else {
                    //if author does not exist add the book through author handler
                    System.out.println("Author does not exist, creating author");
                    AuthorService.addAuthor();
                }

            } else {
                //if pid doesn't exist make it exist
                System.out.println("publisher does not exist...\n" +
                        "Creating publisher");
                PublisherService.addPublisher(publisherId);

                System.out.println("Lets continue!");
                Map<String, Author> authorMap = AuthorDao.createMap();
                System.out.println("Please enter the author id");
                authorId = scan.nextLine();
                authorId = "aid-" + authorId;
                if (authorMap.containsKey(authorId)) {
                    //all passes create book
                    BookDao.add(new Book(title, (isbn), authorId, publisherId));
                    authorMap.get(authorId).getBooks().add(new Book(title, (isbn), authorId, publisherId));
                    System.out.println("Book added! Returning to main menu.");
                    Menu.mainMenu();
                } else {
                    //if author does not exist add the book through author handler
                    System.out.println("Author does not exist, creating author");
                    AuthorService.addAuthor(authorId,title,isbn,publisherId);
                }
            }
        }//end big else
    }


    public static void updateBook() {
        Scanner scan = new Scanner(System.in);
        Map<String, Book> bookMap = BookDao.createMap();
        Map<String, Author> authorMap = AuthorDao.createMap();

        String bookKey;
        String userChoice;

        System.out.println("Enter the ISBN that you would like to update");
        bookKey = scan.nextLine();
        bookKey = "isbn-" + bookKey;

        if (bookMap.containsKey(bookKey)) {
            //create a book based off books csv
            Book b = bookMap.get(bookKey);
            System.out.println("Book found! What would you like to change?\n" +
                    "(1)Book title\n" +
                    "(2)Book ISBN\n" +
                    "(3)Author id\n" +
                    "(4)Publisher id");
            userChoice = scan.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("What would you like to change it to?");
                    String changeTitle = scan.nextLine();

                    authorMap.get(b.getAuthorId()).getBooks().remove(b);
                    b.setTitle(changeTitle);

                    authorMap.get(b.getAuthorId()).getBooks().add(b);
                    bookMap.put(bookKey, b);
                    BookDao.update(bookMap);
                    AuthorDao.update(authorMap);
                    break;

                case "2":
                    System.out.println("What would you like to change ISBN to?");
                    String changeIsbn = scan.nextLine();
                    changeIsbn = "isbn-" + changeIsbn;

                    //validate new key
                    while (bookMap.containsKey(changeIsbn)) {
                        System.out.println("ISBN already exists, please try again");
                        changeIsbn = scan.nextLine();
                        changeIsbn = "isbn-" + changeIsbn;
                    }
                    b.setIsbn(changeIsbn);
                    bookMap.remove(bookKey);
                    bookMap.put(changeIsbn, b);
                    BookDao.update(bookMap);
                    break;

                case "3":
//                    Map<String,Author>authorMap = AuthorDao.createMap();
                    System.out.println("What would you like to change author id to?");
                    String changeAid = scan.nextLine();
                    changeAid = "aid-" + changeAid;
                    if (authorMap.containsKey(changeAid)) {
                        b.setAuthorId(changeAid);
                        bookMap.put(bookKey, b);
                        BookDao.update(bookMap);

                    } else {
                        System.out.println("Cannot change to an author that does not exist");
                    }
                    break;
                case "4":
                    Map<String, Publisher> publisherMap = PublisherDao.createMap();
                    System.out.println("What would you like to change publisher id to?");
                    String changePid = scan.nextLine();
                    changePid = "pid-" + changePid;
                    if (publisherMap.containsKey(changePid)) {
                        b.setPublisherId(changePid);
                        bookMap.put(bookKey, b);
                        BookDao.update(bookMap);
                    } else {
                        System.out.println("Publisher does not exist");
                    }

            }
        } else {
            System.out.println("ISBN does not exist");
        }

    }

    public static void deleteBook() {
        Map<String, Author> authorMap = AuthorDao.createMap();
        Map<String, Book> bookMap = BookDao.createMap();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the isbn you would like to delete");
        String deleteKey = scan.nextLine();
        deleteKey = "isbn-" + deleteKey;
        String finalDeleteKey = deleteKey;

        if (bookMap.containsKey(deleteKey)) {
            authorMap.forEach((key, bookList) -> {
                if (bookList.getBooks().contains(bookMap.get(finalDeleteKey))) {
                    bookList.getBooks().remove(bookMap.get(finalDeleteKey));
                    AuthorDao.update(authorMap);
                    if (authorMap.get(key).getBooks().isEmpty()) {
                        AuthorDao.delete(key, authorMap);
                    }
                }
            });
            BookDao.delete(deleteKey, bookMap);
        } else {
            System.out.println("Book does not exist");
        }
    }
}
