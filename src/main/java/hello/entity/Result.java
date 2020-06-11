package hello.entity;

public class Result {
    private String status;
    private String msg;
    private Boolean isLogin;
    private Object data;

    // 静态工厂方法，重构
    public static Result failure(String msg) {
        return new Result("fail", msg, false);
    }

    public static Result returnOk(String msg) {
        return new Result("ok", msg, false);
    }

    public static Result success(String msg, Object data) {
        return new Result("ok", msg, true, data);
    }

    private Result(String status, String msg, Boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    private Result(String status, String msg, Boolean isLogin, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public Object getData() {
        return data;
    }
}
