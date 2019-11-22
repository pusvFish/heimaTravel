package cn.itcast.travel.domain;

import java.util.List;

/**
 * Created by Administrator on 2019/11/17.
 */
//添加泛型<T>，方便传入数据集合List
public class PageBean<T> {
    //	totalCount：总数据量
    private int totalCount;
    //	totalPage:总页数
    private int totalPage;
    //	pageSize：页面大小
    private int pageSize;
    //	currentPage：当前页
    private int currentPage;
    //	students：当前页的学生信息集合
    private List<T> list;


    public PageBean() {
        super();
    }

    public PageBean(int currentPage, List<T> list, int pageSize, int totalCount, int totalPage) {
        this.currentPage = currentPage;
        this.list = list;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public int getTotalCount() {

        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
