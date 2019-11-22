package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
       //1.根据用户名查询用户对象
        User u = userDao.findByUserName(user.getUsername());
        if (u!=null){
            //用户存在,注册失败
            return false;
        }
        //注册成功
        //2.保存用户信息
        //2.1设置激活码,唯一字符串
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        //2.2设置激活状态
        userDao.save(user);
        //3.激活邮件发送,邮件正文 ?
        String content ="<a href ='http://localhost:8080/travel/user/active?code="+user.getCode()+"'>点击激活[黑马旅游网]</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户
        User user = userDao.findByCode(code);
        if (user!=null){
            //2.调用userDao修改用户激活状态
            userDao.updateUserStatus(user);
        }
        return true;
    }

    /**
     * 用户登录
     * @param loginUser
     * @return
     */
    @Override
    public User login(User loginUser) {
        return userDao.findByUsernameAndPassword(loginUser.getUsername(),loginUser.getPassword());
    }
}
