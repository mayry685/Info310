/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import domain.Customer;
import domain.Product;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jarrad Marshall
 */
public class ProductDAOTest {

    private ProductDAO dao;
    private Map<String, Product> control;

    @BeforeAll
    public static void initialise() {
        JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
    }

    @BeforeEach
    public void setUp() {
        //dao = new ProductCollectionsDAO();
        dao = JdbiDaoFactory.getProductDAO();
        control = new HashMap<>();

        Product p1 = new Product("12345", "Widget", "High-quality widget", "Stuff", new BigDecimal("19.99"), new BigDecimal("100"));
        Product p2 = new Product("67890", "Gizmo", "Advanced gizmo", "Stuff", new BigDecimal("49.95"), new BigDecimal("50"));
        Product p3 = new Product("54321", "Thingy", "Versatile thingy", "Things", new BigDecimal("9.99"), new BigDecimal("200"));
        Product p4 = new Product("98765", "Doodad", "Cutting-edge doodad", "Things", new BigDecimal("149.00"), new BigDecimal("25"));

        dao.saveProduct(p1);
        dao.saveProduct(p2);
        dao.saveProduct(p3);


        control.put("p1", p1);
        control.put("p2", p2);
        control.put("p3", p3);
        control.put("p4", p4);
    }

    @AfterEach
    public void tearDown() {
        Collection<Product> products = dao.getProducts();

        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            iterator.remove();
            dao.removeProduct(p);
        }
    }

    /**
     * Test of filterByCategory method, of class ProductDAO.
     */
    @Test
    public void testFilterByCategory() {
        Collection<Product> filteredProducts = dao.filterByCategory("Stuff");
        Product[] expectedProducts = {control.get("p1"), control.get("p2")};

        assertThat("Ensure products are correctly filtered by category", filteredProducts, containsInAnyOrder(expectedProducts));

    }

    /**
     * Test of getCategories method, of class ProductDAO.
     */
    @Test
    public void testGetCategories() {
        Collection<String> categories = dao.getCategories();
        List<String> expectedCategories = Arrays.asList("Stuff", "Things");
        assertThat("Ensure retrieved categories match the expected categories", categories, containsInAnyOrder(expectedCategories.toArray()));
    }

    /**
     * Test of getProducts method, of class ProductDAO.
     */
    @Test
    public void testGetProducts() {
        dao.saveProduct(control.get("p4"));
        Collection<Product> products = dao.getProducts();
        Collection<Product> expectedProducts = control.values();

        assertThat("Ensure retrieved products match the expected products", products, containsInAnyOrder(expectedProducts.toArray()));

    }

    /**
     * Test of removeProduct method, of class ProductDAO.
     */
    @Test
    public void testRemoveProduct() {
        Product productToRemove = control.get("p2");
        Collection<Product> productsBeforeRemoval = dao.getProducts();
        assertThat("Ensure product to remove exists before removal", productsBeforeRemoval.contains(productToRemove));
        dao.removeProduct(productToRemove);
        Collection<Product> productsAfterRemoval = dao.getProducts();
        assertThat("Ensure product no longer exists after removal", productsAfterRemoval.contains(productToRemove), equalTo(false));
    }

    /**
     * Test of saveProduct method, of class ProductDAO.
     */
    @Test
    public void testSaveProduct() {
        Product newProduct = control.get("p4");
        Collection<Product> productsBeforeSave = dao.getProducts();
        assertThat("Ensure product doesn't exist before saving", productsBeforeSave.contains(newProduct), equalTo(false));
        dao.saveProduct(newProduct);

        Collection<Product> productsAfterSave = dao.getProducts();
        assertThat("Ensure product exists after saving", productsAfterSave.contains(newProduct));
    }

    /**
     * Test of searchById method, of class ProductDAO.
     */
    @Test
    public void testSearchById() {
        Product expectedProduct = control.get("p1");
        Product foundProduct = dao.searchById(expectedProduct.getProductId());

        assertNotNull(foundProduct);
        assertEquals(expectedProduct, foundProduct);

    }
}
