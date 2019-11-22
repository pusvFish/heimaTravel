package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2019/11/18.
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        String rname = request.getParameter("rname");



        //2.处理参数
        int cid=0;  //类别id
        if(cidStr!=null && cidStr.length() > 0){
            cid = Integer.parseInt(cidStr);
        }

        int pageSize=0;  //页面大小，默认大小为5
        if(pageSizeStr!=null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        int currentPage=0;  //当前页码，默认为1页
        if(currentPageStr!=null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        //3.调用service查训pageBean对象
        RouteService routeService = new RouteServiceImpl();
        PageBean<Route> pg = routeService.pageQuery(cid,currentPage,pageSize,rname);

        //4.将pageBean对象序列化json返回
        writeValue(pg,response);

    }


}
