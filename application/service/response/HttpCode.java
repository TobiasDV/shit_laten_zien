package application.service.response;

public enum HttpCode {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    HttpCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
