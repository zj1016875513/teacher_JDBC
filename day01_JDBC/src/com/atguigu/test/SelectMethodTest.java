package com.atguigu.test;

import com.atguigu.bean.User;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class SelectMethodTest {

    public static void main(String[] args) throws Exception {

        Class<User> clazz = null;
        User user = clazz.newInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();//获得总列数
        while (rs.next()){
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);//根据列数获取列名，即字段名，即属性名
                Field filed = clazz.getDeclaredField(columnName);
                filed.setAccessible(true);
                filed.set(user, rs.getObject(i));
            }
        }

    }

}
