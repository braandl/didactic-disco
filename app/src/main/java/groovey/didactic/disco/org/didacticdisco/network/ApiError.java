package groovey.didactic.disco.org.didacticdisco.network;


public class ApiError extends Throwable {

    private int statusCode;
    private String message;

    public ApiError() {
    }

    public ApiError(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
