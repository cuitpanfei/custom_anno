package cn.com.pfinfo.customanno.enums;

public enum HTTPMethodType {

    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private String value;

    HTTPMethodType(String value) {
        this.value=value;
    }

    public String value(){
        return this.value;
    }
}
