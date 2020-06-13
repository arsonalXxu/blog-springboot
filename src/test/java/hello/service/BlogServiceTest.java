package hello.service;

import hello.dao.BlogDao;
import hello.entity.BlogResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @Mock
    BlogDao blogDao;
    @InjectMocks
    BlogService blogService;
    @Test
    void getBlogFromDB() {
        blogService.getBlogs(1, 10, null, null);
        Mockito.verify(blogDao).getBlogs(1, 10, null, null);
    }

    @Test
    void returnFailureWhenExceptionThrown() {
        Mockito.when(blogDao.getBlogs(anyInt(), anyInt(), anyLong(), any())).thenThrow(new RuntimeException());
        BlogResult result = blogService.getBlogs(1, 10, 123L, null);

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("系统异常", result.getMsg());
    }
}
