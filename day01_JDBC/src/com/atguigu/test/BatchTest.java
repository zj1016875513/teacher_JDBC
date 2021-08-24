package com.atguigu.test;

import com.atguigu.utils.JDBCUtilsOld;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class BatchTest {

    /**
     * 向t_user中添加1000条数据
     * 收mysql必须支持批处理，需要在链接地址后添加请求参数?rewriteBatchedStatements=true
     * 可以通过addBatch()方法将sql先攒起来
     * 再通过executeBatch()将攒起来的所有sql一起处理执行
     */

    @Test
    public void testInsertMore(){
        long start = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zijiang");
            for (int i = 1; i <= 10000; i++) {
                ps = connection.prepareStatement("insert into bbb(username) values('admin"+i+"')");
                ps.executeUpdate();
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时："+(end-start));//16785
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, ps, connection);
        }
    }

    @Test
    public void testBatch(){
        long start = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true", "root", "zijiang");
            ps = connection.prepareStatement("insert into bbb(username) values(?)");
            for (int i = 1; i <= 10000; i++) {
                ps.setObject(1, "admin"+i);
                //将所有的sql攒成一批，等到结束之后一起发送到mysql
                ps.addBatch();
            }
            //将攒成一批的所有sql发送到mysql一起执行
            ps.executeBatch();
            long end = System.currentTimeMillis();
            System.out.println("耗时："+(end-start));//16785
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsOld.close(null, ps, connection);
        }
    }


}
