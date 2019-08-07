package com.smoothstack.lms.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.smoothstack.lms.model.Author;
import com.smoothstack.lms.model.Book;
import org.junit.Before;
import org.junit.Test;
import com.smoothstack.lms.dao.AuthorDao;

import static junit.framework.TestCase.assertTrue;

public class AuthorDaoTests {

    Map<String, Author> authorMap;

    @Before
    public void loadMap() throws IOException {
        authorMap = AuthorDao.createMap();
    }

    @Test
    public void MapHasData() throws IOException {
        assertTrue("Map has no data", authorMap.size() > 0);
    }

}
