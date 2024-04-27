package web;

import dao.AccountJdbiDAO;
import dao.JdbiDaoFactory;
import domain.Account;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.gson.GsonModule;
import java.util.Collection;
import java.util.Set;

public class Server extends Jooby {

    public Server() {
        AccountJdbiDAO accountDao = JdbiDaoFactory.getAccountDAO();

        Collection<Account> accounts = accountDao.getAccounts();

        mount(new StaticAssetModule());
        install(new GsonModule());

        install(new BasicAccessAuth(accountDao, Set.of("/api/.*"), Set.of("/exclude/")));
        mount(new AccountModule(JdbiDaoFactory.getAccountDAO()));
        mount(new CourseModule(JdbiDaoFactory.getCourseDAO()));
        mount(new EventModule(JdbiDaoFactory.getEventsDAO()));
        mount(new AssignmentModule(JdbiDaoFactory.getAssignmentsDAO()));
    }

    public static void main(String[] args) {
        System.out.println("\nStarting Server.");
        new Server()
                .setServerOptions(new ServerOptions().setPort(8087))
                .start();
    }

}
