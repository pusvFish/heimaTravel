package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * Created by Administrator on 2019/11/18.
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {

        //6个数据，currentPage和pageSize从前端拿；totalCount和每页List从数据库拿；
        //start和totalPage直接计算。

        //封装pageBean
        PageBean<Route> pg = new PageBean<Route>();
        //设置当前页码
        pg.setCurrentPage(currentPage);
        //设置页面大小
        pg.setPageSize(pageSize);
        //计算当前cid的总数据量
        int totalCount = routeDao.findTotalCount(cid,rname);
        pg.setTotalCount(totalCount);
        //当前页从第几条数据开始拿
        //mysql:从0开始计数.分页,第n页的数据:第(currentPage-1)*pageSize+1条
        int start = (currentPage - 1)*pageSize;
        List<Route> list = routeDao.findByPage(cid , start , pageSize,rname);
        //数值每页显示的数据
        pg.setList(list);
        //计算总页面数
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:( totalCount/pageSize + 1 );
        pg.setTotalPage(totalPage);

        return pg;
    }
}
