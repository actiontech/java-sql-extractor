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
        
        try {
            // 连接到数据库
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // 创建一个Statement对象
            Statement statement = connection.createStatement();

            // 执行查询
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // 遍历结果集并输出数据
            while (resultSet.next()) {
                int id = resultSet.getInt("id"); // 假设你的表中有一个名为 "id" 的列
                String name = resultSet.getString("name"); // 假设你的表中有一个名为 "name" 的列
                // 打印查询结果
                System.out.println("ID: " + id + ", Name: " + name);
            }
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
