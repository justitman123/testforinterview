package com.app.contact_loader.controller;

import com.app.contact_loader.service.ContactServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/schema.sql", "classpath:db/populatedb.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class ContactControllerTest {

    private static final String REGEX = "^A.*$";

    private static final String URL = "/contacts";

    @Autowired
    ContactServiceImpl service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHome() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Здравствуйте. " +
                        "Введите Ваше регулярное выражение.")))
                .andDo(print());
    }

    @Test
    public void testGetContacts() throws Exception {
        this.mvc.perform(get(URL)
                .param("nameFilter", REGEX))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
