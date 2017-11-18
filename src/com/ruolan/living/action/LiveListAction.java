package com.ruolan.living.action;

import com.ruolan.living.model.RoomInfo;
import com.ruolan.living.response.ResponseObj;
import com.ruolan.living.response.Error;
import com.ruolan.living.response.SqlManagerClose;
import com.ruolan.living.sqlutils.SqlManager;
import com.ruolan.living.tag.LocalOrRemoteTag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取直播列表界面
 */
public class LiveListAction implements IAction {

    private static final String RequestParamKey_PageIndex = "pageIndex";
    private static final int PageSize = 20;

    public void doAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {

        String pageIndex = req.getParameter(RequestParamKey_PageIndex);
        int page = -1;
        try {
            page = Integer.valueOf(pageIndex);
        } catch (Exception e) {

        }

        if (page < 0){
            ResponseObj responseObject = ResponseObj.getError(
                    Error.ERROR_CODE_ERROR_PARAM, Error.getErrorMsgException(Error.ERROR_CODE_ERROR_PARAM));
            ResponseObj.send(resp, responseObject);

            return;
        }

        Connection dbConn = null;
        Statement stm = null;

        try {

            //判断是否是本地测试环境  如果是本地测试环境 那么就连接本地的数据库  进行相关测试
            if (LocalOrRemoteTag.isLocal) {
                dbConn = SqlManager.getLocalConnection();
            } else {
                //如果是上线环境  那么久需要使用线上的环境  进行打war包
                dbConn = SqlManager.getRemoteConnection();
            }
            stm = dbConn.createStatement();
            
            //select * from roominfo  order by room_id desc LIMIT 0 , 20 
            String sqlStr = "SELECT * FROM `roominfo` order by room_id desc LIMIT " + page * PageSize + ","  + PageSize ;

            stm.execute(sqlStr);

            ResultSet resultSet = stm.getResultSet();
            ArrayList<RoomInfo> roomInfos = new ArrayList<>();
            if (resultSet != null && !resultSet.wasNull()) {

                while (resultSet.next()) {

                    RoomInfo roomInfo = new RoomInfo();
                    roomInfo.setRoomId(resultSet.getInt("room_id"));
                    roomInfo.setUserId(resultSet.getString("user_id"));
                    roomInfo.setUserName(resultSet.getString("user_name"));
                    roomInfo.setLiveCover(resultSet.getString("live_cover"));
                    roomInfo.setLiveTitle(resultSet.getString("live_title"));
                    roomInfo.setUserAvatar(resultSet.getString("user_avatar"));
                    roomInfo.setWatcherNum(resultSet.getInt("wather_num"));

                    roomInfos.add(roomInfo);
                }


                ResponseObj.send(resp, ResponseObj.getSuccess(roomInfos));

            }


        } catch (Exception e) {

            ResponseObj.send(resp, ResponseObj.getError(Error.ERROR_CODE_EXCEPTION, e.getMessage()));

        } finally {

            SqlManagerClose.resourceClose(dbConn, stm);
        }


    }


}
