/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import domain.Customer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Jarrad Marshall
 */
public class CustomerDAOTest {

    private CustomerDAO dao;
    private Map<String, Customer> control;

    @BeforeAll
    public static void initialise() {
        JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
    }

    @BeforeEach
    public void setUp() {
        //dao = new CustomerCollectionsDAO();
        dao = JdbiDaoFactory.getCustomerDAO();
        control = new HashMap<>();
        Customer c1 = new Customer("user123", "John", "Doe", "123 Main St, City", "john.doe@example.com", "password123");
        Customer c2 = new Customer("jane85", "Jane", "Smith", "456 Elm Ave, Town", "jane.smith@example.com", "securepwd");
        Customer c3 = new Customer("alex12", "Alex", "Johnson", "789 Oak Rd, Village", "alex.johnson@example.com", "mysecretpass");

        dao.saveCustomer(c1);
        dao.saveCustomer(c2);

        control.put("c1", c1);
        control.put("c2", c2);
        control.put("c3", c3);
    }

    @AfterEach
    public void tearDown() {
        Collection<Customer> customers = dao.getCustomers();

        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer c = iterator.next();
            iterator.remove();
            dao.removeCustomer(c);
        }

    }

    /**
     * Test of getCustomers method, of class CustomerCollectionsDAO.
     */
    @Test
    public void testGetCustomers() {
        Collection<Customer> customers = dao.getCustomers();
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(control.get("c1"));
        expectedCustomers.add(control.get("c2"));
        assertThat("Ensure that getCustomers returns the correct number", customers.size(), equalTo(2));
        assertThat("Ensure that the dao holds c1", dao.searchByUsername(expectedCustomers.get(0).getUsername()), notNullValue());
        assertThat("Ensure that the dao holds c2", dao.searchByUsername(expectedCustomers.get(1).getUsername()), notNullValue());
        assertThat("Ensure that getCustomers returns the correct Customers", customers,
                containsInAnyOrder(expectedCustomers.get(0),expectedCustomers.get(1))
        );

    }

    /**
     * Test of removeCustomer method, of class CustomerCollectionsDAO.
     */
    @Test
    public void testRemoveCustomer() {
        Customer expectedCustomer = control.get("c2");
        Collection<Customer> customers = dao.getCustomers();
        assertThat("Ensure that the number of customers is 2 before deletion", customers.size(), equalTo(2));
        assertThat("Ensure that customer exists before deletion", dao.searchByUsername(expectedCustomer.getUsername()).getUsername(), equalTo(expectedCustomer.getUsername()));

        dao.removeCustomer(expectedCustomer);
        customers = dao.getCustomers();

        assertThat("Ensure that the number of customers is 1 after deletion", customers.size(), equalTo(1));
        assertThat("Ensure that customer no longer exists after deletion", dao.searchByUsername(expectedCustomer.getUsername()), nullValue());
    }

    /**
     * Test of saveCustomer method, of class CustomerCollectionsDAO.
     */
    @Test
    public void testSaveCustomer() {
        Customer expectedCustomer = control.get("c3");
        Collection<Customer> customers = dao.getCustomers();

        assertThat("Ensure that the number of customers is 2 before saving", customers.size(), equalTo(2));
        assertThat("Ensure that customer doesnt exist before saving", not(customers.contains(expectedCustomer)));
        //save customer 
        dao.saveCustomer(expectedCustomer);
        customers = dao.getCustomers();
        Customer savedCustomer = dao.searchByUsername(expectedCustomer.getUsername());

        System.out.println("Expected username: " + expectedCustomer.getUsername());
        System.out.println("Saved username: " + savedCustomer.getUsername());

        assertThat(expectedCustomer, Matchers.samePropertyValuesAs(savedCustomer, "customerId"));
        
        assertThat("Ensure that the number of customers is 3 after saving", customers.size(), equalTo(3));
        assertThat("Ensure the saved customer persists", savedCustomer, notNullValue());
        assertThat("Ensure Username is saved correctly", savedCustomer.getUsername().trim(), equalTo(expectedCustomer.getUsername().trim()));
        assertThat("Ensure FirstName is saved correctly", savedCustomer.getFirstName(), equalTo(expectedCustomer.getFirstName()));
        assertThat("Ensure Surname is saved correctly", savedCustomer.getSurname(), equalTo(expectedCustomer.getSurname()));
        assertThat("Ensure EmailAddress is saved correctly", savedCustomer.getEmailAddress(), equalTo(expectedCustomer.getEmailAddress()));
        assertThat("Ensure ShippingAddress is saved correctly", savedCustomer.getShippingAddress(), equalTo(expectedCustomer.getShippingAddress()));
        assertThat("Ensure Password is saved correctly", savedCustomer.getPassword(), equalTo(expectedCustomer.getPassword()));
    }

    /**
     * Test of searchByUsername method, of class CustomerCollectionsDAO.
     */
    @Test
    public void testSearchByUsername() {
        Customer expectedCustomer = control.get("c1");

        assertThat("Ensure search returns correct customer", dao.searchByUsername(expectedCustomer.getUsername()).getUsername(), equalTo(expectedCustomer.getUsername()));
        assertThat("Ensure search returns null on bad username", dao.searchByUsername("no"), equalTo(null));
    }

    /**
     * Test of credentialCheck method, of class CustomerCollectionsDAO.
     */
    @Test
    public void testCredentialCheck() {
        Customer expectedCustomer = control.get("c1");

        assertThat("Ensure correct credentials pass", dao.credentialCheck(expectedCustomer.getUsername(), expectedCustomer.getPassword()));
        assertThat("Ensure false credentials fail", not(dao.credentialCheck("false", "no")));
    }

}
