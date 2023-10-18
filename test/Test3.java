import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) {
        // 数据库连接信息
        String jdbcUrl = "jdbc:mysql://localhost:3306/your_database_name";
        String username = "your_username";
        String password = "your_password";
        // JDBC连接对象
        Connection connection = null;

        // 创建SQL语句
        String sqlQuery = "SELECT * FROM your_table_name;";
        String sqlQuery1 = "UPDATE your_table_name SET a='1';";
        
        try {
            
            // 连接到数据库
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // 创建一个Statement对象
            Statement statement = connection.createStatement();

            // 执行查询
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            ResultSet resultSet1 = statement.executeQuery(sqlQuery1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
