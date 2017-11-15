package com.ruolan.living.response;

public class Error {

    public static String ERROR_CODE_EXCEPTION = "500";
    private static String ERROR_MSG_EXCEPTION = "服务器异常";

    public static String getErrorMsgException(String e){
        return ERROR_MSG_EXCEPTION + " : " + e;
    }

}
