package com.zzz.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class DruidTest {

    /**
     * druid：德鲁伊数据源
     * 数据库连接池
     * 需要设置
     * driverClassName:驱动类名称
     * url:连接地址
     * username:登录mysql的用户名
     * password:登录mysql的密码
     * initialSize:数据库连接池初始化连接的个数
     * maxActive:数据库连接池所支持的最大连接数量
     * maxWait:当连接全部分配完之后，再次获取连接所需要等待的最大时间
     * 通过druid获取的Connection对象，关闭的close()并不是将连接断开释放掉，而是将连接返还给druid
     */

    @Test
    public void testDruid(){
        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/test");
            dataSource.setUsername("root");
            dataSource.setPassword("zijiang");
            dataSource.setInitialSize(0);
            dataSource.setMaxActive(5);
            Connection connection1 = dataSource.getConnection();
            System.out.println(connection1);
            connection1.close();
            Connection connection2 = dataSource.getConnection();
            System.out.println(connection2);
            connection2.close();
            Connection connection3 = dataSource.getConnection();
            System.out.println(connection3);
            Connection connection4 = dataSource.getConnection();
            System.out.println(connection4);
            Connection connection5 = dataSource.getConnection();
            System.out.println(connection5);
            Connection connection6 = dataSource.getConnection();
            System.out.println(connection6);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
