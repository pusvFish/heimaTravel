package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/11/14.
 */

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("baseServlet的service方法被调用。");

        //获取请求路径
        String uri = req.getRequestURI();
        System.out.println("请求uri："+uri);  //  /travel/user/add
        //获取方法名
        String methodName = uri.substring(uri.lastIndexOf("/")+1);
        System.out.println("方法名:"+methodName);//  add
        //获取方法对象
        System.out.println(this);//  cn.itcast.travel.web.servlet.UserServlet


        try {

            //执行方法
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,req,resp);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将传入对象序列化为json，返回客户端
     * @param obj
     * @param response
     * @throws IOException
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);

    }

    /**
     * 将传入对象序列化为json，返回
     * @param obj
     * @param
     * @throws IOException
     */
    public String writeValueAsString(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
