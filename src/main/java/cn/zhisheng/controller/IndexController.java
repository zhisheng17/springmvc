package cn.zhisheng.controller;

import cn.zhisheng.model.UserEntity;
import cn.zhisheng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by 10412 on 2016/12/20.
 */
//采用注解的方式，可以明确地定义该类为处理请求的Controller类
@Controller
public class IndexController
{
    // 自动装配数据库接口，不需要再写原始的Connection来操作数据库
    @Autowired
    UserRepository userRepository;

    //用于定义一个请求映射，value为请求的url，值为 / 说明，该请求首页请求，method用以指定该请求类型，一般为get和post
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index()
    {
        //处理完该请求后返回的页面，此请求返回 index.jsp页面
        return "index";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String getUser(ModelMap modelMap)
    {
        //查询user表中所有的记录
        List<UserEntity> userList = userRepository.findAll();

        // 将所有记录传递给要返回的jsp页面，放在userList当中
        modelMap.addAttribute("userList", userList);

        // 返回pages目录下的admin/users.jsp页面
        return "admin/users";
    }




}
