package com.brasizza.marcus.gertec_printer;
import com.google.gson.Gson;


class ReturnObject<T> {
    private String message;
    private boolean success;
    private T content;

    public ReturnObject(String message, T content, boolean success ) {
        this.message = message;
        this.success = success;
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public T getContent() {
        return content;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}