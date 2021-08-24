package com.zzz.dao;

import com.zzz.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class BaseDao<T> {

    private QueryRunner queryRunner = new QueryRunner();

    /**
     * 抽象的实现增删改功能的方法
     * @param sql：要执行的sql语句
     * @param params：按照顺序为sql中占位符所赋的值
     * @return
     */
    public int update(String sql, Object... params){
        Connection connection = null;
        int i = 0;
        try {
            connection = JDBCUtils.getConnection();
            i = queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(connection);
        }
        return i;
    }

    /**
     * 查询一条数据转换为实体类对象
     * @param clazz：将数据库中的数据转换为实体类的Class对象
     * @param sql：要执行的sql语句
     * @param params：按照顺序为sql中占位符所赋的值
     * @return
     */
    public T getBean(Class<T> clazz, String sql, Object... params){
        Connection connection = null;
        T t = null;
        try {
            connection = JDBCUtils.getConnection();
            t = queryRunner.query(connection, sql, new BeanHandler<>(clazz), params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(connection);
        }
        return t;
    }

    /**
     * 查询多条数据转换为实体类对象集合
     * @param clazz：将数据库中的数据转换为实体类的Class对象
     * @param sql：要执行的sql语句
     * @param params：按照顺序为sql中占位符所赋的值
     * @return
     */
    public List<T> getBeanList(Class<T> clazz, String sql, Object... params){
        Connection connection = null;
        List<T> list = null;
        try {
            connection = JDBCUtils.getConnection();
            list = queryRunner.query(connection, sql, new BeanListHandler<>(clazz), params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(connection);
        }
        return list;
    }

    /**
     * 查询的结果为单行单列
     * 例如：未分组情况下获取分组函数的结果
     * 查询某一条数据的某个字段
     * @param sql：要执行的sql语句
     * @param params：按照顺序为sql中占位符所赋的值
     * @return
     */
    public Object getSingleData(String sql, Object... params){
        Connection connection = null;
        Object o = null;
        try {
            connection = JDBCUtils.getConnection();
            o = queryRunner.query(connection, sql, new ScalarHandler<>(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(connection);
        }
        return o;
    }

}
