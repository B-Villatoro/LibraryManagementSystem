package com.smoothstack.lms.service;

import com.smoothstack.lms.dao.AuthorDao;
import com.smoothstack.lms.dao.BookDao;
import com.smoothstack.lms.dao.PublisherDao;
import com.smoothstack.lms.model.Author;
import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.Publisher;

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
        }
    }
    public static void addPublisher(String publisherId){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the publisher name you would like to add");
        String publisherName = scan.nextLine();

        System.out.println("Please enter the publisher address");
        String address = scan.nextLine();
        PublisherDao.add(new Publisher(publisherName, address, publisherId));
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
                    changeId = "pid" + changeId;
                    while (publisherMap.containsKey(changeId)) {
                        System.out.println("Id already exists, please try again.");
                        changeId = scan.nextLine();
                        changeId = "pid" + changeId;
                    }
                    Map<String, Book> bookMap = BookDao.createMap();
                    String finalChangePid = changeId;
                    String finalPublisherKey = publisherKey;

                    bookMap.forEach((key,value) -> {
                        if (bookMap.get(key).getPublisherId().equalsIgnoreCase(publisherMap.get(finalPublisherKey).getId())) {
                            bookMap.get(key).setPublisherId(finalChangePid);
                        }
                    });
                    p.setId(changeId);
                    publisherMap.remove(publisherKey);
                    publisherMap.put(changeId, p);
                    PublisherDao.update(publisherMap);
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
