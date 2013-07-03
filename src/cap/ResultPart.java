package cap;

public class ResultPart<T> {
    private T data = null;
    private Info info = null;

    public ResultPart() {}

    public ResultPart(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(final Info info) {
        this.info = info;
    }
}
