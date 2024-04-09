/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Customer;
import java.util.Collection;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author Jarrad Marshall
 */
public interface CustomerJdbiDAO extends CustomerDAO{

    @Override
    @SqlQuery ("select * from Customers where Username=:username")
    @RegisterBeanMapper(Customer.class)
    public Customer searchByUsername(@Bind("username") String username);

    @Override
    @SqlUpdate("insert into Customers(Username, FirstName, Surname, Password, EmailAddress, ShippingAddress) values (:username, :firstName, :surname, :password, :emailAddress, :shippingAddress)")
    public void saveCustomer(@BindBean Customer customer);

    @Override
    @SqlUpdate("delete from Customers where Username=:username")
    public void removeCustomer(@BindBean Customer customer);

    @Override
    @SqlQuery("select * from Customers order by CustomerID")
    @RegisterBeanMapper(Customer.class)
    public Collection<Customer> getCustomers();

    @Override
    @SqlQuery("select exists (select * from Customers where Username=:username and Password=:password)")
    public Boolean credentialCheck(@Bind("username") String username, @Bind("password") String password);
    
    
    
}
