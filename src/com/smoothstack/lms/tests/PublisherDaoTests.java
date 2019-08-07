package com.smoothstack.lms.tests;

import java.io.IOException;
import java.util.Map;

import com.smoothstack.lms.dao.PublisherDao;
import com.smoothstack.lms.model.Publisher;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class PublisherDaoTests {

    Map<String, Publisher> publisherMap;

    @Before
    public void loadMap() throws IOException {
        publisherMap = PublisherDao.createMap();
        publisherMap.forEach((key,publisher)->{
            System.out.println(publisher.getName()+";"+publisher.getAddress()+";" +
                    publisher.getName()+";"+publisher.getAddress()+";" +
                    publisher.getId());
        });
    }

    @Test
    public void MapHasData() throws IOException {
        assertTrue("Map has no data", publisherMap.size() > 0);
    }

}
