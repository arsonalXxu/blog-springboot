package hello.dao;

import hello.entity.Blog;
import hello.entity.BlogResult;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {
    private final SqlSession sqlSession;

    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, Integer pageSize, Long userId, Boolean atIndex) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("offset", (page - 1) * pageSize);
        parameters.put("limit", pageSize);
        return this.sqlSession.selectList("selectBlog", parameters);
    }

    public int count(Long userId) {
        int countBlog = sqlSession.selectOne("countBlog", userId);
        return countBlog;
    }
}
