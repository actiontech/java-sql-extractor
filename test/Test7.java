import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String password = "your_password";
        
        // 从其他函数获取参数值
        String value = "SELECT * FROM your_table WHERE column_name = your_parameter_value"; 

        value = "delete from t1;";

        connection = DriverManager.getConnection(jdbcUrl, username, password);

        // 创建一个Statement对象
        Statement statement = connection.createStatement();

        // 执行查询
        ResultSet resultSet = statement.executeQuery(value);
    }
}

class HelperClass {
    public void displayMessage() {
            String querySql = "select BYTES from user_segments where segment_name =?";
            preparedStatement = conn.prepareStatement(querySql);
            preparedStatement.setString(1, segmentName.toUpperCase());
            resultSet = preparedStatement.executeQuery();
    }
}