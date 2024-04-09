/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gui;

import dao.ProductDAO;
import domain.Product;
import helpers.SimpleListModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Jarrad Marshall
 */
public class ProductViewerSearchTest {

    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().delayBetweenEvents(100);

        // add some products 
        Product p1 = new Product("12345", "Widget", "High-quality widget", "Stuff", new BigDecimal("19.99"), new BigDecimal("100"));
        Product p2 = new Product("67890", "Gizmo", "Advanced gizmo", "Stuff", new BigDecimal("49.95"), new BigDecimal("50"));
        Product p3 = new Product("54321", "Thingy", "Versatile thingy", "Things", new BigDecimal("9.99"), new BigDecimal("200"));
        Product p4 = new Product("98765", "Doodad", "Cutting-edge doodad", "Things", new BigDecimal("149.00"), new BigDecimal("25"));
        Collection<Product> products = new ArrayList<>();

        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);

        dao = mock(ProductDAO.class);
        when(dao.getProducts()).thenReturn(products);
        when(dao.searchById("12345")).thenReturn(p1);
        when(dao.searchById("67890")).thenReturn(p2);
        when(dao.searchById("54321")).thenReturn(p3);
        when(dao.searchById("98765")).thenReturn(p4);
    }

    @AfterEach
    public void tearDown() {
        fixture.cleanUp();
    }

    @Test
    public void testCategorySearch() {
        ProductViewer dialog = new ProductViewer(null, true, dao);
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();

        //retrieve JList
        SimpleListModel list = (SimpleListModel) fixture.list("lstProducts").target().getModel();
        //initial tests for default state
        assertThat("Ensure product count is correct", list.getSize(), is(dao.getProducts().size()));
        assertThat("Ensure all products are displayed", list, containsInAnyOrder(dao.getProducts().toArray()));

        //test first id
        String idSearch = "12345";
        fixture.textBox("txtSearchId").setText(idSearch);
        fixture.button("btnSearch").click();
        //test p1
        assertThat("Ensure one result is returned for p1", list.getSize(), is(1));
        assertThat("Ensure p1 is returned", list, contains(dao.searchById(idSearch)));

        //test second id
        idSearch = "67890";
        fixture.textBox("txtSearchId").setText(idSearch);
        fixture.button("btnSearch").click();
        assertThat("Ensure one result is returned for p2", list.getSize(), is(1));
        assertThat("Ensure p2 is returned", list, contains(dao.searchById(idSearch)));

        //test third id
        idSearch = "54321";
        fixture.textBox("txtSearchId").setText(idSearch);
        fixture.button("btnSearch").click();
        assertThat("Ensure one result is returned for p3", list.getSize(), is(1));
        assertThat("Ensure p3 is returned", list, contains(dao.searchById(idSearch)));

        //test second id
        idSearch = "98765";
        fixture.textBox("txtSearchId").setText(idSearch);
        fixture.button("btnSearch").click();
        assertThat("Ensure one result is returned for p4", list.getSize(), is(1));
        assertThat("Ensure p4 is returned", list, contains(dao.searchById(idSearch)));

        //test invalid id
        idSearch = "75612";
        fixture.textBox("txtSearchId").setText(idSearch);
        fixture.button("btnSearch").click();
        assertThat("Ensure npthing is returned for p2", list.getSize(), is(0));
    }

}
