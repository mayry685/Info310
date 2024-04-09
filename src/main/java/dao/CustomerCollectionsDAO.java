/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jarrad Marshall
 */
public class CustomerCollectionsDAO implements CustomerDAO {

    private static final Multimap<String, Customer> usernames = HashMultimap.create();
    private static final Map<Integer, Customer> customers = new HashMap<>();

    @Override
    public Collection<Customer> getCustomers() {
        return customers.values();
    }

    @Override
    public void removeCustomer(Customer customer) {
        customers.remove(customer.getCustomerId());
        usernames.remove(customer.getUsername(), customer);
    }

    @Override
    public void saveCustomer(Customer customer) {
        if(customer.getCustomerId()==null){
            customer.setCustomerId(customers.size()+1000);
        }
        customers.put(customer.getCustomerId(), customer);
        usernames.put(customer.getUsername(), customer);
    }

    @Override
    public Customer searchByUsername(String username) {
        Collection<Customer> customers = usernames.get(username);
        if(customers.isEmpty()){
        return null;
        }
        return (Customer) customers.toArray()[0];
    }

    @Override
    public Boolean credentialCheck(String username, String password) {
        Customer c = searchByUsername(username);
        if(c != null) return c.getPassword().equals(password);
        return false;
    }

}
