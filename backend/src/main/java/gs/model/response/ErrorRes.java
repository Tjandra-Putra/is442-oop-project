package gs.model.response;

import org.springframework.http.HttpStatus;

public class ErrorRes {
    HttpStatus httpStatus;
    String message;

    public ErrorRes(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
