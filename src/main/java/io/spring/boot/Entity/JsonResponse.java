package io.spring.boot.Entity;

public class JsonResponse {

    private String status;
    private Object result;

    private Object result1;

    private Object result2;

    private Object result3;
    public JsonResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult1() {
        return result1;
    }

    public void setResult1(Object result1) {
        this.result1 = result1;
    }

    public Object getResult2() {
        return result2;
    }

    public void setResult2(Object result2) {
        this.result2 = result2;
    }

    public Object getResult3() {
        return result3;
    }

    public void setResult3(Object result3) {
        this.result3 = result3;
    }
}
