package com.smoothstack.lms.service;

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

public class PublisherService {

    public static void addPublisher() {
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
            System.out.println(publisherName + " is added!");
        }
    }

    public static void addPublisher(String publisherId) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the publisher name you would like to add");
        String publisherName = scan.nextLine();

        System.out.println("Please enter the publisher address");
        String address = scan.nextLine();
        PublisherDao.add(new Publisher(publisherName, address, publisherId));
        System.out.println(publisherName + " is added!");
    }

    public static void updatePublisher() {
        Scanner scan = new Scanner(System.in);
        Map<String, Publisher> publisherMap = PublisherDao.createMap();
        String publisherKey;
        String userChoice;

        System.out.println("Enter the publisher id that you would like to update");
        publisherKey = scan.nextLine();
        publisherKey = "pid-" + publisherKey;

        if (publisherMap.containsKey(publisherKey)) {
            Publisher p = publisherMap.get(publisherKey);
            System.out.println("What would you like to change?\n" +
                    "(1)Publisher name\n" +
                    "(2)Publisher address\n" +
                    "(3)Publisher id");
            userChoice = scan.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("What would you like to change the name to?");
                    String changeName = scan.nextLine();
                    p.setName(changeName);
                    publisherMap.put(publisherKey, p);
                    PublisherDao.update(publisherMap);
                    System.out.println("The publisher name has been changed!");
                    break;

                case "2":
                    System.out.println("What would you like to change the address to?");
                    String changeAddress = scan.nextLine();
                    p.setAddress(changeAddress);
                    publisherMap.put(publisherKey, p);
                    PublisherDao.update(publisherMap);
                    System.out.println("The publisher address has been changed!");
                    break;

                case "3":
                    Map<String, Book> bookMap = BookDao.createMap();
                    Map<String, Author> authorMap = AuthorDao.createMap();


                    System.out.println("What would you like to change the publisher id to?");
                    String changeId = scan.nextLine();
                    changeId = "pid-" + changeId;

                    while (publisherMap.containsKey(changeId)) {
                        System.out.println("Id already exists, please try again.");
                        changeId = scan.nextLine();
                        changeId = "pid-" + changeId;
                    }
                    String finalPublisherKey = publisherKey;
                    String finalChangePid = changeId;


                    bookMap.forEach((key, value) -> {
                        if (bookMap.get(key).getPublisherId().equalsIgnoreCase(publisherMap.get(finalPublisherKey).getId())) {
                            //change the pid with every key that had the old one
                            bookMap.get(key).setPublisherId(finalChangePid);
                        }
                    });

                    //first get the author id of current book that was changed, then for each of those books
                    //that contains old key change it then update author map
                    authorMap.forEach((key,author)->{

                        authorMap.get(key).getBooks().forEach(book->{
                            if(book.getPublisherId().equalsIgnoreCase(publisherMap.get(finalPublisherKey).getId())){
                                System.out.println("og:"+authorMap.get(key).getBooks().get(authorMap.get(key).getBooks().indexOf(book)).getPublisherId());
                                authorMap.get(key).getBooks().get(authorMap.get(key).getBooks().indexOf(book))
                                        .setPublisherId(finalChangePid);
                                System.out.println("change:"+authorMap.get(key).getBooks().get(authorMap.get(key).getBooks().indexOf(book)).getPublisherId());
                            }
                        });
                    });

                    p.setId(changeId);
                    publisherMap.remove(publisherKey);
                    publisherMap.put(changeId, p);

                    PublisherDao.update(publisherMap);
                    BookDao.update(bookMap);
                    AuthorDao.update(authorMap);
                    System.out.println("Publisher id has been changed!");
                    break;
            }
        } else {
            System.out.println("Publisher does not exist");
        }

    }

    public static void deletePublisher() {
        Map<String, Publisher> publisherMap = PublisherDao.createMap();
        Map<String, Book> bookMap = BookDao.createMap();
        Map<String, Author> authorMap = AuthorDao.createMap();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the publisher id you would like to delete");
        String deleteKey = scan.nextLine();
        deleteKey = "pid-" + deleteKey;
        String finalDeleteKey = deleteKey;

        if (publisherMap.containsKey(deleteKey)) {
            authorMap.forEach((key, bookList) -> {
                if (bookList.getBooks().contains(bookMap.get(finalDeleteKey))) {
                    bookList.getBooks().remove(bookMap.get(finalDeleteKey));
                    AuthorDao.update(authorMap);
                    if (authorMap.get(key).getBooks().isEmpty()) {
                        AuthorDao.delete(key, authorMap);
                    }
                }
            });

            bookMap.forEach((key, book) -> {
                if (book.getPublisherId().equalsIgnoreCase(publisherMap.get(finalDeleteKey).getId())) {
                    BookDao.delete(key, bookMap);
                }
            });
            PublisherDao.delete(deleteKey, publisherMap);
        } else {
            System.out.println("Publisher does not exist");
        }


    }
}
