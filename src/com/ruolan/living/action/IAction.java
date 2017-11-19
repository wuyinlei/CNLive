package com.ruolan.living.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAction {
	
	
public void doAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException;

    ;
	
}
