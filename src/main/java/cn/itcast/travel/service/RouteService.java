package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * Created by Administrator on 2019/11/18.
 */
public interface RouteService {

        public PageBean<Route> pageQuery(int cid , int currentPage , int pageSize, String rname);
}
