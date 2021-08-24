package com.atguigu.test;

import com.atguigu.bean.User;
import com.atguigu.utils.JDBCUtils;
import com.atguigu.utils.JDBCUtilsOld;
import com.atguigu.utils.JDBCUtilsByDruid;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
//将数据库中的数据取出，放入对象中。
public class SelectDataToBeanTest {

    @Test
    public void selectOneToBean(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement("select * from t_user where id = 1");
            rs = ps.executeQuery();
            User user = null;
            if(rs.next()){
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
            }
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(connection);
        }
    }

    //将数据库中的数据取出，放入新创建的对象中再放入集合中。
    @Test
    public void testSelectMoreToBeanList(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            ps = connection.prepareStatement("select * from t_user");
            rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while(rs.next()){
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                list.add(user);
            }
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(rs, ps, connection);
        }
    }

}
