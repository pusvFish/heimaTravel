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
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
       //验证码校验
        String check = request.getParameter("check");
        //从session域中获取服务器生成的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //移除验证码
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        //判断在验证码
        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo resultInfo = new ResultInfo();
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
        UserService userService = new UserServiceImpl();
        boolean flag = userService.regist(user);
        ResultInfo resultInfo = new ResultInfo();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        this.doPost(request,response);
    }
}
