package com.ruolan.living.response;

public class Error {

    public static String ERROR_CODE_EXCEPTION = "500";
    private static String ERROR_MSG_EXCEPTION = "服务器异常";

    public static String getErrorMsgException(String e) {
        return ERROR_MSG_EXCEPTION + " : " + e;
    }

    public static String ERROR_CODE_NO_PARAM = "501";
    private static String ERROR_MSG_NO_PARAM = "缺少参数值";

    public static String getErrorMsgNoParam(String e) {
        return ERROR_CODE_NO_PARAM + ":" + e;
    }


    public static String ERROR_CODE_ERROR_PARAM = "502";
    private static String ERROR_MSG_ERROR_PARAM = "参数值不正确";

    public static String getErrorCodeErrorParam(String e) {
        return ERROR_CODE_ERROR_PARAM + ":" + e;
    }
}
