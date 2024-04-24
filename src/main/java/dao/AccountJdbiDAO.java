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
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

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
    public Account getAccountsByUsername(@Bind("username") String username);
    
    @SqlUpdate("INSERT INTO Account (FirstName, LastName, Username, Password, Email, Status) VALUES (:account.firstName, :account.lastName, :account.userName, :account.password, :account.email, :account.status)")
    @GetGeneratedKeys
    @RegisterBeanMapper(Account.class)
    Account createAccount(@BindBean("account") Account account);

    
    @SqlUpdate("UPDATE Account SET FirstName=:account.firstName, LastName=:account.lastName, Email=:account.email, Status=:account.status WHERE Username=:account.userName")
    @GetGeneratedKeys
    @RegisterBeanMapper(Account.class)
    Account updateAccount(@BindBean("account") Account account);
    
    @SqlUpdate("DELETE FROM Account WHERE Username=:username")
    void deleteAccountByUsername(@Bind("username") String username);
    
    @SqlQuery("SELECT COUNT(*) FROM Account WHERE Username=:username")
    boolean isUsernameTaken(@Bind("username") String username);
    
    @SqlUpdate("UPDATE Account SET Password=:newPassword WHERE Username=:username")
    void changePassword(@Bind("username") String username, @Bind("newPassword") String newPassword);
    
    @Override
    @SqlQuery("select exists (select * from Account where Username=:username and Password=:password)")
    public Boolean credentialCheck(@Bind("username") String username, @Bind("password") String password);
    
}
