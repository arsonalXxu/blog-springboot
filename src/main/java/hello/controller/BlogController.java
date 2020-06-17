package hello.controller;

import hello.entity.BlogResult;
import hello.entity.Result;
import hello.service.BlogService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class BlogController {
    private BlogService blogService;

    @Inject
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
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

    @PostMapping(value = "/blog", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ResponseBody
    public Result createBlog(@RequestBody Map<String, String> blogPayload) {
        String title = blogPayload.get("title");
        String content = blogPayload.get("content");
        String description = blogPayload.get("description");
        if (title == null || title.length() > 100 || title.length() == 0) {
            return BlogResult.failure("标题不能为空且不能超过100字符");
        }
        if (content == null || content.length() > 10000 || content.length() == 0) {
            return BlogResult.failure("内容不能为空且不能超过10000字符");
        }
        return blogService.createBlog(title, content, description);
    }
}
