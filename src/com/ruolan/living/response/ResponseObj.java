package com.ruolan.living.response;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseObj {


    public static String SUCCESS_CODE = "1";
    public static String FAIL_CODE = "0";

    private String code;
    private String errCode;
    private String errMsg;

    private Object data;


    public static ResponseObj getSuccess(Object data) {

        ResponseObj successObj = new ResponseObj();
        successObj.code = SUCCESS_CODE;
        successObj.data = data;
        successObj.errCode = "";
        successObj.errMsg = "";
        return successObj;
    }

    public static ResponseObj getError(String errCode, String errMsg) {

        ResponseObj successObj = new ResponseObj();
        successObj.code = FAIL_CODE;
        successObj.data = null;
        successObj.errCode = errCode;
        successObj.errMsg = errMsg;
        return successObj;
    }


    public static void send(HttpServletResponse resp, ResponseObj responseObj)  {
        try {
            resp.setHeader("Content-type","text/html;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            //获取到信息之后  发送结果
            PrintWriter writer = resp.getWriter();
            String responseStr = new Gson().toJson(responseObj);
            writer.println(responseStr);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
