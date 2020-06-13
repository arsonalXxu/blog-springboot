package hello.entity;

public class LoginResult extends Result {

    private Boolean isLogin;


    // 静态工厂方法，重构
    public static LoginResult failure(String msg) {
        return new LoginResult("fail", msg, false);
    }

    public static LoginResult returnOk(String msg) {
        return new LoginResult("ok", msg, false);
    }

    public static LoginResult success(String msg, Object data) {
        return new LoginResult("ok", msg, true, data);
    }

    private LoginResult(String status, String msg, Boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    private LoginResult(String status, String msg, Boolean isLogin, Object data) {
        super(status, msg, data);
        this.isLogin = isLogin;
    }

    public Boolean getLogin() {
        return isLogin;
    }
}
