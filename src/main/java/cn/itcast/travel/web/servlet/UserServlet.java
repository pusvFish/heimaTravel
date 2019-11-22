package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Administrator on 2019/11/14.
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();
    private  ResultInfo resultInfo = new ResultInfo();

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("UserServlet的add方法被调用。");
        //验证码校验
        String check = request.getParameter("check");
        //从session域中获取服务器生成的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //移除验证码
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        //判断在验证码
        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            //ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误!");
            //将resultInfo对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultInfo);
            //将json数据写回客户端
            //设置content-type
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            //将数据赋值给user实体
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service完成注册
       // UserService service = new UserServiceImpl();
        boolean flag = userService.regist(user);
        //ResultInfo resultInfo = new ResultInfo();
        //4.响应结果
        if(flag){
            //注册成功
            resultInfo.setFlag(true);
        }else{
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败!");
        }
        //将resultInfo对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);


    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("UserServlet的find方法被调用。");
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
        //UserService userService = new UserServiceImpl();
        User user = userService.login(loginUser);
        //判断用户是否存
        //ResultInfo resultInfo = new ResultInfo();
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
//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType("application/json; charset=utf-8");
//        mapper.writeValue(response.getOutputStream(),resultInfo);
        writeValue(resultInfo,response);
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("UserServlet的find方法被调用。");
        //1.获取激活码
        String code = request.getParameter("code");
        if (code!=null){
            //2.调用service完成激活
            //UserService userService = new UserServiceImpl();
            boolean flag=userService.active(code);
            //3.判断标记
            String msg=null;
            if (flag){
                //激活成功
                msg ="激活成功,请<a target='_blank' href ='http://localhost:8080/travel/login.html'>登录</a>!";
            }else {
                //激活失败
                msg ="激活失败,请联系管理员!";
            }
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("UserServlet的find方法被调用。");
        request.getSession().invalidate();
        response.sendRedirect( request.getContextPath()+ "/login.html");
        System.out.println(request.getContextPath());
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("UserServlet的find方法被调用。");
        //从session域中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将user协会客户端
//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType("application/json; charset=utf-8");
//        mapper.writeValue(response.getOutputStream(),user);
        writeValue(user,response);
    }
}
