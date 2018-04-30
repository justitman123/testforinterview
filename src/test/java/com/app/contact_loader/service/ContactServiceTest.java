package com.app.contact_loader.service;

import com.app.contact_loader.TimingRules;
import com.app.contact_loader.exception.NotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ContactServiceImpl service;

    @Autowired
    private DataSource dataSource;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

    @Test
    public void testDataBase() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            log.info("catalog:" + connection.getCatalog());
        }
    }

    @Test(expected = NotFoundException.class)
    public void testGetAllWithException() throws Exception {
        HttpServletResponse response = new MockHttpServletResponse();
        service.getAll(".*[аео].*$", response.getWriter());
    }

    @Test(expected = NotFoundException.class)
    public void testGetAll() throws Exception {
        HttpServletResponse response = new MockHttpServletResponse();
        service.getAll(".*[ае].*$", response.getWriter());
    }
}
