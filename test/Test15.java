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

        // 执行 SQL 查询
        ResultSet resultSet = statement.executeQuery(sqlQurey);

        return resultSet;
    }

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "yourUsername";
        String password = "yourPassword";

        try {
            DatabaseQuery dbQuery = new DatabaseQuery(jdbcUrl, username, password);

            // 执行 SQL 查询
            String whereStr = " where 1=1;";

            ResultSet resultSet = dbQuery.exec("select * from users"+whereStr);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
