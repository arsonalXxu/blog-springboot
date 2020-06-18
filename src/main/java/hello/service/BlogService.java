package hello.service;

import hello.dao.BlogDao;
import hello.entity.Blog;
import hello.entity.BlogListResult;
import hello.entity.BlogResult;
import hello.entity.Result;
import hello.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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

    public Result getBlogs(Integer page, Integer pageSize, Long userId, Boolean atIndex) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pageSize, userId, atIndex);
            int count = blogDao.count(userId);
            int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            return BlogListResult.success("获取成功", count, page, pageCount, blogs);
        } catch (Exception e) {
            return BlogResult.failure("系统异常");
        }

    }

    public Result createBlog(Blog blog) {
        return BlogResult.createSuccess("创建成功", blogDao.createBlog(blog));
    }

    public Result modifyBlog(Blog newBlog) {
        Blog blog = blogDao.selectBlogById(newBlog.getId());
        if (blog == null) {
            return BlogResult.failure("博客不存在");
        } else {
            if (blog.getUserId().longValue() == newBlog.getUserId().longValue()) {
                return BlogResult.success("修改成功", blogDao.updateBlog(blog));
            } else {
                return BlogResult.failure("无法修改别人的博客");
            }
        }
    }

    public Result deleteBlog(Long blogId, User user) {
        Blog blog = blogDao.selectBlogById(blogId);
        if (blog == null) {
            return BlogResult.failure("博客不存在");
        } else {
            if (blog.getUserId().longValue() == user.getId().longValue()) {
                blogDao.deleteBlog(blogId);
                return BlogResult.success("删除成功", null);
            } else {
                return BlogResult.failure("无法修改别人的博客");
            }
        }
    }

    public Result getBlog(Long blogId) {
        try {
            return BlogResult.success("获取成功", blogDao.selectBlogById(blogId));
        } catch (Exception e) {
            return BlogResult.failure("系统异常");
        }
    }
}
