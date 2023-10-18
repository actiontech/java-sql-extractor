import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCDemo {
    static String sqlQuery = "SELECT * FROM your_table_name;";

    public static void main(String[] args) {
        try {
            // 连接到数据库
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // 创建一个Statement对象
            Statement statement = connection.createStatement();

            // 执行查询
            ResultSet resultSet = statement.executeUpdate(sqlQuery);
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
