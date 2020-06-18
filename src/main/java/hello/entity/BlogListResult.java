package hello.entity;

import java.util.List;

public class BlogListResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;

    public BlogListResult(String status, String msg, List<Blog> data) {
        super(status, msg, data);
    }

    public static BlogListResult success(String msg, int total, int page, int totalPage, List<Blog> data) {
        return new BlogListResult("ok", msg, total, page, totalPage, data);
    }

    public BlogListResult(String status, String msg, int total, int page, int totalPage, List<Blog> data) {
        super(status, msg, data);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public BlogListResult(String status, String msg) {
        super(status, msg, null);
    }

    public static BlogListResult failure(String msg) {
        return new BlogListResult("fail", msg);
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
