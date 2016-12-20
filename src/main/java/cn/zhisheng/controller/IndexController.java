package cn.zhisheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 10412 on 2016/12/20.
 */
//采用注解的方式，可以明确地定义该类为处理请求的Controller类
@Controller
public class IndexController
{

    //用于定义一个请求映射，value为请求的url，值为 / 说明，该请求首页请求，method用以指定该请求类型，一般为get和post
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index()
    {
        //处理完该请求后返回的页面，此请求返回 index.jsp页面
        return "index";
    }
}
