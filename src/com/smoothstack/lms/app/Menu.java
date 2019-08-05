package com.smoothstack.lms.app;

import com.smoothstack.lms.dao.AuthorDao;
import com.smoothstack.lms.model.Author;
import com.smoothstack.lms.model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    List<Author> authorList;
    String name;
    String isbn;
    String title;
    String publisher;
    String address;
    Scanner scan = new Scanner(System.in);
    Map<String, List<Book>> myMap;



    public Menu() {

        myMap = AuthorDao.createMap();//create my map on startup
        System.out.println("Hello welcome to BookBook!\nHere you will be able to organize your books!");
        mainMenu();
    }

    public void mainMenu() {
        System.out.println("What would you like to do today?\n" +
                "(1)View the library\n" +
                "(2)Add to the library\n" +
                "(3)Update an existing item\n" +
                "(4)Remove from the library");

        String optionSelect = scan.nextLine();
        switch (optionSelect) {
            case "1":

                AuthorDao.show();
                backToMenu();
                break;

            case "2":
                System.out.println("Please enter the author name you would like to add");
                name = scan.nextLine();
                if(myMap.containsKey(name)){
                    System.out.println("Author already exists");
                    backToMenu();
                }else {
                    List<Book> bookList = new ArrayList<>();
                    bookList = creatBook(bookList);
                    AuthorDao.add(new Author(name, bookList));
                    myMap = AuthorDao.createMap();
                    System.out.println(myMap.size());
                    backToMenu();
                }
                break;

            case "3":
                System.out.println("What would you like to update?");
                String updateKey = scan.nextLine();
                AuthorDao update = new AuthorDao();
                update.update(updateKey,myMap);
                break;

            case "4":
                System.out.println("Who would you like to delete?");
                String deleteKey = scan.nextLine();
                AuthorDao.delete(deleteKey,myMap);
                myMap = AuthorDao.createMap();
                backToMenu();
                break;

            default:
                System.out.println("Please enter valid option");
                mainMenu();
        }
    }

    //create a book and add isbn- to number then adds to book list
    public List<Book> creatBook(List<Book> bookL) {
        System.out.println("Please enter the title written by said author");
        title = scan.nextLine();
        System.out.println("Please enter the ISBN");
        isbn = "isbn-" + scan.nextLine();
        bookL.add(new Book(title, isbn));
        System.out.println("Would you like to add another? y/n");
        String yn = scan.nextLine();
        yn = yn.toLowerCase();
        if (yn.equals("y") || yn.equals("yes")) {
            return creatBook(bookL);
        }
        return bookL;
    }

    //prompts back to main menu
    public void backToMenu() {
        System.out.println("Would you like to go back to main menu? y/n");
        String yn = scan.nextLine();
        yn = yn.toLowerCase();
        if (yn.equals("y") || yn.equals("yes")) {
            mainMenu();
        } else System.exit(0);
    }

}
