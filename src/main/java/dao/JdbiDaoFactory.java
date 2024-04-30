package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class JdbiDaoFactory {

    private static final String DB_USERNAME = "postgres.lycknztdgdkgxdvbkdkj";
    private static final String DB_PASSWORD = "Info310P@sscode";

    private static final String JDBC_URI = "jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres";

    private static HikariDataSource HIKARI_DATA_SOURCE;
    private static Jdbi JDBI;

    private static void initialisePool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URI);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);

        HIKARI_DATA_SOURCE = new HikariDataSource(config);

        JDBI = Jdbi.create(HIKARI_DATA_SOURCE);
        JDBI.installPlugin(new SqlObjectPlugin());
    }

    public static AccountJdbiDAO getAccountDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(AccountJdbiDAO.class);
    }
  
    public static CoursesJdbiDAO getCourseDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(CoursesJdbiDAO.class);
    }

    public static CourseListsJdbiDAO getCourseListDAO() {
        if(HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(CourseListsJdbiDAO.class);
    }
    
     public static EventsJdbiDAO getEventsDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(EventsJdbiDAO.class);
    }

    public static AssignmentsJdbiDAO getAssignmentsDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(AssignmentsJdbiDAO.class);
    }  
}

