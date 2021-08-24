package com.atguigu.test;

import com.atguigu.utils.JDBCUtilsOld;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class DeleteMoreAndMohuTest {

    /**
     * 批量删除，若获取的多个id使用逗号进行分割，则无法使用占位符进行赋值
     * 因为正确的语法为delete from t_user where id in (1,2,3)
     * 但是为字符串类型的占位符赋值，会自动加单引号，结果就变成了delete from t_user where id in ('1,2,3')
     * 当条件中加入1=1，是一个恒成立的条件，不影响条件的结果
     * 可以使用1=1更好的拼接and关系的条件
     * 也可以通过1!=1更好的拼接or关系的条件
     * 模糊查询比较特殊，因为要把关键字放在'%%'中，若要使用占位符进行赋值
     * 会自动价单引号，结果就变成了'%'a'%'
     * 所以在实现模糊查询时，要么手动拼接
     * 也可以使用concat('%',?,'%')再使用占位符赋值
     */

    @Test
    public void testDeleteMore(){
        String ids = "5,6,7";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            ps = connection.prepareStatement("delete from t_user where id in ("+ids+")");
            int i = ps.executeUpdate();
            System.out.println("结果-->"+i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, ps, connection);
        }
    }

    @Test
    public void testDeleteMoreByOr(){
        int[] ids = {1,2,3};
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            //delete from t_user where id = 1 or id = 2 or id = 3   id为1或者为2,或者为3的要删掉
            String sql = "delete from t_user where 1 != 1";
            for (int id : ids) {
                sql += " or id = " + id;
            }
            System.out.println(sql);
            ps = connection.prepareStatement(sql);
            int i = ps.executeUpdate();
            System.out.println("结果-->"+i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, ps, connection);
        }
    }

    @Test
    public void testMohu(){
        String mohu = "a";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            //ps = connection.prepareStatement("select * from t_user where username like '%"+mohu+"%'");
            ps = connection.prepareStatement("select * from t_user where username like concat('%',?,'%')");
            ps.setObject(1, mohu);
            rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("id:"+rs.getInt(1)+",username:"+rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(rs, ps, connection);
        }
    }

}
