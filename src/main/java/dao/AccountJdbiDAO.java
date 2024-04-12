/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import domain.Account;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

/**
 *
 * @author chsur
 */
public interface AccountJdbiDAO extends CredentialsValidator {
    
    @SqlQuery("SELECT * FROM account ORDER BY AccountID")
    @RegisterBeanMapper(Account.class)
    public Collection<Account> getAccounts();
    
    @SqlQuery("SELECT * FROM account where Username=:username")
    @RegisterBeanMapper(Account.class)
    public Account searchByUsername(@Bind("username") String username);
    
    @SqlQuery("INSERT INTO Account (FirstName, LastName, Username, Password, Email, Status)\n" +
                "VALUES (:FirstName, :LastName, :Username, :Password, :Email, :Status);")
    public Account createAccount(@BindBean Account account);
    
}
