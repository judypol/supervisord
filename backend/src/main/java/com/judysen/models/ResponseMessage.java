package com.judysen.models;

import java.io.Serializable;

/**
 * Created by lizhihua on 2018/9/28.
 */
public class ResponseMessage<T> implements Serializable {
    public static final long serialVersionUID=44L;
    private boolean success;
    private String msg;
    private T data;

    public ResponseMessage(){
        this.success=false;
        this.msg="系统错误";
        this.data=null;
    }
    public ResponseMessage(T data){
        this.success=true;
        this.msg="成功!";
        this.data=data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
