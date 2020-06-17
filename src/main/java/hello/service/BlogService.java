package hello.service;

import hello.controller.AuthController;
import hello.dao.BlogDao;
import hello.entity.Blog;
import hello.entity.BlogResult;
import hello.entity.LoginResult;
import hello.entity.Result;
import hello.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {
    private BlogDao blogDao;
    private UserService userService;

    @Inject
    public BlogService(BlogDao blogDao, UserService userService) {
        this.blogDao = blogDao;
        this.userService = userService;
    }

    public BlogResult getBlogs(Integer page, Integer pageSize, Long userId, Boolean atIndex) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pageSize, userId, atIndex);
            int count = blogDao.count(userId);
            int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            return BlogResult.success("获取成功", count, page, pageCount, blogs);
        } catch (Exception e) {
            return BlogResult.failure("系统异常");
        }

    }

    public Result createBlog(String title, String content, String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication == null ? null : authentication.getName());
        List<Blog> blogs = new ArrayList<>();

        if (user != null) {
            if (description == null || description.length() == 0) {
                description = content.substring(0, 100) + "...";
            }
            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setContent(content);
            blog.setDescription(description);
            blog.setUserId(user.getId());
            blog.setUser(user);
            blogs.add(blog);
            blogDao.createBlog(blog);
            return BlogResult.createSuccess("创建成功", blogs);
        } else {
            return BlogResult.failure("登录后才能操作");
        }

    }
}
