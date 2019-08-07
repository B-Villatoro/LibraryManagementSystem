package com.smoothstack.lms.tests;

import java.io.IOException;
import java.util.Map;

import com.smoothstack.lms.dao.BookDao;
import com.smoothstack.lms.model.Book;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class BookDaoTests {

    Map<String, Book> bookMap;

    @Before
    public void loadMap() throws IOException {
        bookMap = BookDao.createMap();
        bookMap.forEach((key,book)->{
            System.out.println(book.getTitle()+";"+book.getIsbn()+";" +
                    book.getAuthorId()+";"+book.getPublisherId()+";");
        });
    }

    @Test
    public void MapHasData() throws IOException {
        assertTrue("Map has no data", bookMap.size() > 0);
    }

}
