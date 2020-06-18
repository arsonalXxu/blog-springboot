package hello.controller;

import hello.Utils.AssertUtils;
import hello.entity.Blog;
import hello.entity.BlogListResult;
import hello.entity.BlogResult;
import hello.entity.Result;
import hello.entity.User;
import hello.service.AuthService;
import hello.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final AuthService authService;

    @Inject
    public BlogController(BlogService blogService, AuthService authService) {
        this.blogService = blogService;
        this.authService = authService;
    }

    @GetMapping("/blog")
    @ResponseBody
    public Result getBlogs(@RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "userId", required = false) Long userId,
                           @RequestParam(value = "atIndex", required = false) Boolean atIndex) {
        if (page == null || page <= 0) {
            page = 1;
        }

        return blogService.getBlogs(page, 10, userId, atIndex);
    }

    @PostMapping(value = "/blog", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Result createBlog(@RequestBody Map<String, String> blogPayload) {
        return authService.getCurrentUser().map(user -> blogService.createBlog(fromParam(blogPayload, user)))
                .orElse(BlogResult.failure("登录后才能操作"));

    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public Result getBlog(@PathVariable("blogId") Long blogId) {
        return blogService.getBlog(blogId);
    }

    private Blog fromParam(Map<String, String> blogPayload, User user) {
        Blog blog = new Blog();
        String title = blogPayload.get("title");
        String content = blogPayload.get("content");
        String description = blogPayload.get("description");

        AssertUtils.assertTrue(StringUtils.isNotBlank(title) && title.length() <= 100, "title is invalid!");
        AssertUtils.assertTrue(StringUtils.isNotBlank(content) && content.length() <= 10000, "content is invalid!");
        description = StringUtils.isNotBlank(description) ? description : content.substring(0, 100) + " ...";

        blog.setTitle(title);
        blog.setContent(content);
        blog.setDescription(description);
        blog.setUserId(user.getId());
        blog.setUser(user);
        return blog;
    }

    // PATCH /blog/:blogId， 根据id修改博客
    @PatchMapping("/blog/{blogId}")
    public Result modifyBlog(@PathVariable("blogId") Long blogId, @RequestBody Map<String, String> blogPayload) {
        return authService.getCurrentUser().map(user -> blogService.modifyBlog(fromParam(blogPayload, user)))
                .orElse(BlogResult.failure("登录后才能操作"));
    }

    // DELETE /blog/:blogId
    @DeleteMapping("/blog/{blogId}")
    public Result deleteBlog(@PathVariable("blogId") Long blogId) {
        return authService.getCurrentUser().map(user -> blogService.deleteBlog(blogId, user))
                .orElse(BlogResult.failure("登录后才能操作"));
    }
}
