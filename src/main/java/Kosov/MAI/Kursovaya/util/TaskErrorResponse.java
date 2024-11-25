package Kosov.MAI.Kursovaya.util;

public class TaskErrorResponse {
    String message;

    public TaskErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}