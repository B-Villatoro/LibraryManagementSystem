package com.smoothstack.lms.app;

import com.smoothstack.lms.dao.AuthorDao;
import com.smoothstack.lms.dao.BookDao;
import com.smoothstack.lms.dao.PublisherDao;
import com.smoothstack.lms.service.AuthorService;
import com.smoothstack.lms.service.BookService;
import com.smoothstack.lms.service.PublisherService;
import java.util.Scanner;

public class Menu {

    public Menu() {
        System.out.println("Hello welcome to BookBook!\nHere you will be able to organize your books!");
        mainMenu();
    }

    public static void mainMenu() {
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
                AuthorService.addAuthor();
                backToMenu();
                break;
            case "2":
            case "book":
                BookService.addBook();
                backToMenu();
                break;

            case "3":
            case "publisher":
                PublisherService.addPublisher();
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
                AuthorService.updateAuthor();
                backToMenu();
                break;

            case "2":
            case "book":
                BookService.updateBook();
                backToMenu();
                break;

            case "3":
            case "publisher":
                PublisherService.updatePublisher();
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
                AuthorService.deleteAuthor();
                backToMenu();
                break;
            case "2":
            case "book":
                BookService.deleteBook();
                backToMenu();
                break;

            case "3":
            case "publisher":
                PublisherService.deletePublisher();
                backToMenu();
                break;

            default:
                System.out.println("Wrong Input");
                backToMenu();
                break;
        }
    }
}