/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Customer;
import java.util.Collection;

/**
 *
 * @author Jarrad Marshall
 */
public interface CustomerDAO extends CredentialsValidator{

    Collection<Customer> getCustomers();

    void removeCustomer(Customer customer);

    void saveCustomer(Customer customer);
    
    Customer searchByUsername(String username);

}
