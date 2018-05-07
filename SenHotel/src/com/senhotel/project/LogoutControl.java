package com.senhotel.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.on.senecac.prg556.common.Control;
import ca.on.senecac.prg556.senhotel.bean.Guest;

public class LogoutControl implements Control
{
	public String doLogic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		if (!session.isNew()) 
		{
		    session.invalidate();
		    session = request.getSession();
		}
		return "R:" + request.getContextPath() + "/";
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
