import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {
    private Connection connection;

    public DatabaseQuery(String jdbcUrl, String username, String password) throws SQLException {
        // 构造函数中创建数据库连接
        connection = DriverManager.getConnection(jdbcUrl, username, password);
    }

    public ResultSet exec(String sqlQurey) throws SQLException {
        // 创建 Statement 对象
        Statement statement = connection.createStatement();

        String sqlQuery1 = " where a = 1;";

        // 执行 SQL 查询
        ResultSet resultSet = statement.executeQuery(sqlQurey+sqlQuery1);

        return resultSet;
    }

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "yourUsername";
        String password = "yourPassword";

        try {
            DatabaseQuery dbQuery = new DatabaseQuery(jdbcUrl, username, password);

            // 执行 SQL 查询
            ResultSet resultSet = dbQuery.exec("select * from users");

            String queryStr = "select * from students";
            ResultSet resultSet1 = dbQuery.exec(queryStr);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
