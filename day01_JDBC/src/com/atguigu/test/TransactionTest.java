package com.atguigu.test;

import com.atguigu.utils.JDBCUtilsOld;
import org.junit.Test;

import java.sql.*;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class TransactionTest {

    /**
     * 买一本图书，id为1
     * 1、查询该图书的价格
     * 2、更新图书库存和销量
     * 3、更新用户的余额
     *
     * JDBC中和mysql的默认情况一致
     * 一个sql独占一个事务，且自动提交事务
     * 因此在默认的情况下，每个sql语句的执行都会自动提交，不会受到其他sql语句执行成功或失败的影响
     * JDBC中处理事务的过程：
     * try{
     *      connection.setAutoCommit(false);//关闭自动提交事务
     *      //实现功能的整个过程
     *      //成功，需要提交事务
     *      connection.commit();
     * } catch(){
     *      //失败，需要回滚事务
     *      connection.rollback();
     * }
     *
     * emp  dept
     * 删除部门：1、将原部门中的员工所在的部门id设置为null >0    2、删除部门信息
     */

    @Test
    public void testTransaction(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            //关闭自动提交事务
            connection.setAutoCommit(false);
            //1、查询该图书的价格
            ps = connection.prepareStatement("select price from t_book where id = 1");
            rs = ps.executeQuery();
            rs.next();
            double price = rs.getDouble(1);
            //2、更新图书库存和销量
            ps = connection.prepareStatement("update t_book set sales = sales + 1, stock = stock - 1 where id = 1");
            ps.executeUpdate();
            //3、更新用户的余额
            ps = connection.prepareStatement("update t_users set balance = balance - ? where id = 1");
            ps.setObject(1, price);
            ps.executeUpdate();
            //成功，需要提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //失败，需要回滚事务
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCUtilsOld.close(rs, ps, connection);
        }
    }

}
