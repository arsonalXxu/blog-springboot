package hello.entity;

import java.util.List;

public class BlogResult extends Result<Blog> {
    protected BlogResult(String status, String msg, Blog data) {
        super(status, msg, data);
    }

    public static Result failure(String msg) {
        return new BlogResult("fail", msg, null);
    }

    public static Result createSuccess(String msg, Blog blog) {
        return new BlogResult("ok", msg, blog);
    }

    public static Result success(String msg, Blog blog) {
        return new BlogResult("ok", msg, blog);
    }
}
