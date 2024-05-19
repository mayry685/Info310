import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.JdbiDaoFactory;
import dao.AccountJdbiDAO;
import dao.SchemaDAO;
import domain.Account;

public class AccountTest {
    private Map<String, Object> control;
    private AccountJdbiDAO accountDao;

    private Account account1;
    private Account account2;
    private Account account3;

    
    @BeforeAll
    public static void initialise(){
        JdbiDaoFactory.changeUserName("postgres.qrxbjspocpeixwpcamlx");
        JdbiDaoFactory.changeJdbcUri("jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres");
    }

    @BeforeEach
    public void setUp() throws IOException {
        accountDao = JdbiDaoFactory.getAccountDAO();
        control = new HashMap<>();

        SchemaDAO schemaDAO = JdbiDaoFactory.getSchemaDAO();
        schemaDAO.resetSchema();

        account1 = new Account();
        account1.setAccountCode("acc001");
        account1.setFirstName("John");
        account1.setLastName("Doe");
        account1.setUserName("johndoe");
        account1.setPassword("password");
        account1.setEmail("johnDoe@gmail.com");
        account1.setStatus("Student");

        account2 = new Account();
        account2.setAccountCode("acc002");
        account2.setFirstName("Jane");
        account2.setLastName("Doe");
        account2.setUserName("janedoe");
        account2.setPassword("password");
        account2.setEmail("janeDoe@gmail.com");
        account2.setStatus("Student");

        account3 = new Account();
        account3.setAccountCode("acc003");
        account3.setFirstName("Jack");
        account3.setLastName("Doe");
        account3.setUserName("jackdoe");
        account3.setPassword("password");
        account3.setEmail("jackDoegmail.com");
        account3.setStatus("Student");

        accountDao.createAccount(account1);
        accountDao.createAccount(account2);

        account1.setAccountId(accountDao.getAccountsByUsername("johndoe").getAccountId());
        account2.setAccountId(accountDao.getAccountsByUsername("janedoe").getAccountId());

        control.put("a1", account1);
        control.put("a2", account2);
    }

    @AfterEach
    public void tearDown() {
        Collection<Account> accounts = accountDao.getAccounts();
        Iterator<Account> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            Account account = iterator.next();
            iterator.remove();
            accountDao.deleteAccountByUsername(account.getAccountId());
        }
    }

    @Test
    public void testGetAccounts() {
        Collection<Account> accounts = accountDao.getAccounts();
        assertEquals(2, accounts.size());
        assertThat(accounts.toString(), is("[" + account1.toString() + ", " + account2.toString() + "]"));
    }

    @Test
    public void testSearchByAccountID() {
        String accountID = account1.getAccountId();
        Account account = accountDao.getAccountsById(accountID);
        assertEquals(account1.toString(), account.toString());
    }

    @Test
    public void testDeleteByUsername() {
        Collection<Account> accounts = accountDao.getAccounts();
        assertEquals(2, accounts.size());
        accountDao.deleteAccountByUsername(account1.getUserName());
        accounts = accountDao.getAccounts();
        assertEquals(1, accounts.size());
        assertThat(accounts.toString(), is("[" + account2.toString() + "]"));
    }

    @Test
    public void testChangePassword() {
        String username = account1.getUserName();
        String newPassword = "newPassword";
        accountDao.changePassword(username, newPassword);
        Account account = accountDao.getAccountsByUsername(username);
        assertEquals(newPassword, account.getPassword());
    }

    @Test
    public void testCreateAccount(){
        Collection<Account> accounts = accountDao.getAccounts();
        assertEquals(2, accounts.size());
        accountDao.createAccount(account3);
        account3.setAccountId(accountDao.getAccountsByUsername("jackdoe").getAccountId());

        accounts = accountDao.getAccounts();
        assertEquals(3, accounts.size());
        assertThat(accounts.toString(), is("[" + account1.toString() + ", " + account2.toString() + ", " + account3.toString() + "]"));
    }
    
}
