package cn.zhisheng.controller;

import cn.zhisheng.model.UserEntity;
import cn.zhisheng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    //用户列表
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


    //访问添加用户界面
    @RequestMapping(value = "/admin/users/add", method = RequestMethod.GET)
    public String addUser()
    {
        return "admin/addUser";
    }


    //处理添加用户请求，并重定向到用户管理界面
    @RequestMapping(value = "/admin/users/addP", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") UserEntity userEntity)
    {
        // 注意此处，post请求传递过来的是一个UserEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'user'，并创建这个对象

        // 数据库中添加一个用户，该步暂时不会刷新缓存,save()方法处理完毕后，数据依然在缓冲区未写入数据库
        //userRepository.save(userEntity);

        // 数据库中添加一个用户，并立即刷新缓存并写入数据库
        userRepository.saveAndFlush(userEntity);

        // 重定向到用户管理页面，方法为 redirect:url
        return "redirect:/admin/users";
    }


    //查看用户信息详情
    //@PathVariable可以收集url中的变量，需匹配的变量用{}括起来
    // 例如：访问 localhost:8080/admin/users/show/1 ，将匹配 id = 1
    @RequestMapping(value = "/admin/users/show/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") Integer userId, ModelMap modelMap)
    {
        //找到userId所表示的用户
        UserEntity userEntity = userRepository.findOne(userId);

        //传递给请求页面
        modelMap.addAttribute("user", userEntity);

        //转到用户详情页面
        return "admin/userDetail";
    }


    //更新用户信息 页面
    @RequestMapping(value = "/admin/users/update/{id}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Integer userId, ModelMap modelMap)
    {
        //找到userId所表示的用户
        UserEntity userEntity = userRepository.findOne(userId);

        //传递给请求页面
        modelMap.addAttribute("user", userEntity);

        return "admin/updateUser";
    }

    //更新用户信息 操作
    @RequestMapping(value = "/admin/users/updateP", method = RequestMethod.POST)
    public String updateUserPost(@ModelAttribute("userP") UserEntity user)
    {
        //更新用户信息
        userRepository.updateUser(user.getNickname(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getId());

        //刷新缓冲区
        userRepository.flush();
        return "redirect:/admin/users";
    }

    //删除用户
    @RequestMapping(value = "/admin/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Integer userId)
    {
        //指定删除id为userId的用户
        userRepository.delete(userId);

        //刷新缓存区
        userRepository.flush();
        return "redirect:/admin/users";
    }


}
