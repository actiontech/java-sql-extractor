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

    public ResultSet exec(String sqlQuery1) throws SQLException {
        // 创建 Statement 对象
        Statement statement = connection.createStatement();

        String sqlQurey = sqlQuery1 + " WHERE a = 1;";

        // 执行 SQL 查询
        ResultSet resultSet = statement.executeQuery(sqlQurey);

        return resultSet;
    }

    public void closeConnection() throws SQLException {
        // 关闭数据库连接
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "yourUsername";
        String password = "yourPassword";

        try {
            DatabaseQuery dbQuery = new DatabaseQuery(jdbcUrl, username, password);

            // 执行 SQL 查询
            String sqlQuery = "SELECT * FROM employees";
            ResultSet resultSet = dbQuery.exec(sqlQuery);

            // 处理查询结果 
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                System.out.println("Employee ID: " + employeeId);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
            }

            // 关闭数据库连接
            dbQuery.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
