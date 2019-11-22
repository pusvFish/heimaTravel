package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户信息
        Map<String, String[]> map = request.getParameterMap();
        User loginUser = new User();
        try {
            //封装对象
            BeanUtils.populate(loginUser,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //调用service查询user
        UserService userService = new UserServiceImpl();
        User user = userService.login(loginUser);
        //判断用户是否存
        ResultInfo resultInfo = new ResultInfo();
        if (user==null){
            //登录失败,用户名密码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名密码错误!");
        }
        //判断用户是否激活
        if (user!=null&&!"Y".equals(user.getStatus())){
            //用户尚未激活
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("尚未激活,请激活!");
        }
        //判断登录成功
        if(user!=null&&"Y".equals(user.getStatus())){
            //成功登录，给页面头加上自己的名字，返回session给FindUserServlet
            request.getSession().setAttribute("user",user);
            resultInfo.setFlag(true);
        }
        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
