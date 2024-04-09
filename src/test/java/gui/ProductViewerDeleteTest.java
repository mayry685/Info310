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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Jarrad Marshall
 */
public class ProductViewerDeleteTest {

    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;
    private Product p1;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().delayBetweenEvents(20);

        p1 = new Product("12345", "Widget", "High-quality widget", "Stuff", new BigDecimal("19.99"), new BigDecimal("100"));
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

        Mockito.doAnswer((call) -> {
            products.remove(p1);
            return null;
        }).when(dao).removeProduct(p1);
    }

    @AfterEach
    public void tearDown() {
        fixture.cleanUp();
    }

    @Test
    public void deleteTest() {
        ProductViewer dialog = new ProductViewer(null, true, dao);
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();

        //retrieve JList
        SimpleListModel list = (SimpleListModel) fixture.list("lstProducts").target().getModel();

        assertThat("Ensure product count is correct before deletion", list.getSize(), is(dao.getProducts().size()));
        assertThat("Ensure product exists before deletion", list.contains(p1), equalTo(true));

        fixture.list("lstProducts").selectItem(p1.toString());
        fixture.button("btnDelete").click();
        fixture.optionPane().requireVisible().yesButton().click();
        verify(dao).removeProduct(p1);

        assertThat("Ensure product count is correct after deletion", list.getSize(), is(dao.getProducts().size()));
        assertThat("Ensure product is deleted", list.contains(p1), equalTo(false));

    }

}
