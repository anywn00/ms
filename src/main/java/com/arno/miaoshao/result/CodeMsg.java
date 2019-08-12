package com.arno.miaoshao.result;

/**
 * @Author arno
 * @date 2019-08-07 22:18
 */
public class CodeMsg {
    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    public static CodeMsg PARAM_ERROR = new CodeMsg(500101,"参数异常");

    //5002XX登录异常
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500200,"手机号不能为空");
    public static CodeMsg MOBILE_EXIST = new CodeMsg(500201,"手机号不存在");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500202,"密码不能为空");
    public static CodeMsg PASSWORD_EXIST = new CodeMsg(500203,"密码错误");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
