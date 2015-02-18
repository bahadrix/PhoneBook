package me.bahadir.bil425.phonebook.backend;

/**
 * Created by bahadir on 16/02/15.
 */
public class Response {
    String message;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
