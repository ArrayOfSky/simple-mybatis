package com.arrayofsky.arrayofskymybatissimple.test;

/**
 * 用于测试的dao
 */
public interface UserDao {
    String queryUserName(String uId);

    Integer queryUserAge(String uId);
}
