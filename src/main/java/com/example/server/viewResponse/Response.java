package com.example.server.viewResponse;

public class Response<T> {
    public int code;
    public String msg;
    public T data;

    public static <T> Response success(){
        Response r = new Response("suc", 0, null);
        return r;
    }

    public static <T> Response fail(){
        Response r = new Response("fail", 1, null);
        return r;
    }

    public static <T> Response success(T data){
        Response r = new Response("suc", 0, data);
        return r;
    }

    private Response(String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
}
