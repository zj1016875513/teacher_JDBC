package com.zzz.test;

import java.sql.*;

public class test1 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1、注册驱动（加载驱动）
        Class.forName("com.mysql.jdbc.Driver");
        //2、创建连接
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "zijiang";

        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = connection.prepareStatement("select max(age),username from t_user");
//        ps.setObject(1,6);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        int maxage = resultSet.getInt(1);
        String name = resultSet.getString(2);
        System.out.println(maxage);
        System.out.println(name);

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps !=null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection !=null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
