package hello.service;

import hello.dao.BlogDao;
import hello.entity.Blog;
import hello.entity.BlogResult;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {
    private BlogDao blogDao;

    @Inject
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
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
}
