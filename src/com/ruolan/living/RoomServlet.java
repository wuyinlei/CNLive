package com.ruolan.living;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
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

	private static final String Param_User_id = "userId";
	private static final String Param_User_avatar = "userAvatar";
	private static final String Param_User_name = "userName";
	private static final String Param_Live_cover = "liveCover";
	private static final String Param_Live_title = "liveTitle";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String userId = req.getParameter(Param_User_id);
		if (userId == null) {
			userId = "";
		}

		String userAvatar = req.getParameter(Param_User_avatar);
		if (userAvatar == null) {
			userAvatar = "";
		}
		String userName = req.getParameter(Param_User_name);
		if (userName == null) {
			userName = "";
		}
		String liveCover = req.getParameter(Param_Live_cover);
		if (liveCover == null) {
			liveCover = "";
		}

		String liveTitle = req.getParameter(Param_Live_title);
		if (liveTitle == null) {
			liveTitle = "";
		}

		// jdbc:mysql://localhost:3306/mc_userdb
		// 獲取到参数之后 保存到服务器mysql里面
		// String url = driverName + Host + ":" + Port + "/" + dbName;
		String driverName = "jdbc:mysql://";
		String host = "192.168.1.89";
		// String host = "192.168.0.1";
		String port = "30351"; // 30351
		String dbName = "dd8bca5e";// dd8bca5e
		final String charset = "?useUnicode=true&charactsetEncoding=utf-8";

		String url = driverName + host + ":" + port + "/" + dbName + charset;
		String user = "7f79db3a"; // 7f79db3a
		String password = "8477deb8"; // 8477deb8
		Connection dbConn = null;
		Statement stm = null;

		String driverUrl = "jdbc:mysql://localhost:3306/CN_LIVE?"
				+ "user=root&password=root&useUnicode=true&characterEncoding=UTF8";

		try {
			
			//判断是否是本地测试环境  如果是本地测试环境 那么就连接本地的数据库  进行相关测试   
			if (LocalOrRemoteTag.isLocal) {
				Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
				dbConn = DriverManager.getConnection(driverUrl);
				
			} else {
				//如果是上线环境  那么久需要使用线上的环境  进行打war包
				dbConn = DriverManager.getConnection(url, user, password);
				
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
				String sqlRoomStr = "SELECT `room_id` FROM `roominfo` WHERE `user_id` =" + userId;
				System.out.println(sqlRoomStr);
				stm.execute(sqlRoomStr);
				ResultSet resultSet = stm.getResultSet();
				if (resultSet != null && !resultSet.wasNull()) {
					int roomId = 0;
					while (resultSet.next()) {
						roomId = resultSet.getInt("room_id");
					}

					// 获取到room_id 发送结果

					ResponseObj.send(resp, ResponseObj.getSuccess(roomId));
					// writer.println("得到的房间id是:" + roomId);

				}
			}
		} catch (Exception e) {

			ResponseObj.send(resp, ResponseObj.getError(Error.ERROR_CODE_EXCEPTION, e.getMessage()));

		} finally {

			try {

				if (dbConn != null) {

					dbConn.close();
				}

				if (stm != null) {
					stm.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
