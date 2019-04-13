package com.omnicuris.falcon.model;

import org.springframework.stereotype.Component;

/**
 * Created by Jagadish Varma on 4/13/2019.
 */
@Component
public class ResponseObj<T> {

    private int code;

    private String error;

    private T payload;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
