package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2019/11/16.
 */
public interface CategoryDao {

    public List<Category> findAll();


}
