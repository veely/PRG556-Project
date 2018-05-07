package com.senhotel.project;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.on.senecac.prg556.common.Control;
import ca.on.senecac.prg556.common.StringHelper;
import ca.on.senecac.prg556.senhotel.bean.Guest;

import com.senhotel.project.data.GuestDAOFactory;

public class WelcomeControl implements Control
{
	public String doLogic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			HttpSession session = request.getSession();
			Guest usession = (Guest)session.getAttribute("userSession");
			if (null == usession)
			{
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				if ("POST".equals(request.getMethod()) && StringHelper.isNotNullOrEmpty(username) && StringHelper.isNotNullOrEmpty(password))
				{
					Guest guest = GuestDAOFactory.getGuestDAO().authenticateGuest(username, password);
					if (guest != null)
					{
						session.setAttribute("userSession", guest);
						return "R:" + request.getContextPath() + "/"; // redirect to context root folder
					}
					else
					{
						request.setAttribute("invalid", true);
					}
				}
				return null;
			}
			return "R:" + request.getContextPath() + "/";
		}
		catch (SQLException e)
		{
			throw new ServletException(e);
		}
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() throws ServletException {
		
	}
}
