package hello.entity;

import java.util.List;

public class BlogResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;

    public static BlogResult success(String msg, int total, int page, int totalPage, List<Blog> data) {
        return new BlogResult("ok", msg, total, page, totalPage, data);
    }

    public BlogResult(String status, String msg, int total, int page, int totalPage, List<Blog> data) {
        super(status, msg, data);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public BlogResult(String status, String msg) {
        super(status, msg, null);
    }

    public static BlogResult failure(String msg) {
        return new BlogResult("fail", msg);
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
