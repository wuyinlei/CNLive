package com.ruolan.living;

import com.ruolan.living.action.SendOrGetGiftAction;
import com.ruolan.living.response.Error;
import com.ruolan.living.response.ResponseObj;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private static final String RequestAction_Is_Send = "isSend";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String extra =  req.getParameter(RequestAction_Is_Send);


        if (extra == null || "".equals(extra)) {
            ResponseObj responseObject = ResponseObj.getError(
                    Error.ERROR_CODE_EXCEPTION, Error.getErrorMsgException(Error.ERROR_CODE_EXCEPTION));
            ResponseObj.send(resp, responseObject);
            return;
        }

        if (extra.equals("send")){
            //创建直播房间的action
            new SendOrGetGiftAction().doAction(req, resp, true);
        } else {
            //获取直播列表的action
            new SendOrGetGiftAction().doAction(req, resp, false);
        }

    }
}
