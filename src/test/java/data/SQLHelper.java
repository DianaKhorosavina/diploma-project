package data;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }


    @SneakyThrows
    public static DataHelper.StatusCard getStatusCard() {
        var statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        var status = QUERY_RUNNER.query(conn, statusSQL, new ScalarHandler<String>());
        return new DataHelper.StatusCard(status);
    }
    @SneakyThrows
    public static DataHelper.StatusCardCredit getStatusCardCredit() {
        var statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        var statusCredit = QUERY_RUNNER.query(conn, statusSQL, new ScalarHandler<String>());
        return new DataHelper.StatusCardCredit(statusCredit);
    }

}