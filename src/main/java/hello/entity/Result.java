package hello.entity;

public abstract class Result<T> {
    private String status;
    private String msg;
    private T data;

    protected Result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    protected Result(String status, String msg) {
        this(status, msg, null);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
