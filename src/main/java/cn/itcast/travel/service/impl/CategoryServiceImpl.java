package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2019/11/16.
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.redis查询
        Jedis jedis = JedisUtil.getJedis();

        //2.判断查询是否为空
        //Set<String> categorys = jedis.zrange("category",0,-1);
        //将jedis里面的cid与cname一同取出
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        System.out.println(categorys);

        List<Category> cs = null;
        if(categorys==null || categorys.size()==0){
            //3.为空，从数据库查询
            cs = categoryDao.findAll();
            //存入jedis
            for(int i=0; i<cs.size(); i++){
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            //4.不为空，因为取出来为set，要转变为List
            cs = new ArrayList<Category>();
            for(Tuple tuple:categorys ){
                Category category =new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        System.out.println(cs);
        return cs;
    }
}
