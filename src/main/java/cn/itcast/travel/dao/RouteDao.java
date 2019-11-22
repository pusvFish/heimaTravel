package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * Created by Administrator on 2019/11/18.
 */
public interface RouteDao {
    /**
     *查询这种cid分类一共多少条数据
     * @param cid
     * @return
     */
    public int findTotalCount(int cid,String rname);

    /**
     * 查询当前页的数据集合
     * @param cid
     * @param start //从第几条数据开始查询
     * @param pageSize
     * @return
     */
    public List<Route> findByPage(int cid , int start , int pageSize,String rname);

}
