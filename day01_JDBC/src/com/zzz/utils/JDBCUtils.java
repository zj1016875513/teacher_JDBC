package com.zzz.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class JDBCUtils {

    /**
     * Properties继承了Hashtable，因此其中也是键值对的结构
     * Properties可以操作后缀为properties的文件
     * 后缀为properties的文件中存储的数据也必须为键值对结构
     * 但是文件中只能存储文本，Properties在操作后缀为properties的文件时，只能操作字符串类型的键值对
     * Properties常用方法：
     * void setProperty(String key, String value)
     * String getProperty(String key);
     * load():读取properties文件中的数据
     * store()：向properties文件中输出数据
     */

    private static DataSource dataSource;

    static {
        try {
            Properties prop = new Properties();
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
