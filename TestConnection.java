import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class TestConnection {
    public static void main(String[] args) throws IOException {
        try {
            runTest();
        }
        catch (SQLException ex){
            for (Throwable t:ex)
                t.printStackTrace();
        }
    }
    /**
     * Runs a test by creating a table,adding a value,showing the table contents,and removing the table
     */
    public static void runTest() throws SQLException,IOException{
        try (Connection conn=getConnection();
             Statement stat=conn.createStatement()){
            stat.executeUpdate("CRATE TABLE Greetings (MESSAGE CHAR (20))");
            stat.executeUpdate("INSERT INTO Greetings VALUES ('Hello,World!')");
            try (ResultSet result=stat.executeQuery("SELECT * FROM Greetings")){
                if (result.next())
                    System.out.println(result.getString(1));

            }
            stat.executeUpdate("DROP TABLE Greetings");
        }
    }
    /**
     * Gets a connection from the properties specified in the file database.properties.
     * @return the database connection
     * SQL server version
     */
    public static Connection getConnection() throws SQLException,IOException{
       String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=test";//数据源  ！！！！注意若出现加载或者连接数据库失败一般是这里出现问题
        String Name="数据库名  比如sa";
        String Pwd="数据库密码";
        return DriverManager.getConnection(dbURL,Name,Pwd);
    }

}
