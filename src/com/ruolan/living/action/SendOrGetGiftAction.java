package com.ruolan.living.action;

import com.ruolan.living.model.UserInfo;
import com.ruolan.living.response.ResponseObj;
import com.ruolan.living.response.SqlManagerClose;
import com.ruolan.living.response.Error;
import com.ruolan.living.sqlutils.SqlManager;
import com.ruolan.living.tag.LocalOrRemoteTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SendOrGetGiftAction {

    private static final String Param_User_id = "userId";
    private static final String Param_Gift_Exp = "exp";

    private boolean isSend = false;

    public void doAction(HttpServletRequest req, HttpServletResponse resp, boolean isSend) {


        this.isSend = isSend;

        Connection dbConn = null;
        Statement stm = null;

        String userId = req.getParameter(Param_User_id);
        if (userId == null || userId.isEmpty()) {
            //
            ResponseObj responseObject = ResponseObj.getError(
                    Error.ERROR_CODE_ERROR_PARAM, Error.getErrorMsgException(Error.ERROR_CODE_ERROR_PARAM));
            ResponseObj.send(resp, responseObject);

            return;
        }

        int exp = -1;
        try {

            exp = Integer.valueOf(req.getParameter(Param_Gift_Exp));

        } catch (Exception e) {

        }

        if (exp < 0) {
            ResponseObj responseObject = ResponseObj.getError(
                    Error.ERROR_CODE_ERROR_PARAM, Error.getErrorMsgException(Error.ERROR_CODE_ERROR_PARAM + exp));
            ResponseObj.send(resp, responseObject);

            return;
        }


        try {

            //判断是否是本地测试环境  如果是本地测试环境 那么就连接本地的数据库  进行相关测试
            if (LocalOrRemoteTag.isLocal) {
                dbConn = SqlManager.getLocalConnection();
            } else {
                //如果是上线环境  那么久需要使用线上的环境  进行打war包
                dbConn = SqlManager.getRemoteConnection();
            }
            stm = dbConn.createStatement();

            //首先查询用户
            String selectSql = "SELECT * FROM `userinfo` WHERE `user_id` = \"" + userId + "\"";
            stm.execute(selectSql);
            ResultSet userResult = stm.getResultSet();
            UserInfo userInfo = null;
            if (userResult == null || !userResult.next()) {
                //说明没有用户信息  插入新的用户信息
                boolean insertSuccess = insertNewUser(dbConn, userId);
                if (insertSuccess) {
                    userInfo = updateUserInfo(dbConn, userId, exp);
                }

            } else {
                //有用户信息  更新用户信息
                userInfo = updateUserInfo(dbConn, userId, exp);
            }

            //将用户信息返回  供app更新数据用
            ResponseObj responseObj = ResponseObj.getSuccess(userInfo);
            ResponseObj.send(resp, responseObj);


        } catch (Exception e) {

            ResponseObj.send(resp, ResponseObj.getError(Error.ERROR_CODE_EXCEPTION, e.getMessage()));

        } finally {

            SqlManagerClose.resourceClose(dbConn, stm);
        }


    }

    /**
     * 更新用户信息
     *
     * @param dbConn Connection
     * @param userId 用户id
     * @param exp    经验值
     * @return
     */
    private UserInfo updateUserInfo(Connection dbConn, String userId, int exp) {

        Statement stm = null;
        UserInfo userInfo = null;

        try {
            stm = dbConn.createStatement();
            String queryUserInfoSql = "SELECT * FROM `userinfo` WHERE `user_id` =\""
                    + userId + "\"";
            stm.execute(queryUserInfoSql);

            ResultSet resultSet = stm.getResultSet();
            if (resultSet != null && !resultSet.wasNull()) {
                //获取用户信息
                int dbGetNum = 0;
                int dbSendNum = 0;
                int dbExp = 0;
                int dbLevel = 0;
                if (resultSet.next()) {
                    dbGetNum = resultSet.getInt("get_nums");
                    dbSendNum = resultSet.getInt("send_nums");
                    dbExp = resultSet.getInt("exp");
                    dbLevel = resultSet.getInt("user_level");
                }

                //增加新的个数和经验
                if (isSend) {  //如果是发送礼物  那么就增加发送礼物的个数
                    dbExp += exp;
                    dbLevel = dbExp / 200 + 1;
                    dbSendNum++;
                } else {  //如果是接受离我  那么久增加礼物的个数  只增加得到礼物的个数  经验和等级都是不增加的
                    dbGetNum++;
                    dbLevel = dbExp / 200 + 1;
                }


                //更新数据库
                String updateSql = "UPDATE `userinfo` SET `user_level` = " + dbLevel + ",`send_nums` = " + dbSendNum
                        + ",`exp` = " + dbExp + " WHERE `user_id` = \"" + userId + "\"";


                stm.execute(updateSql);
                int updateCount = stm.getUpdateCount();
                if (updateCount > 0) {
                    //更新成功
                    userInfo = new UserInfo();
                    userInfo.setUserId(Integer.parseInt(userId));
                    userInfo.setExp(dbExp);
                    userInfo.setGetNums(dbGetNum);
                    userInfo.setSendNums(dbSendNum);
                    userInfo.setUserLevel(dbLevel);
                }

            }

            return userInfo;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean insertNewUser(Connection dbConn, String userId) {

        Statement stm = null;

        try {
            stm = dbConn.createStatement();

            String inserUserSql = "INSERT INTO `userinfo`(`user_id` , `user_level` , `send_nums` ,`get_nums` , `exp`) VALUES ("
                    + "\"" + userId + "\"," + "0 , 0, 0, 0" + ")";

            stm.execute(inserUserSql);
            int updateCount = stm.getUpdateCount();
            return (updateCount > 0);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stm != null) {

                try {
                    stm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
