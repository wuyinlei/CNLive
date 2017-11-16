package com.ruolan.living;

import com.ruolan.living.action.CreateRoomAction;
import com.ruolan.living.model.RoomInfo;
import com.ruolan.living.response.Error;
import com.ruolan.living.response.ResponseObj;
import com.ruolan.living.sqlutils.SqlManager;
import com.ruolan.living.tag.LocalOrRemoteTag;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class RoomServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String RequestParamKey_Action = "action";
    private static final String RequestAction_Create = "create";

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter(RequestParamKey_Action);

        if (action == null || "".equals(action)) {
            ResponseObj responseObject = ResponseObj.getError(
                    Error.ERROR_CODE_EXCEPTION, Error.getErrorMsgException(Error.ERROR_CODE_EXCEPTION));
            responseObject.send(resp, responseObject);
            return;
        }

        try {
            if (RequestAction_Create.equals(action)) {
                //创建直播房间的action
                new CreateRoomAction().doAction(req, resp);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            ResponseObj responseObject = ResponseObj.getError(
                    Error.ERROR_CODE_EXCEPTION,
                    Error.getErrorMsgException(e.getMessage()));
            responseObject.send(resp, null);

        }

    }

}
