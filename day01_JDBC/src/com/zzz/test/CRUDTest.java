package com.zzz.test;

import com.zzz.utils.JDBCUtilsOld;
import org.junit.Test;

import java.sql.*;

/**
 * Date:2021/1/6
 * Author:ybc
 * Description:通过JDBC测试增删改查
 */
public class CRUDTest {

    /**
     * JDBC的步骤：
     * 1、注册驱动（加载驱动）
     * 2、创建连接
     * url：使用统一资源定位符连接mysql
     * 统一资源定位符：协议://ip:port/资源
     * 连接mysql的统一资源定位符-->jdbc:mysql://localhost:3306/数据库名
     * 访问服务器中资源的统一资源定位符-->http://localhost:8080/资源
     * username：mysql的登录名
     * password：mysql的登录密码
     * 3、创建预编译对象Statement
     * 4、执行sql
     * 实现增删改-->executeUpdate()
     * 实现查询-->executeQuery()
     * 查询功能的返回值为ResultSet，可以通过rs.next()判断是否由下一个数据，并将指针指向下一个数据
     * rs.getXxx(int columnCount):通过列数获取数据
     * rs.getXxx(String columnName):通过列名（字段名）获取数据
     */

    @Test
    public void testInsert(){
        Connection connection = null;
        Statement statement = null;
        try {
            //1、注册驱动（加载驱动）
            Class.forName("com.mysql.jdbc.Driver");
            //2、创建连接
            String url = "jdbc:mysql://localhost:3306/test";
            String username = "root";
            String password = "zijiang";
            connection = DriverManager.getConnection(url, username, password);
            //3、创建预编译对象
            statement = connection.createStatement();
            //执行sql
            int i = statement.executeUpdate("insert into t_user values(null,'admin1','zijiang',23,'男'),(null,'admin2','zijiang',23,'男')");
            System.out.println("结果-->"+i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testDelete(){
        Connection connection = null;
        Statement statement = null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //创建连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            //创建预编译对象
            statement = connection.createStatement();
            //执行sql
            int i = statement.executeUpdate("delete from t_user where id = 4");
            System.out.println("结果-->"+i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, statement, connection);
        }
    }

    @Test
    public void testUpdate(){
        Connection connection = null;
        Statement statement = null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //创建连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            //创建预编译对象
            statement = connection.createStatement();
            //执行sql
            int i = statement.executeUpdate("update t_user set username = 'ybc', password = '123' where id = 5");
            System.out.println("结果-->"+i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, statement, connection);
        }
    }

    @Test
    public void testSelect(){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from t_user");
            while (rs.next()){
                System.out.println("id:"+rs.getInt(1)+",username:"+rs.getString(2)+",password:"+rs.getString(3)+",age:"+rs.getInt(4)+",sex:"+rs.getString(5));
                //System.out.println("id:"+rs.getInt("id")+",username:"+rs.getString("username")+",password:"+rs.getString("password")+",age:"+rs.getInt("age")+",sex:"+rs.getString("sex"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(rs, statement, connection);
        }
    }

    @Test
    public void testSelectOne(){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from t_user where id = 1");
            while (rs.next()){
                System.out.println("id:"+rs.getInt(1)+",username:"+rs.getString(2)+",password:"+rs.getString(3)+",age:"+rs.getInt(4)+",sex:"+rs.getString(5));
                //System.out.println("id:"+rs.getInt("id")+",username:"+rs.getString("username")+",password:"+rs.getString("password")+",age:"+rs.getInt("age")+",sex:"+rs.getString("sex"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(rs, statement, connection);
        }
    }

    @Test
    public void testLogin(){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from t_user where username = 'admin123' and password = 'zijiang'");
            System.out.println(rs.next()?"登录成功":"登录失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(rs, statement, connection);
        }
    }

}
