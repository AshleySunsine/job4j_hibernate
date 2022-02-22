package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Or;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndFindByIdOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        Order ordStart = Order.of("name1", "description1");

        int id = store.save(ordStart).getId();
        Order order = store.findById(id);

        assertEquals(order, ordStart);
    }

    @Test
    public void whenSaveOrderAndUpdateOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        Order ordStart = Order.of("name1", "description1");
        Order ordUpdate = Order.of("name1", "DEEEESCRRRRR");

        int id = store.save(ordStart).getId();
        store.updateOrder(ordUpdate);
        Order orderEnd = store.findById(id);

        assertEquals(orderEnd.getDescription(), "DEEEESCRRRRR");
    }

    @Test
    public void whenSaveOrderAndFindByNameALotRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));
        store.save(Order.of("name1", "description2"));
        store.save(Order.of("name1", "description3"));
        store.save(Order.of("name5", "description77"));

        List<Order> all = (List<Order>) store.findByName("name1");

        assertThat(all.size(), is(3));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(1).getDescription(), is("description2"));
        assertThat(all.get(2).getDescription(), is("description3"));
    }

}