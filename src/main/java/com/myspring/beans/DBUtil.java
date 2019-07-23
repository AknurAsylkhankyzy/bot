package com.myspring.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
    private static Connection connection;
    private static String dbName="rates";
    private static String userName="root";
    private static String password="";

    static {
        connect();
    }
    private static void connect(){
        System.out.println(dbName);
        System.out.println(userName);
        System.out.println(password);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName+"?useUnicode=true&serverTimezone=UTC&characterEncoding=utf-8", userName, password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return  connection;
    }

    private void disconnect(){
        try{
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
