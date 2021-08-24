package com.atguigu.dao;

import com.atguigu.bean.User;
import org.junit.Test;

import java.util.List;

/**
 * Date:2021/1/8
 * Author:ybc
 * Description:
 */
public class UserDao extends BaseDao<User> {

    @Test
    public void testInsert(){
        String username = "ybc";
        String password = "123";
        int age = 33;
        String sex = "男";
        String sql = "insert into t_user values(null,?,?,?,?)";
        int i = this.update(sql, username, password, age, sex);
        System.out.println(i);
    }

    @Test
    public void testSelectOne(){
        int id = 1;
        String sql = "select * from t_user where id = ?";
        User user = this.getBean(User.class, sql, id);
        System.out.println(user);
    }

    @Test
    public void testSelectMore(){
        String sql = "select id,name username,password,age,sex from t_user";
        List<User> list = this.getBeanList(User.class, sql);
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void testGetCount(){
        String sql = "select count(*) from t_user";
        /*Long count = (Long) this.getSingleData(sql);
        int i = count.intValue();*/
        long count = (Long) this.getSingleData(sql);
        int i = (int) count;
        System.out.println(count);
    }

}
