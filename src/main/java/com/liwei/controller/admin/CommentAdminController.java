package com.liwei.controller.admin;

import com.liwei.entity.Comment;
import com.liwei.entity.PageBean;
import com.liwei.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liwei on 16/8/22.
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

    private static final Logger logger = LoggerFactory.getLogger(CommentAdminController.class);

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map<String,Object> list(
            String state,
            String page,
            String rows
    ){
        logger.debug("state => " + state);
        logger.debug("page => " + page);
        logger.debug("rows => " + rows);

        PageBean pageBean = new PageBean();
        pageBean.setPage(Integer.parseInt(page));
        pageBean.setPageSize(Integer.parseInt(rows));

        Map<String,Object> params = new HashMap<>();
        params.put("start",pageBean.getStart());
        params.put("pageSize",pageBean.getPageSize());

        if(StringUtils.isNotBlank(state)){
            params.put("state",state);
        }

        List<Comment> commentList = commentService.list(params);
        Long total = commentService.getTotal(params);
        Map<String,Object> result = new HashMap<>();

        result.put("total",total);
        result.put("rows",commentList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/review",method = RequestMethod.GET)
    public Map<String,Object> review(
            String ids,
            String state){
        logger.debug("ids => " + ids);
        logger.debug("state => " + state);

        String[] idArray = ids.split(",");
        Integer updateNum = 0;
        if(idArray.length == 1){
            Comment comment = new Comment();
            comment.setId(Integer.parseInt(idArray[0]));
            comment.setState(Integer.parseInt(state));
            updateNum = commentService.update(comment);
        }else {
            List<Integer> idList = new ArrayList<>();
            for (String id :idArray){
                idList.add(Integer.parseInt(id));
            }
            updateNum = commentService.batchUpdateState(idList,Integer.parseInt(state));
        }

        Map<String,Object> result = new HashMap<>();
        if(updateNum>0){
            result.put("success",true);
            result.put("successInfo","成功更新了 " + updateNum + "条数据");
        }else {
            result.put("success",false);
            result.put("errorInfo","更新数据失败!");
        }
        return result;
    }

}
