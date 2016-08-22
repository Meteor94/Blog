package com.liwei.controller.admin;

import com.liwei.entity.BlogType;
import com.liwei.entity.PageBean;
import com.liwei.service.BlogService;
import com.liwei.service.BlogTypeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liwei on 2016/8/21.
 */
@RequestMapping("/admin/blogType")
@Controller
public class BlogTypeAdminController {

    private static final Logger logger = LoggerFactory.getLogger(BlogTypeAdminController.class);
    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;


    /**
     * 前台使用 easyui 分页组件
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(
            String page,
            String rows){
        logger.debug("easyui 分页组件传来 page => " + page);
        logger.debug("easyui 分页组件传来 rows => " + rows);
        PageBean pageBean = new PageBean();
        pageBean.setPage(Integer.parseInt(page));
        pageBean.setPageSize(Integer.parseInt(rows));

        Map<String,Object> params = new HashMap<>();
        params.put("start",pageBean.getStart());
        params.put("pageSize",pageBean.getPageSize());
        List<BlogType> blogTypeList = blogTypeService.list(params);
        Long total = blogTypeService.getTotal(params);

        Map<String,Object> result = new HashMap<>();
        result.put("total",total);
        result.put("rows",blogTypeList);
        return result;

    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Map<String,Object> add(
            @RequestParam(value = "id",required = false) String id,
            @RequestParam(value = "typeName",required = true) String typeName,
            @RequestParam(value = "orderNo",required = true) String orderNo){
        logger.debug("id => " + id);
        logger.debug("typeName => " + typeName);
        logger.debug("orderNo => " + orderNo);
        BlogType blogType = new BlogType();
        blogType.setTypeName(typeName);
        blogType.setOrderNo(Integer.parseInt(orderNo));
        Integer updateNum = 0;
        if(StringUtils.isBlank(id)){
            updateNum =blogTypeService.add(blogType);
        }else {
            blogType.setId(Integer.parseInt(id));
            updateNum = blogTypeService.update(blogType);
        }
        Map<String,Object> result = new HashMap<>();
        if(updateNum>0){
            result.put("success",true);
        }else {
            result.put("success",false);
            result.put("errorInfo","添加失败。");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Map<String,Object> delete(@RequestParam("deleteIdsStr") String ids){
        logger.debug("deleteIdsStr => " + ids);
        List<Integer> idList = new ArrayList<>();
        String[] idStrArray = ids.split(",");
        for(String id:idStrArray){
            idList.add(Integer.parseInt(id));
        }
        Integer deleteNum = blogTypeService.deleteList(idList);

        Map<String,Object> result = new HashMap<>();
        if(deleteNum > 0){
            result.put("success",true);
            logger.debug("成为删除了 " + deleteNum + "条记录。");
        }else {
            result.put("success",false);
            result.put("errorInfo","删除失败！");
        }
        return result;
    };

}
