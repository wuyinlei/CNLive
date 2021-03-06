package com.ruolan.living.action;

import com.ruolan.living.model.RoomInfo;
import com.ruolan.living.response.ResponseObj;
import com.ruolan.living.response.SqlManagerClose;
import com.ruolan.living.sqlutils.SqlManager;
import com.ruolan.living.tag.LocalOrRemoteTag;
import com.ruolan.living.response.Error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateRoomAction implements IAction{


    private static final String Param_User_id = "userId";
    private static final String Param_User_avatar = "userAvatar";
    private static final String Param_User_name = "userName";
    private static final String Param_Live_cover = "liveCover";
    private static final String Param_Live_title = "liveTitle";

    public void doAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {


        String userId = req.getParameter(Param_User_id);
        if (userId == null) {
            userId = "100";
        }

        String userAvatar = req.getParameter(Param_User_avatar);
        if (userAvatar == null) {
            userAvatar = "121212";
        }
        String userName = req.getParameter(Param_User_name);
        if (userName == null) {
            userName = "1213";
        }
        String liveCover = req.getParameter(Param_Live_cover);
        if (liveCover == null) {
            liveCover = "312343";
        }

        String liveTitle = req.getParameter(Param_Live_title);
        if (liveTitle == null) {
            liveTitle = "21213";
        }

        // jdbc:mysql://localhost:3306/mc_userdb
        // 獲取到参数之后 保存到服务器mysql里面
//        // String url = driverName + Host + ":" + Port + "/" + dbName;
//        String driverName = "jdbc:mysql://";
//        String host = "192.168.1.89";
//        // String host = "192.168.0.1";
//        String port = "30351"; // 30351
//        String dbName = "dd8bca5e";// dd8bca5e
//        final String charset = "?useUnicode=true&charactsetEncoding=utf-8";
//
//        String url = driverName + host + ":" + port + "/" + dbName + charset;
//        String user = "7f79db3a"; // 7f79db3a
//        String password = "8477deb8"; // 8477deb8
        Connection dbConn = null;
        Statement stm = null;

//        //https://www.cnblogs.com/taoweiji/archive/2012/12/11/2812852.html  参考
//        String driverUrl = "jdbc:mysql://localhost:3306/CN_LIVE?"
//                + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";

        try {

            //判断是否是本地测试环境  如果是本地测试环境 那么就连接本地的数据库  进行相关测试
            if (LocalOrRemoteTag.isLocal) {
                dbConn = SqlManager.getLocalConnection();

            } else {
                //如果是上线环境  那么久需要使用线上的环境  进行打war包
                dbConn = SqlManager.getRemoteConnection();

            }

            stm = dbConn.createStatement();
            String sqlStr = "INSERT INTO `roominfo`(`room_id`, `user_id`, `user_avatar`,"
                    + " `live_cover`, `live_title`, `wather_num`, `user_name`) " + "VALUES (" + "0," + "\"" + userId
                    + "\"" + "," + "\"" + userAvatar + "\"" + "," + "\"" + liveCover + "\"" + "," + "\"" + liveTitle
                    + "\"" + "," + 0 + "," + "\"" + userName + "\"" + ")";
            System.out.println(sqlStr);
            stm.execute(sqlStr);
            int updateCount = stm.getUpdateCount();
            if (updateCount > 0) {
                // 执行成功
                String sqlRoomStr = "SELECT * FROM `roominfo` WHERE `user_id` =" + "'" + userId + "'";
                System.out.println(sqlRoomStr);
                stm.execute(sqlRoomStr);
                ResultSet resultSet = stm.getResultSet();
                if (resultSet != null && !resultSet.wasNull()) {
//					int roomId = 0;
                    RoomInfo roomInfo = new RoomInfo();
                    while (resultSet.next()) {
                        roomInfo.setRoomId(resultSet.getInt("room_id"));
                        roomInfo.setUserId(resultSet.getString("user_id"));
                        roomInfo.setUserName(resultSet.getString("user_name"));
                        roomInfo.setLiveCover(resultSet.getString("live_cover"));
                        roomInfo.setLiveTitle(resultSet.getString("live_title"));
                        roomInfo.setUserAvatar(resultSet.getString("user_avatar"));
                        roomInfo.setWatcherNum(resultSet.getInt("wather_num"));
                    }

                    // 获取到room_id 发送结果

                    ResponseObj.send(resp, ResponseObj.getSuccess(roomInfo));
                    // writer.println("得到的房间id是:" + roomId);

                }
            }
        } catch (Exception e) {

            ResponseObj.send(resp, ResponseObj.getError(Error.ERROR_CODE_EXCEPTION, e.getMessage()));

        } finally {

            SqlManagerClose.resourceClose(dbConn, stm);
        }
    }

}
