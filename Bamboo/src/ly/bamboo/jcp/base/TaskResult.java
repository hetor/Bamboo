package ly.bamboo.jcp.base;

/**
 * Created by hetao on 15-7-7.
 */
public class TaskResult {
    private boolean isSuccuess;
    private String message;

    public TaskResult() {

    }

    public TaskResult(boolean isSuccuess, String message) {
        this.isSuccuess = isSuccuess;
        this.message = message;
    }

    public boolean isSuccuess() {
        return isSuccuess;
    }

    public void setIsSuccuess(boolean isSuccuess) {
        this.isSuccuess = isSuccuess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[isSuccess: " + isSuccuess + ", message: " + message + "]";
    }
}
