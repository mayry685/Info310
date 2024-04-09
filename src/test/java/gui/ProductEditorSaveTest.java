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
public class ProductEditorSaveTest {

    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().delayBetweenEvents(20);

        dao = mock(ProductDAO.class);
    }

    @AfterEach
    public void tearDown() {
        fixture.cleanUp();
    }

    @Test
    public void saveTest() {
        ProductEditor dialog = new ProductEditor(null, true, dao);
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();

        // enter some details into the UI components
        fixture.textBox("txtId").enterText("12345");
        fixture.textBox("txtName").enterText("Doodad");
        fixture.textBox("txtDescription").enterText("Cutting-edge doodad");
        fixture.textBox("txtPrice").enterText("541");
        fixture.textBox("txtQuantity").enterText("555");
        fixture.comboBox("cmbCategory").enterText("Things");

        fixture.button("btnSave").click();
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        verify(dao).saveProduct(argument.capture());
        Product savedProduct = argument.getValue();

        // test that the products details were properly saved
        assertThat("Ensure the ID was saved", savedProduct, hasProperty("productId", equalTo("12345")));
        assertThat("Ensure the Name was saved", savedProduct, hasProperty("productName", equalTo("Doodad")));
        assertThat("Ensure the Description was saved", savedProduct, hasProperty("description", equalTo("Cutting-edge doodad")));
        assertThat("Ensure the Price was saved", savedProduct.getListPrice(), equalTo(new BigDecimal("541")));
        assertThat("Ensure the Quantity was saved", savedProduct.getQuantityInStock(), equalTo(new BigDecimal("555")));
        assertThat("Ensure the Category was saved", savedProduct, hasProperty("category", equalTo("Things")));
    }

}
