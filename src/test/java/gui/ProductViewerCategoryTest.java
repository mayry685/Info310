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
import java.util.List;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Jarrad Marshall
 */
public class ProductViewerCategoryTest {

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
        Collection<Product> stuffProducts = new ArrayList<>();
        Collection<Product> thingProducts = new ArrayList<>();

        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        stuffProducts.add(p1);
        stuffProducts.add(p2);
        thingProducts.add(p3);
        thingProducts.add(p4);

        dao = mock(ProductDAO.class);
        when(dao.getCategories()).thenReturn(List.of("Stuff", "Things"));
        when(dao.filterByCategory("Stuff")).thenReturn(stuffProducts);
        when(dao.filterByCategory("Things")).thenReturn(thingProducts);
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

        //assign combo box to stuff category
        String cmbSearch = "Stuff";
        fixture.comboBox("cmbCategories").selectItem(cmbSearch);
        verify(dao).filterByCategory("Stuff");

        //test stuff category
        assertThat("Ensure two results returned on Stuff", list.getSize(), is(dao.filterByCategory(cmbSearch).size()));
        assertThat("Ensure results are correct for Stuff", list, containsInAnyOrder(dao.filterByCategory(cmbSearch).toArray()));

        //assign combo box to thing category and test
        cmbSearch = "Things";
        fixture.comboBox("cmbCategories").selectItem(cmbSearch);
        verify(dao).filterByCategory("Things");
        assertThat("Ensure two results returned on Thing", list.getSize(), is(dao.filterByCategory(cmbSearch).size()));
        assertThat("Ensure results are correct for Thing", list, containsInAnyOrder(dao.filterByCategory(cmbSearch).toArray()));
    }

}
