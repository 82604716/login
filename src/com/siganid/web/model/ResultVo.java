package com.siganid.web.model;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ResultVo {
    boolean result;
    Object data;
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResultVo(boolean result) {
        this.result = result;
    }

    public ResultVo(boolean result, Object data) {
        this.result = result;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}
