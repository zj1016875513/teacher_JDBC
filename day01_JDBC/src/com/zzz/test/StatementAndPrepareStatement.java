package com.zzz.test;

import com.zzz.utils.JDBCUtilsOld;

import java.sql.*;
import java.util.Scanner;

/**
 * Date:2021/1/6
 * Author:ybc
 * Description:
 */
public class StatementAndPrepareStatement {

    /**
     * Statement预编译对象的缺点：
     * 1、拼接字符串非常麻烦
     * 2、会造成sql注入：可以通过一些特殊的值使sql的条件成立
     * select * from t_user where username = '"+username+"' and password = '"+password+"';
     * username = a' or '1'='1
     * password = b' or '2' = '2
     * 3、不能处理blob类型的字段
     *
     * PreparedStatement的优点：
     * 1、通过占位符为某个字段进行赋值，并且在为字符串类型的字段赋值时，可以自动加单引号
     * ps.setInt(int paramCount, int paramValue);
     * ps.setString(int paramCount, String paramValue);
     * ps.setObject(int paramCount, Object paramValue);//建议使用这种方式
     * 2、防止sql注入
     * 3、能够处理blob类型的字段
     */
    public static void main(String[] args){
        //testLogin();
        //testParamByPreparedStatement();
        testLoginByPreparedStatement();
    }

    public static void testLoginByPreparedStatement(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            ps = connection.prepareStatement("select * from t_user where username = ? and password = ?");
            ps.setObject(1, username);
            ps.setObject(2, password);
            resultSet = ps.executeQuery();
            System.out.println(resultSet.next()?"登录成功":"登录失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(resultSet, ps, connection);
        }
    }

    public static void testParamByPreparedStatement(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        System.out.println("请输入年龄：");
        int age = Integer.parseInt(sc.nextLine());
        System.out.println("请输入性别：");
        String sex = sc.nextLine();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            ps = connection.prepareStatement("insert into t_user values(null,?,?,?,?)");
            /*ps.setString(1,username);
            ps.setString(2,password);
            ps.setInt(3,age);
            ps.setString(4,sex);*/
            ps.setObject(1,username);
            ps.setObject(2,password);
            ps.setObject(3,age);
            ps.setObject(4,sex);
            int i = ps.executeUpdate();
            System.out.println("结果-->"+i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, ps, connection);
        }
    }

    public static void testLogin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from t_user where username = '"+username+"' and password = '"+password+"'");
            if(resultSet.next()){
                System.out.println("登陆成功");
            }else{
                System.out.println("登陆失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, statement, connection);
        }
    }

    public static void testParam(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        System.out.println("请输入年龄：");
        int age = Integer.parseInt(sc.nextLine());
        System.out.println("请输入性别：");
        String sex = sc.nextLine();
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            statement = connection.createStatement();
            statement.executeUpdate("insert into t_user values(null, '"+username+"', '"+password+"', "+age+", '"+sex+"')");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, statement, connection);
        }
    }

}
