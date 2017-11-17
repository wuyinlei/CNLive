package com.ruolan.living.response;

import java.sql.Connection;
import java.sql.Statement;

public class SqlManagerClose {

    public static void resourceClose(Connection dbConn, Statement stm) {
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
