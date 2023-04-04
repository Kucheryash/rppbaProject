package DB;

import java.sql.*;
import java.util.ArrayList;

public class DBConnect {
    private static String dbHost = "localhost";
    private static String dbPort = "3306";
    private static String dbUser = "root";
    private static String dbPass = "j08260716";
    private static String dbName = "rppba";
    public static Connection dbConnect;
    static ArrayList<String[]> masResult;
    private static Statement statement;
    private static ResultSet resultSet;

    public static Connection getDBConnect() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnect = DriverManager.getConnection(connectionString, dbUser, dbPass);
        //System.out.println(dbConnect);
        statement = dbConnect.createStatement();
        return dbConnect;
    }


    public static ArrayList<String[]> getArrayResult(String str) {
        masResult = new ArrayList<String[]>();
        try {
            resultSet = statement.executeQuery(str);
            int count = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                String[] arrayString = new String[count];
                for (int i = 1;  i <= count; i++)
                    arrayString[i - 1] = resultSet.getString(i);

                masResult.add(arrayString);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return masResult;
    }

}
