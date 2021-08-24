package com.zzz.test;

import com.zzz.utils.JDBCUtilsOld;
import org.junit.Test;

import java.sql.*;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class GetPrimaryKeyTest {

    /**
     * 班级表clazz(cid,cname)
     * 学生表student(sid,sname,age,sex,cid)
     * 添加班级信息的同时，为班级分配学生
     * 1、添加班级
     * insert into clazz values()
     * 2、获取新添加班级的cid
     *
     * 3、为班级分配学生
     * update student set cid = ? where sid in (1,2,3,4,5)
     * 测试添加数据的同时获取自动增长的id
     *
     * JDBC在添加时可以获取自动增长的id
     * JDBC默认不允许获取自增的id
     * 只有在prepareStatement(String sql, int i)中
     * 只有将i设置Statement.RETURN_GENERATED_KEYS，即1才可以获取
     */
    @Test
    public void testGetPrimaryKey(){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            ps = connection.prepareStatement("insert into t_user values(null, 'ybc', '123', 33, '男')", Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, ps, connection);
        }
    }

}
