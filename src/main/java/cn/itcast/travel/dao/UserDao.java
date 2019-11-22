package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 用户保存
     * @param user
     */
    void save(User user);

    /**
     * 通过激活码来查询用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 修改用户激活状态
     * @param user
     */
    void updateUserStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
