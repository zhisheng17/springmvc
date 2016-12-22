package cn.zhisheng.controller;

import cn.zhisheng.model.BlogEntity;
import cn.zhisheng.model.UserEntity;
import cn.zhisheng.repository.BlogRepository;
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
 * Created by 10412 on 2016/12/22.
 */
@Controller
public class BlogController
{
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    //查看所有博文
    @RequestMapping(value = "/admin/blogs", method = RequestMethod.GET)
    public String showBlogs(ModelMap modelMap)
    {
        List<BlogEntity> blogList = blogRepository.findAll();

        modelMap.addAttribute("blogList", blogList);

        return "admin/blogs";
    }


    //访问添加博客界面
    @RequestMapping(value = "/admin/blogs/add", method = RequestMethod.GET)
    public String addBlog(ModelMap modelMap)
    {
        List<UserEntity> userList = userRepository.findAll();

        //像jsp中注入用户列表
        modelMap.addAttribute("userList", userList);

       return "admin/addBlog";
    }


    //处理添加博客请求，并重定向到博客管理界面
    @RequestMapping(value = "/admin/blogs/addP", method = RequestMethod.POST)
    public String addBlogPost(@ModelAttribute("blog") BlogEntity blogEntity)
    {
        // 注意此处，post请求传递过来的是一个BlogEntity对象，里面包含了该博客的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'blog'，并创建这个对象

        //打印博客标题
        System.out.println(blogEntity.getTitle());

        //打印博客作者id
        System.out.println(blogEntity.getUserByUserId());


        // 数据库中添加一篇博客，并立即刷新缓存并写入数据库
        blogRepository.saveAndFlush(blogEntity);

        // 重定向到用户管理页面，方法为 redirect:url
        return "redirect:/admin/blogs";
    }


    //查看博客详情
    //@PathVariable可以收集url中的变量，需匹配的变量用{}括起来
    // 例如：访问 localhost:8080/admin/blogs/show/1 ，将匹配 id = 1
    @RequestMapping(value = "/admin/blogs/show/{id}")
    public String showBlog(@PathVariable("id") int id, ModelMap modelMap)
    {
        //找到id所对应的博客
        BlogEntity blog = blogRepository.findOne(id);

        //传递给请求页面
        modelMap.addAttribute("blog", blog);

        //转到博客详情页面
        return "admin/blogDetail";
    }



    // 修改博客内容，页面
    @RequestMapping("/admin/blogs/update/{id}")
    public String updateBlog(@PathVariable("id") int id, ModelMap modelMap) {

        BlogEntity blog = blogRepository.findOne(id);
        List<UserEntity> userList = userRepository.findAll();
        modelMap.addAttribute("blog", blog);
        modelMap.addAttribute("userList", userList);
        return "admin/updateBlog";
    }

    // 修改博客内容，POST请求
    @RequestMapping(value = "/admin/blogs/updateP", method = RequestMethod.POST)
    public String updateBlogP(@ModelAttribute("blogP") BlogEntity blogEntity) {
        // 更新博客信息
        blogRepository.updateBlog(blogEntity.getTitle(), blogEntity.getUserByUserId().getId(),
                blogEntity.getContent(), blogEntity.getPubDate(), blogEntity.getId());
        blogRepository.flush();
        return "redirect:/admin/blogs";
    }


    // 删除博客文章
    @RequestMapping("/admin/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") int id) {
        blogRepository.delete(id);
        blogRepository.flush();
        return "redirect:/admin/blogs";
    }

}
